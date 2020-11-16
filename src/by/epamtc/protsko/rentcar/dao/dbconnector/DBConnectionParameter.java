package by.epamtc.protsko.rentcar.dao.dbconnector;

/**
 * The class {@code DBConnectionParameter} stores parameter names that are
 * necessary to connect to the database with jdbc
 *
 * @author Matvey Protsko
 */

public class DBConnectionParameter {
    public static final String DB_DRIVER = "db.driver";
    public static final String JDBC_URL = "jdbc.url";
    public static final String DB_CONNECTION_LOGIN = "db.connection.login";
    public static final String DB_CONNECTION_PASSWORD = "db.connection.password";
    public static final String DB_POOL_SIZE = "db.poolsize";

    private DBConnectionParameter() {
    }
}
