package by.epamtc.protsko.rentcar.controller.command;

import by.epamtc.protsko.rentcar.controller.command.util.RequestURL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToMainPageCommand implements Command {
    private static final String PAGE_HEADER_MAPPING = "WEB-INF/jsp/mainPage.jsp";
    private static final String PREV_REQ_URL_ATTRIBUTE_NAME = "previousRequestURL";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentRequestURL = RequestURL.getRequestURL(request);

        request.getSession().setAttribute(PREV_REQ_URL_ATTRIBUTE_NAME, currentRequestURL);

        request.getRequestDispatcher(PAGE_HEADER_MAPPING).forward(request, response);
    }
}
