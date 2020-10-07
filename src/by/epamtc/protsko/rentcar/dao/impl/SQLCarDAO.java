package by.epamtc.protsko.rentcar.dao.impl;

import by.epamtc.protsko.rentcar.bean.car.Car;
import by.epamtc.protsko.rentcar.dao.CarDAO;
import by.epamtc.protsko.rentcar.dao.exception.DAOException;

import java.util.List;

public class SQLCarDAO implements CarDAO {
    @Override
    public boolean addCar(Car car) throws DAOException {
        return false;
    }

    @Override
    public boolean editCarData(Car car) throws DAOException {
        return false;
    }

    @Override
    public boolean deleteCar(int carId) throws DAOException {
        return false;
    }

    @Override
    public Car showCar(String criteria) throws DAOException {
        return null;
    }

    @Override
    public List<Car> showAllCars() throws DAOException {
        return null;
    }
}
