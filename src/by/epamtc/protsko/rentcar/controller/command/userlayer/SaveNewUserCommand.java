package by.epamtc.protsko.rentcar.controller.command.userlayer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.protsko.rentcar.dto.FullUserDTO;
import by.epamtc.protsko.rentcar.dto.RegistrationUserDTO;
import by.epamtc.protsko.rentcar.controller.command.Command;
import by.epamtc.protsko.rentcar.service.ServiceFactory;
import by.epamtc.protsko.rentcar.service.UserService;
import by.epamtc.protsko.rentcar.service.exception.UserServiceException;

public class SaveNewUserCommand implements Command {
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final UserService userService = serviceFactory.getUserService();
    private static final String REGISTRATION_MAPPING = "mainController?command=registration";
    private static final String SHOW_USER_REG_DATA_MAPPING = "mainController?command=go_to_user_profile";
    private static final String PARAMETER_PASSWORD = "password";
    private static final String PARAMETER_REPEAT_PASSWORD = "password_repeat";
    private static final String PARAMETER_LOGIN = "login";
    private static final String PARAMETER_SURNAME = "surname";
    private static final String PARAMETER_NAME = "name";
    private static final String PARAMETER_PASSPORT_ID = "passport_Id_Number";
    private static final String PARAMETER_DRIVER_LICENSE = "driver_license";
    private static final String PARAMETER_DATE_BIRTH = "date_of_birth";
    private static final String PARAMETER_EMAIL = "e_mail";
    private static final String PARAMETER_PHONE = "phone";
    private static final String PASSWORD_ERROR_ATTRIBUTE_NAME = "passwordsError";
    private static final String PASSWORD_ERROR_MESSAGE = "Passwords do not match!";
    private static final String VALIDATION_ERROR_ATTRIBUTE_NAME = "validationError";
    private static final String DATE_VALIDATOR_ERROR_MESSAGE = "Incorrect date format";
    private static final String FILL_FORM_ERROR_ATTRIBUTE_NAME = "fillRegDataError";
    private static final String FILL_FORM_ERROR_TEXT = "filling error";
    private static final String SUCCESSFUL_REGISTR_ATTRIBUTE_NAME = "registration_successfully";
    private static final String USER_REG_DATA_ATTRIBUTE_NAME = "userRegData";
    private static final String CURRENT_USER_LOGIN_ATTRIBUTE = "currentUserLogin";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        FullUserDTO fullUserDTO = null;
        boolean isRegistrationSuccessfully = false;
        String registrationError;
        HttpSession session;

        try {
            if (request.getParameter(PARAMETER_PASSWORD).equals(request.getParameter(PARAMETER_REPEAT_PASSWORD))) {
                fullUserDTO = new FullUserDTO();

                fullUserDTO.setLogin(request.getParameter(PARAMETER_LOGIN));
                fullUserDTO.setPassword(request.getParameter(PARAMETER_PASSWORD));
                fullUserDTO.setSurname(request.getParameter(PARAMETER_SURNAME));
                fullUserDTO.setName(request.getParameter(PARAMETER_NAME));
                fullUserDTO.setPassportIdNumber(request.getParameter(PARAMETER_PASSPORT_ID));
                fullUserDTO.setDriverLicense(request.getParameter(PARAMETER_DRIVER_LICENSE));
                fullUserDTO.setDateOfBirth(LocalDate.parse(request.getParameter(PARAMETER_DATE_BIRTH)));
                fullUserDTO.seteMail(request.getParameter(PARAMETER_EMAIL));
                fullUserDTO.setPhone(request.getParameter(PARAMETER_PHONE));

                isRegistrationSuccessfully = userService.add(fullUserDTO);
            } else {
                registrationError = PASSWORD_ERROR_MESSAGE;
                request.setAttribute(PASSWORD_ERROR_ATTRIBUTE_NAME, registrationError);
                request.getRequestDispatcher(REGISTRATION_MAPPING).forward(request, response);
            }
        } catch (UserServiceException e) {
            registrationError = e.getMessage();
            if (registrationError.contains(FILL_FORM_ERROR_TEXT)) {
                request.setAttribute(FILL_FORM_ERROR_ATTRIBUTE_NAME, registrationError);
            } else {
                request.setAttribute(VALIDATION_ERROR_ATTRIBUTE_NAME, registrationError);
            }
            request.getRequestDispatcher(REGISTRATION_MAPPING).forward(request, response);
        } catch (DateTimeParseException e) {
            request.setAttribute(VALIDATION_ERROR_ATTRIBUTE_NAME, DATE_VALIDATOR_ERROR_MESSAGE);
            request.getRequestDispatcher(REGISTRATION_MAPPING).forward(request, response);
        }

        if (isRegistrationSuccessfully) {
            session = request.getSession(false);
            RegistrationUserDTO registrationUserDTO = getUserRegistrationData(fullUserDTO);

            session.setAttribute(USER_REG_DATA_ATTRIBUTE_NAME, registrationUserDTO);
            session.setAttribute(SUCCESSFUL_REGISTR_ATTRIBUTE_NAME, "");
            session.setAttribute(CURRENT_USER_LOGIN_ATTRIBUTE, fullUserDTO.getLogin());

            fullUserDTO = null;
            response.sendRedirect(SHOW_USER_REG_DATA_MAPPING);
        }
    }

    private RegistrationUserDTO getUserRegistrationData(FullUserDTO fullUserDTO) {
        RegistrationUserDTO user = null;
        try {
            user = userService.authentication(fullUserDTO.getLogin(), fullUserDTO.getPassword());
        } catch (UserServiceException e) {
            //loger;
        }
        return user;
    }
}
