package by.epamtc.protsko.rentcar.controller.command.carlayer;

import by.epamtc.protsko.rentcar.bean.car.CarDTO;
import by.epamtc.protsko.rentcar.controller.command.Command;
import by.epamtc.protsko.rentcar.controller.exception.ControllerException;
import by.epamtc.protsko.rentcar.service.CarService;
import by.epamtc.protsko.rentcar.service.ServiceFactory;
import by.epamtc.protsko.rentcar.service.exception.CarServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddCarCommand implements Command {
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final CarService carService = serviceFactory.getCarService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        CarDTO newCar = null;
        boolean isAddedCarSuccessfully = false;
        String addedError;

        try {
            newCar = new CarDTO();

            newCar.setVin(request.getParameter("carVIN"));
            newCar.setManufactureDate(request.getParameter("manufactureDate"));
            newCar.setEnginePower(request.getParameter("enginePower"));
            newCar.setFuelConsumption(request.getParameter("fuelConsumption"));
            newCar.setIsAvailableToRent(request.getParameter("availableToRent"));
            newCar.setTransmissionType(request.getParameter("transmissionType"));
            newCar.setClassType(request.getParameter("carClassType"));
            newCar.setModel(request.getParameter("carModel"));
            newCar.setBrand(request.getParameter("carBrand"));
            newCar.setPricePerDay(request.getParameter("pricePerDay"));

            request.getParameter("photo");

            //private List<String> photos;              //TODO реализовать

            isAddedCarSuccessfully = carService.addCar(newCar);
        } catch (CarServiceException e) {
            addedError = e.getMessage();
            request.setAttribute("added error", addedError);
            request.getRequestDispatcher("mainController?command=go_to_main_page").forward(request, response);
        }

        if (isAddedCarSuccessfully) {
            request.getSession().setAttribute("added_result", "All OK");
            response.sendRedirect("mainController?command=go_to_main_page");
        }
    }
}
