package by.epamtc.protsko.rentcar.controller.command.orderlayer;

import by.epamtc.protsko.rentcar.controller.command.Command;
import by.epamtc.protsko.rentcar.controller.exception.ControllerException;
import by.epamtc.protsko.rentcar.service.OrderService;
import by.epamtc.protsko.rentcar.service.ServiceFactory;
import by.epamtc.protsko.rentcar.service.exception.OrderServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AcceptOrderCommand implements Command {
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final OrderService orderService = serviceFactory.getOrderService();
    private static final String BACK_TO_ALL_ORDERS_PAGE_MAPPING = "mainController?command=get_all_orders";
    private static final String ORDER_ID_PARAMETER_NAME = "id";
    private static final String ACCEPT_ERROR_ATTRIBUTE_NAME = "acceptedError";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        String acceptedError;

        final int orderId = Integer.parseInt(request.getParameter(ORDER_ID_PARAMETER_NAME));
        boolean isOrderAccepted = false;

        try {
            isOrderAccepted = orderService.acceptOrder(orderId);
        } catch (OrderServiceException e) {
            acceptedError = e.getMessage();
            request.setAttribute(ACCEPT_ERROR_ATTRIBUTE_NAME, acceptedError);
            request.setAttribute(ORDER_ID_PARAMETER_NAME, orderId);
            request.getRequestDispatcher(BACK_TO_ALL_ORDERS_PAGE_MAPPING).forward(request, response);
        }
        if (isOrderAccepted) {
            response.sendRedirect(BACK_TO_ALL_ORDERS_PAGE_MAPPING);
        }
    }
}
