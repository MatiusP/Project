package by.epamtc.protsko.rentcar.dao.dbconnector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

/**
 * The class {@code ConnectionPool} provides an implementation
 * to manage access to the {@code Connection} objects
 *
 * @author Matvey Protsko
 * @see DBConnectionManager
 * @see DBConnectionParameter
 */

public final class ConnectionPool {
    private static final ConnectionPool instance = new ConnectionPool();
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);

    /**
     * This queue stores {@code Connection} objects, which are available
     * for using.
     */
    private static BlockingQueue<Connection> connectionQueue;

    /**
     * This queue stores {@code Connection} objects that are currently
     * being used by the program.
     */
    private static BlockingQueue<Connection> givenAwayConnectionQueue;

    private static String driverName;
    private static String connectionLogin;
    private static String connectionPassword;
    private static int poolSize;


    /**
     * {@code ConnectionPool} constructor
     */
    private ConnectionPool() {
    }

    /**
     * Initialization of the queues with {@code PooledConnection} objects
     */
    private static void initConnectionPool(String jdbcUrl) {
        try {
            Class.forName(driverName);
            connectionQueue = new ArrayBlockingQueue<>(poolSize);
            givenAwayConnectionQueue = new ArrayBlockingQueue<>(poolSize);

            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(jdbcUrl, connectionLogin, connectionPassword);
                PooledConnection pooledConnection = new ConnectionPool.PooledConnection(connection);
                connectionQueue.add(pooledConnection);
            }
        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolException("DB driver not found", e);
        } catch (SQLException e) {
            throw new ConnectionPoolException("SQL exception in database pool", e);
        }
    }

    /**
     * {@code ConnectionPool} initializes parameters for database connection and
     * get instance access point
     *
     * @param isDBForApplication if {@code isDBForApplication} true, the method provides
     *                           a connection to the application database, else - method
     *                           provides a connection to the database for testing.
     * @return instance of the {@code ConnectionPool} class
     */
    public static ConnectionPool getInstance(boolean isDBForApplication) {
        String jdbcURL = null;
        DBConnectionManager dbConnectionManager = DBConnectionManager.getInstance();
        driverName = dbConnectionManager.getApplicationPropertyValue(DBConnectionParameter.DB_DRIVER);
        connectionLogin = dbConnectionManager.getApplicationPropertyValue(DBConnectionParameter.DB_CONNECTION_LOGIN);
        connectionPassword = dbConnectionManager.getApplicationPropertyValue(DBConnectionParameter.DB_CONNECTION_PASSWORD);

        if (isDBForApplication) {
            jdbcURL = dbConnectionManager.getApplicationPropertyValue(DBConnectionParameter.JDBC_URL);
        } else {
            jdbcURL = dbConnectionManager.getTestPropertyValue(DBConnectionParameter.JDBC_URL);
        }

        try {
            poolSize = Integer.parseInt(dbConnectionManager.getApplicationPropertyValue(DBConnectionParameter.DB_POOL_SIZE));
        } catch (NumberFormatException e) {
            logger.error("Incorrect connection pool size value in property file.");
            poolSize = 15;
        }
        initConnectionPool(jdbcURL);
        return instance;
    }

    public Connection takeConnection() {
        Connection connection;

        try {
            connection = connectionQueue.take();
            givenAwayConnectionQueue.add(connection);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ConnectionPoolException("Error connection to the data source", e);
        }
        return connection;
    }

    public void closeConnection(Connection connection, Statement statement) {
        try {
            statement.close();
        } catch (SQLException e) {
            logger.error("Statement closing error", e);

        }
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error("Connection closing error", e);
        }
    }

    public void closeConnection(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            resultSet.close();
        } catch (SQLException e) {
            logger.error("ResultSet closing error", e);

        }
        try {
            statement.close();
        } catch (SQLException e) {
            logger.error("Statement closing error", e);

        }
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error("Connection closing error", e);
        }
    }

    public void dispose() throws SQLException {
        clearConnectionQueue();
    }

    private void clearConnectionQueue() throws SQLException {
        closeConnectionQueue(connectionQueue);
        closeConnectionQueue(givenAwayConnectionQueue);
    }

    private void closeConnectionQueue(BlockingQueue<Connection> queue) throws SQLException {
        Connection connection;

        while ((connection = queue.poll()) != null) {
            if (!connection.getAutoCommit()) {
                connection.commit();
            }
            ((PooledConnection) connection).reallyClose();
        }
    }

    /**
     * Wrapper {@code PooledConnection} class for {@code Connection}
     * Changes default behavior for the close() method to removing and offering
     * connection instances using queues
     */
    private static class PooledConnection implements Connection {
        private Connection connection;

        public PooledConnection(Connection connection) throws SQLException {
            this.connection = connection;
            this.connection.setAutoCommit(true);
        }

        public void reallyClose() throws SQLException {
            connection.close();
        }

        @Override
        public void close() throws SQLException {
            if (connection.isClosed()) {
                throw new SQLException("Attempting to close closed connection");
            }
            if (connection.isReadOnly()) {
                connection.setReadOnly(false);
            }
            if (!givenAwayConnectionQueue.remove(this)) {
                throw new SQLException("Error deleting connection from the given away connection pool");
            }
            if (!connectionQueue.offer(this)) {
                throw new SQLException("Error allocating connection in the pool");
            }
        }

        @Override
        public Statement createStatement() throws SQLException {
            return connection.createStatement();
        }

        @Override
        public PreparedStatement prepareStatement(String sql) throws SQLException {
            return connection.prepareStatement(sql);
        }

        @Override
        public CallableStatement prepareCall(String sql) throws SQLException {
            return connection.prepareCall(sql);
        }

        @Override
        public String nativeSQL(String sql) throws SQLException {
            return connection.nativeSQL(sql);
        }

        @Override
        public void setAutoCommit(boolean autoCommit) throws SQLException {
            connection.setAutoCommit(autoCommit);
        }

        @Override
        public boolean getAutoCommit() throws SQLException {
            return connection.getAutoCommit();
        }

        @Override
        public void commit() throws SQLException {
            connection.commit();
        }

        @Override
        public void rollback() throws SQLException {
            connection.rollback();
        }

        @Override
        public boolean isClosed() throws SQLException {
            return connection.isClosed();
        }

        @Override
        public DatabaseMetaData getMetaData() throws SQLException {
            return connection.getMetaData();
        }

        @Override
        public void setReadOnly(boolean readOnly) throws SQLException {
            connection.setReadOnly(readOnly);
        }

        @Override
        public boolean isReadOnly() throws SQLException {
            return connection.isReadOnly();
        }

        @Override
        public void setCatalog(String catalog) throws SQLException {
            connection.setCatalog(catalog);
        }

        @Override
        public String getCatalog() throws SQLException {
            return connection.getCatalog();
        }

        @Override
        public void setTransactionIsolation(int level) throws SQLException {
            connection.setTransactionIsolation(level);
        }

        @Override
        public int getTransactionIsolation() throws SQLException {
            return connection.getTransactionIsolation();
        }

        @Override
        public SQLWarning getWarnings() throws SQLException {
            return connection.getWarnings();
        }

        @Override
        public void clearWarnings() throws SQLException {
            connection.clearWarnings();
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.createStatement(resultSetType, resultSetConcurrency);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
        }

        @Override
        public Map<String, Class<?>> getTypeMap() throws SQLException {
            return connection.getTypeMap();
        }

        @Override
        public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
            connection.setTypeMap(map);
        }

        @Override
        public void setHoldability(int holdability) throws SQLException {
            connection.setHoldability(holdability);
        }

        @Override
        public int getHoldability() throws SQLException {
            return connection.getHoldability();
        }

        @Override
        public Savepoint setSavepoint() throws SQLException {
            return connection.setSavepoint();
        }

        @Override
        public Savepoint setSavepoint(String name) throws SQLException {
            return connection.setSavepoint(name);
        }

        @Override
        public void rollback(Savepoint savepoint) throws SQLException {
            connection.rollback(savepoint);
        }

        @Override
        public void releaseSavepoint(Savepoint savepoint) throws SQLException {
            connection.releaseSavepoint(savepoint);
        }

        @Override
        public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return connection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return connection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
            return connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
            return connection.prepareStatement(sql, autoGeneratedKeys);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
            return connection.prepareStatement(sql, columnIndexes);
        }

        @Override
        public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
            return connection.prepareStatement(sql, columnNames);
        }

        @Override
        public Clob createClob() throws SQLException {
            return connection.createClob();
        }

        @Override
        public Blob createBlob() throws SQLException {
            return connection.createBlob();
        }

        @Override
        public NClob createNClob() throws SQLException {
            return connection.createNClob();
        }

        @Override
        public SQLXML createSQLXML() throws SQLException {
            return connection.createSQLXML();
        }

        @Override
        public boolean isValid(int timeout) throws SQLException {
            return connection.isValid(timeout);
        }

        @Override
        public void setClientInfo(String name, String value) throws SQLClientInfoException {
            connection.setClientInfo(name, value);
        }

        @Override
        public void setClientInfo(Properties properties) throws SQLClientInfoException {
            connection.setClientInfo(properties);
        }

        @Override
        public String getClientInfo(String name) throws SQLException {
            return connection.getClientInfo(name);
        }

        @Override
        public Properties getClientInfo() throws SQLException {
            return connection.getClientInfo();
        }

        @Override
        public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
            return connection.createArrayOf(typeName, elements);
        }

        @Override
        public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
            return connection.createStruct(typeName, attributes);
        }

        @Override
        public void setSchema(String schema) throws SQLException {
            connection.setSchema(schema);
        }

        @Override
        public String getSchema() throws SQLException {
            return connection.getSchema();
        }

        @Override
        public void abort(Executor executor) throws SQLException {
            connection.abort(executor);
        }

        @Override
        public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
            connection.setNetworkTimeout(executor, milliseconds);
        }

        @Override
        public int getNetworkTimeout() throws SQLException {
            return connection.getNetworkTimeout();
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return connection.unwrap(iface);
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return connection.isWrapperFor(iface);
        }
    }
}
