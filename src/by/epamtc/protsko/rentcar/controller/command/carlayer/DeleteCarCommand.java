package by.epamtc.protsko.rentcar.controller.command.carlayer;

import by.epamtc.protsko.rentcar.controller.command.Command;
import by.epamtc.protsko.rentcar.service.CarService;
import by.epamtc.protsko.rentcar.service.ServiceFactory;
import by.epamtc.protsko.rentcar.service.exception.CarServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class DeleteCarCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DeleteCarCommand.class);
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final CarService carService = serviceFactory.getCarService();
    private static final String DELETE_CAR_RESULT = "deleteCarResult";
    private static final String DELETED_CAR_SUCCESSFULLY = "Successfully deleted";
    private static final String DELETED_CAR_ERROR = "Deleted error";
    private static final String SHOW_ALL_CARS_MAPPING = "mainController?command=go_to_car_management_page&param=findCar";
    private static final String DELETE_CAR_ID_PARAMETER_NAME = "deleteCarId";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session;
        boolean isDeleteCarSuccessfully = false;

        try {
            session = request.getSession();
            int carId = Integer.parseInt(request.getParameter(DELETE_CAR_ID_PARAMETER_NAME));
            isDeleteCarSuccessfully = carService.delete(carId);

            if (isDeleteCarSuccessfully) {
                session.setAttribute(DELETE_CAR_RESULT, DELETED_CAR_SUCCESSFULLY);
                response.sendRedirect(SHOW_ALL_CARS_MAPPING);
            } else {
                session.setAttribute(DELETE_CAR_RESULT, DELETED_CAR_ERROR);
                response.sendRedirect(SHOW_ALL_CARS_MAPPING);
            }
        } catch (CarServiceException e) {
            logger.error("Error while deleting car", e);
        }
    }
}
