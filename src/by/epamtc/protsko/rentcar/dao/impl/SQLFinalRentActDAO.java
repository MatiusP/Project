package by.epamtc.protsko.rentcar.dao.impl;

import by.epamtc.protsko.rentcar.bean.order.FinalRentAct;
import by.epamtc.protsko.rentcar.dao.FinalRentActDAO;
import by.epamtc.protsko.rentcar.dao.dbconnector.ConnectionPool;
import by.epamtc.protsko.rentcar.dao.exception.FinalActDAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLFinalRentActDAO implements FinalRentActDAO {
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
