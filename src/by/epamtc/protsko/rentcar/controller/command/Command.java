package by.epamtc.protsko.rentcar.controller.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.epamtc.protsko.rentcar.controller.exception.ControllerException;

public interface Command {

    void execute(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ControllerException;
}
