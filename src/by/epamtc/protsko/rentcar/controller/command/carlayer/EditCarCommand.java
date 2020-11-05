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

public class EditCarCommand implements Command {
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final CarService carService = serviceFactory.getCarService();

    private static final String EDIT_CAR_DATA_MAPPING = "mainController?command=go_to_edit_car_page&id=";
    private static final String CAR_MANAGEMENT_PAGE_MAPPING = "mainController?command=go_to_car_management_page&param=findCar";
    private static final String EDITING_CAR_DATA_ERROR = "editingError";
    private static final String PARAMETER_ID = "carId";
    private static final String PARAMETER_BRAND = "carBrand";
    private static final String PARAMETER_MODEL = "carModel";
    private static final String PARAMETER_CLASS_TYPE = "carClass";
    private static final String PARAMETER_TRANSMISSION = "carTransmission";
    private static final String PARAMETER_PRICE = "pricePerDay";
    private static final String PARAMETER_VIN = "carVIN";
    private static final String PARAMETER_MANUFACTURE_DATE = "carManufactureDate";
    private static final String PARAMETER_ENGINE_POWER = "carEnginePower";
    private static final String PARAMETER_FUEL_CONSUMPTION = "carFuelConsumption";
    private static final String PARAMETER_IS_AVAILABLE = "isAvailableToRent";
    private static final String PARAMETER_IS_DELETED = "isCarDeleted";
    private static final String SUCCESSFUL_EDIT_DATA_ATTRIBUTE_NAME = "editCarResult";
    private static final String SUCCESSFUL_EDIT_DATA_ATTRIBUTE_VALUE = "Editing car was successfully";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        CarDTO carForEdit = null;
        int carId = Integer.parseInt(request.getParameter(PARAMETER_ID));
        boolean isEditCarDataSuccessfully = false;
        String editCarDataError;

        try {
            carForEdit = new CarDTO();

            carForEdit.setId(Integer.parseInt(request.getParameter(PARAMETER_ID)));
            carForEdit.setBrand(request.getParameter(PARAMETER_BRAND));
            carForEdit.setModel(request.getParameter(PARAMETER_MODEL));
            carForEdit.setClassType(request.getParameter(PARAMETER_CLASS_TYPE));
            carForEdit.setTransmissionType(request.getParameter(PARAMETER_TRANSMISSION));
            carForEdit.setPricePerDay(request.getParameter(PARAMETER_PRICE));
            carForEdit.setVin(request.getParameter(PARAMETER_VIN));
            carForEdit.setManufactureDate(request.getParameter(PARAMETER_MANUFACTURE_DATE));
            carForEdit.setEnginePower(request.getParameter(PARAMETER_ENGINE_POWER));
            carForEdit.setFuelConsumption(request.getParameter(PARAMETER_FUEL_CONSUMPTION));
            carForEdit.setIsAvailableToRent(request.getParameter(PARAMETER_IS_AVAILABLE));
            carForEdit.setIsDeleted(request.getParameter(PARAMETER_IS_DELETED));

            isEditCarDataSuccessfully = carService.editCarData(carForEdit);
        } catch (CarServiceException e) {
            editCarDataError = e.getMessage();
            request.setAttribute(EDITING_CAR_DATA_ERROR, editCarDataError);
            request.getRequestDispatcher(EDIT_CAR_DATA_MAPPING+carId).forward(request, response);
        }

        if (isEditCarDataSuccessfully) {
            request.getSession().setAttribute(SUCCESSFUL_EDIT_DATA_ATTRIBUTE_NAME, SUCCESSFUL_EDIT_DATA_ATTRIBUTE_VALUE);
            response.sendRedirect(CAR_MANAGEMENT_PAGE_MAPPING);
        }
    }
}
