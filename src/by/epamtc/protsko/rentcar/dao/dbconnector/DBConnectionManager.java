package by.epamtc.protsko.rentcar.dao.dbconnector;

import java.util.ResourceBundle;

/**
 * The class {@code DBConnectionManager} provides values from the .properties
 * file using the supplied key.
 * This class reads .property files for connecting to the
 * application database or to the test database.
 *
 * @author Matvey Protsko
 */

public class DBConnectionManager {
    private static final DBConnectionManager instance = new DBConnectionManager();
    private static ResourceBundle applicationBundle = ResourceBundle.getBundle("property/dbConnectionParameter");
    private static ResourceBundle testBundle = ResourceBundle.getBundle("test/property/dbConnectionParameter");

    private DBConnectionManager() {
    }

    public static DBConnectionManager getInstance() {
        return instance;
    }

    public String getApplicationPropertyValue(String key) {
        return applicationBundle.getString(key);
    }

    public String getTestPropertyValue(String key) {
        return testBundle.getString(key);
    }
}
