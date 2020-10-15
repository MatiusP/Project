package by.epamtc.protsko.rentcar.service.util;

import by.epamtc.protsko.rentcar.bean.car.CarDTO;

public class CarUtil {

    private CarUtil() {
    }

    public static String createSearchCarQuery(CarDTO searchingCar) {
        final int carId = searchingCar.getId();
        final String carVIN = searchingCar.getCarVIN();
        final int carManufactureDate = searchingCar.getManufactureDate();
        final int carEnginePower = searchingCar.getEnginePower();
        final double carFuelConsumption = searchingCar.getFuelConsumption();
        final boolean isAvailableToRent = searchingCar.isAvailableToRent();
        final boolean isDeleted = searchingCar.isDeleted();
        final String carTransmission = searchingCar.getTransmissionType();
        final String carClass = searchingCar.getCarClassType();
        final String carModel = searchingCar.getCarModel();
        final String carBrand = searchingCar.getCarBrand();

        StringBuilder searchCarCriteria = new StringBuilder();

        if (carId != 0) {
            searchCarCriteria.append("car_id=").append(carId).append(" ");
        }
        if (carVIN != null) {
            searchCarCriteria.append("car_VIN=").append("'").append(carVIN).append("'").append(" ");
        }
        if (carManufactureDate != 0) {
            searchCarCriteria.append("car_manufacture_date=").append(carManufactureDate).append(" ");
        }
        if (carEnginePower != 0) {
            searchCarCriteria.append("car_engine_power=").append(carEnginePower).append(" ");
        }
        if (carFuelConsumption != 0) {
            searchCarCriteria.append("car_fuel_consumption=").append(carFuelConsumption).append(" ");
        }
        searchCarCriteria.append("is_car_available=").append(isAvailableToRent).append(" ");
        searchCarCriteria.append("is_deleted=").append(isDeleted).append(" ");
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
