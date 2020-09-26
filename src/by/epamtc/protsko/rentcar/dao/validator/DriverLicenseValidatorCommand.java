package by.epamtc.protsko.rentcar.dao.validator;

import java.io.IOException;
import java.util.regex.Pattern;

//import by.epamtc.protsko.rentcar.dao.reader.PropertyReader;
//import by.epamtc.protsko.rentcar.dao.reader.PropertyReaderFactory;
//import by.epamtc.protsko.rentcar.dao.reader.PropertyType;

public class DriverLicenseValidatorCommand implements CommandValidator {
	private static final String VALIDATOR_REGEX = "^(?!^0+$)[a-zA-Z0-9]{3,14}$";
	//private PropertyReader propertyReader;
	private Pattern pattern;

	@Override
	public boolean execute(String userDriverLicense) throws IOException {
		//propertyReader = new PropertyReaderFactory().getPropertyReader(PropertyType.VALIDATOR_REGEX_PROPERTY);
		//String driverLicenseVakidatorRegex = propertyReader.readProperty().getProperty("driverLicense.validator.regex");
		//pattern = Pattern.compile(driverLicenseVakidatorRegex);
		
		pattern = Pattern.compile(VALIDATOR_REGEX);

		return pattern.matcher(userDriverLicense).matches();
	}

	@Override
	public String getDataEntryRules() {
		return "";
	}
}
