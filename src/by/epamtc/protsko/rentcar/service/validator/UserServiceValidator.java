package by.epamtc.protsko.rentcar.service.validator;

import by.epamtc.protsko.rentcar.bean.user.FullUserDTO;

public class UserServiceValidator {

    public boolean isRegistrationDataFilled(FullUserDTO userData) {
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

        if (isLoginFilled && isPasswordFilled && isSurnameFilled && isNameFilled && isPassportIDFilled
                && isDriverLicenseFilled && isDateOfBirthFilled && isEMailFilled && isPhoneFilled) {
            return true;
        }
        return false;
    }
}
