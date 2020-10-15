package by.epamtc.protsko.rentcar.service;

import by.epamtc.protsko.rentcar.bean.car.CarDTO;
import by.epamtc.protsko.rentcar.service.exception.CarServiceException;

import java.util.List;

public interface CarService {

    boolean addCar(CarDTO car) throws CarServiceException;

    boolean editCarData(CarDTO car) throws CarServiceException;

    boolean deleteCarFromDatabase(int carId) throws CarServiceException;

    boolean deleteCarFromSystem(int carId) throws CarServiceException;

    List<CarDTO> findCar(CarDTO criteria) throws CarServiceException;

    List<CarDTO> getAllCars() throws CarServiceException;
}
