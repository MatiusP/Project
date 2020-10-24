package by.epamtc.protsko.rentcar.dao;

import by.epamtc.protsko.rentcar.dao.impl.SQLCarDAO;
import by.epamtc.protsko.rentcar.dao.impl.SQLOrderDAO;
import by.epamtc.protsko.rentcar.dao.impl.SQLUserDAO;

public class DAOFactory {
    private static final DAOFactory instance = new DAOFactory();
    private static UserDAO userDAO = new SQLUserDAO();
    private static CarDAO carDAO = new SQLCarDAO();
    private static OrderDAO orderDAO = new SQLOrderDAO();

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
}
