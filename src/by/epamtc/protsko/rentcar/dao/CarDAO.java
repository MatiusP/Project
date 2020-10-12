package by.epamtc.protsko.rentcar.dao;

import by.epamtc.protsko.rentcar.bean.car.Car;
import by.epamtc.protsko.rentcar.dao.exception.UserDAOException;

import java.util.List;

public interface CarDAO {

    boolean addCar(Car car) throws UserDAOException;

    boolean editCarData(Car car) throws UserDAOException;

    boolean deleteCar(int carId) throws UserDAOException;

    List<Car> findCar(String criteria) throws UserDAOException;

    List<Car> showAllCars() throws UserDAOException;
}
