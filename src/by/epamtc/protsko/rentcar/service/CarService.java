package by.epamtc.protsko.rentcar.service;

import by.epamtc.protsko.rentcar.dto.CarDTO;
import by.epamtc.protsko.rentcar.service.exception.CarServiceException;

import java.util.List;

public interface CarService {

    boolean add(CarDTO car) throws CarServiceException;

    boolean edit(CarDTO car) throws CarServiceException;

    boolean delete(int carId) throws CarServiceException;

    List<CarDTO> findByCriteria(CarDTO criteria) throws CarServiceException;

    List<CarDTO> findAll() throws CarServiceException;
}
