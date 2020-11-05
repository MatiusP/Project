package by.epamtc.protsko.rentcar.controller.command.carlayer;

import by.epamtc.protsko.rentcar.controller.command.Command;
import by.epamtc.protsko.rentcar.controller.exception.ControllerException;
import by.epamtc.protsko.rentcar.service.CarService;
import by.epamtc.protsko.rentcar.service.ServiceFactory;
import by.epamtc.protsko.rentcar.service.exception.CarServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class DeleteCarCommand implements Command {
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final CarService carService = serviceFactory.getCarService();
    private static final String DELETE_CAR_RESULT = "deleteCarResult";
    private static final String DELETED_CAR_SUCCESSFULLY = "Successfully deleted";
    private static final String DELETED_CAR_ERROR = "Deleted error";
    private static final String SHOW_ALL_CARS_MAPPING = "mainController?command=go_to_car_management_page&param=findCar";
    private static final String DELETE_CAR_ID_PARAMETER_NAME = "deleteCarId";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        HttpSession session = request.getSession();
        boolean isDeleteCarSuccessfully = false;

        try {
            int carId = Integer.parseInt(request.getParameter(DELETE_CAR_ID_PARAMETER_NAME));
            isDeleteCarSuccessfully = carService.deleteCarFromSystem(carId);

            if (isDeleteCarSuccessfully) {
                session.setAttribute(DELETE_CAR_RESULT, DELETED_CAR_SUCCESSFULLY);
                response.sendRedirect(SHOW_ALL_CARS_MAPPING);
            } else {
                session.setAttribute(DELETE_CAR_RESULT, DELETED_CAR_ERROR);
                response.sendRedirect(SHOW_ALL_CARS_MAPPING);
            }
        } catch (CarServiceException e) {
            //logger

        }
    }
}
