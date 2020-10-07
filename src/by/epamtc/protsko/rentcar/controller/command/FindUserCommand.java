package by.epamtc.protsko.rentcar.controller.command;

import by.epamtc.protsko.rentcar.bean.user.FullUserDTO;
import by.epamtc.protsko.rentcar.controller.exception.ControllerException;
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
    private HttpSession session;
    private static final String NO_USERS_EXC = "No any users in database";
    private static final String VALIDATION_ERROR = "VALIDATION ERROR FIND USERS";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        session = request.getSession(false);
        FullUserDTO userSearchParameters = new FullUserDTO();

        userSearchParameters.setId(Integer.parseInt(request.getParameter("id")));
        userSearchParameters.setLogin(request.getParameter("login"));
        userSearchParameters.setSurname(request.getParameter("surname"));
        userSearchParameters.setName(request.getParameter("name"));
        userSearchParameters.setPassportIdNumber(request.getParameter("passportID"));
        userSearchParameters.setDriverLicense(request.getParameter("driverLicense"));
        userSearchParameters.setDateOfBirth(LocalDate.parse(request.getParameter("dateOfBirth")));
        userSearchParameters.seteMail(request.getParameter("eMail"));
        userSearchParameters.setPhone(request.getParameter("phone"));
        userSearchParameters.setRole(Integer.parseInt("role"));

        try {
            final List<FullUserDTO> usersFoundList = userService.getUser(userSearchParameters);
            if (usersFoundList.isEmpty()) {
                session.setAttribute("validationError", VALIDATION_ERROR);
                response.sendRedirect("mainController?command=find_users");
            } else {
                request.setAttribute("usersFoundList", usersFoundList);
                request.getRequestDispatcher("WEB-INF/jsp/findUser.jsp").forward(request, response);        //TODO размещение относительно catch - подумать
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
