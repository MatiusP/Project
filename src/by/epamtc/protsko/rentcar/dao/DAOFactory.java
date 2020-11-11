package by.epamtc.protsko.rentcar.dao;

import by.epamtc.protsko.rentcar.dao.impl.*;

public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();
    private static UserDAO userDAO = new SQLUserDAO();
    private static CarDAO carDAO = new SQLCarDAO();
    private static OrderDAO orderDAO = new SQLOrderDAO();
    private static FinalRentActDAO finalRentActDAO = new SQLFinalRentActDAO();

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return instance;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public CarDAO getCarDAO() {
        return carDAO;
    }

    public OrderDAO getOrderDAO() {
        return orderDAO;
    }

    public FinalRentActDAO getFinalRentActDAO() {
        return finalRentActDAO;
    }

}
