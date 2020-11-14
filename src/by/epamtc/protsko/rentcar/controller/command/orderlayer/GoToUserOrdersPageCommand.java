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

public class GoToUserOrdersPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GoToUserOrdersPageCommand.class);
    private static final String USER_ORDERS_PAGE_MAPPING = "WEB-INF/jsp/userOrdersPage.jsp";
    private static final String PREV_REQ_URL_ATTRIBUTE_NAME = "previousRequestURL";
    private static final String USER_ID_PARAMETER_NAME = "userId";
    private static final String NO_USER_ORDERS_ATTRIBUTE_NAME = "noEnyOrders";
    private static final String NO_USER_ORDERS_ATTRIBUTE_MESSAGE = "You haven't any orders";
    private static final String USER_ORDER_LIST_ATTRIBUTE_NAME = "userOrders";
    private static final String GET_USER_ORDERS_ERROR_ATTRIBUTE_NAME = "getOrdersError";
    private static final String GET_USER_ORDERS_ERROR_ATTRIBUTE_VALUE = "Ooops, something wrong";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentRequestMapping = getCurrentRequestURL(request);
        request.getSession().setAttribute(PREV_REQ_URL_ATTRIBUTE_NAME, currentRequestMapping);

        final ServiceFactory serviceFactory = ServiceFactory.getInstance();
        final OrderService orderService = serviceFactory.getOrderService();
        int userId = Integer.parseInt(request.getParameter(USER_ID_PARAMETER_NAME));
        List<OrderForShowDTO> userOrders;

        try {
            userOrders = orderService.findByUserId(userId);
            if (userOrders.isEmpty()) {
                request.setAttribute(NO_USER_ORDERS_ATTRIBUTE_NAME, NO_USER_ORDERS_ATTRIBUTE_MESSAGE);
                request.getRequestDispatcher(USER_ORDERS_PAGE_MAPPING).forward(request, response);
                return;
            }
            request.setAttribute(USER_ORDER_LIST_ATTRIBUTE_NAME, userOrders);
            request.getRequestDispatcher(USER_ORDERS_PAGE_MAPPING).forward(request, response);
        } catch (OrderServiceException e) {
            logger.error("Error while getting user's orders", e);
            request.setAttribute(GET_USER_ORDERS_ERROR_ATTRIBUTE_NAME, GET_USER_ORDERS_ERROR_ATTRIBUTE_VALUE);
            request.getRequestDispatcher(USER_ORDERS_PAGE_MAPPING).forward(request, response);
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
