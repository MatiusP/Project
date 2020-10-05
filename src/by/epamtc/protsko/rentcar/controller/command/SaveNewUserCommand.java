package by.epamtc.protsko.rentcar.controller.command;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.protsko.rentcar.bean.RegistrationUserDTO;
import by.epamtc.protsko.rentcar.bean.User;
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

        User user = null;
        boolean isRegistrationSuccessfully = false;
        HttpSession session = request.getSession();
        String registrationError;

        try {
            if (request.getParameter("password").equals(request.getParameter("password_repeat"))) {
                user = new User();

                user.setLogin(request.getParameter("login"));
                user.setPassword(request.getParameter("password"));
                user.setSurname(request.getParameter("surname"));
                user.setName(request.getParameter("name"));
                user.setPassportIdNumber(request.getParameter("passport_Id_Number"));
                user.setDriverLicense(request.getParameter("driver_license"));
                user.setDateOfBirth(LocalDate.parse(request.getParameter("date_of_birth")));
                user.seteMail(request.getParameter("e_mail"));
                user.setPhone(request.getParameter("phone"));

                isRegistrationSuccessfully = userService.registration(user);
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
            RegistrationUserDTO registrationUserDTO = getUserRegistrationData(user);

            session.setAttribute("userRegData", registrationUserDTO);
            session.setAttribute("currentUserLogin", user.getLogin());
            session.setAttribute("currentUserRole", user.getRole());
            user = null;
            response.sendRedirect("mainController?command=show_user_reg_data");
        }
    }

    private RegistrationUserDTO getUserRegistrationData(User userData) {
        RegistrationUserDTO user = null;
        try {
            user = userService.authentication(userData.getLogin(), userData.getPassword());
        } catch (ServiceException e) {
            //loger;
        }
        return user;
    }
}
