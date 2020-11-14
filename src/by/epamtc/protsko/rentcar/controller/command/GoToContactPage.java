package by.epamtc.protsko.rentcar.controller.command;

import by.epamtc.protsko.rentcar.controller.command.util.RequestURL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToContactPage implements Command {
    private static final String CONTACT_PAGE_MAPPING = "WEB-INF/jsp/contactPage.jsp";
    private static final String PREV_REQ_URL_ATTRIBUTE_NAME = "previousRequestURL";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentRequestURL = RequestURL.getRequestURL(request);

        request.getSession().setAttribute(PREV_REQ_URL_ATTRIBUTE_NAME, currentRequestURL);

        request.getRequestDispatcher(CONTACT_PAGE_MAPPING).forward(request, response);
    }
}
