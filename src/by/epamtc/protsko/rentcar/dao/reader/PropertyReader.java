package by.epamtc.protsko.rentcar.dao.reader;

import java.io.IOException;

public interface PropertyReader {
	
	String getPropertyValue(String key) throws IOException;

}
