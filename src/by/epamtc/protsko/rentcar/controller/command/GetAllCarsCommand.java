package by.epamtc.protsko.rentcar.controller.command;

import by.epamtc.protsko.rentcar.bean.car.CarDTO;
import by.epamtc.protsko.rentcar.controller.exception.ControllerException;
import by.epamtc.protsko.rentcar.service.CarService;
import by.epamtc.protsko.rentcar.service.ServiceFactory;
import by.epamtc.protsko.rentcar.service.exception.CarServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetAllCarsCommand implements Command {
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final CarService carService = serviceFactory.getCarService();
    private static final String SHOW_ALL_CARS_PAGE = "WEB-INF/jsp/ourCarsPage.jsp";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        List<CarDTO> cars;

        try {
            cars = carService.getAllCars();
            request.setAttribute("cars", cars);
        } catch (CarServiceException e) {
            request.setAttribute("No_eny_cars_found", e);
        }

        request.getRequestDispatcher(SHOW_ALL_CARS_PAGE).forward(request, response);
    }
}
