package by.epamtc.protsko.rentcar.service.util;

import by.epamtc.protsko.rentcar.bean.user.EditUserDTO;
import by.epamtc.protsko.rentcar.bean.user.FullUserDTO;
import by.epamtc.protsko.rentcar.bean.user.Role;
import by.epamtc.protsko.rentcar.service.exception.UserServiceException;
import by.epamtc.protsko.rentcar.service.validator.user.UserCommandProvider;
import by.epamtc.protsko.rentcar.service.validator.user.UserCredentialType;

import java.time.LocalDate;

public class UserUtil {
    private static final UserCommandProvider VALIDATOR_USER_COMMAND_PROVIDER = new UserCommandProvider();
    private static final String LOGIN_OR_PASSWORD_INVALID_MESSAGE = "Invalid login or password";
    private static final String LOGIN_INVALID_MESSAGE = "Login invalid";
    private static final String PASSWORD_INVALID_MESSAGE = "Password invalid";
    private static final String NEW_PASSWORD_INVALID_MESSAGE = "New password invalid";
    private static final String SURNAME_INVALID_MESSAGE = "Surname invalid";
    private static final String NAME_INVALID_MESSAGE = "Name invalid";
    private static final String PASSPORT_INVALID_MESSAGE = "Passport ID number invalid";
    private static final String DRIVER_LICENSE_INVALID_MESSAGE = "Driver license number invalid";
    private static final String DATE_BIRTH_INVALID_MESSAGE = "Date of birth invalid";
    private static final String EMAIL_INVALID_MESSAGE = "E-mail invalid";
    private static final String PHONE_INVALID_MESSAGE = "Phone number invalid";
    private static final String USER_ACTIVE_STATUS = "ACTIVE";

    private UserUtil() {
    }

    public static boolean isAuthenticationDataValid(String login, String password) throws UserServiceException {
        boolean isLoginValid = VALIDATOR_USER_COMMAND_PROVIDER.getValidator(UserCredentialType.LOGIN).execute(login);
        boolean isPassportValid = VALIDATOR_USER_COMMAND_PROVIDER.getValidator(UserCredentialType.PASSWORD)
                .execute(password);

        if (isLoginValid && isPassportValid) {
            return true;
        } else {
            throw new UserServiceException(LOGIN_OR_PASSWORD_INVALID_MESSAGE);
        }
    }

    public static boolean isRegistrationDataValid(FullUserDTO user) throws UserServiceException {
        String userLogin = user.getLogin();
        String userPassword = user.getPassword();
        String userSurname = user.getSurname();
        String userName = user.getName();
        String userPassportID = user.getPassportIdNumber();
        String userDriverLicense = user.getDriverLicense();
        String userDateOfBirth = String.valueOf(user.getDateOfBirth());
        String userEMail = user.geteMail();
        String userPhone = user.getPhone();

        boolean isLoginValid = VALIDATOR_USER_COMMAND_PROVIDER.getValidator(UserCredentialType.LOGIN).execute(userLogin);
        boolean isPasswordValid = VALIDATOR_USER_COMMAND_PROVIDER.getValidator(UserCredentialType.PASSWORD)
                .execute(userPassword);
        boolean isSurnameValid = VALIDATOR_USER_COMMAND_PROVIDER.getValidator(UserCredentialType.SURNAME)
                .execute(userSurname);
        boolean isNameValid = VALIDATOR_USER_COMMAND_PROVIDER.getValidator(UserCredentialType.NAME).execute(userName);
        boolean isPassportIDValid = VALIDATOR_USER_COMMAND_PROVIDER.getValidator(UserCredentialType.PASSPORT_ID_NUMBER)
                .execute(userPassportID);
        boolean isDriverLicenseValid = VALIDATOR_USER_COMMAND_PROVIDER.getValidator(UserCredentialType.DRIVER_LICENSE)
                .execute(userDriverLicense);
        boolean isDateOfBirthValid = VALIDATOR_USER_COMMAND_PROVIDER.getValidator(UserCredentialType.DATE_OF_BIRTH)
                .execute(userDateOfBirth);
        boolean isEMailValid = VALIDATOR_USER_COMMAND_PROVIDER.getValidator(UserCredentialType.E_MAIL).execute(userEMail);
        boolean isPhoneValid = VALIDATOR_USER_COMMAND_PROVIDER.getValidator(UserCredentialType.PHONE).execute(userPhone);

        if (!isLoginValid) {
            throw new UserServiceException(LOGIN_INVALID_MESSAGE);
        } else if (!isPasswordValid) {
            throw new UserServiceException(PASSWORD_INVALID_MESSAGE);
        } else if (!isSurnameValid) {
            throw new UserServiceException(SURNAME_INVALID_MESSAGE);
        } else if (!isNameValid) {
            throw new UserServiceException(NAME_INVALID_MESSAGE);
        } else if (!isPassportIDValid) {
            throw new UserServiceException(PASSPORT_INVALID_MESSAGE);
        } else if (!isDriverLicenseValid) {
            throw new UserServiceException(DRIVER_LICENSE_INVALID_MESSAGE);
        } else if (!isDateOfBirthValid) {
            throw new UserServiceException(DATE_BIRTH_INVALID_MESSAGE);
        } else if (!isEMailValid) {
            throw new UserServiceException(EMAIL_INVALID_MESSAGE);
        } else if (!isPhoneValid) {
            throw new UserServiceException(PHONE_INVALID_MESSAGE);
        }
        return true;
    }

