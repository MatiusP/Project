package by.epamtc.protsko.rentcar.dao.validator;

import java.io.IOException;
import java.util.regex.Pattern;

//import by.epamtc.protsko.rentcar.dao.reader.PropertyReader;
//import by.epamtc.protsko.rentcar.dao.reader.PropertyReaderFactory;
//import by.epamtc.protsko.rentcar.dao.reader.PropertyType;

public class PasswordValidatorCommand implements CommandValidator {
	private static final String VALIDATOR_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{5,45}$";
	
	//private PropertyReader propertyReader;
	private Pattern pattern;

	@Override
	public boolean execute(String userPassword) throws IOException {
		//propertyReader = new PropertyReaderFactory().getPropertyReader(PropertyType.VALIDATOR_REGEX_PROPERTY);
		//String passwordValidatorRegex = propertyReader.readProperty().getProperty("password.validator.regex");
		//pattern = Pattern.compile(passwordValidatorRegex);
		
		pattern = Pattern.compile(VALIDATOR_REGEX);

		return pattern.matcher(userPassword).matches();
	}

	@Override
	public String getDataEntryRules() {
		return "Пароль должен содержать только латинские буквы (минимум одна строчная и одна прописная), минимум одну цифру"
				+ " и минимум один спецсимвол. Длина пароля: от 5 до 45 символов";
	}
}
