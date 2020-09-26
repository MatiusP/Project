package by.epamtc.protsko.rentcar.dao.reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBPropertyReader implements PropertyReader {
	private static final String DB_PROPERTY_FILE = "dbConnection.properties";

	@Override
	public Properties readProperty() throws IOException {
		Properties property = new Properties();
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(DB_PROPERTY_FILE);
			property.load(fis);
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		return property;
	}
}
