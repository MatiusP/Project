package by.epamtc.protsko.rentcar.dao.impl;

import by.epamtc.protsko.rentcar.bean.car.Car;
import by.epamtc.protsko.rentcar.dao.CarDAO;
import by.epamtc.protsko.rentcar.dao.dbconnector.ConnectionPool;
import by.epamtc.protsko.rentcar.dao.exception.UserDAOException;

import java.util.List;

public class SQLCarDAO implements CarDAO {
    private static ConnectionPool connectionPool = new ConnectionPool();

    @Override
    public boolean addCar(Car car) throws UserDAOException {
        return false;
    }

    @Override
    public boolean editCarData(Car car) throws UserDAOException {
        return false;
    }

    @Override
    public boolean deleteCar(int carId) throws UserDAOException {
        return false;
    }

    @Override
    public List<Car> findCar(String criteria) throws UserDAOException {
        return null;
    }

    @Override
    public List<Car> showAllCars() throws UserDAOException {
        return null;
    }
}
