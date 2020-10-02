package by.epamtc.protsko.rentcar.controller.command;

import by.epamtc.protsko.rentcar.controller.exception.ControllerException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeLocaleCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        request.getSession(true).setAttribute("local", request.getParameter("local"));

        //request.getRequestDispatcher("WEB-INF/jsp/mainPage.jsp").forward(request, response);
        //request.getRequestDispatcher(requestURI).forward(request, response);
        response.sendRedirect((String) request.getSession().getAttribute("previousRequestURL"));
    }
}