    public static boolean isEditUserDataValid(EditUserDTO user) throws UserServiceException {
        String userLogin = user.getNewLogin();
        String newUserPassword = user.getNewPassword();
        String userSurname = user.getSurname();
        String userName = user.getName();
        String userPassportID = user.getPassportIdNumber();
        String userDriverLicense = user.getDriverLicense();
        String userDateOfBirth = String.valueOf(user.getDateOfBirth());
        String userEMail = user.geteMail();
        String userPhone = user.getPhone();

        boolean isLoginValid = VALIDATOR_USER_COMMAND_PROVIDER.getValidator(UserCredentialType.LOGIN).execute(userLogin);
        boolean isNewUserPasswordValid;
        if (!newUserPassword.isEmpty()) {
            isNewUserPasswordValid = VALIDATOR_USER_COMMAND_PROVIDER.getValidator(UserCredentialType.PASSWORD)
                    .execute(newUserPassword);
        } else {
            isNewUserPasswordValid = true;
        }
        boolean isSurnameValid = VALIDATOR_USER_COMMAND_PROVIDER.getValidator(UserCredentialType.SURNAME)
                .execute(userSurname);
        boolean isNameValid = VALIDATOR_USER_COMMAND_PROVIDER.getValidator(UserCredentialType.NAME).execute(userName);
        boolean isPassportIDValid = VALIDATOR_USER_COMMAND_PROVIDER.getValidator(UserCredentialType.PASSPORT_ID_NUMBER)
                .execute(userPassportID);
        boolean isDriverLicenseValid = VALIDATOR_USER_COMMAND_PROVIDER.getValidator(UserCredentialType.DRIVER_LICENSE)
                .execute(userDriverLicense);
        boolean isDateOfBirthValid = VALIDATOR_USER_COMMAND_PROVIDER.getValidator(UserCredentialType.DATE_OF_BIRTH)
                .execute(userDateOfBirth);
        boolean isEMailValid = VALIDATOR_USER_COMMAND_PROVIDER.getValidator(UserCredentialType.E_MAIL).execute(userEMail);
        boolean isPhoneValid = VALIDATOR_USER_COMMAND_PROVIDER.getValidator(UserCredentialType.PHONE).execute(userPhone);

        if (!isLoginValid) {
            throw new UserServiceException(LOGIN_INVALID_MESSAGE);
        } else if (!isNewUserPasswordValid) {
            throw new UserServiceException(NEW_PASSWORD_INVALID_MESSAGE);
        } else if (!isSurnameValid) {
            throw new UserServiceException(SURNAME_INVALID_MESSAGE);
        } else if (!isNameValid) {
            throw new UserServiceException(NAME_INVALID_MESSAGE);
        } else if (!isPassportIDValid) {
            throw new UserServiceException(PASSPORT_INVALID_MESSAGE);
        } else if (!isDriverLicenseValid) {
            throw new UserServiceException(DRIVER_LICENSE_INVALID_MESSAGE);
        } else if (!isDateOfBirthValid) {
            throw new UserServiceException(DATE_BIRTH_INVALID_MESSAGE);
        } else if (!isEMailValid) {
            throw new UserServiceException(EMAIL_INVALID_MESSAGE);
        } else if (!isPhoneValid) {
            throw new UserServiceException(PHONE_INVALID_MESSAGE);
        }
        return true;
    }

