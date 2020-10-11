package by.epamtc.protsko.rentcar.service.util;

import by.epamtc.protsko.rentcar.bean.user.EditUserDTO;
import by.epamtc.protsko.rentcar.bean.user.FullUserDTO;
import by.epamtc.protsko.rentcar.service.exception.ServiceException;
import by.epamtc.protsko.rentcar.service.validator.CommandProvider;
import by.epamtc.protsko.rentcar.service.validator.UserCredentialType;

import java.time.LocalDate;

public class UserUtil {
    private static final CommandProvider validatorCommandProvider = new CommandProvider();

    private UserUtil() {
    }

    public static boolean isAuthenticationDataValid(String login, String password) throws ServiceException {
        boolean isLoginValid = validatorCommandProvider.getValidator(UserCredentialType.LOGIN).execute(login);
        boolean isPassportValid = validatorCommandProvider.getValidator(UserCredentialType.PASSWORD)
                .execute(password);

        if (isLoginValid && isPassportValid) {
            return true;
        } else {
            throw new ServiceException("Invalid login or password");
        }
    }

    public static boolean isRegistrationDataValid(FullUserDTO user) throws ServiceException {
        String userLogin = user.getLogin();
        String userPassword = user.getPassword();
        String userSurname = user.getSurname();
        String userName = user.getName();
        String userPassportID = user.getPassportIdNumber();
        String userDriverLicense = user.getDriverLicense();
        String userDateOfBirth = String.valueOf(user.getDateOfBirth());
        String userEMail = user.geteMail();
        String userPhone = user.getPhone();

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
            throw new ServiceException("Login invalid");
        } else if (!isPasswordValid) {
            throw new ServiceException("Password invalid");
        } else if (!isSurnameValid) {
            throw new ServiceException("Surname invalid");
        } else if (!isNameValid) {
            throw new ServiceException("Name invalid");
        } else if (!isPassportIDValid) {
            throw new ServiceException("PassportID invalid");
        } else if (!isDriverLicenseValid) {
            throw new ServiceException("DriverLicense invalid");
        } else if (!isDateOfBirthValid) {
            throw new ServiceException("Date of birth invalid");
        } else if (!isEMailValid) {
            throw new ServiceException("E-mail invalid");
        } else if (!isPhoneValid) {
            throw new ServiceException("Phone invalid");
        }
        return true;
    }

    public static boolean isEditUserDataValid(EditUserDTO user) throws ServiceException {
        String userLogin = user.getNewLogin();
        String newUserPassword = user.getNewPassword();
        String userSurname = user.getSurname();
        String userName = user.getName();
        String userPassportID = user.getPassportIdNumber();
        String userDriverLicense = user.getDriverLicense();
        String userDateOfBirth = String.valueOf(user.getDateOfBirth());
        String userEMail = user.geteMail();
        String userPhone = user.getPhone();

        boolean isLoginValid = validatorCommandProvider.getValidator(UserCredentialType.LOGIN).execute(userLogin);
        boolean isNewUserPasswordValid;
        if (!newUserPassword.isEmpty()) {
            isNewUserPasswordValid = validatorCommandProvider.getValidator(UserCredentialType.PASSWORD)
                    .execute(newUserPassword);
        } else {
            isNewUserPasswordValid = true;
        }
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
            throw new ServiceException("Login invalid");
        } else if (!isNewUserPasswordValid) {
            throw new ServiceException("New password invalid");
        } else if (!isSurnameValid) {
            throw new ServiceException("Surname invalid");
        } else if (!isNameValid) {
            throw new ServiceException("Name invalid");
        } else if (!isPassportIDValid) {
            throw new ServiceException("PassportID invalid");
        } else if (!isDriverLicenseValid) {
            throw new ServiceException("DriverLicense invalid");
        } else if (!isDateOfBirthValid) {
            throw new ServiceException("Date of birth invalid");
        } else if (!isEMailValid) {
            throw new ServiceException("E-mail invalid");
        } else if (!isPhoneValid) {
            throw new ServiceException("Phone invalid");
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
        final int role = userData.getRole();

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
        if (role != 0) {
            searchUserCriteria.append("role_id=").append(role).append(" ");
        }

        return searchUserCriteria.toString().trim().replace(" ", " AND ");
    }
}
