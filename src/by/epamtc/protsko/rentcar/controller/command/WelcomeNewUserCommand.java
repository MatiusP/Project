package by.epamtc.protsko.rentcar.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.protsko.rentcar.controller.command.util.RequestURL;

public class WelcomeNewUserCommand implements Command {
    private static final String USER_REG_PARAMETERS_MAPPING = "WEB-INF/jsp/userRegistrationParameter.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String currentRequestURL = RequestURL.getRequestURL(request);

        request.getSession(true).setAttribute("previousRequestURL", currentRequestURL);

        request.getRequestDispatcher(USER_REG_PARAMETERS_MAPPING).forward(request, response);
    }
}
