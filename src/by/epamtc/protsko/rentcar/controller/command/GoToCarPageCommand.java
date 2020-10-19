package by.epamtc.protsko.rentcar.controller.command;

import by.epamtc.protsko.rentcar.bean.car.CarDTO;
import by.epamtc.protsko.rentcar.controller.command.util.RequestURL;
import by.epamtc.protsko.rentcar.controller.exception.ControllerException;
import by.epamtc.protsko.rentcar.service.CarService;
import by.epamtc.protsko.rentcar.service.ServiceFactory;
import by.epamtc.protsko.rentcar.service.exception.CarServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoToCarPageCommand implements Command {
    private static final String CAR_PAGE_MAPPING = "WEB-INF/jsp/carPage.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        String currentRequestURL = RequestURL.getRequestURL(request);
        request.getSession(true).setAttribute("previousRequestURL", currentRequestURL);
        ServiceFactory serviceFactory = ServiceFactory.getInstance();
        CarService carService = serviceFactory.getCarService();
        List<CarDTO> car = new ArrayList<>();
        CarDTO carDTO;

        try {
            carDTO = new CarDTO();
            carDTO.setId(Integer.parseInt(request.getParameter("carId")));
            car = carService.findCar(carDTO);

            request.setAttribute("car", car);
        } catch (CarServiceException e) {
            //logger
            e.printStackTrace();
        }
        request.getRequestDispatcher(CAR_PAGE_MAPPING).forward(request, response);
    }
}
