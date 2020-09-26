package by.epamtc.protsko.rentcar.dao.util;

import java.io.IOException;

import by.epamtc.protsko.rentcar.bean.UserDTO;
import by.epamtc.protsko.rentcar.dao.exception.DAOException;
import by.epamtc.protsko.rentcar.dao.validator.CommandProvider;
import by.epamtc.protsko.rentcar.dao.validator.UserCredentialType;

public class UserUtil {
	private final CommandProvider validatorCommandProvider = new CommandProvider();

	public boolean isAuthenticationDataValid(String login, String password) throws DAOException {
		try {
			boolean isLoginValid = validatorCommandProvider.getValidator(UserCredentialType.LOGIN).execute(login);
			boolean isPassportValid = validatorCommandProvider.getValidator(UserCredentialType.PASSWORD)
					.execute(password);

			if (isLoginValid && isPassportValid) {
				return true;
			}
		} catch (IOException e) {
			throw new DAOException(e);
		}
		return false;
	}

	public boolean isRegistrationDataValid(UserDTO userDTO) throws DAOException {
		String userLogin = userDTO.getLogin();
		String userPassword = userDTO.getPassword();
		String userSurname = userDTO.getSurname();
		String userName = userDTO.getName();
		String userPassportID = userDTO.getPassportIdNumber();
		String userDriverLicense = userDTO.getDriverLicense();
		String userDateOfBirth = String.valueOf(userDTO.getDateOfBirth());
		String userEMail = userDTO.geteMail();
		String userPhone = userDTO.getPhone();

		try {
			boolean isLoginValid = validatorCommandProvider.getValidator(UserCredentialType.LOGIN).execute(userLogin);
			boolean isPasswordValid = validatorCommandProvider.getValidator(UserCredentialType.PASSWORD)
					.execute(userPassword);
			boolean isSurnameValid = validatorCommandProvider.getValidator(UserCredentialType.SURNAME)
					.execute(userSurname);
			boolean isNameValid = validatorCommandProvider.getValidator(UserCredentialType.NAME).execute(userName);
			boolean isPassportIDValid = validatorCommandProvider.getValidator(UserCredentialType.PASSPORT_ID_NUMBER)
					.execute(userPassportID);
			boolean isDriverLicenseValid = validatorCommandProvider.getValidator(UserCredentialType.DRIVER_LICENSE)
					.execute(userDriverLicense);
			boolean isDateOfBirthValid = validatorCommandProvider.getValidator(UserCredentialType.DATE_OF_BIRTH)
					.execute(userDateOfBirth);
			boolean isEMailValid = validatorCommandProvider.getValidator(UserCredentialType.E_MAIL).execute(userEMail);
			boolean isPhoneValid = validatorCommandProvider.getValidator(UserCredentialType.PHONE).execute(userPhone);

			if (isLoginValid && isPasswordValid && isSurnameValid && isNameValid && isPassportIDValid
					&& isDriverLicenseValid && isDateOfBirthValid && isEMailValid && isPhoneValid) {
				return true;
			}
		} catch (IOException e) {
			throw new DAOException(e);
		}
		return false;
	}

	public boolean registrationUser(UserDTO userDTO) {

		return false;
	}

}
