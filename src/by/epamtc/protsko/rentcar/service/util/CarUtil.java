package by.epamtc.protsko.rentcar.service.util;

import by.epamtc.protsko.rentcar.bean.car.CarDTO;
import by.epamtc.protsko.rentcar.service.exception.CarServiceException;
import by.epamtc.protsko.rentcar.service.validator.car.CarParameterType;
import by.epamtc.protsko.rentcar.service.validator.car.CarValidatorFactory;

public class CarUtil {

    private CarUtil() {
    }

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
            throw new CarServiceException("VIN value invalid");
        } else if (!isManufDateValid) {
            throw new CarServiceException("Manufacture date value invalid");
        } else if (!isEnginePowerValid) {
            throw new CarServiceException("Engine power value invalid");
        } else if (!isFuelConsValid) {
            throw new CarServiceException("Fuel consumption value invalid");
        } else if (!isTransmissionValid) {
            throw new CarServiceException("Transmission type invalid");
        } else if (!isCarClassValid) {
            throw new CarServiceException("Car class invalid");
        } else if (!isCarBrandValid) {
            throw new CarServiceException("Car brand value invalid");
        } else try {
            Double.parseDouble(carPricePerDay);
        } catch (NumberFormatException e) {
            throw new CarServiceException("Price per day value invalid");
        }
        return true;
    }

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
            searchCarCriteria.append("car_VIN=").append("'").append(carVin).append("'").append(" ");
        }
        if (carManufactureDate != null) {
            searchCarCriteria.append("car_manufacture_date=").append("'").append(carManufactureDate).append("'").append(" ");
        }
        if (carEnginePower != null) {
            searchCarCriteria.append("car_engine_power=").append("'").append(carEnginePower).append("'").append(" ");
        }
        if (carFuelConsumption != null) {
            searchCarCriteria.append("car_fuel_consumption=").append("'").append(carFuelConsumption).append("'").append(" ");
        }
        if (isAvailableToRent.equalsIgnoreCase("NOT_AVAILABLE")) {
            searchCarCriteria.append("is_car_available=").append("true").append(" ");
        } else {
            searchCarCriteria.append("is_car_available=").append("false").append(" ");
        }
        if (isDeleted.equalsIgnoreCase("DELETED")) {
            searchCarCriteria.append("is_deleted=").append("true").append(" ");
        } else {
            searchCarCriteria.append("is_deleted=").append("false").append(" ");
        }
        if (pricePerDay != null) {
            searchCarCriteria.append("pricePerDay=").append("'").append(pricePerDay).append("'").append(" ");
        }
        if (carTransmission != null) {
            searchCarCriteria.append("car_transmission=").append("'").append(carTransmission).append("'").append(" ");
        }
        if (carClass != null) {
            searchCarCriteria.append("car_class=").append("'").append(carClass).append("'").append(" ");
        }
        if (carModel != null) {
            searchCarCriteria.append("car_model=").append("'").append(carModel).append("'").append(" ");
        }
        if (carBrand != null) {
            searchCarCriteria.append("car_brand=").append("'").append(carClass).append("'").append(" ");
        }
        return searchCarCriteria.toString().trim().replace(" ", " AND ");
    }
}
