package by.epamtc.protsko.rentcar.controller.command.orderlayer;

import by.epamtc.protsko.rentcar.dto.FinalRentActDTO;
import by.epamtc.protsko.rentcar.controller.command.Command;
import by.epamtc.protsko.rentcar.service.*;
import by.epamtc.protsko.rentcar.service.exception.FinalRentActServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class GetFinalRentActCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GetFinalRentActCommand.class);
    private final ServiceFactory factory = ServiceFactory.getInstance();
    private final FinalActService finalRentActService = factory.getFinalActService();

    private static final String PREV_REQ_URL_ATTRIBUTE_NAME = "previousRequestURL";
    private static final String BACK_TO_ALL_ORDERS_PAGE_MAPPING = "mainController?command=get_all_orders";
    private static final String FINAL_RENT_ACT_PAGE_MAPPING = "WEB-INF/jsp/finalRentActPage.jsp";
    private static final String ORDER_ID_PARAMETER_NAME = "id";
    private static final String GET_FINAL_ACT_ERROR_ATTRIBUTE_NAME = "getFinalActError";
    private static final String FINAL_RENT_ACT_ATTRIBUTE_NAME = "finalRentAct";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentRequestMapping = getCurrentRequestURL(request);
        request.getSession().setAttribute(PREV_REQ_URL_ATTRIBUTE_NAME, currentRequestMapping);
        final int orderId = Integer.parseInt(request.getParameter(ORDER_ID_PARAMETER_NAME));
        String finalActError;
        FinalRentActDTO finalRentActDTO = null;

        try {
            finalRentActDTO = finalRentActService.find(orderId);
        } catch (FinalRentActServiceException e) {
            logger.error("Error while getting final rent act", e);
            finalActError = e.getMessage();
            request.setAttribute(GET_FINAL_ACT_ERROR_ATTRIBUTE_NAME, finalActError);
            request.getRequestDispatcher(BACK_TO_ALL_ORDERS_PAGE_MAPPING).forward(request, response);
        }
        request.setAttribute(FINAL_RENT_ACT_ATTRIBUTE_NAME, finalRentActDTO);
        request.getRequestDispatcher(FINAL_RENT_ACT_PAGE_MAPPING).forward(request, response);
    }

    private String getCurrentRequestURL(HttpServletRequest request) {
        Enumeration<String> requestParameterNames = request.getParameterNames();
        StringBuilder requestParameters = new StringBuilder();

        while (requestParameterNames.hasMoreElements()) {
            String requestParameter = requestParameterNames.nextElement();
            requestParameters.append(requestParameter)
                    .append("=")
                    .append(request.getParameter(requestParameter));
            if (requestParameterNames.hasMoreElements()) {
                requestParameters.append("&");
            }
        }
        return request.getRequestURL().append("?").append(requestParameters).toString();
    }
}
