package by.epamtc.protsko.rentcar.controller.command.orderlayer;

import by.epamtc.protsko.rentcar.dto.OrderDTO;
import by.epamtc.protsko.rentcar.controller.command.Command;
import by.epamtc.protsko.rentcar.controller.exception.ControllerException;
import by.epamtc.protsko.rentcar.service.OrderService;
import by.epamtc.protsko.rentcar.service.ServiceFactory;
import by.epamtc.protsko.rentcar.service.exception.OrderServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreateOrderCommand implements Command {
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final OrderService orderService = serviceFactory.getOrderService();
    private static final String BACK_TO_CAR_PAGE_MAPPING = "mainController?command=go_to_car_page&carId=";
    private static final String START_RENT_DATE_PARAMETER_NAME = "startRentByOrder";
    private static final String END_RENT_DATE_PARAMETER_NAME = "endRentByOrder";
    private static final String TOTAL_PRICE_PARAMETER_NAME = "totalPrice";
    private static final String USER_ID_PARAMETER_NAME = "user_id";
    private static final String CAR_ID_PARAMETER_NAME = "car_id";
    private static final String ADDED_ERROR_ATTRIBUTE_NAME = "added error";
    private static final String ADDED_RESULT_ATTRIBUTE_NAME = "added_result_ok";
    private static final String ADDED_RESULT_MESSAGE = "Create new order was successfully";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        boolean isAddedOrderSuccessfully = false;
        OrderDTO newOrder = null;
        String addedError;

        try {
            newOrder = new OrderDTO();

            newOrder.setOrderDate(LocalDateTime.now());
            newOrder.setStartRent(LocalDate.parse(request.getParameter(START_RENT_DATE_PARAMETER_NAME)));
            newOrder.setEndRent(LocalDate.parse(request.getParameter(END_RENT_DATE_PARAMETER_NAME)));
            newOrder.setTotalPrice(request.getParameter(TOTAL_PRICE_PARAMETER_NAME));
            newOrder.setUserId(request.getParameter(USER_ID_PARAMETER_NAME));
            newOrder.setCarId(request.getParameter(CAR_ID_PARAMETER_NAME));

            isAddedOrderSuccessfully = orderService.add(newOrder);
        } catch (OrderServiceException e) {
            //logger
            addedError = e.getMessage();
            request.setAttribute(ADDED_ERROR_ATTRIBUTE_NAME, addedError);
            request.getRequestDispatcher(BACK_TO_CAR_PAGE_MAPPING).forward(request, response);
        }
        if (isAddedOrderSuccessfully) {
            request.getSession().setAttribute(ADDED_RESULT_ATTRIBUTE_NAME, ADDED_RESULT_MESSAGE);
            response.sendRedirect(BACK_TO_CAR_PAGE_MAPPING + request.getParameter(CAR_ID_PARAMETER_NAME));
        }
    }
}
