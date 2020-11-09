package by.epamtc.protsko.rentcar.controller.command.userlayer;

import by.epamtc.protsko.rentcar.dto.FullUserDTO;
import by.epamtc.protsko.rentcar.controller.command.Command;
import by.epamtc.protsko.rentcar.controller.command.util.RequestURL;
import by.epamtc.protsko.rentcar.service.ServiceFactory;
import by.epamtc.protsko.rentcar.service.UserService;
import by.epamtc.protsko.rentcar.service.exception.UserServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToEditUserDataPageCommand implements Command {
    private static final String SHOW_USER_DATA_PAGE_MAPPING = "WEB-INF/jsp/editUserData.jsp";
    private static final String PREV_REQ_URL_ATTRIBUTE_NAME = "previousRequestURL";
    private static final String USER_ID_PARAMETER_NAME = "id";
    private static final String USER_ATTRIBUTE_NAME = "user";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServiceFactory factory = ServiceFactory.getInstance();
        UserService userService = factory.getUserService();
        String currentRequestURL = RequestURL.getRequestURL(request);
        int userId = Integer.parseInt(request.getParameter(USER_ID_PARAMETER_NAME));
        FullUserDTO user = null;

        try {
            user = userService.findByUserId(userId);

        } catch (UserServiceException e) {
            //logger
        }
        request.setAttribute(USER_ATTRIBUTE_NAME, user);
        request.getSession(true).setAttribute(PREV_REQ_URL_ATTRIBUTE_NAME, currentRequestURL);
        request.getRequestDispatcher(SHOW_USER_DATA_PAGE_MAPPING).forward(request, response);
    }
}
