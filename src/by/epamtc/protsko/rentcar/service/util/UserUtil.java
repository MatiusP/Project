package by.epamtc.protsko.rentcar.service.util;

import by.epamtc.protsko.rentcar.bean.user.EditUserDTO;
import by.epamtc.protsko.rentcar.bean.user.FullUserDTO;
import by.epamtc.protsko.rentcar.bean.user.Role;
import by.epamtc.protsko.rentcar.service.exception.UserServiceException;
import by.epamtc.protsko.rentcar.service.validator.UserCommandProvider;
import by.epamtc.protsko.rentcar.service.validator.UserCredentialType;

import java.time.LocalDate;

public class UserUtil {
    private static final UserCommandProvider VALIDATOR_USER_COMMAND_PROVIDER = new UserCommandProvider();

    private UserUtil() {
    }

    public static boolean isAuthenticationDataValid(String login, String password) throws UserServiceException {
        boolean isLoginValid = VALIDATOR_USER_COMMAND_PROVIDER.getValidator(UserCredentialType.LOGIN).execute(login);
        boolean isPassportValid = VALIDATOR_USER_COMMAND_PROVIDER.getValidator(UserCredentialType.PASSWORD)
                .execute(password);

        if (isLoginValid && isPassportValid) {
            return true;
        } else {
            throw new UserServiceException("Invalid login or password");
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
            throw new UserServiceException("Login invalid");
        } else if (!isPasswordValid) {
            throw new UserServiceException("Password invalid");
        } else if (!isSurnameValid) {
            throw new UserServiceException("Surname invalid");
        } else if (!isNameValid) {
            throw new UserServiceException("Name invalid");
        } else if (!isPassportIDValid) {
            throw new UserServiceException("PassportID invalid");
        } else if (!isDriverLicenseValid) {
            throw new UserServiceException("DriverLicense invalid");
        } else if (!isDateOfBirthValid) {
            throw new UserServiceException("Date of birth invalid");
        } else if (!isEMailValid) {
            throw new UserServiceException("E-mail invalid");
        } else if (!isPhoneValid) {
            throw new UserServiceException("Phone invalid");
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
            throw new UserServiceException("Login invalid");
        } else if (!isNewUserPasswordValid) {
            throw new UserServiceException("New password invalid");
        } else if (!isSurnameValid) {
            throw new UserServiceException("Surname invalid");
        } else if (!isNameValid) {
            throw new UserServiceException("Name invalid");
        } else if (!isPassportIDValid) {
            throw new UserServiceException("PassportID invalid");
        } else if (!isDriverLicenseValid) {
            throw new UserServiceException("DriverLicense invalid");
        } else if (!isDateOfBirthValid) {
            throw new UserServiceException("Date of birth invalid");
        } else if (!isEMailValid) {
            throw new UserServiceException("E-mail invalid");
        } else if (!isPhoneValid) {
            throw new UserServiceException("Phone invalid");
        }
        return true;
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
        final boolean status = userData.isDeleted();
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
        searchUserCriteria.append("is_deleted=").append(status).append(" ");
        if (role != null) {
            searchUserCriteria.append("role_id=").append(Role.valueOf(role).ordinal() + 1).append(" ");
        }

        return searchUserCriteria.toString().trim().replace(" ", " AND ");
    }
}
