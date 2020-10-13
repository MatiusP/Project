package by.epamtc.protsko.rentcar.dao;

import by.epamtc.protsko.rentcar.bean.car.Car;
import by.epamtc.protsko.rentcar.bean.car.FullCarData;
import by.epamtc.protsko.rentcar.dao.exception.CarDAOException;

import java.util.List;

public interface CarDAO {

    boolean addCar(FullCarData car) throws CarDAOException;

    boolean editCarData(Car car) throws CarDAOException;

    boolean deleteCarFromDatabase(int carId) throws CarDAOException;

    boolean deleteCarFromSystem(int carId) throws CarDAOException;

    List<FullCarData> findCar(String criteria) throws CarDAOException;

    List<FullCarData> getAllCars() throws CarDAOException;
}
