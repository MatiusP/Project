package by.epamtc.protsko.rentcar.controller.command.userlayer;

import by.epamtc.protsko.rentcar.controller.command.Command;
import by.epamtc.protsko.rentcar.service.ServiceFactory;
import by.epamtc.protsko.rentcar.service.UserService;
import by.epamtc.protsko.rentcar.service.exception.UserServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class DeleteUserCommand implements Command {
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final UserService userService = serviceFactory.getUserService();
    private static final String DELETE_USER_RESULT = "deleteUserResult";
    private static final String DELETED_USER_SUCCESSFULLY = "Successfully deleted";
    private static final String DELETED_USER_ERROR = "Deleted error";
    private static final String SHOW_ALL_USERS_MAPPING = "mainController?command=go_to_user_management_page";
    private static final String DELETE_USER_ID_PARAMETER_NAME = "deleteUserId";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        boolean isDeleteSuccessfully = false;

        try {
            int userId = Integer.parseInt(request.getParameter(DELETE_USER_ID_PARAMETER_NAME));
            isDeleteSuccessfully = userService.deleteUser(userId);

            if (isDeleteSuccessfully) {
                session.setAttribute(DELETE_USER_RESULT, DELETED_USER_SUCCESSFULLY);
                response.sendRedirect(SHOW_ALL_USERS_MAPPING);
            } else {
                session.setAttribute(DELETE_USER_RESULT, DELETED_USER_ERROR);
                response.sendRedirect(SHOW_ALL_USERS_MAPPING);
            }
        } catch (UserServiceException e) {
            //logger
            e.printStackTrace();
        }
    }
}
