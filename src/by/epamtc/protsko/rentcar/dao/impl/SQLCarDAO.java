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

    private static final String GET_ALL_CARS_QUERY =
            "SELECT cars.id, cars.VIN, cars.manufacture_date, cars.engine_power, cars.fuel_consumption, cars.is_avaliable_to_rent, tt.type, ct.type, cm.model_name, cb.brand_name\n" +
                    "FROM cars \n" +
                    "INNER JOIN `transmissionTypes` tt ON tt.id=cars.transmissiontype_id\n" +
                    "INNER JOIN `carTypes` ct ON ct.id=cars.cartype_id\n" +
                    "INNER JOIN `carModels` cm ON cm.id=cars.carmodels_id\n" +
                    "INNER JOIN `carBrands` cb ON cb.id=cm.carbrands_id";
    private static final String GET_CAR_PHOTOS_QUERY = "SELECT * FROM carPhotos WHERE cars_id=?";

    @Override
    public boolean addCar(Car car) throws CarDAOException {
        return false;
    }

    @Override
    public boolean editCarData(Car car) throws CarDAOException {
        return false;
    }

    @Override
    public boolean deleteCar(int carId) throws CarDAOException {

        return false;
    }

    @Override
    public List<Car> findCar(String criteria) throws CarDAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Car> foundCarsList = new ArrayList<>();
        Car car;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_ALL_CARS_QUERY);





        }catch (SQLException e){
            throw new CarDAOException("Find car SQL exception", e);
        }finally {
            if (connection != null){
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return foundCarsList;
    }

    @Override
    public List<FullCarData> getAllCars() throws CarDAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        FullCarData fullCarData;
        List<FullCarData> cars = new ArrayList<>();

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_ALL_CARS_QUERY);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int i = 0;

                fullCarData = new FullCarData();

                fullCarData.setId(resultSet.getInt(++i));
                fullCarData.setCarVIN(resultSet.getString(++i));
                fullCarData.setManufactureDate(resultSet.getInt(++i));
                fullCarData.setEnginePower(resultSet.getInt(++i));
                fullCarData.setFuelConsumption(resultSet.getInt(++i));
                fullCarData.setValidateToRent(resultSet.getBoolean(++i));
                fullCarData.setTransmissionType(Transmission.valueOf(resultSet.getString(++i)));
                fullCarData.setCarClassType(CarClass.valueOf(resultSet.getString(++i)));
                fullCarData.setCarModel(resultSet.getString(++i));
                fullCarData.setCarBrand(resultSet.getString(++i));
                fullCarData.setCarPhotos(getCarPhotos(fullCarData.getId()));

                cars.add(fullCarData);
            }
        } catch (SQLException e) {
            throw new CarDAOException("Database connection error", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return cars;
    }

    private List<CarPhoto> getCarPhotos(int carId) throws CarDAOException {
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
                carPhoto.setFileUrl(resultSet.getString(++i));
                carPhoto.setCarId(resultSet.getInt(++i));

                carPhotos.add(carPhoto);
            }
        } catch (SQLException e) {
            throw new CarDAOException("Get cars photo SQL connection error", e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return carPhotos;
    }
}
