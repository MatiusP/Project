package by.epamtc.protsko.rentcar.dao;

import by.epamtc.protsko.rentcar.entity.car.Car;
import by.epamtc.protsko.rentcar.dao.exception.CarDAOException;

import java.util.List;

public interface CarDAO {

    boolean add(Car car) throws CarDAOException;

    boolean edit(Car car) throws CarDAOException;

    boolean delete(int carId) throws CarDAOException;

    boolean isVinExists(String carVin) throws CarDAOException;

    List<Car> findBySearchCriteria(String criteria) throws CarDAOException;

    List<Car> findAll() throws CarDAOException;
}
