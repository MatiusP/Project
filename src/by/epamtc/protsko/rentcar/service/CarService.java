package by.epamtc.protsko.rentcar.service;

import by.epamtc.protsko.rentcar.dto.CarDTO;
import by.epamtc.protsko.rentcar.service.exception.CarServiceException;

import java.util.List;

/**
 * This interface provides methods for car data business logic
 * and providing access to car's information.
 *
 * @author Matvey Protsko
 */

public interface CarService {

    /**
     * Method {@code add} provides validation entered car's data from UI
     * and adding new car to the system.
     *
     * @param car {@link CarDTO} contains entered car's data value.
     * @return true if entered new car's data is valid and has been adding to the database.
     * @throws CarServiceException when problems in CarDAO {@code CarDAO} layer or
     *                             in business logic.
     */
    boolean add(CarDTO car) throws CarServiceException;

    /**
     * Method {@code edit} provides validation entered car's updating information
     * and updating car's data.
     *
     * @param car {@link CarDTO} contains entered car's update data value.
     * @return true if entered car's update data is valid and has been added to the database.
     * @throws CarServiceException when problems in CarDAO {@code CarDAO} layer or
     *                             in business logic.
     */
    boolean edit(CarDTO car) throws CarServiceException;

    /**
     * Method {@code delete} provides business logic for removing a car
     * from the system.
     *
     * @param carId - id of the car we want to remove from the system.
     * @return true if the car was successfully removed from system.
     * @throws CarServiceException when problems in CarDAO {@code CarDAO} layer or
     *                             in business logic.
     */
    boolean delete(int carId) throws CarServiceException;

    /**
     * Method {@code findByCriteria} provides validation and business
     * logic for finding cars by search criteria.
     *
     * @param criteria - criteria that can contains one or more car's data parameters.
     * @return List of {@link CarDTO} objects.
     * @throws CarServiceException when problems in CarDAO {@code CarDAO} layer or
     *                             in business logic.
     */
    List<CarDTO> findByCriteria(CarDTO criteria) throws CarServiceException;

    /**
     * Method {@code findAll} provides business logic for finding
     * all cars in system.
     *
     * @return List of {@link CarDTO} objects, whose contains
     * all information about car.
     * @throws CarServiceException when problems in CarDAO {@code CarDAO} layer or
     *                             in business logic.
     */
    List<CarDTO> findAll() throws CarServiceException;
}
