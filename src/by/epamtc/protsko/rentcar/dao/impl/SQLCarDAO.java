package by.epamtc.protsko.rentcar.dao.impl;

import by.epamtc.protsko.rentcar.bean.car.*;
import by.epamtc.protsko.rentcar.dao.CarDAO;
import by.epamtc.protsko.rentcar.dao.dbconnector.ConnectionPool;
import by.epamtc.protsko.rentcar.dao.exception.CarDAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLCarDAO implements CarDAO {
    private static ConnectionPool connectionPool = new ConnectionPool();

    private static final String GET_ALL_UN_DELETED_CARS_QUERY = "SELECT * FROM fullCarsData WHERE is_deleted=0";
    private static final String GET_ALL_CARS_QUERY = "SELECT * FROM fullCarsData";

    private static final String FIND_CAR_QUERY = "SELECT * FROM fullCarsData WHERE";

    private static final String GET_CAR_PHOTOS_QUERY = "SELECT picture_URL FROM carPhotos WHERE cars_id=?";
    private static final String ADD_CAR_PHOTOS_QUERY = "INSERT INTO carphotos (picture_URL, cars_id) VALUES (?,?)";
    private static final String DELETE_CAR_PHOTO_QUERY = "DELETE FROM carphotos WHERE cars_id=?";
    private static final String ADD_NEW_CAR_QUERY =
            "INSERT INTO cars" +
                    "(VIN, manufacture_date, engine_power, fuel_consumption, is_avaliable_to_rent," +
                    "is_deleted, price_per_day, transmissionType_id, carType_id, carModels_id)" +
                    " VALUES (?,?,?,?,?,?,?,?,?,?)";
    private static final String EDIT_CAR_DATA_QUERY =
            "UPDATE cars SET" +
                    " VIN=?, manufacture_date=?, engine_power=?, fuel_consumption=?, is_avaliable_to_rent=?," +
                    "is_deleted=?, price_per_day=?, transmissionType_id=?, carType_id=?, carModels_id=?" +
                    " WHERE id=?";
    private static final String DELETE_CAR_FROM_DB_QUERY = "DELETE FROM cars WHERE cars.id=?";
    private static final String DELETE_CAR_FROM_SYSTEM_QUERY = "UPDATE cars SET is_deleted=1 WHERE id=?";
    private static final String IS_CAR_VIN_EXIST_QUERY = "SELECT vin FROM cars WHERE vin=?";
    private static final String ADD_NEW_CAR_BRAND_QUERY = "INSERT INTO carbrands (brand_name) VALUES (?)";
    private static final String GET_CAR_BRAND_ID_QUERY = "SELECT id FROM carbrands WHERE brand_name=?";
    private static final String IS_CAR_MODEL_EXISTS_QUERY = "SELECT * FROM carmodels WHERE (model_name=? AND carbrands_id=?)";
    private static final String ADD_NEW_CAR_MODEL_QUERY = "INSERT INTO carmodels (model_name, carbrands_id) VALUES (?, ?)";
    private static final String GET_CAR_MODEL_ID_QUERY = "SELECT id FROM carmodels WHERE (model_name=? AND carbrands_id=?)";
    private static final String GET_CAR_ID_QUERY = "SELECT id FROM cars WHERE vin=?";
    private static final String CAR_EXISTS_MESSAGE = "Car VIN already exists in database";

    private static final String ADD_CAR_ERROR_MESSAGE = "Error while saving new car";
    private static final String EDIT_CAR_ERROR_MESSAGE = "Error while editing car";
    private static final String DELETE_CAR_ERROR_MESSAGE = "Error while deleting car from database";
    private static final String DELETE_CAR_FROM_SYSTEM_ERROR_MESSAGE = "Error while deleting car from system";
    private static final String GET_CAR_LIST_ERROR_MESSAGE = "Error while getting cars list";
    private static final String GET_CAR_PHOTO_ERROR_MESSAGE = "Error while getting cars photo";
    private static final String ADD_CAR_PHOTO_ERROR_MESSAGE = "Error while adding cars photo";
    private static final String GET_CAR_BRAND_ERROR_MESSAGE = "Error while getting car brand";
    private static final String GET_CAR_MODEL_ERROR_MESSAGE = "Error while getting car model";
    private static final String GET_CAR_ID_ERROR_MESSAGE = "Error while getting car id";
    private static final String CAR_VIN_NOT_FOUND_MESSAGE = "Car VIN not found in system";

    @Override
    public boolean addCar(Car car) throws CarDAOException {

        if (isCarVINExist(car.getVin())) {
            throw new CarDAOException(CAR_EXISTS_MESSAGE);
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            int carBrandId = getCarBrandId(car.getBrand());
            int carModelId = getCarModelId(car.getModel(), carBrandId);

            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(ADD_NEW_CAR_QUERY);

            preparedStatement.setString(1, car.getVin());
            preparedStatement.setInt(2, car.getManufactureDate());
            preparedStatement.setInt(3, car.getEnginePower());
            preparedStatement.setDouble(4, car.getFuelConsumption());
            preparedStatement.setBoolean(5, car.isAvailableToRent());
            preparedStatement.setBoolean(6, car.isDeleted());
            preparedStatement.setDouble(7, car.getPricePerDay());
            preparedStatement.setInt(8, (car.getTransmissionType().ordinal() + 1));
            preparedStatement.setInt(9, (car.getClassType().ordinal() + 1));
            preparedStatement.setInt(10, carModelId);

            int addedRowsCount = preparedStatement.executeUpdate();

            if (addedRowsCount > 0) {
                if (car.getPhotos() != null && !car.getPhotos().isEmpty()) {
                    int carId = getCarId(car.getVin());
                    addCarPhotos(car.getPhotos(), carId);
                }
                return true;
            }
        } catch (SQLException e) {
            throw new CarDAOException(ADD_CAR_ERROR_MESSAGE, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
        return false;
    }

    @Override
    public boolean editCarData(Car car) throws CarDAOException {

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            int carBrandId = getCarBrandId(car.getBrand());
            int carModelId = getCarModelId(car.getModel(), carBrandId);

            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(EDIT_CAR_DATA_QUERY);

            preparedStatement.setString(1, car.getVin());
            preparedStatement.setInt(2, car.getManufactureDate());
            preparedStatement.setInt(3, car.getEnginePower());
            preparedStatement.setDouble(4, car.getFuelConsumption());
            preparedStatement.setBoolean(5, car.isAvailableToRent());
            preparedStatement.setBoolean(6, car.isDeleted());
            preparedStatement.setDouble(7, car.getPricePerDay());
            preparedStatement.setInt(8, (car.getTransmissionType().ordinal() + 1));
            preparedStatement.setInt(9, (car.getClassType().ordinal() + 1));
            preparedStatement.setInt(10, carModelId);
            preparedStatement.setInt(11, car.getId());

            int addedRowsCount = preparedStatement.executeUpdate();

            if (addedRowsCount > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new CarDAOException(EDIT_CAR_ERROR_MESSAGE, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
        return false;
    }

    @Override
    public boolean deleteCarFromDatabase(int carId) throws CarDAOException {
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
            throw new CarDAOException(DELETE_CAR_ERROR_MESSAGE);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
        return false;
    }

    @Override
    public boolean deleteCarFromSystem(int carId) throws CarDAOException {
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
            throw new CarDAOException(DELETE_CAR_FROM_SYSTEM_ERROR_MESSAGE);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
        return false;
    }

    @Override
    public List<Car> findCar(String searchCriteria) throws CarDAOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Car> foundCarsList = new ArrayList<>();

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
    public List<Car> getAllCars() throws CarDAOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Car> carsList = new ArrayList<>();

        try {
            connection = connectionPool.takeConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(GET_ALL_UN_DELETED_CARS_QUERY);

            carsList = getCarsList(resultSet);
        } catch (SQLException e) {
            throw new CarDAOException(e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, statement, resultSet);
            }
        }
        return carsList;
    }

    private List<Car> getCarsList(ResultSet resultSet) throws CarDAOException {
        List<Car> cars = new ArrayList<>();
        Car car;

        try {
            while (resultSet.next()) {
                car = new Car();

                car.setId(resultSet.getInt(1));
                car.setVin(resultSet.getString(2));
                car.setManufactureDate(resultSet.getInt(3));
                car.setEnginePower(resultSet.getInt(4));
                car.setFuelConsumption(resultSet.getInt(5));
                car.setAvailableToRent(resultSet.getBoolean(6));
                car.setDeleted(resultSet.getBoolean(7));
                car.setPricePerDay(resultSet.getInt(8));
                car.setTransmissionType(Transmission.valueOf(resultSet.getString(9)));
                car.setClassType(CarClass.valueOf(resultSet.getString(10)));
                car.setModel(resultSet.getString(11));
                car.setBrand(resultSet.getString(12));
                car.setPhotos(getCarPhotos(car.getId()));

                cars.add(car);
            }
        } catch (SQLException e) {
            throw new CarDAOException(GET_CAR_LIST_ERROR_MESSAGE, e);
        }
        return cars;
    }

    private List<String> getCarPhotos(int carId) throws CarDAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<String> carPhotos = new ArrayList<>();

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_CAR_PHOTOS_QUERY);
            preparedStatement.setInt(1, carId);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int i = 1;
                carPhotos.add(resultSet.getString(i));
            }
        } catch (SQLException e) {
            throw new CarDAOException(GET_CAR_PHOTO_ERROR_MESSAGE, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return carPhotos;
    }

    private void addCarPhotos(List<String> photoList, int carId) throws CarDAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            if (!photoList.isEmpty()) {
                connection = connectionPool.takeConnection();
                preparedStatement = connection.prepareStatement(ADD_CAR_PHOTOS_QUERY);

                for (String photo : photoList) {
                    preparedStatement.setString(1, photo);
                    preparedStatement.setInt(2, carId);
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }
        } catch (SQLException e) {
            throw new CarDAOException(ADD_CAR_PHOTO_ERROR_MESSAGE, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
    }

    private int getCarBrandId(String carBrand) throws CarDAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        carBrand = carBrand.toUpperCase();

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_CAR_BRAND_ID_QUERY);
            preparedStatement.setString(1, carBrand);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                preparedStatement = connection.prepareStatement(ADD_NEW_CAR_BRAND_QUERY);
                preparedStatement.setString(1, carBrand);
                preparedStatement.executeUpdate();

                preparedStatement = connection.prepareStatement(GET_CAR_BRAND_ID_QUERY);
                preparedStatement.setString(1, carBrand);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new CarDAOException(GET_CAR_BRAND_ERROR_MESSAGE, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
    }

    private int getCarModelId(String carModel, int carBrandId) throws CarDAOException {
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
                preparedStatement.executeUpdate();

                preparedStatement = connection.prepareStatement(GET_CAR_MODEL_ID_QUERY);
                preparedStatement.setString(1, carModel);
                preparedStatement.setInt(2, carBrandId);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return resultSet.getInt(1);
                }
            }
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new CarDAOException(GET_CAR_MODEL_ERROR_MESSAGE, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
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

    private int getCarId(String carVIN) throws CarDAOException {
        if (!isCarVINExist(carVIN)) {
            throw new CarDAOException(CAR_VIN_NOT_FOUND_MESSAGE);
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_CAR_ID_QUERY);
            preparedStatement.setString(1, carVIN);

            resultSet = preparedStatement.executeQuery();
            resultSet.next();

            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new CarDAOException(GET_CAR_ID_ERROR_MESSAGE, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
    }
}