    public static boolean isRegistrationFormFilled(FullUserDTO userData) {
        String userLogin = userData.getLogin();
        String userPassword = userData.getPassword();
        String userSurname = userData.getSurname();
        String userName = userData.getName();
        String userPassportID = userData.getPassportIdNumber();
        String userDriverLicense = userData.getDriverLicense();
        String userDateOfBirth = String.valueOf(userData.getDateOfBirth());
        String userEMail = userData.geteMail();
        String userPhone = userData.getPhone();

        boolean isLoginFilled = ((userLogin != null) && (!userLogin.isEmpty()));
        boolean isPasswordFilled = ((userPassword != null) && (!userPassword.isEmpty()));
        boolean isSurnameFilled = ((userSurname != null) && (!userSurname.isEmpty()));
        boolean isNameFilled = ((userName != null) && (!userName.isEmpty()));
        boolean isPassportIDFilled = ((userPassportID != null) && (!userPassportID.isEmpty()));
        boolean isDriverLicenseFilled = ((userDriverLicense != null) && (!userDriverLicense.isEmpty()));
        boolean isDateOfBirthFilled = ((userDateOfBirth != null) && (!userDateOfBirth.isEmpty()));
        boolean isEMailFilled = ((userEMail != null) && (!userEMail.isEmpty()));
        boolean isPhoneFilled = ((userPhone != null) && (!userPhone.isEmpty()));

        return (isLoginFilled && isPasswordFilled && isSurnameFilled && isNameFilled && isPassportIDFilled
                && isDriverLicenseFilled && isDateOfBirthFilled && isEMailFilled && isPhoneFilled);
    }

    public static String createSearchUserQuery(FullUserDTO userData) {
        final int id = userData.getId();
        final String login = userData.getLogin();
        final String surname = userData.getSurname();
        final String name = userData.getName();
        final String passportIdNumber = userData.getPassportIdNumber();
        final String driverLicense = userData.getDriverLicense();
        final LocalDate dateOfBirth = userData.getDateOfBirth();
        final String eMail = userData.geteMail();
        final String phone = userData.getPhone();
        final String status = userData.getStatus();
        final String role = userData.getRole();

        StringBuilder searchUserCriteria = new StringBuilder();

        if (id != 0) {
            searchUserCriteria.append("id=").append(id).append(" ");
        }
        if (login != null) {
            searchUserCriteria.append("login=" + "'").append(login).append("'").append(" ");
        }
        if (surname != null) {
            searchUserCriteria.append("surname=" + "'").append(surname).append("'").append(" ");
        }
        if (name != null) {
            searchUserCriteria.append("name=" + "'").append(name).append("'").append(" ");
        }
        if (passportIdNumber != null) {
            searchUserCriteria.append("passport_id_number=" + "'").append(passportIdNumber).append("'").append(" ");
        }
        if (driverLicense != null) {
            searchUserCriteria.append("driver_license=" + "'").append(driverLicense).append("'").append(" ");
        }
        if (dateOfBirth != null) {
            searchUserCriteria.append("date_of_birth=" + "'").append(dateOfBirth).append("'").append(" ");
        }
        if (eMail != null) {
            searchUserCriteria.append("e_mail=" + "'").append(eMail).append("'").append(" ");
        }
        if (phone != null) {
            searchUserCriteria.append("phone=" + "'").append(phone).append("'").append(" ");
        }
        if (status.equals(USER_ACTIVE_STATUS)) {
            searchUserCriteria.append("is_deleted=").append(false).append(" ");
        } else {
            searchUserCriteria.append("is_deleted=").append(true).append(" ");
        }
        if (role != null) {
            searchUserCriteria.append("role_id=").append(Role.valueOf(role).ordinal() + 1).append(" ");
        }

        return searchUserCriteria.toString().trim().replace(" ", " AND ");
    }
}
