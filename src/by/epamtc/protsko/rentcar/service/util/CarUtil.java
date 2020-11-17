package by.epamtc.protsko.rentcar.service.util;

import by.epamtc.protsko.rentcar.dto.CarDTO;
import by.epamtc.protsko.rentcar.service.exception.CarServiceException;
import by.epamtc.protsko.rentcar.service.validator.car.CarParameterType;
import by.epamtc.protsko.rentcar.service.validator.car.CarValidatorFactory;

/**
 * The {@code CarUtil} class provides methods for checking the input
 * car data
 *
 * @author Matvey Protsko
 */
public class CarUtil {
    private static final String VIN_INVALID_MESSAGE = "VIN value invalid";
    private static final String MANUFACTURE_DATE_INVALID_MESSAGE = "Manufacture date value invalid";
    private static final String ENGINE_POWER_INVALID_MESSAGE = "Engine power value invalid";
    private static final String FUEL_CONSUMPTION_INVALID_MESSAGE = "Fuel consumption value invalid";
    private static final String TRANSMISSION_TYPE_INVALID_MESSAGE = "Transmission type invalid";
    private static final String CAR_CLASS_TYPE_INVALID_MESSAGE = "Car class invalid";
    private static final String CAR_BRAND_INVALID_MESSAGE = "Car brand value invalid";
    private static final String PRICE_INVALID_MESSAGE = "Price per day value invalid";

    private CarUtil() {
    }

    /**
     * The method {@code isEnteredDataValid} validation the input
     * user data
     *
     * @param car {@link CarDTO} object which contains entered car's data values.
     * @return true if entered car's data values is valid. If entered car's data values
     * is not valid, this method throws CarServiceException
     * @throws CarServiceException if entered car's data values is not valid.
     */
    public static boolean isEnteredDataValid(CarDTO car) throws CarServiceException {
        final CarValidatorFactory factory = new CarValidatorFactory();
        final String carVin = car.getVin();
        final String carManufactureDate = car.getManufactureDate();
        final String carEnginePower = car.getEnginePower();
        final String carFuelConsumption = car.getFuelConsumption();
        final String carTransmission = car.getTransmissionType();
        final String carClass = car.getClassType();
        final String carBrand = car.getBrand();
        final String carPricePerDay = car.getPricePerDay();

        boolean isVinValid = factory.getValidator(CarParameterType.VIN).isValid(carVin);
        boolean isManufDateValid = factory.getValidator(CarParameterType.MANUFACTURE_DATE).isValid(carManufactureDate);
        boolean isEnginePowerValid = factory.getValidator(CarParameterType.ENGINE_POWER).isValid(carEnginePower);
        boolean isFuelConsValid = factory.getValidator(CarParameterType.FUEL_CONSUMPTION).isValid(carFuelConsumption);
        boolean isTransmissionValid = factory.getValidator(CarParameterType.TRANSMISSION_TYPE).isValid(carTransmission);
        boolean isCarClassValid = factory.getValidator(CarParameterType.CAR_CLASS).isValid(carClass);
        boolean isCarBrandValid = factory.getValidator(CarParameterType.CAR_BRAND).isValid(carBrand);

        if (!isVinValid) {
            throw new CarServiceException(VIN_INVALID_MESSAGE);
        } else if (!isManufDateValid) {
            throw new CarServiceException(MANUFACTURE_DATE_INVALID_MESSAGE);
        } else if (!isEnginePowerValid) {
            throw new CarServiceException(ENGINE_POWER_INVALID_MESSAGE);
        } else if (!isFuelConsValid) {
            throw new CarServiceException(FUEL_CONSUMPTION_INVALID_MESSAGE);
        } else if (!isTransmissionValid) {
            throw new CarServiceException(TRANSMISSION_TYPE_INVALID_MESSAGE);
        } else if (!isCarClassValid) {
            throw new CarServiceException(CAR_CLASS_TYPE_INVALID_MESSAGE);
        } else if (!isCarBrandValid) {
            throw new CarServiceException(CAR_BRAND_INVALID_MESSAGE);
        } else try {
            Double.parseDouble(carPricePerDay);
        } catch (NumberFormatException e) {
            throw new CarServiceException(PRICE_INVALID_MESSAGE);
        }
        return true;
    }

    /**
     * The method {@code createSearchCarQuery} build search criteria from
     * user request.
     *
     * @param searchingCar {@link CarDTO} object which contains user request
     *                     search parameters.
     * @return search criteria String line.
     */
    public static String createSearchCarQuery(CarDTO searchingCar) {
        final int carId = searchingCar.getId();
        final String carVin = searchingCar.getVin();
        final String carManufactureDate = searchingCar.getManufactureDate();
        final String carEnginePower = searchingCar.getEnginePower();
        final String carFuelConsumption = searchingCar.getFuelConsumption();
        final String isAvailableToRent = searchingCar.getIsAvailableToRent();
        final String isDeleted = searchingCar.getIsDeleted();
        final String pricePerDay = searchingCar.getPricePerDay();
        final String carTransmission = searchingCar.getTransmissionType();
        final String carClass = searchingCar.getClassType();
        final String carModel = searchingCar.getModel();
        final String carBrand = searchingCar.getBrand();

        StringBuilder searchCarCriteria = new StringBuilder();

        if (carId != 0) {
            searchCarCriteria.append("car_id=").append(carId).append(" ");
        }
        if (carVin != null) {
            searchCarCriteria.append("car_VIN='").append(carVin).append("' ");
        }
        if (carManufactureDate != null) {
            searchCarCriteria.append("car_manufacture_date='").append(carManufactureDate).append("' ");
        }
        if (carEnginePower != null) {
            searchCarCriteria.append("car_engine_power='").append(carEnginePower).append("' ");
        }
        if (carFuelConsumption != null) {
            searchCarCriteria.append("car_fuel_consumption='").append(carFuelConsumption).append("' ");
        }
        if (pricePerDay != null) {
            searchCarCriteria.append("pricePerDay='").append(pricePerDay).append("' ");
        }
        if (carTransmission != null) {
            searchCarCriteria.append("car_transmission='").append(carTransmission).append("' ");
        }
        if (carClass != null) {
            searchCarCriteria.append("car_class='").append(carClass).append("' ");
        }
        if (carModel != null) {
            searchCarCriteria.append("car_model='").append(carModel).append("' ");
        }
        if (carBrand != null) {
            searchCarCriteria.append("car_brand='").append(carClass).append("' ");
        }

        if (isAvailableToRent != null) {
            if (isAvailableToRent.equalsIgnoreCase("NOT_AVAILABLE")) {
                searchCarCriteria.append("is_car_available=false").append(" ");
            } else {
                searchCarCriteria.append("is_car_available=true").append(" ");
            }
        }
        if (isDeleted != null) {
            if (isDeleted.equalsIgnoreCase("DELETED")) {
                searchCarCriteria.append("is_deleted=true").append(" ");
            } else {
                searchCarCriteria.append("is_deleted=false").append(" ");
            }
        }

        return searchCarCriteria.toString().trim().replace(" ", " AND ");
    }
}
