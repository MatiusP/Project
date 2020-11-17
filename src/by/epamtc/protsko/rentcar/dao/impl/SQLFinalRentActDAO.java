package by.epamtc.protsko.rentcar.dao.impl;

import by.epamtc.protsko.rentcar.dao.FinalRentActDAO;
import by.epamtc.protsko.rentcar.dao.dbconnector.ConnectionPool;
import by.epamtc.protsko.rentcar.dao.exception.FinalActDAOException;
import by.epamtc.protsko.rentcar.entity.order.FinalRentAct;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class implementation of {@link FinalRentActDAO}. Methods use
 * {@link ConnectionPool} to connect to database and work with final rent act layer.
 *
 * @author Matvey Protsko
 */

public class SQLFinalRentActDAO implements FinalRentActDAO {
    /**
     * A single instance of the class {@code ConnectionPool} (pattern Singleton)
     */
    private static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String CREATE_FINAL_ACT_ERROR_MESSAGE = "Error while creating final act";
    private static final String FIND_FINAL_ACT_ERROR_MESSAGE = "Final act does not found";
    private static final String FIND_FINAL_ACT_SQL_ERROR_MESSAGE = "Error while getting final act";
    private static final String UPDATE_FINAL_ACT_ERROR_MESSAGE = "Error while updating final rent act";
    private static final String CREATE_FINAL_ACT_QUERY = "INSERT INTO additionalpayments (orders_id) VALUES (?)";
    private static final String GET_FINAL_ACT_QUERY = "SELECT * FROM additionalpayments WHERE Orders_id=?";
    private static final String UPDATE_FINAL_ACT_QUERY =
            "UPDATE additionalpayments SET" +
                    " cost_by_overdue_period=?, cost_by_fuel=?, cost_by_mileage=?, cost_by_parking_penalty=?," +
                    " cost_by_police_penalty=?, cost_by_damage=?, cost_by_other_penalty=?" +
                    " WHERE id=?";

    /**
     * Method {@code create} create a new final rent act.
     * All orders in the system have their own final rent acts.
     * <p>
     * This method take a Connection {@code Connection} from ConnectionPool
     * {@link ConnectionPool}, create PreparedStatement object ({@code PreparedStatement}),
     * and create a new final rent act in database.
     *
     * @param orderId id of the order we want to create final rent act.
     * @return true if the final rent act was successfully created, false -
     * if final rent act has not been created.
     * @throws FinalActDAOException when problems with database access occur.
     */
    @Override
    public boolean create(int orderId) throws FinalActDAOException {
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
            throw new FinalActDAOException(CREATE_FINAL_ACT_ERROR_MESSAGE, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
        return false;
    }

    /**
     * Method {@code find} finds a final rent act by order id.
     * This method take a Connection {@code Connection} from ConnectionPool
     * {@link ConnectionPool}, create PreparedStatement object ({@code PreparedStatement}),
     * ResultSet {@code ResultSet}, find and return final rent act data from database.
     *
     * @param orderId - id of the order, final rent act of which we want to find.
     * @return FinalRentAct object which id matches orderId parameter value. If
     * final rent act has not been founded, this method throws FinalActDAOException.
     * @throws FinalActDAOException when problems with database access occur.
     */
    @Override
    public FinalRentAct find(int orderId) throws FinalActDAOException {
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
                throw new FinalActDAOException(FIND_FINAL_ACT_ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            throw new FinalActDAOException(FIND_FINAL_ACT_SQL_ERROR_MESSAGE, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement, resultSet);
            }
        }
        return rentAct;
    }

    /**
     * Method {@code update} updates final rent act data in database.
     * This method take a Connection {@code Connection} from ConnectionPool
     * {@link ConnectionPool}, create PreparedStatement object ({@code PreparedStatement})
     * and try to update final rent act's data from database.
     *
     * @param rentAct contains entered new final rent act's data.
     * @return true if the final rent act data was successfully updated. If
     * final rent act has not been updated, this method throws FinalActDAOException.
     * @throws FinalActDAOException when problems with database access occur.
     */
    @Override
    public boolean update(FinalRentAct rentAct) throws FinalActDAOException {
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
                throw new FinalActDAOException(UPDATE_FINAL_ACT_ERROR_MESSAGE);
            }
            return true;
        } catch (SQLException e) {
            throw new FinalActDAOException(UPDATE_FINAL_ACT_ERROR_MESSAGE, e);
        } finally {
            if (connection != null) {
                connectionPool.closeConnection(connection, preparedStatement);
            }
        }
    }
}
