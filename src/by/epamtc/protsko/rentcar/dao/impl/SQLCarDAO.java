package by.epamtc.protsko.rentcar.dao.impl;

import by.epamtc.protsko.rentcar.bean.car.*;
import by.epamtc.protsko.rentcar.dao.CarDAO;
import by.epamtc.protsko.rentcar.dao.dbconnector.ConnectionPool;
import by.epamtc.protsko.rentcar.dao.exception.CarDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLCarDAO implements CarDAO {
    private static final Logger logger = LogManager.getLogger(SQLCarDAO.class);
    private static ConnectionPool connectionPool = new ConnectionPool();

    private static final String GET_ALL_CARS_QUERY = "SELECT * FROM fullCarsData";
    private static final String FIND_CAR_QUERY = "SELECT * FROM fullCarsData WHERE ";
    private static final String GET_CAR_PHOTOS_QUERY = "SELECT * FROM carPhotos WHERE cars_id=?";
    private static final String DELETE_CAR_PHOTO_QUERY = "DELETE FROM carphotos WHERE cars_id=?";
    private static final String DELETE_CAR_QUERY = "DELETE FROM cars WHERE cars.id=?";
    private static final String IS_CAR_VIN_EXIST_QUERY = "SELECT vin FROM cars WHERE vin=?";

    @Override
    public boolean addCar(FullCarData car) throws CarDAOException {
        final String CAR_VIN_EXISTS = "Car VIN already exists in database";

        if (isCarVINExist(car.getCarVIN())) {
            throw new CarDAOException(CAR_VIN_EXISTS);
        }


        return false;
    }

    @Override
    public boolean editCarData(Car car) throws CarDAOException {
        return false;
    }

    @Override
    public boolean deleteCar(int carId) throws CarDAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(DELETE_CAR_PHOTO_QUERY);
            preparedStatement.setInt(1, carId);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(DELETE_CAR_QUERY);
            preparedStatement.setInt(1, carId);
            int countDeletedRows = preparedStatement.executeUpdate();

            if (countDeletedRows > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new CarDAOException("Delete car SQL error");
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
        return false;
    }

    @Override
    public List<FullCarData> findCar(String searchCriteria) throws CarDAOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<FullCarData> foundCarsList = new ArrayList<>();

        try {
            connection = connectionPool.takeConnection();
            statement = connection.createStatement();

            if (!searchCriteria.isEmpty()) {
                resultSet = statement.executeQuery(FIND_CAR_QUERY + "(" + searchCriteria + ")");
            } else {
                resultSet = statement.executeQuery(GET_ALL_CARS_QUERY);
            }

            foundCarsList = getCarsList(resultSet);
        } catch (SQLException e) {
            throw new CarDAOException(e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, statement, resultSet);
            }
        }
        return foundCarsList;
    }

    @Override
    public List<FullCarData> getAllCars() throws CarDAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<FullCarData> carsList = new ArrayList<>();

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_ALL_CARS_QUERY);
            resultSet = preparedStatement.executeQuery();

            carsList = getCarsList(resultSet);
        } catch (SQLException e) {
            throw new CarDAOException(e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return carsList;
    }

    private List<CarPhoto> getCarPhotos(int carId) throws CarDAOException {
        final String GET_CAR_PHOTO_SQL_ERROR = "Get cars photo SQL connection error";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        CarPhoto carPhoto;
        List<CarPhoto> carPhotos = new ArrayList<>();

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_CAR_PHOTOS_QUERY);

            preparedStatement.setInt(1, carId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int i = 0;

                carPhoto = new CarPhoto();
                carPhoto.setId(resultSet.getInt(++i));
                carPhoto.setPictureName(resultSet.getString(++i));
                carPhoto.setCarId(resultSet.getInt(++i));

                carPhotos.add(carPhoto);
            }
        } catch (SQLException e) {
            throw new CarDAOException(GET_CAR_PHOTO_SQL_ERROR, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return carPhotos;
    }

    private List<FullCarData> getCarsList(ResultSet resultSet) throws CarDAOException {
        final String CREATE_FULL_CAR_DATA_SQL_ERROR = "Get cars list SQL error";
        List<FullCarData> cars = new ArrayList<>();
        FullCarData car;

        try {
            while (resultSet.next()) {
                int i = 0;

                car = new FullCarData();

                car.setId(resultSet.getInt(++i));
                car.setCarVIN(resultSet.getString(++i));
                car.setManufactureDate(resultSet.getInt(++i));
                car.setEnginePower(resultSet.getInt(++i));
                car.setFuelConsumption(resultSet.getInt(++i));
                car.setValidateToRent(resultSet.getBoolean(++i));
                car.setTransmissionType(Transmission.valueOf(resultSet.getString(++i)));
                car.setCarClassType(CarClass.valueOf(resultSet.getString(++i)));
                car.setCarModel(resultSet.getString(++i));
                car.setCarBrand(resultSet.getString(++i));
                car.setCarPhotos(getCarPhotos(car.getId()));

                cars.add(car);
            }
        } catch (SQLException e) {
            throw new CarDAOException(CREATE_FULL_CAR_DATA_SQL_ERROR, e);
        }
        return cars;
    }

    private boolean isCarVINExist(String carVIN) throws CarDAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(IS_CAR_VIN_EXIST_QUERY);
            preparedStatement.setString(1, carVIN);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new CarDAOException(e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return false;
    }
}

class Main {

    public static void main(String[] args) throws CarDAOException {
        SQLCarDAO o = new SQLCarDAO();
//
//        final List<FullCarData> cars = o.getAllCars();
//
//        for (FullCarData car : cars) {
//            System.out.println(car);
//        }
//        System.out.println();
//
//        String criteria = "car_brand='BMW'";
//
//        final List<FullCarData> car = o.findCar(criteria);
//
//        for (FullCarData caE : car) {
//            System.out.println(caE);
//        }

        final boolean b = o.deleteCar(6);
        System.out.println(b);
    }
}
