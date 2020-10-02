package by.epamtc.protsko.rentcar.dao.util;

import by.epamtc.protsko.rentcar.bean.UserData;
import by.epamtc.protsko.rentcar.dao.exception.DAOException;
import by.epamtc.protsko.rentcar.dao.validator.CommandProvider;
import by.epamtc.protsko.rentcar.dao.validator.UserCredentialType;

public class UserUtil {
    private final CommandProvider validatorCommandProvider = new CommandProvider();

    public boolean isAuthenticationDataValid(String login, String password) throws DAOException {
        boolean isLoginValid = validatorCommandProvider.getValidator(UserCredentialType.LOGIN).execute(login);
        boolean isPassportValid = validatorCommandProvider.getValidator(UserCredentialType.PASSWORD)
                .execute(password);

        if (isLoginValid && isPassportValid) {
            return true;
        } else {
            throw new DAOException("Invalid login or password");
        }
    }

    public boolean isRegistrationDataValid(UserData userData) throws DAOException {
        String userLogin = userData.getLogin();
        String userPassword = userData.getPassword();
        String userSurname = userData.getSurname();
        String userName = userData.getName();
        String userPassportID = userData.getPassportIdNumber();
        String userDriverLicense = userData.getDriverLicense();
        String userDateOfBirth = String.valueOf(userData.getDateOfBirth());
        String userEMail = userData.geteMail();
        String userPhone = userData.getPhone();


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

        if (!isLoginValid) {
            throw new DAOException("Login invalid");
        } else if (!isPasswordValid) {
            throw new DAOException("Password invalid");
        } else if (!isSurnameValid) {
            throw new DAOException("Surname invalid");
        } else if (!isNameValid) {
            throw new DAOException("Name invalid");
        } else if (!isPassportIDValid) {
            throw new DAOException("PassportID invalid");
        } else if (!isDriverLicenseValid) {
            throw new DAOException("DriverLicense invalid");
        } else if (!isDateOfBirthValid) {
            throw new DAOException("Date of birth invalid");
        } else if (!isEMailValid) {
            throw new DAOException("E-mail invalid");
        } else if (!isPhoneValid) {
            throw new DAOException("Phone invalid");
        }
        return true;
    }
}
