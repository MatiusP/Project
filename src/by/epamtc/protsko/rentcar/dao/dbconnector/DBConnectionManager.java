package by.epamtc.protsko.rentcar.dao.dbconnector;

import java.util.ResourceBundle;

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
