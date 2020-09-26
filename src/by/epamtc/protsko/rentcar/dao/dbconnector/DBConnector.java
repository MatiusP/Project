package by.epamtc.protsko.rentcar.dao.dbconnector;

//import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import by.epamtc.protsko.rentcar.dao.exception.DAOException;
//import by.epamtc.protsko.rentcar.dao.reader.PropertyReader;
//import by.epamtc.protsko.rentcar.dao.reader.PropertyReaderFactory;
//import by.epamtc.protsko.rentcar.dao.reader.PropertyType;

public class DBConnector {
	//private static final PropertyReader propertyReader = new PropertyReaderFactory().getPropertyReader(PropertyType.DB_CONNECTION_PROPERTY);

	private static final DBConnector instance = new DBConnector();

	private DBConnector() {
	}

	public static DBConnector getInstance() {
		return instance;
	}

	public final Connection getDBConnection() throws DAOException {
		return DBConnector.connectToDB();
	}

	private static Connection connectToDB() throws DAOException {
		String dbDriverName;
		String jdbcURL;
		String connectionDBLogin;
		String connectionDBPassword;

		Connection connection = null;

		try {
			//dbDriverName = propertyReader.readProperty().getProperty("db.driver");
			//jdbcURL = propertyReader.readProperty().getProperty("jdbc.url");
			//connectionDBLogin = propertyReader.readProperty().getProperty("db.connection.login");
			//connectionDBPassword = propertyReader.readProperty().getProperty("db.connection.password");
			
			dbDriverName = "com.mysql.cj.jdbc.Driver";
			jdbcURL = "jdbc:mysql://127.0.0.1/rentcar_db?serverTimezone=UTC&useSSL=true";
			connectionDBLogin = "root";
			connectionDBPassword = "root";
			
			Class.forName(dbDriverName);
			connection = DriverManager.getConnection(jdbcURL, connectionDBLogin, connectionDBPassword);

			return connection;
		} catch (ClassNotFoundException e) {
			throw new DAOException("Database driver is not found", e);
		} catch (SQLException e) {
			throw new DAOException("Database's connection error", e);
		}
	}
}