package by.epamtc.protsko.rentcar.dao.impl;

import by.epamtc.protsko.rentcar.entity.order.Order;
import by.epamtc.protsko.rentcar.entity.order.OrderForShow;
import by.epamtc.protsko.rentcar.dao.OrderDAO;
import by.epamtc.protsko.rentcar.dao.dbconnector.ConnectionPool;
import by.epamtc.protsko.rentcar.dao.exception.OrderDAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLOrderDAO implements OrderDAO {
    private static ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static final String CREATE_ORDER_ERROR_MESSAGE = "Error while creating new order";
    private static final String ACCEPT_ORDER_ERROR_MESSAGE = "Error while accepting order";
    private static final String CLOSE_ORDER_ERROR_MESSAGE = "Error while closing order";
    private static final String CANCEL_ORDER_ERROR_MESSAGE = "Error while canceling order";
    private static final String GET_ALL_ORDERS_ERROR_MESSAGE = "Error while getting all orders";
    private static final String GET_ALL_USER_ORDERS_ERROR_MESSAGE = "Error while getting user's orders";
    private static final String GET_CAR_ORDERS_ERROR_MESSAGE = "Error while getting car's orders";
    private static final String GET_ORDER_BY_ID_ERROR_MESSAGE = "Error while getting order by id";
    private static final String CREATE_ORDER_QUERY = "INSERT INTO orders" +
            "(order_date, start_rent, end_rent, total_price, user_id, car_id)" +
            " VALUES (?,?,?,?,?,?)";
    private static final String GET_ORDER_BY_ID_QUERY = "SELECT * FROM orders WHERE id=?";
    private static final String GET_ALL_ORDERS_QUERY = "SELECT * FROM fullorderdata";
    private static final String GET_ALL_USER_ORDERS_QUERY = "SELECT * FROM fullorderdata WHERE user_id=?";
    private static final String GET_ALL_CAR_ORDERS_QUERY = "SELECT * FROM orders WHERE car_id=?";
    private static final String GET_LAST_ORDER_INDEX_QUERY = "SELECT LAST_INSERT_ID()";
    private static final String ACCEPT_ORDER_QUERY = "UPDATE orders SET is_order_accepted=1 WHERE id=?";
    private static final String CLOSE_ORDER_QUERY = "UPDATE orders SET is_order_closed=1 WHERE id=?";
    private static final String CANCEL_ORDER_QUERY = "UPDATE orders SET is_order_canceled=1 WHERE id=?";

    @Override
    public int add(Order order) throws OrderDAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int newOrderId = -1;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(CREATE_ORDER_QUERY);

            preparedStatement.setTimestamp(1, Timestamp.valueOf(order.getOrderDate()));
            preparedStatement.setDate(2, Date.valueOf(order.getStartRent()));
            preparedStatement.setDate(3, Date.valueOf(order.getEndRent()));
            preparedStatement.setDouble(4, order.getTotalPrice());
            preparedStatement.setInt(5, order.getUserId());
            preparedStatement.setInt(6, order.getCarId());
            final int countAddedRows = preparedStatement.executeUpdate();

            if (countAddedRows == 0) {
                throw new OrderDAOException(CREATE_ORDER_ERROR_MESSAGE);
            }

            preparedStatement = connection.prepareStatement(GET_LAST_ORDER_INDEX_QUERY);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                newOrderId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new OrderDAOException(CREATE_ORDER_ERROR_MESSAGE, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return newOrderId;
    }

    @Override
    public boolean accept(int orderId) throws OrderDAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(ACCEPT_ORDER_QUERY);
            preparedStatement.setInt(1, orderId);

            int countUpdatedRows = preparedStatement.executeUpdate();

            if (countUpdatedRows == 0) {
                throw new OrderDAOException(ACCEPT_ORDER_ERROR_MESSAGE);
            }
            return true;
        } catch (SQLException e) {
            throw new OrderDAOException(ACCEPT_ORDER_ERROR_MESSAGE, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
    }

    @Override
    public boolean close(int orderId) throws OrderDAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(CLOSE_ORDER_QUERY);
            preparedStatement.setInt(1, orderId);
            int countUpdatedRows = preparedStatement.executeUpdate();

            if (countUpdatedRows == 0) {
                throw new OrderDAOException(CLOSE_ORDER_ERROR_MESSAGE);
            }
            return true;
        } catch (SQLException e) {
            throw new OrderDAOException(CLOSE_ORDER_ERROR_MESSAGE, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
    }

    @Override
    public boolean cancel(int orderId) throws OrderDAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(CANCEL_ORDER_QUERY);
            preparedStatement.setInt(1, orderId);
            int countUpdatedRows = preparedStatement.executeUpdate();

            if (countUpdatedRows == 0) {
                throw new OrderDAOException(CANCEL_ORDER_ERROR_MESSAGE);
            }
            return true;
        } catch (SQLException e) {
            throw new OrderDAOException(CANCEL_ORDER_ERROR_MESSAGE, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
    }

    @Override
    public Order findByOrderId(int orderId) throws OrderDAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Order order = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_ORDER_BY_ID_QUERY);
            preparedStatement.setInt(1, orderId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                order = buildOrderFromDB(resultSet);
            }
        } catch (SQLException e) {
            throw new OrderDAOException(GET_ORDER_BY_ID_ERROR_MESSAGE, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
        return order;
    }

    @Override
    public List<OrderForShow> findAll() throws OrderDAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<OrderForShow> orderList = new ArrayList<>();
        OrderForShow order;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_ALL_ORDERS_QUERY);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                order = buildOrderFromDatabase(resultSet);

                orderList.add(order);
            }
        } catch (SQLException e) {
            throw new OrderDAOException(GET_ALL_ORDERS_ERROR_MESSAGE, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return orderList;
    }

    @Override
    public List<OrderForShow> findByUserId(int userId) throws OrderDAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<OrderForShow> userOrders = new ArrayList<>();
        OrderForShow order;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_ALL_USER_ORDERS_QUERY);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                order = buildOrderFromDatabase(resultSet);
                userOrders.add(order);
            }
        } catch (SQLException e) {
            throw new OrderDAOException(GET_ALL_USER_ORDERS_ERROR_MESSAGE, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return userOrders;
    }

    @Override
    public List<Order> findByCarId(int carId) throws OrderDAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Order> carOrderList = new ArrayList<>();
        Order order;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_ALL_CAR_ORDERS_QUERY);
            preparedStatement.setInt(1, carId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                order = buildOrderFromDB(resultSet);
                carOrderList.add(order);
            }
        } catch (SQLException e) {
            throw new OrderDAOException(GET_CAR_ORDERS_ERROR_MESSAGE, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return carOrderList;
    }

    private Order buildOrderFromDB(ResultSet resultSet) throws SQLException {
        Order order = new Order();

        order.setId(resultSet.getInt(1));
        order.setOrderDate(resultSet.getTimestamp(2).toLocalDateTime());
        order.setStartRent(resultSet.getDate(3).toLocalDate());
        order.setEndRent(resultSet.getDate(4).toLocalDate());
        order.setTotalPrice(resultSet.getDouble(5));
        order.setOrderAccepted(resultSet.getBoolean(6));
        order.setOrderCanceled(resultSet.getBoolean(7));
        order.setOrderClosed(resultSet.getBoolean(8));
        order.setUserId(resultSet.getInt(9));
        order.setCarId(resultSet.getInt(10));

        return order;
    }

    private OrderForShow buildOrderFromDatabase(ResultSet resultSet) throws SQLException {
        OrderForShow order = new OrderForShow();

        order.setOrderId(resultSet.getInt(1));
        order.setOrderDate(resultSet.getTimestamp(2).toLocalDateTime());
        order.setOrderStartRent(resultSet.getDate(3));
        order.setOrderEndRent(resultSet.getDate(4));
        order.setOrderTotalPrice(resultSet.getDouble(5));
        order.setOrderAccepted(resultSet.getBoolean(6));
        order.setOrderCanceled(resultSet.getBoolean(7));
        order.setOrderClosed(resultSet.getBoolean(8));
        order.setOrderUserId(resultSet.getInt(9));
        order.setOrderUserPassport(resultSet.getString(10));
        order.setOrderCarId(resultSet.getInt(11));
        order.setOrderCarBrand(resultSet.getString(12));
        order.setOrderCarModel(resultSet.getString(13));

        return order;
    }
}
