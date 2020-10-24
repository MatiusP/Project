package by.epamtc.protsko.rentcar.dao.impl;

import by.epamtc.protsko.rentcar.bean.order.FinalRentAct;
import by.epamtc.protsko.rentcar.bean.order.Order;
import by.epamtc.protsko.rentcar.dao.OrderDAO;
import by.epamtc.protsko.rentcar.dao.dbconnector.ConnectionPool;
import by.epamtc.protsko.rentcar.dao.exception.OrderDAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLOrderDAO implements OrderDAO {
    private static final Logger logger = LogManager.getLogger(SQLCarDAO.class);
    private static ConnectionPool connectionPool = new ConnectionPool();

    private static final String CREATE_ORDER_QUERY =
            "INSERT INTO orders" +
                    "(start_rent, end_rent, total_price, user_id, car_id)" +
                    " VALUES (?,?,?,?,?)";

    private static final String GET_ALL_ORDERS_QUERY = "SELECT * FROM orders";

    private static final String GET_ALL_USER_ORDERS_QUERY = "SELECT * FROM orders WHERE user_id=?";

    private static final String CANCEL_ORDER_QUERY = "UPDATE orders SET is_order_canceled=1 WHERE id=?";

    private static final String ACCEPT_ORDER_QUERY = "UPDATE orders SET is_order_accepted=1 WHERE id=?";

    private static final String CLOSE_ORDER_QUERY =
            "UPDATE orders" +
                    " SET is_order_closed=1" +
                    " WHERE id=?";

    private static final String CREATE_FINAL_ACT_QUERY =
            "INSERT INTO additionalpayments (Orders_id)" +
                    " VALUES (?)";

    private static final String GET_FINAL_ACT_QUERY = "SELECT * FROM additionalpayments WHERE Orders_id=?";

    private static final String UPDATE_FINAL_ACT_QUERY =
            "UPDATE additionalpayments SET" +
                    " cost_by_overdue_period=?, cost_by_fuel=?, cost_by_mileage=?, cost_by_parking_penalty=?," +
                    " cost_by_police_penalty=?, cost_by_damage=?, cost_by_other_penalty=?" +
                    " WHERE id=?";


    @Override
    public boolean createOrder(Order order) throws OrderDAOException {
        final String CREATE_ORDER_ERROR = "Create order SQL error";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(CREATE_ORDER_QUERY);

            preparedStatement.setDate(1, (Date) order.getStartRent());
            preparedStatement.setDate(2, (Date) order.getEndRent());
            preparedStatement.setDouble(3, order.getTotalPrice());
            preparedStatement.setInt(4, order.getUserId());
            preparedStatement.setInt(5, order.getCarId());

            int countAddedRows = preparedStatement.executeUpdate();

            if (countAddedRows > 0) {
                boolean isFinalRentActCreated = createFinalRentAct(order.getId());
                if (isFinalRentActCreated) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new OrderDAOException(CREATE_ORDER_ERROR, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
        return false;
    }

    @Override
    public boolean cancelOrder(int orderId) throws OrderDAOException {
        final String CANCEL_ORDER_ERROR = "Cancel order SQL error";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(CANCEL_ORDER_QUERY);
            preparedStatement.setInt(1, orderId);

            int countUpdatedRows = preparedStatement.executeUpdate();

            if (countUpdatedRows > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new OrderDAOException(CANCEL_ORDER_ERROR, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
        return false;
    }

    @Override
    public boolean acceptOrder(int orderId) throws OrderDAOException {
        final String ACCEPT_ORDER_ERROR = "Cancel order SQL error";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(ACCEPT_ORDER_QUERY);
            preparedStatement.setInt(1, orderId);

            int countUpdatedRows = preparedStatement.executeUpdate();

            if (countUpdatedRows > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new OrderDAOException(ACCEPT_ORDER_ERROR, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
        return false;
    }

    @Override
    public boolean closeOrder(int orderId) throws OrderDAOException {
        final String CLOSE_ORDER_ERROR = "Close order SQL error";
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(CLOSE_ORDER_QUERY);
            preparedStatement.setInt(1, orderId);

            int countUpdatedRows = preparedStatement.executeUpdate();

            if (countUpdatedRows > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new OrderDAOException(CLOSE_ORDER_ERROR, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
        return false;
    }

    @Override
    public List<Order> getAllOrders() throws OrderDAOException {
        final String GET_ALL_ORDERS_SQL_ERROR = "Get all orders SQL error";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Order> ordersList = new ArrayList<>();
        Order order;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_ALL_ORDERS_QUERY);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int i = 0;
                order = new Order();

                order.setId(resultSet.getInt(++i));
                order.setStartRent(resultSet.getDate(++i));
                order.setEndRent(resultSet.getDate(++i));
                order.setTotalPrice(resultSet.getDouble(++i));
                order.setOrderAccepted(resultSet.getBoolean(++i));
                order.setOrderAccepted(resultSet.getBoolean(++i));
                order.setOrderClosed(resultSet.getBoolean(++i));
                order.setUserId(resultSet.getInt(++i));
                order.setCarId(resultSet.getInt(++i));

                ordersList.add(order);
            }
        } catch (SQLException e) {
            throw new OrderDAOException(GET_ALL_ORDERS_SQL_ERROR, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return ordersList;
    }

    @Override
    public List<Order> getUserOrders(int userId) throws OrderDAOException {
        final String GET_USER_ORDERS_SQL_ERROR = "Get user orders SQL error";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Order> ordersList = new ArrayList<>();
        Order order;

        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement(GET_ALL_USER_ORDERS_QUERY);
            preparedStatement.setInt(1, userId);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int i = 0;
                order = new Order();

                order.setId(resultSet.getInt(++i));
                order.setStartRent(resultSet.getDate(++i));
                order.setEndRent(resultSet.getDate(++i));
                order.setTotalPrice(resultSet.getDouble(++i));
                order.setOrderAccepted(resultSet.getBoolean(++i));
                order.setOrderAccepted(resultSet.getBoolean(++i));
                order.setOrderClosed(resultSet.getBoolean(++i));
                order.setUserId(resultSet.getInt(++i));
                order.setCarId(resultSet.getInt(++i));

                ordersList.add(order);
            }
        } catch (SQLException e) {
            throw new OrderDAOException(GET_USER_ORDERS_SQL_ERROR, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return ordersList;
    }

    @Override
    public FinalRentAct getFinalRentAct(int orderId) throws OrderDAOException {
        final String GET_FINAL_ACT_ERROR = "Get final rent act error";
        final String GET_FINAL_ACT_SQL_ERROR = "Get final rent act SQL error";

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
                throw new OrderDAOException(GET_FINAL_ACT_ERROR);
            }
        } catch (SQLException e) {
            throw new OrderDAOException(GET_FINAL_ACT_SQL_ERROR, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return rentAct;
    }

    @Override
    public boolean updateFinalRentAct(FinalRentAct rentAct) throws OrderDAOException {
        final String UPDATE_FINAL_RENT_ACT_ERROR = "Update final rent act SQL error";
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

            if (countUpdatedRows > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new OrderDAOException(UPDATE_FINAL_RENT_ACT_ERROR, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
        return false;
    }

    private boolean createFinalRentAct(int orderId) throws OrderDAOException {
        final String CREATE_FINAL_ACT_ERROR = "Create final act SQL error";
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
            throw new OrderDAOException(CREATE_FINAL_ACT_ERROR, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
        return false;
    }
}
