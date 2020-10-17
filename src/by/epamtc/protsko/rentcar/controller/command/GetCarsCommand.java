package by.epamtc.protsko.rentcar.controller.command;

import by.epamtc.protsko.rentcar.bean.car.CarDTO;
import by.epamtc.protsko.rentcar.service.CarService;
import by.epamtc.protsko.rentcar.service.ServiceFactory;
import by.epamtc.protsko.rentcar.service.exception.CarServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetCarsCommand implements Command {
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final CarService carService = serviceFactory.getCarService();
    private static final String SHOW_ALL_CARS_PAGE = "WEB-INF/jsp/ourCarsPage.jsp";
    private static final String CAR_CLASS_PARAMETER = "class";
    private static final String ECONOMY_CLASS_NAME = "ECONOMY";
    private static final String MIDDLE_CLASS_NAME = "MIDDLE";
    private static final String PREMIUM_CLASS_NAME = "PREMIUM";
    private static final String NO_CARS_FOUND_MESSAGE = "No_eny_cars_found";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<CarDTO> carsTmpList;
        List<CarDTO> cars = new ArrayList<>();

        try {
            carsTmpList = carService.getAllCars();

            if (request.getParameter(CAR_CLASS_PARAMETER).equalsIgnoreCase(ECONOMY_CLASS_NAME)) {
                for (CarDTO car : carsTmpList) {
                    if (car.getClassType().equals(ECONOMY_CLASS_NAME)) {
                        cars.add(car);
                    }
                }
            } else if (request.getParameter(CAR_CLASS_PARAMETER).equalsIgnoreCase(MIDDLE_CLASS_NAME)) {
                for (CarDTO car : carsTmpList) {
                    if (car.getClassType().equals(MIDDLE_CLASS_NAME)) {
                        cars.add(car);
                    }
                }
            } else if (request.getParameter(CAR_CLASS_PARAMETER).equalsIgnoreCase(PREMIUM_CLASS_NAME)) {
                for (CarDTO car : carsTmpList) {
                    if (car.getClassType().equals(PREMIUM_CLASS_NAME)) {
                        cars.add(car);
                    }
                }
            } else {
                cars = carsTmpList;
            }
            request.setAttribute("cars", cars);
        } catch (CarServiceException e) {
            request.setAttribute(NO_CARS_FOUND_MESSAGE, e);
        }
        request.getRequestDispatcher(SHOW_ALL_CARS_PAGE).forward(request, response);
    }
}
