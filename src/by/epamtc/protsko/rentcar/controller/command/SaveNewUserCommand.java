package by.epamtc.protsko.rentcar.controller.command;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.protsko.rentcar.bean.UserRegistrationDTO;
import by.epamtc.protsko.rentcar.bean.UserData;
import by.epamtc.protsko.rentcar.controller.exception.ControllerException;
import by.epamtc.protsko.rentcar.service.ServiceFactory;
import by.epamtc.protsko.rentcar.service.UserService;
import by.epamtc.protsko.rentcar.service.exception.ServiceException;

public class SaveNewUserCommand implements Command {
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final UserService userService = serviceFactory.getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ControllerException, ServletException {

        UserData userData = null;
        boolean isRegistrationSuccessfully = false;
        HttpSession session = request.getSession();
        String registrationError;

        try {
            if (request.getParameter("password").equals(request.getParameter("password_repeat"))) {
                userData = new UserData();

                userData.setLogin(request.getParameter("login"));
                userData.setPassword(request.getParameter("password"));
                userData.setSurname(request.getParameter("surname"));
                userData.setName(request.getParameter("name"));
                userData.setPassportIdNumber(request.getParameter("passport_Id_Number"));
                userData.setDriverLicense(request.getParameter("driver_license"));
                userData.setDateOfBirth(LocalDate.parse(request.getParameter("date_of_birth")));
                userData.seteMail(request.getParameter("e_mail"));
                userData.setPhone(request.getParameter("phone"));

                isRegistrationSuccessfully = userService.registration(userData);
            } else {
                registrationError = "Passwords do not match!";
                request.setAttribute("passwordsError", registrationError);
                request.getRequestDispatcher("mainController?command=registration").forward(request, response);
            }
        } catch (ServiceException e) {
            registrationError = e.getMessage();
            if (registrationError.contains("filling error")) {
                request.setAttribute("fillRegDataError", registrationError);
            } else {
                request.setAttribute("validationError", registrationError);
            }
            request.getRequestDispatcher("mainController?command=registration").forward(request, response);
        }

        if (isRegistrationSuccessfully) {
            UserRegistrationDTO user = getUserRegistrationData(userData);
            userData = null;

            session.setAttribute("userRegData", user);
            session.setAttribute("currentUserLogin", user.getSurname());
            session.setAttribute("currentUserRole", user.getRole());
            response.sendRedirect("mainController?command=show_user_reg_data");
        }
    }

    private UserRegistrationDTO getUserRegistrationData(UserData userData) {
        UserRegistrationDTO user = null;
        try {
            user = userService.authentication(userData.getLogin(), userData.getPassword());
        } catch (ServiceException e) {
            //loger;
        }
        return user;
    }
}
