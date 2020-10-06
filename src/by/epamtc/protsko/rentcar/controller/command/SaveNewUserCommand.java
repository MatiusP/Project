package by.epamtc.protsko.rentcar.controller.command;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.protsko.rentcar.bean.user.FullUserDTO;
import by.epamtc.protsko.rentcar.bean.user.RegistrationUserDTO;
import by.epamtc.protsko.rentcar.service.ServiceFactory;
import by.epamtc.protsko.rentcar.service.UserService;
import by.epamtc.protsko.rentcar.service.exception.ServiceException;

public class SaveNewUserCommand implements Command {
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final UserService userService = serviceFactory.getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        FullUserDTO fullUserDTO = null;
        boolean isRegistrationSuccessfully = false;
        String registrationError;
        HttpSession session;

        try {
            if (request.getParameter("password").equals(request.getParameter("password_repeat"))) {
                fullUserDTO = new FullUserDTO();

                fullUserDTO.setLogin(request.getParameter("login"));
                fullUserDTO.setPassword(request.getParameter("password"));
                fullUserDTO.setSurname(request.getParameter("surname"));
                fullUserDTO.setName(request.getParameter("name"));
                fullUserDTO.setPassportIdNumber(request.getParameter("passport_Id_Number"));
                fullUserDTO.setDriverLicense(request.getParameter("driver_license"));
                fullUserDTO.setDateOfBirth(LocalDate.parse(request.getParameter("date_of_birth")));
                fullUserDTO.seteMail(request.getParameter("e_mail"));
                fullUserDTO.setPhone(request.getParameter("phone"));

                isRegistrationSuccessfully = userService.registration(fullUserDTO);
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
            session = request.getSession();
            RegistrationUserDTO registrationUserDTO = getUserRegistrationData(fullUserDTO);

            session.setAttribute("userRegData", registrationUserDTO);
            session.setAttribute("currentUserLogin", fullUserDTO.getLogin());
            session.setAttribute("currentUserRole", registrationUserDTO.getRole());
            session.setAttribute("currentUserID", registrationUserDTO.getId());
            fullUserDTO = null;
            response.sendRedirect("mainController?command=show_user_reg_data");
        }
    }

    private RegistrationUserDTO getUserRegistrationData(FullUserDTO fullUserDTO) {
        RegistrationUserDTO user = null;
        try {
            user = userService.authentication(fullUserDTO.getLogin(), fullUserDTO.getPassword());
        } catch (ServiceException e) {
            //loger;
        }
        return user;
    }
}
