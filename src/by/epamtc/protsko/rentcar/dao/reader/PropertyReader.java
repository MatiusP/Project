package by.epamtc.protsko.rentcar.dao.reader;

import java.io.IOException;
import java.util.Properties;

public interface PropertyReader {
	
	String getPropertyValue(String key) throws IOException;

}
