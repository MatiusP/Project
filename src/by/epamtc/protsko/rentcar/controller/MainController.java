package by.epamtc.protsko.rentcar.controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.protsko.rentcar.controller.command.Command;
import by.epamtc.protsko.rentcar.controller.command.CommandProvider;

public class MainController extends HttpServlet {
    private static final long serialVersionUID = -1152395021812818130L;

    private static final String COMMAND_NAME = "command";
    private static final CommandProvider provider = new CommandProvider();

    public MainController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String currentCommandName = request.getParameter(COMMAND_NAME);
        final Command command = provider.getCommand(currentCommandName);

        command.execute(request, response);
    }
}