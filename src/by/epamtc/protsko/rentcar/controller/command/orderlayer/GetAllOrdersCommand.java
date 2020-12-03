package by.epamtc.protsko.rentcar.controller.command.orderlayer;

import by.epamtc.protsko.rentcar.dto.OrderForShowDTO;
import by.epamtc.protsko.rentcar.controller.command.Command;
import by.epamtc.protsko.rentcar.service.OrderService;
import by.epamtc.protsko.rentcar.service.ServiceFactory;
import by.epamtc.protsko.rentcar.service.exception.OrderServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

public class GetAllOrdersCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GetAllOrdersCommand.class);
    private static final String SHOW_ALL_ORDERS_MAPPING = "WEB-INF/jsp/orderManagementPage.jsp";
    private static final String PREV_REQ_URL_ATTRIBUTE_NAME = "previousRequestURL";
    private static final String ORDER_LIST_ATTRIBUTE_NAME = "orderList";
    private static final String NO_ORDERS_ATTRIBUTE_NAME = "noAnyOrders";
    private static final String NO_ORDERS_ATTRIBUTE_VALUE = "No any orders in database";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentRequestMapping = getCurrentRequestURL(request);
        request.getSession().setAttribute(PREV_REQ_URL_ATTRIBUTE_NAME, currentRequestMapping);

        final ServiceFactory factory = ServiceFactory.getInstance();
        final OrderService orderService = factory.getOrderService();
        List<OrderForShowDTO> orderList;

        try {
            orderList = orderService.findAll();
            request.setAttribute(ORDER_LIST_ATTRIBUTE_NAME, orderList);
            request.getRequestDispatcher(SHOW_ALL_ORDERS_MAPPING).forward(request, response);
            return;
        } catch (OrderServiceException e) {
            logger.error("Error while getting all orders", e);
            request.getSession().setAttribute(NO_ORDERS_ATTRIBUTE_NAME, NO_ORDERS_ATTRIBUTE_VALUE);
            request.getRequestDispatcher(SHOW_ALL_ORDERS_MAPPING).forward(request, response);
//            response.sendRedirect(SHOW_ALL_ORDERS_MAPPING);
        }
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
