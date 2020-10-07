package by.epamtc.protsko.rentcar.dao;

import by.epamtc.protsko.rentcar.bean.car.Car;
import by.epamtc.protsko.rentcar.dao.exception.DAOException;

import java.util.List;

public interface CarDAO {

    boolean addCar(Car car) throws DAOException;

    boolean editCarData(Car car) throws DAOException;

    boolean deleteCar(int carId) throws DAOException;

    Car showCar(String criteria) throws DAOException;

    List<Car> showAllCars() throws DAOException;
}
