package by.epamtc.protsko.rentcar.controller.command;

import by.epamtc.protsko.rentcar.controller.command.util.RequestURL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToMainPageCommand implements Command {
    private static final String PAGE_HEADER_MAPPING = "WEB-INF/jsp/PageHeader.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentRequestURL = RequestURL.getRequestURL(request);

        request.getSession(true).setAttribute("previousRequestURL", currentRequestURL);

        request.getRequestDispatcher(PAGE_HEADER_MAPPING).forward(request, response);
    }
}
