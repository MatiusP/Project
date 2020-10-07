package by.epamtc.protsko.rentcar.controller.command;

import by.epamtc.protsko.rentcar.controller.exception.ControllerException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignOutCommand implements Command {
    private static final String SIGN_OUT_MAPPING = "WEB-INF/jsp/signOut.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {

        request.getRequestDispatcher(SIGN_OUT_MAPPING).forward(request, response);
    }
}
