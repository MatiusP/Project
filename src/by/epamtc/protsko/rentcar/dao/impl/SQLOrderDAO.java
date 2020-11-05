package by.epamtc.protsko.rentcar.dao.impl;

import by.epamtc.protsko.rentcar.bean.order.FinalRentAct;
import by.epamtc.protsko.rentcar.bean.order.Order;
import by.epamtc.protsko.rentcar.bean.order.OrderForShow;
import by.epamtc.protsko.rentcar.dao.OrderDAO;
import by.epamtc.protsko.rentcar.dao.dbconnector.ConnectionPool;
import by.epamtc.protsko.rentcar.dao.exception.OrderDAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLOrderDAO implements OrderDAO {
    private static ConnectionPool connectionPool = new ConnectionPool();

    private static final String CREATE_ORDER_ERROR_MESSAGE = "Error while creating new order";
    private static final String CREATE_FINAL_ACT_ERROR_MESSAGE = "Error while creating final act";
    private static final String ACCEPT_ORDER_ERROR_MESSAGE = "Error while accepting order";
    private static final String CLOSE_ORDER_ERROR_MESSAGE = "Error while closing order";
    private static final String CANCEL_ORDER_ERROR_MESSAGE = "Error while canceling order";
    private static final String GET_FINAL_ACT_ERROR_MESSAGE = "Final act does not found";
    private static final String GET_FINAL_ACT_SQL_ERROR_MESSAGE = "Error while getting final act";
    private static final String UPDATE_FINAL_ACT_ERROR_MESSAGE = "Error while updating final rent act";
    private static final String GET_ALL_ORDERS_ERROR_MESSAGE = "Error while getting all orders";
    private static final String GET_ALL_USER_ORDERS_ERROR_MESSAGE = "Error while getting user's orders";
    private static final String GET_CAR_ORDERS_ERROR_MESSAGE = "Error while getting car's orders";

    private static final String CREATE_ORDER_QUERY = "INSERT INTO orders" +
            "(order_date, start_rent, end_rent, total_price, user_id, car_id)" +
            " VALUES (?,?,?,?,?,?)";
    private static final String UPDATE_FINAL_ACT_QUERY =
            "UPDATE additionalpayments SET" +
                    " cost_by_overdue_period=?, cost_by_fuel=?, cost_by_mileage=?, cost_by_parking_penalty=?," +
                    " cost_by_police_penalty=?, cost_by_damage=?, cost_by_other_penalty=?" +
                    " WHERE id=?";
    private static final String GET_ALL_ORDERS_QUERY = "SELECT * FROM fullorderdata";
    private static final String GET_ALL_USER_ORDERS_QUERY = "SELECT * FROM fullorderdata WHERE user_id=?";
    private static final String GET_ALL_CAR_ORDERS_QUERY = "SELECT * FROM orders WHERE car_id=?";
    private static final String GET_LAST_ORDER_INDEX_QUERY = "SELECT LAST_INSERT_ID()";
    private static final String CREATE_FINAL_ACT_QUERY = "INSERT INTO additionalpayments (orders_id) VALUES (?)";
    private static final String ACCEPT_ORDER_QUERY = "UPDATE orders SET is_order_accepted=1 WHERE id=?";
    private static final String CLOSE_ORDER_QUERY = "UPDATE orders SET is_order_closed=1 WHERE id=?";
    private static final String CANCEL_ORDER_QUERY = "UPDATE orders SET is_order_canceled=1 WHERE id=?";
    private static final String GET_FINAL_ACT_QUERY = "SELECT * FROM additionalpayments WHERE Orders_id=?";

    @Override
    public boolean createOrder(Order order) throws OrderDAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        int orderId = 0;

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
                orderId = resultSet.getInt(1);
                createFinalRentAct(orderId);
            }
            return true;
        } catch (SQLException e) {
            throw new OrderDAOException(CREATE_ORDER_ERROR_MESSAGE, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
    }

    @Override
    public boolean acceptOrder(int orderId) throws OrderDAOException {
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
    public boolean closeOrder(int orderId) throws OrderDAOException {
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
    public boolean cancelOrder(int orderId) throws OrderDAOException {
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
    public List<OrderForShow> getAllOrders() throws OrderDAOException {
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
    public List<OrderForShow> getUserOrders(int userId) throws OrderDAOException {
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
    public List<Order> getCarOrders(int carId) throws OrderDAOException {
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
                order = new Order();

                order.setId(resultSet.getInt(1));
                order.setOrderDate(resultSet.getTimestamp(2).toLocalDateTime());
                order.setStartRent(resultSet.getDate(3).toLocalDate());
                order.setEndRent(resultSet.getDate(4).toLocalDate());
                order.setTotalPrice(resultSet.getDouble(5));
                order.setOrderAccepted(resultSet.getBoolean(6));
                order.setOrderClosed(resultSet.getBoolean(7));
                order.setUserId(resultSet.getInt(8));
                order.setCarId(resultSet.getInt(9));

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

    @Override
    public FinalRentAct getFinalRentAct(int orderId) throws OrderDAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        FinalRentAct rentAct = new FinalRentAct();

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_FINAL_ACT_QUERY);
            preparedStatement.setInt(1, orderId);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                rentAct.setId(resultSet.getInt(1));
                rentAct.setCostOverduePeriod(resultSet.getDouble(2));
                rentAct.setCostByFuel(resultSet.getDouble(3));
                rentAct.setCostByMileage(resultSet.getDouble(4));
                rentAct.setCostByParkingPenalty(resultSet.getDouble(5));
                rentAct.setCostByPolicePenalty(resultSet.getDouble(6));
                rentAct.setCostByDamagePenalty(resultSet.getDouble(7));
                rentAct.setCostByOtherPenalty(resultSet.getDouble(8));
                rentAct.setOrderId(resultSet.getInt(9));
            }
            if (rentAct == null) {
                throw new OrderDAOException(GET_FINAL_ACT_ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            throw new OrderDAOException(GET_FINAL_ACT_SQL_ERROR_MESSAGE, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return rentAct;
    }

    @Override
    public boolean updateFinalRentAct(FinalRentAct rentAct) throws OrderDAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(UPDATE_FINAL_ACT_QUERY);

            preparedStatement.setDouble(1, rentAct.getCostOverduePeriod());
            preparedStatement.setDouble(2, rentAct.getCostByFuel());
            preparedStatement.setDouble(3, rentAct.getCostByMileage());
            preparedStatement.setDouble(4, rentAct.getCostByParkingPenalty());
            preparedStatement.setDouble(5, rentAct.getCostByPolicePenalty());
            preparedStatement.setDouble(6, rentAct.getCostByDamagePenalty());
            preparedStatement.setDouble(7, rentAct.getCostByOtherPenalty());
            preparedStatement.setInt(8, rentAct.getId());

            int countUpdatedRows = preparedStatement.executeUpdate();

            if (countUpdatedRows == 0) {
                throw new OrderDAOException(UPDATE_FINAL_ACT_ERROR_MESSAGE);
            }
            return true;
        } catch (SQLException e) {
            throw new OrderDAOException(UPDATE_FINAL_ACT_ERROR_MESSAGE, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
    }

    private boolean createFinalRentAct(int orderId) throws OrderDAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(CREATE_FINAL_ACT_QUERY);
            preparedStatement.setInt(1, orderId);

            int countAddedRows = preparedStatement.executeUpdate();
            if (countAddedRows > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new OrderDAOException(CREATE_FINAL_ACT_ERROR_MESSAGE, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
        return false;
    }

    private OrderForShow buildOrderFromDatabase(ResultSet resultSet) throws SQLException {
        OrderForShow order = new OrderForShow();

        order.setOrderId(resultSet.getInt(1));
        order.setOrderDate(resultSet.getTimestamp(2).toLocalDateTime());
        order.setOrderStartRent(resultSet.getDate(3));
        order.setOrderEndRent(resultSet.getDate(4));
        order.setOrderTotalPrice(resultSet.getDouble(5));
        order.setOrderAccepted(resultSet.getBoolean(6));
        order.setOrderClosed(resultSet.getBoolean(7));
        order.setOrderCarBrand(resultSet.getString(8));
        order.setOrderCarModel(resultSet.getString(9));
        order.setOrderCarVin(resultSet.getString(10));
        order.setOrderUserId(resultSet.getInt(11));

        return order;
    }
}
