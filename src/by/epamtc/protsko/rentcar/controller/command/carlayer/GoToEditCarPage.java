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
import java.util.List;

public class GoToEditCarPage implements Command {
    private static final Logger logger = LogManager.getLogger(GoToEditCarPage.class);
    private static final String SHOW_CAR_DATA_PAGE_MAPPING = "WEB-INF/jsp/editCarPage.jsp";
    private static final String PREV_REQ_URL_ATTRIBUTE_NAME = "previousRequestURL";
    private static final String CAR_ID_PARAMETER_NAME = "id";
    private static final String CAR_ATTRIBUTE_NAME = "car";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceFactory factory = ServiceFactory.getInstance();
        CarService carService = factory.getCarService();
        String currentRequestURL = RequestURL.getRequestURL(request);
        int carId = Integer.parseInt(request.getParameter(CAR_ID_PARAMETER_NAME));
        List<CarDTO> car = null;

        try {
            CarDTO carSearchCriteria = new CarDTO();
            carSearchCriteria.setId(carId);

            car = carService.findByCriteria(carSearchCriteria);
        } catch (CarServiceException e) {
            logger.error("Error while getting a car by criteria", e);
        }
        request.setAttribute(CAR_ATTRIBUTE_NAME, car);
        request.getSession().setAttribute(PREV_REQ_URL_ATTRIBUTE_NAME, currentRequestURL);
        request.getRequestDispatcher(SHOW_CAR_DATA_PAGE_MAPPING).forward(request, response);
    }
}
