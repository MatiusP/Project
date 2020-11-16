package by.epamtc.protsko.rentcar.dao;

import by.epamtc.protsko.rentcar.entity.car.Car;
import by.epamtc.protsko.rentcar.dao.exception.CarDAOException;

import java.util.List;

/**
 * This interface provides methods for working with cars's data.
 *
 * @author Matvey Protsko
 */

public interface CarDAO {

    /**
     * Method {@code add} adds a new car to database.
     *
     * @param car contains entered car's data value from service layer.
     * @return true if the new car was successfully added to database.
     * @throws CarDAOException when problems with database access occur.
     */
    boolean add(Car car) throws CarDAOException;

    /**
     * Method {@code edit} updates car's data in database.
     *
     * @param car contains entered new cars's data for updating.
     * @return true if the car's data was successfully updated, false -
     * if has not been updated.
     * @throws CarDAOException when problems with database access occur.
     */
    boolean edit(Car car) throws CarDAOException;

    /**
     * Method {@code delete} removes a car from the system.
     * This method change cars's parameter isDeleted from false to true.
     *
     * @param carId - id of the car we want to remove from the system.
     * @return true if the carId was successfully removed from system.
     * @throws CarDAOException when problems with database access occur.
     */
    boolean delete(int carId) throws CarDAOException;

    /**
     * Method {@code isVinExists} checks the entered vin for existence.
     *
     * @param carVin - car's VIN number we want to check for existence.
     * @return true if the carVin already exists in database.
     * @throws CarDAOException when problems with database access occur.
     */
    boolean isVinExists(String carVin) throws CarDAOException;

    /**
     * Method {@code findBySearchCriteria} finds cars by search criteria.
     *
     * @param criteria - criteria that can contains one or more cars's data parameters.
     * @return List of cars whose data match the search criteria.
     * @throws CarDAOException when problems with database access occur.
     */
    List<Car> findBySearchCriteria(String criteria) throws CarDAOException;

    /**
     * Method {@code findAll} finds all cars in database.
     *
     * @return List of Car objects.
     * @throws CarDAOException when problems with database access occur.
     */
    List<Car> findAll() throws CarDAOException;
}
