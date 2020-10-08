package by.epamtc.protsko.rentcar.controller.command;

import by.epamtc.protsko.rentcar.bean.user.FullUserDTO;
import by.epamtc.protsko.rentcar.service.ServiceFactory;
import by.epamtc.protsko.rentcar.service.UserService;
import by.epamtc.protsko.rentcar.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class FindUserCommand implements Command {
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final UserService userService = serviceFactory.getUserService();
    private static final String FIND_USER_PAGE_MAPPING = "mainController?command=go_to_find_user_page";
    private static final String NO_USERS_EXC = "No any users in database";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String searchingId = request.getParameter("id");
        final String searchingLogin = request.getParameter("login");
        final String searchingSurname = request.getParameter("surname");
        final String searchingName = request.getParameter("name");
        final String searchingPassportID = request.getParameter("passportID");
        final String searchingDriverLicense = request.getParameter("driverLicense");
        final String searchingDateOfBirth = request.getParameter("dateOfBirth");
        final String searchingEMail = request.getParameter("eMail");
        final String searchingPhone = request.getParameter("phone");
        final String searchingRole = request.getParameter("role");

        List<FullUserDTO> usersFoundList = null;
        FullUserDTO userSearchParameters = new FullUserDTO();
        HttpSession session = request.getSession(false);

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
        if (!searchingRole.isEmpty()) {
            userSearchParameters.setRole(Integer.parseInt(searchingRole));
        }

        try {
            usersFoundList = userService.getUser(userSearchParameters);

            if (usersFoundList.isEmpty()) {
                session.setAttribute("noUsersMessage", NO_USERS_EXC);
            } else {
                session.removeAttribute("noUsersMessage");
                request.setAttribute("usersFoundList", usersFoundList);
            }
        } catch (ServiceException e) {
            //logger
        }
        request.getRequestDispatcher(FIND_USER_PAGE_MAPPING).forward(request, response);
    }
}
