package by.epamtc.protsko.rentcar.controller.command;

import by.epamtc.protsko.rentcar.service.ServiceFactory;
import by.epamtc.protsko.rentcar.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetAllUsersCommand implements Command {
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final UserService userService = serviceFactory.getUserService();
    private static final String NO_USERS_EXC = "No any users in database";
    private static final String SHOW_ALL_USERS_PAGE = "WEB-INF/jsp/allUsersPage.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        List<FullUserDTO> usersList;
//
//        try {
//            usersList = userService.getAllUsers();
//
//            if (!usersList.isEmpty()) {
//                request.setAttribute("usersList", usersList);
//            } else {
//                request.setAttribute("noUsersException", NO_USERS_EXC);
//            }
//        } catch (ServiceException e) {
//            //logger
//            e.printStackTrace();
//        }
//        request.getRequestDispatcher(SHOW_ALL_USERS_PAGE).forward(request, response);
    }
}
