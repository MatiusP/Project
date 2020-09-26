package by.epamtc.protsko.rentcar.service.validator;

import by.epamtc.protsko.rentcar.bean.UserDTO;

public class UserServiceValidator {


    public boolean isRegistrationDataFilled(UserDTO userDTO) {
        String userLogin = userDTO.getLogin();
        String userPassword = userDTO.getPassword();
        String userSurname = userDTO.getSurname();
        String userName = userDTO.getName();
        String userPassportID = userDTO.getPassportIdNumber();
        String userDriverLicense = userDTO.getDriverLicense();
        String userDateOfBirth = String.valueOf(userDTO.getDateOfBirth());
        String userEMail = userDTO.geteMail();
        String userPhone = userDTO.getPhone();

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
