package by.epamtc.protsko.rentcar.controller.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeLocaleCommand implements Command {
    private static final String LOCAL_ATTRIBUTE_NAME = "local";
    private static final String PREVIOUS_URL_MAPPING = "previousRequestURL";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute(LOCAL_ATTRIBUTE_NAME, request.getParameter(LOCAL_ATTRIBUTE_NAME));
        response.sendRedirect((String) request.getSession().getAttribute(PREVIOUS_URL_MAPPING));
    }
}
