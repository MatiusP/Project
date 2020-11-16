package by.epamtc.protsko.rentcar.dao;

import by.epamtc.protsko.rentcar.dao.impl.*;

/**
 * Factory class that provides different implementation of DAO interfaces.
 *
 * @author Matvey Protsko
 */

public class DAOFactory {
    /**
     * A single instance of the class (pattern Singleton)
     */
    private static final DAOFactory instance = new DAOFactory();
    /**
     * An object of {@link SQLUserDAO}
     */
    private static UserDAO userDAO = new SQLUserDAO();
    /**
     * An object of {@link SQLCarDAO}
     */
    private static CarDAO carDAO = new SQLCarDAO();
    /**
     * An object of {@link SQLOrderDAO}
     */
    private static OrderDAO orderDAO = new SQLOrderDAO();
    /**
     * An object of {@link SQLFinalRentActDAO}
     */
    private static FinalRentActDAO finalRentActDAO = new SQLFinalRentActDAO();

    private DAOFactory() {
    }

    /**
     * Returns singleton object of the class
     *
     * @return Object of {@link DAOFactory}
     */
    public static DAOFactory getInstance() {
        return instance;
    }

    /**
     * Returns field of {@link UserDAO} object
     */
    public UserDAO getUserDAO() {
        return userDAO;
    }

    /**
     * Returns field of {@link CarDAO} object
     */
    public CarDAO getCarDAO() {
        return carDAO;
    }

    /**
     * Returns field of {@link OrderDAO} object
     */
    public OrderDAO getOrderDAO() {
        return orderDAO;
    }

    /**
     * Returns field of {@link FinalRentActDAO} object
     */
    public FinalRentActDAO getFinalRentActDAO() {
        return finalRentActDAO;
    }

}
