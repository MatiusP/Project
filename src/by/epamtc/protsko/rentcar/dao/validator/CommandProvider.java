package by.epamtc.protsko.rentcar.dao.validator;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
	private static Map<UserCredentialType, CommandValidator> commands = new HashMap<>();

	public CommandProvider() {
		commands.put(UserCredentialType.LOGIN, new LoginValidatorCommand());
		commands.put(UserCredentialType.PASSWORD, new PasswordValidatorCommand());
		commands.put(UserCredentialType.SURNAME, new SurnameValidatorCommand());
		commands.put(UserCredentialType.NAME, new NameValidatorCommand());
		commands.put(UserCredentialType.PASSPORT_ID_NUMBER, new PassportIDValidatorCommand());
		commands.put(UserCredentialType.DRIVER_LICENSE, new DriverLicenseValidatorCommand());
		commands.put(UserCredentialType.DATE_OF_BIRTH, new DateOfBirthValidatorCommand());
		commands.put(UserCredentialType.E_MAIL, new EMailValidatorCommand());
		commands.put(UserCredentialType.PHONE, new PhoneValidatorCommand());
	}

	public CommandValidator getValidator(UserCredentialType type) {
		return commands.get(type);
	}
}
