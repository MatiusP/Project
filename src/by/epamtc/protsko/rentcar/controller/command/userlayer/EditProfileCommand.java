package by.epamtc.protsko.rentcar.controller.command.userlayer;

import by.epamtc.protsko.rentcar.dto.EditUserDTO;
import by.epamtc.protsko.rentcar.dto.RegistrationUserDTO;
import by.epamtc.protsko.rentcar.controller.command.Command;
import by.epamtc.protsko.rentcar.service.ServiceFactory;
import by.epamtc.protsko.rentcar.service.UserService;
import by.epamtc.protsko.rentcar.service.exception.UserServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class EditProfileCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditProfileCommand.class);
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final UserService userService = serviceFactory.getUserService();
    private static final String EDIT_PROFILE_MAPPING = "mainController?command=go_to_edit_profile_page";
    private static final String SHOW_USER_REG_DATA_MAPPING = "mainController?command=go_to_user_profile";
    private static final String VALIDATION_ERROR = "validationError";
    private static final String PARAMETER_USER_ID = "currentUserID";
    private static final String PARAMETER_CURRENT_LOGIN = "currentLogin";
    private static final String PARAMETER_LOGIN = "login";
    private static final String PARAMETER_CURRENT_PASSWORD = "currentPassword";
    private static final String PARAMETER_NEW_PASSWORD = "newPassword";
    private static final String PARAMETER_REPEAT_NEW_PASSWORD = "repeatNewPassword";
    private static final String PARAMETER_SURNAME = "surname";
    private static final String PARAMETER_NAME = "name";
    private static final String PARAMETER_PASSPORT_ID = "passportID";
    private static final String PARAMETER_DRIVER_LICENSE = "driverLicense";
    private static final String PARAMETER_DATE_BIRTH = "dateOfBirth";
    private static final String PARAMETER_EMAIL = "eMail";
    private static final String PARAMETER_PHONE = "phone";
    private static final String PARAMETER_STATUS = "status";
    private static final String PARAMETER_USER_ROLE = "currentRole";
    private static final String DATE_VALIDATOR_ERROR_MESSAGE = "Incorrect date format";
    private static final String USER_REG_DATA_ATTRIBUTE_NAME = "userRegData";
    private static final String CURRENT_USER_LOGIN_ATTRIBUTE = "currentUserLogin";
    private static final String PASSWORD_ERROR_MESSAGE = "New passwords do not match!";
    private static final String PASSWORD_ERROR_ATTRIBUTE_NAME = "passwordsError";
    private static final String LOGIN_ERROR_ATTRIBUTE_NAME = "loginExists";
    private static final String LOGIN_EXISTS_MESSAGE_PARAMETER = "Login exists";
    private static final String LOGIN_EXISTS_MESSAGE_VALUE = "Sorry, but the login is already exists";
    private static final String CURRENT_PASSWORD_ERROR_ATTRIBUTE_NAME = "currentPasswordError";
    private static final String CURRENT_PASSWORD_ERROR_MESSAGE = "Current password incorrect";
    private static final String CURRENT_PASSWORD_ERROR_VALUE = "Current password incorrect";
    private static final String SUCCESSFUL_EDIT_DATA_ATTRIBUTE_NAME = "edit_data_successfully";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        EditUserDTO editUserData = null;
        boolean isEditUserDataSuccessfully = false;
        HttpSession session;
        String editUserDataError;

        try {
            if (!request.getParameter(PARAMETER_NEW_PASSWORD).equals(request.getParameter(PARAMETER_REPEAT_NEW_PASSWORD))) {
                editUserDataError = PASSWORD_ERROR_MESSAGE;
                request.setAttribute(PASSWORD_ERROR_ATTRIBUTE_NAME, editUserDataError);
                request.getRequestDispatcher(EDIT_PROFILE_MAPPING).forward(request, response);
                return;
            }
            editUserData = new EditUserDTO();

            editUserData.setId(Integer.parseInt(request.getParameter(PARAMETER_USER_ID)));
            editUserData.setCurrentLogin(request.getParameter(PARAMETER_CURRENT_LOGIN));
            editUserData.setNewLogin(request.getParameter(PARAMETER_LOGIN));
            editUserData.setCurrentPassword(request.getParameter(PARAMETER_CURRENT_PASSWORD));
            editUserData.setNewPassword(request.getParameter(PARAMETER_NEW_PASSWORD));
            editUserData.setSurname(request.getParameter(PARAMETER_SURNAME));
            editUserData.setName(request.getParameter(PARAMETER_NAME));
            editUserData.setPassportIdNumber(request.getParameter(PARAMETER_PASSPORT_ID));
            editUserData.setDriverLicense(request.getParameter(PARAMETER_DRIVER_LICENSE));
            editUserData.setDateOfBirth(LocalDate.parse(request.getParameter(PARAMETER_DATE_BIRTH)));
            editUserData.seteMail(request.getParameter(PARAMETER_EMAIL));
            editUserData.setPhone(request.getParameter(PARAMETER_PHONE));
            editUserData.setStatus(request.getParameter(PARAMETER_STATUS));
            editUserData.setRole(request.getParameter(PARAMETER_USER_ROLE));

            isEditUserDataSuccessfully = userService.edit(editUserData);
        } catch (UserServiceException e) {
            logger.info(e.getMessage());
            editUserDataError = e.getMessage();
            if (editUserDataError.contains(LOGIN_EXISTS_MESSAGE_PARAMETER)) {
                editUserDataError = LOGIN_EXISTS_MESSAGE_VALUE;
                request.setAttribute(LOGIN_ERROR_ATTRIBUTE_NAME, editUserDataError);
            }
            if (editUserDataError.contains(CURRENT_PASSWORD_ERROR_MESSAGE)) {
                editUserDataError = CURRENT_PASSWORD_ERROR_VALUE;
                request.setAttribute(CURRENT_PASSWORD_ERROR_ATTRIBUTE_NAME, editUserDataError);
            }
            request.getRequestDispatcher(EDIT_PROFILE_MAPPING).forward(request, response);
        } catch (DateTimeParseException e) {
            logger.error("Invalid date value", e);
            request.setAttribute(VALIDATION_ERROR, DATE_VALIDATOR_ERROR_MESSAGE);
            request.getRequestDispatcher(EDIT_PROFILE_MAPPING).forward(request, response);
        }

        if (isEditUserDataSuccessfully) {
            session = request.getSession();
            RegistrationUserDTO registrationUserDTO = getUserRegistrationData(editUserData);

            session.setAttribute(USER_REG_DATA_ATTRIBUTE_NAME, registrationUserDTO);
            session.setAttribute(CURRENT_USER_LOGIN_ATTRIBUTE, editUserData.getNewLogin());
            session.setAttribute(SUCCESSFUL_EDIT_DATA_ATTRIBUTE_NAME, "");

            editUserData = null;
            response.sendRedirect(SHOW_USER_REG_DATA_MAPPING);
        }
    }

    private RegistrationUserDTO getUserRegistrationData(EditUserDTO editUserDTO) {
        RegistrationUserDTO userRegData = new RegistrationUserDTO();

        userRegData.setId(editUserDTO.getId());
        userRegData.setSurname(editUserDTO.getSurname());
        userRegData.setName(editUserDTO.getName());
        userRegData.setPassportIdNumber(editUserDTO.getPassportIdNumber());
        userRegData.setDriverLicense(editUserDTO.getDriverLicense());
        userRegData.setDateOfBirth(editUserDTO.getDateOfBirth());
        userRegData.seteMail(editUserDTO.geteMail());
        userRegData.setPhone(editUserDTO.getPhone());
        userRegData.setRole(editUserDTO.getRole());

        return userRegData;
    }
}
