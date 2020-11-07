package by.epamtc.protsko.rentcar.dao.impl;

import by.epamtc.protsko.rentcar.bean.car.Car;
import by.epamtc.protsko.rentcar.bean.order.Order;
import by.epamtc.protsko.rentcar.dao.CarDAO;
import by.epamtc.protsko.rentcar.dao.DAOFactory;
import by.epamtc.protsko.rentcar.dao.OrderDAO;
import by.epamtc.protsko.rentcar.dao.TransactionDAO;
import by.epamtc.protsko.rentcar.dao.dbconnector.ConnectionPool;
import by.epamtc.protsko.rentcar.dao.exception.CarDAOException;
import by.epamtc.protsko.rentcar.dao.exception.OrderDAOException;
import by.epamtc.protsko.rentcar.dao.exception.TransactionException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class TransactionDAOImpl implements TransactionDAO {
    private static ConnectionPool connectionPool = new ConnectionPool();
    private static DAOFactory factory = DAOFactory.getInstance();
    private static CarDAO carDAO = factory.getCarDAO();
    private static OrderDAO orderDAO = factory.getOrderDAO();
    private static final String ACCEPT_ORDER_QUERY = "UPDATE orders SET is_order_accepted=1 WHERE id=";
    private static final String SET_CAR_NOT_AVAILABLE_QUERY = "UPDATE cars SET is_avaliable_to_rent=0 WHERE id=";
    private static final String CLOSE_ORDER_QUERY = "UPDATE orders SET is_order_closed=1 WHERE id=";
    private static final String CANCEL_ORDER_QUERY = "UPDATE orders SET is_order_canceled=1 WHERE id=";
    private static final String SET_CAR_AVAILABLE_QUERY = "UPDATE cars SET is_avaliable_to_rent=1 WHERE id=";
    private static final String CAR_ID_CRITERIA_NAME = "car_id=";


    @Override
    public boolean acceptOrderTransaction(int orderId, int carId) {
        final String orderSQLQuery = ACCEPT_ORDER_QUERY + orderId;
        final String carSQLQuery = SET_CAR_NOT_AVAILABLE_QUERY + carId;

        Connection connection = null;
        Statement statement = null;
        Car car = null;

        try {
            List<Car> carList = carDAO.findCar(CAR_ID_CRITERIA_NAME + carId);
            car = carList.get(0);

            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            statement.executeUpdate(orderSQLQuery);
            statement.executeUpdate(carSQLQuery);

            if (!car.isAvailableToRent()) {
                throw new SQLException();
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                throw new TransactionException(ex);
            }
            try {
                if (statement != null) {
                    statement.executeUpdate(orderSQLQuery);
                    connection.commit();
                    return true;
                }
                return false;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (CarDAOException e) {
            throw new TransactionException(e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, statement);
            }
        }
        return true;
    }

    @Override
    public boolean closeOrderTransaction(int orderId, int carId) {
        return false;

    }

    @Override
    public boolean cancelOrderTransaction(int orderId, int carId) {
        final String orderSQLQuery = CANCEL_ORDER_QUERY + orderId;
        final String carSQLQuery = SET_CAR_AVAILABLE_QUERY + carId;
        int carOrdersCount = 0;
        Connection connection = null;
        Statement statement = null;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();

            statement.executeUpdate(orderSQLQuery);
            statement.executeUpdate(carSQLQuery);

            List<Order> carOrders = orderDAO.getCarOrders(carId);
            for (Order order : carOrders) {
                if (order.isOrderAccepted() & !order.isOrderClosed() & !order.isOrderCanceled())
                    carOrdersCount++;
            }

            if (carOrdersCount > 1) {
                throw new SQLException();
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                throw new TransactionException(ex);
            }
            try {
                if (statement != null) {
                    statement.executeUpdate(orderSQLQuery);
                    connection.commit();
                    return true;
                }
                return false;
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (OrderDAOException e) {
            throw new TransactionException(e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, statement);
            }
        }
        return true;
    }
}
//
//class Main {
//
//    public static void main(String[] args) throws TransactionException, SQLException {
//        TransactionDAOImpl o = new TransactionDAOImpl();
//        o.acceptOrderTransaction(1, 2);
//        System.out.println();
//    }
//
//
//}
