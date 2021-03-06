package by.epamtc.protsko.rentcar.controller.command.orderlayer;

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

public class CancelOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger(CancelOrderCommand.class);
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final OrderService orderService = serviceFactory.getOrderService();
    private static final String BACK_TO_ALL_ORDERS_PAGE_MAPPING = "mainController?command=get_all_orders";
    private static final String BACK_TO_USER_ORDERS_PAGE_MAPPING = "mainController?command=go_to_user_orders_page&userId=";
    private static final String USER_ROLE_PARAMETER_NAME = "userRole";
    private static final String ORDER_ID_PARAMETER_NAME = "id";
    private static final String USER_ID_PARAMETER_NAME = "userId";
    private static final String CANCEL_ERROR_ATTRIBUTE_NAME = "cancelError";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final int orderId = Integer.parseInt(request.getParameter(ORDER_ID_PARAMETER_NAME));
        final String userId = request.getParameter(USER_ID_PARAMETER_NAME);
        final String userRole = request.getParameter(USER_ROLE_PARAMETER_NAME);

        boolean isOrderCanceled = false;
        String canceledError;

        try {
            isOrderCanceled = orderService.cancel(orderId);
        } catch (OrderServiceException e) {
            logger.error("Error while canceling order", e);
            canceledError = e.getMessage();
            request.setAttribute(CANCEL_ERROR_ATTRIBUTE_NAME, canceledError);
            request.setAttribute(ORDER_ID_PARAMETER_NAME, orderId);
            request.getRequestDispatcher(BACK_TO_ALL_ORDERS_PAGE_MAPPING).forward(request, response);
        }
        if (isOrderCanceled) {
            if (userRole != null && userId != null) {
                response.sendRedirect(BACK_TO_USER_ORDERS_PAGE_MAPPING + Integer.parseInt(userId));
                return;
            }
            response.sendRedirect(BACK_TO_ALL_ORDERS_PAGE_MAPPING);
        }
    }
}
