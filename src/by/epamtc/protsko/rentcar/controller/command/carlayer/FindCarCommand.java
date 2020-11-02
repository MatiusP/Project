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
import java.util.List;

public class FindCarCommand implements Command {
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final CarService carService = serviceFactory.getCarService();

    private static final String GET_FIND_RESULT_MAPPING = "mainController?command=go_to_car_management_page&param=findCar";
    private static final String GET_ALL_CARS_MAPPING = "mainController?command=go_to_car_management_page";
    private static final String ALL_CARS_ATTRIBUTE_NAME = "command";
    private static final String ALL_CARS_ATTRIBUTE_VALUE = "getAllCars";
    private static final String ID_PARAMETER_NAME = "carId";
    private static final String VIN_PARAMETER_NAME = "carVIN";
    private static final String MANUFACTURE_DATE_PARAMETER_NAME = "manufactureDate";
    private static final String ENGINE_POWER_PARAMETER_NAME = "enginePower";
    private static final String FUEL_CONSUMPTION_PARAMETER_NAME = "fuelConsumption";
    private static final String IS_AVAILABLE_PARAMETER_NAME = "isAvailable";
    private static final String IS_DELETED_PARAMETER_NAME = "isDeleted";
    private static final String PRICE_PARAMETER_NAME = "price";
    private static final String TRANSMISSION_PARAMETER_NAME = "transmission";
    private static final String CAR_CLASS_PARAMETER_NAME = "classType";
    private static final String CAR_MODEL_PARAMETER_NAME = "model";
    private static final String CAR_BRAND_PARAMETER_NAME = "brand";
    private static final String CAR_NOT_FOUND_ATTRIBUTE_NAME = "carNotFound";
    private static final String CAR_NOT_FOUND_ATTRIBUTE_MESSAGE = "Car not found";
    private static final String FOUND_CAR_LIST_ATTRIBUTE_NAME = "cars";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {

        final String carId = request.getParameter(ID_PARAMETER_NAME);
        final String carVIN = request.getParameter(VIN_PARAMETER_NAME);
        final String carManufactureDate = request.getParameter(MANUFACTURE_DATE_PARAMETER_NAME);
        final String carEnginePower = request.getParameter(ENGINE_POWER_PARAMETER_NAME);
        final String carFuelConsumption = request.getParameter(FUEL_CONSUMPTION_PARAMETER_NAME);
        final String isAvailable = request.getParameter(IS_AVAILABLE_PARAMETER_NAME);
        final String isDeleted = request.getParameter(IS_DELETED_PARAMETER_NAME);
        final String carPrice = request.getParameter(PRICE_PARAMETER_NAME);
        final String carTransmission = request.getParameter(TRANSMISSION_PARAMETER_NAME);
        final String carClass = request.getParameter(CAR_CLASS_PARAMETER_NAME);
        final String carModel = request.getParameter(CAR_MODEL_PARAMETER_NAME);
        final String carBrand = request.getParameter(CAR_BRAND_PARAMETER_NAME);

        List<CarDTO> carFoundList = null;
        CarDTO searchCarCriteria = new CarDTO();

        if (carId != null && !carId.isEmpty()) {
            searchCarCriteria.setId(Integer.parseInt(carId));
        }
        if (carVIN != null && !carVIN.isEmpty()) {
            searchCarCriteria.setVin(carVIN);
        }
        if (carManufactureDate != null && !carManufactureDate.isEmpty()) {
            searchCarCriteria.setManufactureDate(carManufactureDate);
        }
        if (carEnginePower != null && !carEnginePower.isEmpty()) {
            searchCarCriteria.setEnginePower(carEnginePower);
        }
        if (carFuelConsumption != null && !carFuelConsumption.isEmpty()) {
            searchCarCriteria.setFuelConsumption(carFuelConsumption);
        }
        if (isAvailable != null) {
            searchCarCriteria.setIsAvailableToRent(isAvailable);
        }
        if (isDeleted != null) {
            searchCarCriteria.setIsDeleted(isDeleted);
        }
        if (carPrice != null && !carPrice.isEmpty()) {
            searchCarCriteria.setPricePerDay(carPrice);
        }
        if (carTransmission != null) {
            searchCarCriteria.setTransmissionType(carTransmission);
        }
        if (carClass != null) {
            searchCarCriteria.setClassType(carClass);
        }
        if (carModel != null && !carModel.isEmpty()) {
            searchCarCriteria.setModel(carModel);
        }
        if (carBrand != null && !carBrand.isEmpty()) {
            searchCarCriteria.setBrand(carBrand);
        }
        try {
            carFoundList = carService.findCar(searchCarCriteria);

            if (carFoundList.isEmpty()) {
                request.setAttribute(CAR_NOT_FOUND_ATTRIBUTE_NAME, CAR_NOT_FOUND_ATTRIBUTE_MESSAGE);
            } else {
                request.setAttribute(FOUND_CAR_LIST_ATTRIBUTE_NAME, carFoundList);
            }
        } catch (CarServiceException e) {
            //logger
        }
        if (request.getSession().getAttribute(ALL_CARS_ATTRIBUTE_NAME) != null
                && request.getSession().getAttribute(ALL_CARS_ATTRIBUTE_NAME).equals(ALL_CARS_ATTRIBUTE_VALUE)) {
            request.getRequestDispatcher(GET_ALL_CARS_MAPPING).forward(request, response);
        } else {
            request.getRequestDispatcher(GET_FIND_RESULT_MAPPING).forward(request, response);
        }
    }
}

