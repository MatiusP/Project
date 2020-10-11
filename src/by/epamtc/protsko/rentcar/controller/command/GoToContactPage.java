package by.epamtc.protsko.rentcar.controller.command;

import by.epamtc.protsko.rentcar.controller.command.util.RequestURL;
import by.epamtc.protsko.rentcar.controller.exception.ControllerException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToContactPage implements Command {
    private static final String CONTACT_PAGE_MAPPING = "WEB-INF/jsp/contactPage.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        String currentRequestURL = RequestURL.getRequestURL(request);

        request.getSession(true).setAttribute("previousRequestURL", currentRequestURL);

        request.getRequestDispatcher(CONTACT_PAGE_MAPPING).forward(request, response);
    }
}
