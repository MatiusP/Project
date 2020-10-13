package by.epamtc.protsko.rentcar.dao;

import by.epamtc.protsko.rentcar.bean.car.Car;
import by.epamtc.protsko.rentcar.bean.car.FullCarData;
import by.epamtc.protsko.rentcar.dao.exception.CarDAOException;

import java.util.List;

public interface CarDAO {

    boolean addCar(Car car) throws CarDAOException;

    boolean editCarData(Car car) throws CarDAOException;

    boolean deleteCar(int carId) throws CarDAOException;

    List<Car> findCar(String criteria) throws CarDAOException;

    List<FullCarData> getAllCars() throws CarDAOException;
}
