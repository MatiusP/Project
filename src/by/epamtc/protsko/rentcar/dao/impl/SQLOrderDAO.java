package by.epamtc.protsko.rentcar.dao.impl;

import by.epamtc.protsko.rentcar.entity.order.Order;
import by.epamtc.protsko.rentcar.entity.order.OrderForShow;
import by.epamtc.protsko.rentcar.dao.OrderDAO;
import by.epamtc.protsko.rentcar.dao.dbconnector.ConnectionPool;
import by.epamtc.protsko.rentcar.dao.exception.OrderDAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implementation of {@link OrderDAO}. Methods use
 * {@link ConnectionPool} to connect to database and work with order layer.
 *
 * @author Matvey Protsko
 */

public class SQLOrderDAO implements OrderDAO {
    /**
     * A single instance of the class {@code ConnectionPool} (pattern Singleton)
     */
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

    /**
     * Method {@code add} adds a new order to database.
     * This method take a Connection {@code Connection} from ConnectionPool
     * {@link ConnectionPool}, create PreparedStatement object ({@code PreparedStatement}),
     * create ResultSet {@code ResultSet} and add a new order to database.
     *
     * @param order contains entered order's data from service layer.
     * @return added order id if the new order was successfully added to database.
     * If order has not been added to the database, this method throws OrderDAOException.
     * @throws OrderDAOException when problems with database access occur.
     */
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

    /**
     * Method {@code accept} used for confirming the user's order and
     * change order's parameter isOrderAccepted from false to true.
     * This method take a Connection {@code Connection} from ConnectionPool
     * {@link ConnectionPool} and create PreparedStatement for connecting
     * to database.
     *
     * @param orderId - id of the order which we want to accept.
     * @return true if the order was successfully accepted. If order has not
     * been accepted, this method throws OrderDAOException.
     * @throws OrderDAOException when problems with database access occur.
     */
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

    /**
     * Method {@code close} used for closing the user's order and
     * change order's parameter isOrderClosed from false to true.
     * This method take a Connection {@code Connection} from ConnectionPool
     * {@link ConnectionPool} and create PreparedStatement for connecting
     * to database.
     *
     * @param orderId - id of the order which we want to close.
     * @return true if the order was successfully closed. If order has not
     * been closed, this method throws OrderDAOException.
     * @throws OrderDAOException when problems with database access occur.
     */
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

    /**
     * Method {@code cancel} used for canceling the user's order and
     * change order's parameter isOrderCanceled from false to true.
     * This method take a Connection {@code Connection} from ConnectionPool
     * {@link ConnectionPool} and create PreparedStatement for connecting
     * to database.
     *
     * @param orderId - id of the order which we want to cancel.
     * @return true if the order was successfully canceled. If order has not
     * been canceled, this method throws OrderDAOException.
     * @throws OrderDAOException when problems with database access occur.
     */
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

    /**
     * Method {@code findByOrderId} finds an order by order id.
     * This method take a Connection {@code Connection} from ConnectionPool
     * {@link ConnectionPool}, create PreparedStatement object ({@code PreparedStatement}),
     * ResultSet {@code ResultSet}, find and return order's data from database.
     *
     * @param orderId - id of the order we want to find in the system.
     * @return Order object which id matches orderId parameter value.
     * @throws OrderDAOException when problems with database access occur.
     */
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

    /**
     * Method {@code findAll} finds all orders in database.
     * This method take a Connection {@code Connection} from ConnectionPool
     * {@link ConnectionPool}, create PreparedStatement object ({@code PreparedStatement}),
     * ResultSet {@code ResultSet}, find and return orders list from database.
     *
     * @return List of ordersForShow objects, whose contains all information about orders.
     * @throws OrderDAOException when problems with database access occur.
     */
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

    /**
     * Method {@code findByUserId} finds an order by user id.
     * This method take a Connection {@code Connection} from ConnectionPool
     * {@link ConnectionPool}, create PreparedStatement object ({@code PreparedStatement}),
     * ResultSet {@code ResultSet}, find and return user's orders from database.
     *
     * @param userId - id of the user whose orders we are looking.
     * @return List of ordersForShow, whose contains all user orders.
     * @throws OrderDAOException when problems with database access occur.
     */
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

    /**
     * Method {@code findByCarId} finds an order by car id.
     * This method take a Connection {@code Connection} from ConnectionPool
     * {@link ConnectionPool}, create PreparedStatement object ({@code PreparedStatement}),
     * ResultSet {@code ResultSet}, find and return car's orders from database.
     *
     * @param carId - id of the car whose orders we are looking.
     * @return List of Order, whose contains all car's orders.
     * @throws OrderDAOException when problems with database access occur.
     */
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

    /**
     * Method {@code buildOrderFromDB} is additional method which used for build order
     * from database data like Order object.
     *
     * @param resultSet - resultSet, that contains order's data from database.
     * @return Order object which contains order's data from database.
     * @throws SQLException when problems with database access occur.
     */
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

    /**
     * Method {@code buildOrderFromDatabase} is additional method which used
     * for build order from database data like OrderForShow {@link OrderForShow}
     * object.
     * OrderForShow object used for show all needed information about order's
     * data in web page.
     *
     * @param resultSet - resultSet, that contains order's data from database.
     * @return OrderForShow object which contains order's data from database.
     * @throws SQLException when problems with database access occur.
     */
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
