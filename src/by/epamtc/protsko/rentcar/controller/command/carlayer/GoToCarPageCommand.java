package by.epamtc.protsko.rentcar.controller.command.carlayer;

import by.epamtc.protsko.rentcar.dto.CarDTO;
import by.epamtc.protsko.rentcar.controller.command.Command;
import by.epamtc.protsko.rentcar.controller.command.util.RequestURL;
import by.epamtc.protsko.rentcar.service.CarService;
import by.epamtc.protsko.rentcar.service.ServiceFactory;
import by.epamtc.protsko.rentcar.service.exception.CarServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoToCarPageCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GoToCarPageCommand.class);
    private static final String CAR_PAGE_MAPPING = "WEB-INF/jsp/carPage.jsp";
    private static final String PREV_REQ_URL_ATTRIBUTE_NAME = "previousRequestURL";
    private static final String CAR_ID_PARAMETER_NAME = "carId";
    private static final String CAR_ATTRIBUTE_NAME = "car";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentRequestURL = RequestURL.getRequestURL(request);
        request.getSession(true).setAttribute(PREV_REQ_URL_ATTRIBUTE_NAME, currentRequestURL);

        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        CarService carService = serviceFactory.getCarService();

        List<CarDTO> car = new ArrayList<>();
        CarDTO carDTO;

        try {
            carDTO = new CarDTO();
            carDTO.setId(Integer.parseInt(request.getParameter(CAR_ID_PARAMETER_NAME)));
            car = carService.findByCriteria(carDTO);

            request.setAttribute(CAR_ATTRIBUTE_NAME, car);
        } catch (CarServiceException e) {
            logger.error("Error while finding a car by criteria", e);
        }
        request.getRequestDispatcher(CAR_PAGE_MAPPING).forward(request, response);
    }
}
