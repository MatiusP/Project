package by.epamtc.protsko.rentcar.dao.validator;

import java.io.IOException;
import java.util.regex.Pattern;

//import by.epamtc.protsko.rentcar.dao.reader.PropertyReader;
//import by.epamtc.protsko.rentcar.dao.reader.PropertyReaderFactory;
//import by.epamtc.protsko.rentcar.dao.reader.PropertyType;

public class PassportIDValidatorCommand implements CommandValidator {
	private static final String VALIDATOR_REGEX = "^(?!^0+$)[a-zA-Z0-9]{3,14}$";

	//private PropertyReader propeptyReader;
	private Pattern pattern;

	@Override
	public boolean execute(String userPassportIDNumber) throws IOException {
		//propeptyReader = new PropertyReaderFactory().getPropertyReader(PropertyType.VALIDATOR_REGEX_PROPERTY);
		//String passportIDValidatorRegex = propeptyReader.readProperty().getProperty("passportIdNumber.validator.regex");
		//pattern = Pattern.compile(passportIDValidatorRegex);
		
		pattern = Pattern.compile(VALIDATOR_REGEX);

		return pattern.matcher(userPassportIDNumber).matches();
	}

	@Override
	public String getDataEntryRules() {
		return "";
	}
}
