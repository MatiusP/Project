package by.epamtc.protsko.rentcar.dao.validator;

import java.io.IOException;

public interface CommandValidator {

	boolean execute(String userDataParameter) throws IOException;

	String getDataEntryRules();

}
