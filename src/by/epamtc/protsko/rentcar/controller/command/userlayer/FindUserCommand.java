package by.epamtc.protsko.rentcar.controller.command.userlayer;

import by.epamtc.protsko.rentcar.dto.FullUserDTO;
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
import java.util.List;

public class FindUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger(FindUserCommand.class);
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final UserService userService = serviceFactory.getUserService();
    private static final String USER_MANAGEMENT_PAGE_MAPPING = "mainController?command=go_to_user_management_page";
    private static final String PARAMETER_USER_ID = "id";
    private static final String PARAMETER_LOGIN = "login";
    private static final String PARAMETER_SURNAME = "surname";
    private static final String PARAMETER_NAME = "name";
    private static final String PARAMETER_PASSPORT_ID = "passportID";
    private static final String PARAMETER_DRIVER_LICENSE = "driverLicense";
    private static final String PARAMETER_DATE_BIRTH = "dateOfBirth";
    private static final String PARAMETER_EMAIL = "eMail";
    private static final String PARAMETER_PHONE = "phone";
    private static final String PARAMETER_STATUS = "status";
    private static final String PARAMETER_ROLE = "role";
    private static final String NO_USERS_FOUND_ATTRIBUTE_NAME = "noUsersFound";
    private static final String NO_USERS_FOUND_MESSAGE = "No any users in database";
    private static final String USERS_LIST_ATTRIBUTE_NAME = "usersFoundList";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String searchingId = request.getParameter(PARAMETER_USER_ID);
        final String searchingLogin = request.getParameter(PARAMETER_LOGIN);
        final String searchingSurname = request.getParameter(PARAMETER_SURNAME);
        final String searchingName = request.getParameter(PARAMETER_NAME);
        final String searchingPassportID = request.getParameter(PARAMETER_PASSPORT_ID);
        final String searchingDriverLicense = request.getParameter(PARAMETER_DRIVER_LICENSE);
        final String searchingDateOfBirth = request.getParameter(PARAMETER_DATE_BIRTH);
        final String searchingEMail = request.getParameter(PARAMETER_EMAIL);
        final String searchingPhone = request.getParameter(PARAMETER_PHONE);
        final String searchingStatus = request.getParameter(PARAMETER_STATUS);
        final String searchingRole = request.getParameter(PARAMETER_ROLE);

        List<FullUserDTO> usersFoundList = null;
        FullUserDTO userSearchParameters = new FullUserDTO();

        if (!searchingId.isEmpty()) {
            userSearchParameters.setId(Integer.parseInt(searchingId));
        }
        if (!searchingLogin.isEmpty()) {
            userSearchParameters.setLogin(searchingLogin);
        }
        if (!searchingSurname.isEmpty()) {
            userSearchParameters.setSurname(searchingSurname);
        }
        if (!searchingName.isEmpty()) {
            userSearchParameters.setName(searchingName);
        }
        if (!searchingPassportID.isEmpty()) {
            userSearchParameters.setPassportIdNumber(searchingPassportID);
        }
        if (!searchingDriverLicense.isEmpty()) {
            userSearchParameters.setDriverLicense(searchingDriverLicense);
        }
        if (!searchingDateOfBirth.isEmpty()) {
            userSearchParameters.setDateOfBirth(LocalDate.parse(searchingDateOfBirth));
        }
        if (!searchingEMail.isEmpty()) {
            userSearchParameters.seteMail(searchingEMail);
        }
        if (!searchingPhone.isEmpty()) {
            userSearchParameters.setPhone(searchingPhone);
        }
        if (!searchingStatus.isEmpty()) {
            userSearchParameters.setStatus(searchingStatus);
        }
        if (!searchingRole.isEmpty()) {
            userSearchParameters.setRole(searchingRole);
        }
        try {
            usersFoundList = userService.findByCriteria(userSearchParameters);

            if (usersFoundList.isEmpty()) {
                request.setAttribute(NO_USERS_FOUND_ATTRIBUTE_NAME, NO_USERS_FOUND_MESSAGE);
            } else {
                request.setAttribute(USERS_LIST_ATTRIBUTE_NAME, usersFoundList);
            }
        } catch (UserServiceException e) {
            logger.info("Error while finding user", e);
        }
        request.getRequestDispatcher(USER_MANAGEMENT_PAGE_MAPPING).forward(request, response);
    }
}
