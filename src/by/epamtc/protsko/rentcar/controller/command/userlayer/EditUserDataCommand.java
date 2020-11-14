package by.epamtc.protsko.rentcar.controller.command.userlayer;

import by.epamtc.protsko.rentcar.dto.EditUserDTO;
import by.epamtc.protsko.rentcar.controller.command.Command;
import by.epamtc.protsko.rentcar.service.ServiceFactory;
import by.epamtc.protsko.rentcar.service.UserService;
import by.epamtc.protsko.rentcar.service.exception.UserServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class EditUserDataCommand implements Command {
    private static final Logger logger = LogManager.getLogger(EditUserDataCommand.class);
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final UserService userService = serviceFactory.getUserService();
    private static final String EDIT_USER_DATA_MAPPING = "mainController?command=go_to_edit_user_data_page";
    private static final String USER_MANAGEMENT_PAGE_MAPPING = "mainController?command=go_to_user_management_page";
    private static final String VALIDATION_ERROR = "validationError";
    private static final String PARAMETER_USER_ID = "id";
    private static final String PARAMETER_LOGIN = "login";
    private static final String PARAMETER_CURRENT_PASSWORD_VALUE = "";
    private static final String PARAMETER_NEW_PASSWORD_VALUE = "";
    private static final String PARAMETER_SURNAME = "surname";
    private static final String PARAMETER_NAME = "name";
    private static final String PARAMETER_PASSPORT_ID = "passportID";
    private static final String PARAMETER_DRIVER_LICENSE = "driverLicense";
    private static final String PARAMETER_DATE_BIRTH = "dateOfBirth";
    private static final String PARAMETER_EMAIL = "eMail";
    private static final String PARAMETER_PHONE = "phone";
    private static final String PARAMETER_STATUS = "status";
    private static final String PARAMETER_USER_ROLE = "role";
    private static final String SUCCESSFUL_EDIT_DATA_ATTRIBUTE_NAME = "editUserResult";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        final int USER_ID = Integer.parseInt(request.getParameter(PARAMETER_USER_ID));
        final String userIdForErrorMapping = "&id=" + USER_ID;
        EditUserDTO editUserData = null;
        boolean isEditUserDataSuccessfully = false;
        String editUserDataError;

        try {
            editUserData = new EditUserDTO();

            editUserData.setId(USER_ID);
            editUserData.setCurrentLogin(request.getParameter(PARAMETER_LOGIN));
            editUserData.setNewLogin(request.getParameter(PARAMETER_LOGIN));
            editUserData.setCurrentPassword(PARAMETER_CURRENT_PASSWORD_VALUE);
            editUserData.setNewPassword(PARAMETER_NEW_PASSWORD_VALUE);
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
            logger.info("Error while editing user date", e);
            editUserDataError = e.getMessage();
            request.setAttribute(VALIDATION_ERROR, editUserDataError);
            request.getRequestDispatcher(EDIT_USER_DATA_MAPPING + userIdForErrorMapping).forward(request, response);
        }

        if (isEditUserDataSuccessfully) {
            request.getSession().setAttribute(SUCCESSFUL_EDIT_DATA_ATTRIBUTE_NAME, "");
            response.sendRedirect(USER_MANAGEMENT_PAGE_MAPPING);
        }
    }
}
