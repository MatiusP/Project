package by.epamtc.protsko.rentcar.controller.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SignOutCommand implements Command {
    private static final String GO_TO_MAIN_PAGE_MAPPING = "mainController?command=go_to_main_page";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        session.invalidate();

        request.getRequestDispatcher(GO_TO_MAIN_PAGE_MAPPING).forward(request, response);
    }
}
