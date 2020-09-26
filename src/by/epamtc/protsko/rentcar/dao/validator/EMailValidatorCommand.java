package by.epamtc.protsko.rentcar.dao.validator;

import java.io.IOException;
import java.util.regex.Pattern;

//import by.epamtc.protsko.rentcar.dao.reader.PropertyReader;
//import by.epamtc.protsko.rentcar.dao.reader.PropertyReaderFactory;
//import by.epamtc.protsko.rentcar.dao.reader.PropertyType;

public class EMailValidatorCommand implements CommandValidator {
	private static final String VALIDATOR_REGEX = "^[a-zA-Z]{1}[a-zA-Z\\d\\u002E\\u005F]+@([a-zA-Z]+\\u002E){1,2}[a-z]{1,3}$";
	//private PropertyReader propertyReader;
	private Pattern pattern;

	@Override
	public boolean execute(String userEMail) throws IOException {
		//propertyReader = new PropertyReaderFactory().getPropertyReader(PropertyType.VALIDATOR_REGEX_PROPERTY);
		//String eMailValidatorRegex = propertyReader.readProperty().getProperty("email.validator.regex");
		//pattern = Pattern.compile(eMailValidatorRegex);
		
		pattern = Pattern.compile(VALIDATOR_REGEX);

		return pattern.matcher(userEMail).matches();
	}

	@Override
	public String getDataEntryRules() {
		return "";
	}
}
