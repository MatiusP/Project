package by.epamtc.protsko.rentcar.controller.command.userlayer;

import by.epamtc.protsko.rentcar.controller.command.Command;
import by.epamtc.protsko.rentcar.controller.command.util.RequestURL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToRegistrationPageCommand implements Command {
    private static final String REGISTRATION_PAGE_MAPPING = "WEB-INF/jsp/registration.jsp";
    private static final String PREV_REQ_URL_ATTRIBUTE_NAME = "previousRequestURL";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentRequestURL = RequestURL.getRequestURL(request);
        request.getSession(true).setAttribute(PREV_REQ_URL_ATTRIBUTE_NAME, currentRequestURL);

        request.getRequestDispatcher(REGISTRATION_PAGE_MAPPING).forward(request, response);
    }
}
