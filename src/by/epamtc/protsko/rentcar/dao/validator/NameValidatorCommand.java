package by.epamtc.protsko.rentcar.dao.validator;

import java.io.IOException;
import java.util.regex.Pattern;

//import by.epamtc.protsko.rentcar.dao.reader.PropertyReader;
//import by.epamtc.protsko.rentcar.dao.reader.PropertyReaderFactory;
//import by.epamtc.protsko.rentcar.dao.reader.PropertyType;

public class NameValidatorCommand implements CommandValidator {
	private static final String VALIDATOR_REGEX = "^.{1,75}$";
	
	//private PropertyReader propertyReader;
	private Pattern pattern;

	@Override
	public boolean execute(String userName) throws IOException {
		//propertyReader = new PropertyReaderFactory().getPropertyReader(PropertyType.VALIDATOR_REGEX_PROPERTY);
		//String nameValidatorRegex = propertyReader.readProperty().getProperty("name.validator.regex");
		//pattern = Pattern.compile(nameValidatorRegex);
		
		pattern = Pattern.compile(VALIDATOR_REGEX);

		return pattern.matcher(userName).matches();
	}

	@Override
	public String getDataEntryRules() {
		return "";
	}
}
