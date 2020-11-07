package by.epamtc.protsko.rentcar.controller.command.orderlayer;

import by.epamtc.protsko.rentcar.bean.order.FinalRentActDTO;
import by.epamtc.protsko.rentcar.controller.command.Command;
import by.epamtc.protsko.rentcar.controller.exception.ControllerException;
import by.epamtc.protsko.rentcar.service.OrderService;
import by.epamtc.protsko.rentcar.service.ServiceFactory;
import by.epamtc.protsko.rentcar.service.exception.OrderServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class GetFinalRentActCommand implements Command {
    private final ServiceFactory factory = ServiceFactory.getInstance();
    private final OrderService orderService = factory.getOrderService();
    private static final String PREV_REQ_URL_ATTRIBUTE_NAME = "previousRequestURL";
    private static final String BACK_TO_ALL_ORDERS_PAGE_MAPPING = "mainController?command=get_all_orders";
    private static final String FINAL_RENT_ACT_PAGE_MAPPING = "WEB-INF/jsp/finalRentActPage.jsp";
    private static final String ORDER_ID_PARAMETER_NAME = "id";
    private static final String GET_FINAL_ACT_ERROR_ATTRIBUTE_NAME = "getFinalActError";
    private static final String FINAL_RENT_ACT_ATTRIBUTE_NAME = "finalRentAct";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        String currentRequestMapping = getCurrentRequestURL(request);
        request.getSession().setAttribute(PREV_REQ_URL_ATTRIBUTE_NAME, currentRequestMapping);
        final int orderId = Integer.parseInt(request.getParameter(ORDER_ID_PARAMETER_NAME));
        String finalActError;
        FinalRentActDTO finalRentActDTO = null;

        try {
            finalRentActDTO = orderService.getFinalRentAct(orderId);
        } catch (OrderServiceException e) {
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
