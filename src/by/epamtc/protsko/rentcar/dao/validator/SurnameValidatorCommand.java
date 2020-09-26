package by.epamtc.protsko.rentcar.dao.validator;

import java.io.IOException;
import java.util.regex.Pattern;

//import by.epamtc.protsko.rentcar.dao.reader.PropertyReader;
//import by.epamtc.protsko.rentcar.dao.reader.PropertyReaderFactory;
//import by.epamtc.protsko.rentcar.dao.reader.PropertyType;

public class SurnameValidatorCommand implements CommandValidator {
	private static final String VALIDATOR_REGEX = "^.{1,75}$";
	
	//private PropertyReader propertyReader;
	private Pattern pattern;

	@Override
	public boolean execute(String userSurname) throws IOException {
		//propertyReader = new PropertyReaderFactory().getPropertyReader(PropertyType.VALIDATOR_REGEX_PROPERTY);
		//String surnameValidatorRegex = propertyReader.readProperty().getProperty("surname.validator.regex");
		//pattern = Pattern.compile(surnameValidatorRegex);
		
		pattern = Pattern.compile(VALIDATOR_REGEX);

		return pattern.matcher(userSurname).matches();
	}

	@Override
	public String getDataEntryRules() {
		return "";
	}
}
