package by.epamtc.protsko.rentcar.controller.command;

import by.epamtc.protsko.rentcar.controller.command.util.RequestURL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToShowAllUserDataPage implements Command {
    private static final String SHOW_ALL_USER_DATA_PAGE_MAPPING = "WEB-INF/jsp/showAllUserData.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentRequestURL = RequestURL.getRequestURL(request);

        request.getSession(true).setAttribute("previousRequestURL", currentRequestURL);
        request.setAttribute("currentId", request.getParameter("currentId"));
        request.getRequestDispatcher(SHOW_ALL_USER_DATA_PAGE_MAPPING).forward(request, response);
    }
}
