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

    private static final String GET_ALL_UNDELETED_CARS_QUERY = "SELECT * FROM fullCarsData WHERE is_deleted=0";
    private static final String GET_ALL_CARS_QUERY = "SELECT * FROM fullCarsData";
    private static final String FIND_CAR_QUERY = "SELECT * FROM fullCarsData WHERE is_deleted=0 ";
    private static final String GET_CAR_PHOTOS_QUERY = "SELECT * FROM carPhotos WHERE cars_id=?";
    private static final String DELETE_CAR_PHOTO_QUERY = "DELETE FROM carphotos WHERE cars_id=?";
    private static final String DELETE_CAR_FROM_DB_QUERY = "DELETE FROM cars WHERE cars.id=?";
    private static final String DELETE_CAR_FROM_SYSTEM_QUERY = "UPDATE cars SET is_deleted=1 WHERE id=?";
    private static final String IS_CAR_VIN_EXIST_QUERY = "SELECT vin FROM cars WHERE vin=?";
    private static final String IS_CAR_BRAND_EXISTS_QUERY = "SELECT * FROM carbrands WHERE brand_name=?";
    private static final String ADD_NEW_CAR_BRAND_QUERY = "INSERT INTO carbrands (brand_name) VALUES (?)";
    private static final String GET_CAR_BRAND_ID_QUERY = "SELECT id FROM carbrands WHERE brand_name=?";
    private static final String IS_CAR_MODEL_EXISTS_QUERY = "SELECT * FROM carmodels WHERE (model_name=? AND carbrands_id=?)";
    private static final String ADD_NEW_CAR_MODEL_QUERY = "INSERT INTO carmodels (model_name, carbrands_id) VALUES (?, ?)";
    private static final String GET_CAR_MODEL_ID_QUERY = "SELECT id FROM carmadels WHERE (model_name=? AND carbrands_id=?)";
    private static final String ADD_NEW_CAR_QUERY =
            "INSERT INTO cars" +
                    "(VIN, manufacture_date, engine_power, fuel_consumption, is_avaliable_to_rent," +
                    "transmissionType_id, carType_id, carModels_id)" +
                    " VALUES (?,?,?,?,?,?,?,?)";


    @Override
    public boolean addCar(FullCarData car) throws CarDAOException {
        final String CAR_VIN_EXISTS = "Car VIN already exists in database";
        final String ADD_CAR_ERROR = "Add new car SQL exception";

        String carVIN = car.getCarVIN();
        int carManufactureDate = car.getManufactureDate();
        int carEnginePower = car.getEnginePower();
        int carFuelConsumption = car.getFuelConsumption();
        boolean isCarAvailableToRent = car.isValidateToRent();
        Transmission carTransmission = car.getTransmissionType();
        CarClass carClassType = car.getCarClassType();
        String carModel = car.getCarModel();
        String carBrand = car.getCarBrand();
        List<CarPhoto> carPhotos = car.getCarPhotos();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            if (isCarVINExist(car.getCarVIN())) {
                throw new CarDAOException(CAR_VIN_EXISTS);
            }

            int carBrandId = getCarBrandId(carBrand);
            int carModelId = getCarModelId(carModel, carBrandId);

            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(ADD_NEW_CAR_QUERY);

            preparedStatement.setString(1, carVIN);
            preparedStatement.setInt(2, carManufactureDate);
            preparedStatement.setInt(3, carEnginePower);
            preparedStatement.setInt(4, carFuelConsumption);
            preparedStatement.setBoolean(5, isCarAvailableToRent);
            preparedStatement.setInt(6, carTransmission.ordinal());         /////проверить номер
            preparedStatement.setInt(7, carClassType.ordinal());
            preparedStatement.setInt(8, carModelId);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new CarDAOException(ADD_CAR_ERROR, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return false;
    }

    @Override                                                                                                //TODO
    public boolean editCarData(Car car) throws CarDAOException {
        return false;
    }

    @Override
    public boolean deleteCarFromDatabase(int carId) throws CarDAOException {
        final String DELETE_CAR_ERROR = "Delete car from database SQL error";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(DELETE_CAR_PHOTO_QUERY);
            preparedStatement.setInt(1, carId);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(DELETE_CAR_FROM_DB_QUERY);
            preparedStatement.setInt(1, carId);
            int countDeletedRows = preparedStatement.executeUpdate();

            if (countDeletedRows > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new CarDAOException(DELETE_CAR_ERROR);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
        return false;
    }

    @Override
    public boolean deleteCarFromSystem(int carId) throws CarDAOException {
        final String DELETE_CAR_ERROR = "Delete car from system SQL error";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement(DELETE_CAR_FROM_SYSTEM_QUERY);
            preparedStatement.setInt(1, carId);
            int countDeletedRows = preparedStatement.executeUpdate();

            if (countDeletedRows > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new CarDAOException(DELETE_CAR_ERROR);
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
                resultSet = statement.executeQuery(FIND_CAR_QUERY + "AND " + searchCriteria);
            } else {
                resultSet = statement.executeQuery(GET_ALL_UNDELETED_CARS_QUERY);
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
                car.setDeleted(resultSet.getBoolean(++i));
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

    private int getCarBrandId(String carBrand) throws CarDAOException {
        final String GET_CAR_BRAND_SQL_ERROR_MESSAGE = "Get car brand SQL error";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        carBrand = carBrand.toUpperCase();

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(IS_CAR_BRAND_EXISTS_QUERY);
            preparedStatement.setString(1, carBrand);

            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                preparedStatement = connection.prepareStatement(ADD_NEW_CAR_BRAND_QUERY);
                preparedStatement.setString(1, carBrand);
                resultSet = preparedStatement.executeQuery();

                preparedStatement = connection.prepareStatement(GET_CAR_BRAND_ID_QUERY);
                preparedStatement.setString(1, carBrand);
                preparedStatement.executeQuery();
            }
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new CarDAOException(GET_CAR_BRAND_SQL_ERROR_MESSAGE, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
    }

    private int getCarModelId(String carModel, int carBrandId) throws CarDAOException {
        final String GET_CAR_MODEL_ID_ERROR_MESSAGE = "Get car model id SQL error";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        carModel = carModel.toUpperCase();

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(IS_CAR_MODEL_EXISTS_QUERY);
            preparedStatement.setString(1, carModel);
            preparedStatement.setInt(2, carBrandId);

            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                preparedStatement = connection.prepareStatement(ADD_NEW_CAR_MODEL_QUERY);
                preparedStatement.setString(1, carModel);
                preparedStatement.setInt(2, carBrandId);
                preparedStatement.executeQuery();

                preparedStatement = connection.prepareStatement(GET_CAR_MODEL_ID_QUERY);
                preparedStatement.setString(1, carModel);
                preparedStatement.setInt(2, carBrandId);
                preparedStatement.executeQuery();
            }
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new CarDAOException(GET_CAR_MODEL_ID_ERROR_MESSAGE, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
    }
}

