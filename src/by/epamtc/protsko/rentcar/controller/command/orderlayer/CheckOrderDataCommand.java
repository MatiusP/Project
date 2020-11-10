package by.epamtc.protsko.rentcar.controller.command.orderlayer;

import by.epamtc.protsko.rentcar.dto.OrderForClientAcceptDTO;
import by.epamtc.protsko.rentcar.controller.command.Command;
import by.epamtc.protsko.rentcar.controller.exception.ControllerException;
import by.epamtc.protsko.rentcar.service.OrderService;
import by.epamtc.protsko.rentcar.service.ServiceFactory;
import by.epamtc.protsko.rentcar.service.exception.OrderServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;

public class CheckOrderDataCommand implements Command {
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final OrderService orderService = serviceFactory.getOrderService();
    private static final String BACK_TO_CAR_PAGE_MAPPING = "mainController?command=go_to_car_page&carId=";
    private static final String USER_ID_PARAMETER_NAME = "current_user";
    private static final String CAR_ID_PARAMETER_NAME = "current_car";
    private static final String START_RENT_PARAMETER_NAME = "start_rent";
    private static final String END_RENT_PARAMETER_NAME = "end_rent";
    private static final String USER_NOT_AUTH_ATTRIBUTE_NAME = "notLogin";
    private static final String USER_NOT_AUTH_ATTRIBUTE_VALUE = "You must be logged in";
    private static final String INVALID_PERIOD_ATTRIBUTE_NAME = "invalidPeriod";
    private static final String INVALID_PERIOD_ATTRIBUTE_VALUE = "Invalid period. Check your entered data";
    private static final String CAR_NOT_AVAILABLE_ATTRIBUTE_NAME = "carNotAvailable";
    private static final String CAR_NOT_AVAILABLE_ATTRIBUTE_MESSAGE = "Car not available for this dates";
    private static final String ORDER_FOR_ACCEPT_ATTRIBUTE_NAME = "orderForAccept";
    private static final String CAR_ID_ATTRIBUTE_NAME = "currentCarId";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        final int carId = Integer.parseInt(request.getParameter(CAR_ID_PARAMETER_NAME));
        final LocalDate orderDate = LocalDate.now();
        final LocalDate startRent = LocalDate.parse(request.getParameter(START_RENT_PARAMETER_NAME));
        final LocalDate endRent = LocalDate.parse(request.getParameter(END_RENT_PARAMETER_NAME));
        HttpSession session = request.getSession();

        if (request.getParameter(USER_ID_PARAMETER_NAME).isEmpty()) {
            session.setAttribute(USER_NOT_AUTH_ATTRIBUTE_NAME, USER_NOT_AUTH_ATTRIBUTE_VALUE);
            response.sendRedirect(BACK_TO_CAR_PAGE_MAPPING + carId);
        } else if (orderDate.isAfter(startRent) || startRent.isAfter(endRent)) {
            session.setAttribute(INVALID_PERIOD_ATTRIBUTE_NAME, INVALID_PERIOD_ATTRIBUTE_VALUE);
            response.sendRedirect(BACK_TO_CAR_PAGE_MAPPING + carId);
        } else {
            try {
                OrderForClientAcceptDTO orderForAccept = orderService.createOrderForClientAccept(carId, startRent, endRent);
                request.setAttribute(ORDER_FOR_ACCEPT_ATTRIBUTE_NAME, orderForAccept);
                request.setAttribute(CAR_ID_ATTRIBUTE_NAME, carId);
                request.getRequestDispatcher(BACK_TO_CAR_PAGE_MAPPING + carId).forward(request, response);
            } catch (OrderServiceException e) {
                session.setAttribute(CAR_NOT_AVAILABLE_ATTRIBUTE_NAME, CAR_NOT_AVAILABLE_ATTRIBUTE_MESSAGE);
                response.sendRedirect(BACK_TO_CAR_PAGE_MAPPING + carId);
            }
        }
    }
}
