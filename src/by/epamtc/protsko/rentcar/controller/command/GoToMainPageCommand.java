package by.epamtc.protsko.rentcar.controller.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToMainPageCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentRequestURI = request.getRequestURL().toString();
        String currentRequestQuery = request.getQueryString();
        String currentRequestURL = currentRequestURI + "?" + currentRequestQuery;

        request.getSession(true).setAttribute("previousRequestURL", currentRequestURL);

        request.getRequestDispatcher("WEB-INF/jsp/PageHeader.jsp").forward(request, response);
    }
}
