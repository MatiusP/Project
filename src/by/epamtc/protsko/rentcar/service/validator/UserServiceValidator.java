package by.epamtc.protsko.rentcar.service.validator;

import by.epamtc.protsko.rentcar.bean.User;

public class UserServiceValidator {

    public boolean isRegistrationDataFilled(User user) {
        String userLogin = user.getLogin();
        String userPassword = user.getPassword();
        String userSurname = user.getSurname();
        String userName = user.getName();
        String userPassportID = user.getPassportIdNumber();
        String userDriverLicense = user.getDriverLicense();
        String userDateOfBirth = String.valueOf(user.getDateOfBirth());
        String userEMail = user.geteMail();
        String userPhone = user.getPhone();

        boolean isLoginFilled = ((userLogin != null) && (!userLogin.isEmpty()));
        boolean isPasswordFilled = ((userPassword != null) && (!userPassword.isEmpty()));
        boolean isSurnameFilled = ((userSurname != null) && (!userSurname.isEmpty()));
        boolean isNameFilled = ((userName != null) && (!userName.isEmpty()));
        boolean isPassportIDFilled = ((userPassportID != null) && (!userPassportID.isEmpty()));
        boolean isDriverLicenseFilled = ((userDriverLicense != null) && (!userDriverLicense.isEmpty()));
        boolean isDateOfBirthFilled = ((userDateOfBirth != null) && (!userDateOfBirth.isEmpty()));
        boolean isEMailFilled = ((userEMail != null) && (!userEMail.isEmpty()));
        boolean isPhoneFilled = ((userPhone != null) && (!userPhone.isEmpty()));

        if (isLoginFilled && isPasswordFilled && isSurnameFilled && isNameFilled && isPassportIDFilled
                && isDriverLicenseFilled && isDateOfBirthFilled && isEMailFilled && isPhoneFilled) {
            return true;
        }
        return false;
    }

}
