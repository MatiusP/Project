package by.epamtc.protsko.rentcar.controller.command.carlayer;

import by.epamtc.protsko.rentcar.controller.command.Command;
import by.epamtc.protsko.rentcar.controller.command.util.RequestURL;
import by.epamtc.protsko.rentcar.controller.exception.ControllerException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class GoToCarManagementPageCommand implements Command {
    private static final String CAR_MANAGEMENT_PAGE_MAPPING = "WEB-INF/jsp/carManagementPage.jsp";
    private static final String FIND_CAR_COMMAND_MAPPING = "mainController?command=find_car";
    private static final String PREV_REQ_URL_ATTRIBUTE_NAME = "previousRequestURL";
    private static final String REQUEST_PARAMETER_NAME = "param";
    private static final String ADD_CAR_PARAMETER_VALUE = "addCar";
    private static final String FIND_CAR_PARAMETER_VALUE = "findCar";
    private static final String ALL_CARS_PARAMETER_VALUE = "getAllCars";
    private static final String ATTRIBUTE_NAME = "command";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        HttpSession session = request.getSession(false);
        String currentRequestURL = RequestURL.getRequestURL(request);
        session.setAttribute(PREV_REQ_URL_ATTRIBUTE_NAME, currentRequestURL);

        String commandParam = request.getParameter(REQUEST_PARAMETER_NAME);
        if (commandParam != null) {
            if (commandParam.equals(ADD_CAR_PARAMETER_VALUE)) {
                session.setAttribute(ATTRIBUTE_NAME, ADD_CAR_PARAMETER_VALUE);
            }
            if (commandParam.equals(FIND_CAR_PARAMETER_VALUE)) {
                session.setAttribute(ATTRIBUTE_NAME, FIND_CAR_PARAMETER_VALUE);
            }
            if (commandParam.equals(ALL_CARS_PARAMETER_VALUE)) {
                session.setAttribute(ATTRIBUTE_NAME, ALL_CARS_PARAMETER_VALUE);
                response.sendRedirect(FIND_CAR_COMMAND_MAPPING);
                return;
            }
        }
        request.getRequestDispatcher(CAR_MANAGEMENT_PAGE_MAPPING).forward(request, response);
    }
}
