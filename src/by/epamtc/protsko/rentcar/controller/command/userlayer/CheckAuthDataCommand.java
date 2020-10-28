package by.epamtc.protsko.rentcar.controller.command.userlayer;

import by.epamtc.protsko.rentcar.bean.user.RegistrationUserDTO;
import by.epamtc.protsko.rentcar.controller.command.Command;
import by.epamtc.protsko.rentcar.service.ServiceFactory;
import by.epamtc.protsko.rentcar.service.UserService;
import by.epamtc.protsko.rentcar.service.exception.UserServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CheckAuthDataCommand implements Command {
    private static final String AUTH_ERROR = "Incorrect login or password";
    private static final String AUTHENTICATION_MAPPING = "mainController?command=authentication";
    private static final String MAIN_PAGE_MAPPING = "mainController?command=go_to_main_page";
    private static final String PARAMETER_LOGIN = "login";
    private static final String PARAMETER_PASSWORD = "password";
    private static final String AUTH_ERROR_PARAM = "authError";
    private static final String USER_REG_DATA_PARAM = "userRegData";
    private static final String CURRENT_USER_LOGIN_ATTRIBUTE = "currentUserLogin";

    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = null;

        String userLogin = request.getParameter(PARAMETER_LOGIN);
        String userPassword = request.getParameter(PARAMETER_PASSWORD);

        RegistrationUserDTO user = null;

        try {
            session = request.getSession();

            user = userService.authentication(userLogin, userPassword);
        } catch (UserServiceException e) {
            //logger
        }

        if (user != null) {
            session.setAttribute(CURRENT_USER_LOGIN_ATTRIBUTE, userLogin);
            session.setAttribute(USER_REG_DATA_PARAM, user);
            response.sendRedirect(MAIN_PAGE_MAPPING);
            return;
        }
        session.setAttribute(AUTH_ERROR_PARAM, AUTH_ERROR);
        response.sendRedirect(AUTHENTICATION_MAPPING);
    }
}
