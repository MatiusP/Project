package by.epamtc.protsko.rentcar.controller.command;

import by.epamtc.protsko.rentcar.bean.user.RegistrationUserDTO;
import by.epamtc.protsko.rentcar.service.ServiceFactory;
import by.epamtc.protsko.rentcar.service.UserService;
import by.epamtc.protsko.rentcar.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CheckUserAuthData implements Command {
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.getUserService();
    private static final String AUTH_ERROR = "Incorrect login or password";
    private static final String AUTHENTICATION_MAPPING = "mainController?command=authentication";
    private static final String MAIN_PAGE_MAPPING = "mainController?command=go_to_main_page";
    private static final String PARAMETER_LOGIN = "login";
    private static final String PARAMETER_PASSWORD = "password";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String userLogin = request.getParameter(PARAMETER_LOGIN);
        String userPassword = request.getParameter(PARAMETER_PASSWORD);

        RegistrationUserDTO user = null;

        try {
            user = userService.authentication(userLogin, userPassword);
        } catch (ServiceException e) {
            //log
        }

        if (user != null) {
            session.removeAttribute("authError");
            session.setAttribute("currentUserLogin", userLogin);
            session.setAttribute("userRegData", user);

            response.sendRedirect(MAIN_PAGE_MAPPING);
            return;
        }
        session.setAttribute("authError", AUTH_ERROR);
        response.sendRedirect(AUTHENTICATION_MAPPING);
    }
}
