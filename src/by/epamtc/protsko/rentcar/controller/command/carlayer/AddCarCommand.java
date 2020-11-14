package by.epamtc.protsko.rentcar.controller.command.carlayer;

import by.epamtc.protsko.rentcar.dto.CarDTO;
import by.epamtc.protsko.rentcar.controller.command.Command;
import by.epamtc.protsko.rentcar.service.CarService;
import by.epamtc.protsko.rentcar.service.ServiceFactory;
import by.epamtc.protsko.rentcar.service.exception.CarServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddCarCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AddCarCommand.class);
    private static final String ADD_CAR_PAGE_MAPPING = "mainController?command=go_to_add_car_page";
    private static final String CAR_MANAGEMENT_PAGE_MAPPING = "mainController?command=go_to_car_management_page";
    private static final String CAR_VAN_PARAMETER_NAME = "carVIN";
    private static final String MANUFACTURE_DATE_PARAMETER_NAME = "manufactureDate";
    private static final String ENGINE_POWER_PARAMETER_NAME = "enginePower";
    private static final String FUEL_CONSUMPTION_PARAMETER_NAME = "fuelConsumption";
    private static final String AVAILABLE_TO_RENT_PARAMETER_NAME = "availableToRent";
    private static final String TRANSMISSION_PARAMETER_NAME = "transmissionType";
    private static final String CAR_CLASS_PARAMETER_NAME = "carClassType";
    private static final String MODEL_PARAMETER_NAME = "carModel";
    private static final String BRAND_PARAMETER_NAME = "carBrand";
    private static final String PRICE_PARAMETER_NAME = "pricePerDay";
    private static final String ADDED_ERROR_ATTRIBUTE_NAME = "added error";
    private static final String ADDED_RESULT_ATTRIBUTE_NAME = "added_result";
    private static final String ADDED_RESULT_MESSAGE = "Add new car was successfully";

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final CarService carService = serviceFactory.getCarService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CarDTO newCar = null;
        boolean isAddedCarSuccessfully = false;
        String addedError;

        try {
            newCar = new CarDTO();

            newCar.setVin(request.getParameter(CAR_VAN_PARAMETER_NAME));
            newCar.setManufactureDate(request.getParameter(MANUFACTURE_DATE_PARAMETER_NAME));
            newCar.setEnginePower(request.getParameter(ENGINE_POWER_PARAMETER_NAME));
            newCar.setFuelConsumption(request.getParameter(FUEL_CONSUMPTION_PARAMETER_NAME));
            newCar.setIsAvailableToRent(request.getParameter(AVAILABLE_TO_RENT_PARAMETER_NAME));
            newCar.setTransmissionType(request.getParameter(TRANSMISSION_PARAMETER_NAME));
            newCar.setClassType(request.getParameter(CAR_CLASS_PARAMETER_NAME));
            newCar.setModel(request.getParameter(MODEL_PARAMETER_NAME));
            newCar.setBrand(request.getParameter(BRAND_PARAMETER_NAME));
            newCar.setPricePerDay(request.getParameter(PRICE_PARAMETER_NAME));

//            List<String> photos;              //TODO реализовать
//
//            for (Part part : request.getParts()) {
//                String fileName = URLDecoder.decode(part.getSubmittedFileName(), "UTF-8");
//                String path = request.getSession().getServletContext().getInitParameter("uploadFilesPath");
//                File file = new File(path + File.separator + fileName);
//                file.getParentFile().mkdirs();
//                InputStream inputStream = part.getInputStream();
//                java.nio.file.Files.copy(inputStream, file.toPath());
//                inputStream.close();
//            }


            isAddedCarSuccessfully = carService.add(newCar);
        } catch (CarServiceException e) {
            logger.error("Error while adding a new car", e);
            addedError = e.getMessage();
            request.setAttribute(ADDED_ERROR_ATTRIBUTE_NAME, addedError);
            request.getRequestDispatcher(ADD_CAR_PAGE_MAPPING).forward(request, response);
        }

        if (isAddedCarSuccessfully) {
            request.getSession().setAttribute(ADDED_RESULT_ATTRIBUTE_NAME, ADDED_RESULT_MESSAGE);
            response.sendRedirect(CAR_MANAGEMENT_PAGE_MAPPING);
        }
    }
}
