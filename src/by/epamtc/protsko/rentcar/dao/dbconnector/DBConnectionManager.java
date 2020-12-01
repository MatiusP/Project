package by.epamtc.protsko.rentcar.dao.dbconnector;

import java.util.ResourceBundle;

/**
 * The class {@code DBConnectionManager} provides values from the .properties
 * file using the supplied key
 *
 * @author Matvey Protsko
 */

public class DBConnectionManager {
    private static final DBConnectionManager instance = new DBConnectionManager();
    private static ResourceBundle bundle = ResourceBundle.getBundle("property/dbConnectionParameter");

    private DBConnectionManager() {
    }

    public static DBConnectionManager getInstance() {
        return instance;
    }

    public String getPropertyValue(String key) {
        return bundle.getString(key);
    }
}
