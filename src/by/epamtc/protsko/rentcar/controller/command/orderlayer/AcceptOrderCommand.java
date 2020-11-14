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

public class AcceptOrderCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AcceptOrderCommand.class);
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final OrderService orderService = serviceFactory.getOrderService();
    private static final String BACK_TO_ALL_ORDERS_PAGE_MAPPING = "mainController?command=get_all_orders";
    private static final String ORDER_ID_PARAMETER_NAME = "id";
    private static final String ACCEPT_ERROR_ATTRIBUTE_NAME = "acceptedError";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String acceptedError;

        final int orderId = Integer.parseInt(request.getParameter(ORDER_ID_PARAMETER_NAME));
        boolean isOrderAccepted = false;

        try {
            isOrderAccepted = orderService.accept(orderId);
        } catch (OrderServiceException e) {
            logger.error("Error while accepting order", e);
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
