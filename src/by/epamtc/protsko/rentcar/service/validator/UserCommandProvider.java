package by.epamtc.protsko.rentcar.service.validator;

import java.util.HashMap;
import java.util.Map;

public class UserCommandProvider {
	private static Map<UserCredentialType, UserCommandValidator> commands = new HashMap<>();

	public UserCommandProvider() {
		commands.put(UserCredentialType.LOGIN, new LoginValidatorUserCommand());
		commands.put(UserCredentialType.PASSWORD, new PasswordValidatorUserCommand());
		commands.put(UserCredentialType.SURNAME, new SurnameValidatorUserCommand());
		commands.put(UserCredentialType.NAME, new NameValidatorUserCommand());
		commands.put(UserCredentialType.PASSPORT_ID_NUMBER, new PassportIDValidatorUserCommand());
		commands.put(UserCredentialType.DRIVER_LICENSE, new DriverLicenseValidatorUserCommand());
		commands.put(UserCredentialType.DATE_OF_BIRTH, new DateOfBirthValidatorUserCommand());
		commands.put(UserCredentialType.E_MAIL, new EMailValidatorUserCommand());
		commands.put(UserCredentialType.PHONE, new PhoneValidatorUserCommand());
	}

	public UserCommandValidator getValidator(UserCredentialType type) {
		return commands.get(type);
	}
}
