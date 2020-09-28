package by.epamtc.protsko.rentcar.controller.command;

import by.epamtc.protsko.rentcar.bean.User;
import by.epamtc.protsko.rentcar.controller.exception.ControllerException;
import by.epamtc.protsko.rentcar.service.ServiceFactory;
import by.epamtc.protsko.rentcar.service.UserService;
import by.epamtc.protsko.rentcar.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CheckUserAuthData implements Command {
    private ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private UserService userService = serviceFactory.getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        HttpSession session = request.getSession();
        String userLogin = request.getParameter("login");
        String userPassword = request.getParameter("password");

        User user = null;
        try {
            user = userService.authentication(userLogin, userPassword);
        } catch (ServiceException e) {
            //log
        }

        if (user == null) {
            String authError = "Incorrect login or password";
            session.setAttribute("authError", authError);

            response.sendRedirect("userController?command=authentication");
        } else {
            session.removeAttribute("authError");
            session.setAttribute("currentUserLogin", userLogin);
            session.setAttribute("currentUserRole", user.getRole());

            response.sendRedirect("userController?command=go_to_main_page");
        }
    }
}
