package by.epamtc.protsko.rentcar.controller.command;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.epamtc.protsko.rentcar.bean.User;
import by.epamtc.protsko.rentcar.bean.UserDTO;
import by.epamtc.protsko.rentcar.controller.exception.ControllerException;
import by.epamtc.protsko.rentcar.service.ServiceFactory;
import by.epamtc.protsko.rentcar.service.UserService;
import by.epamtc.protsko.rentcar.service.exception.ServiceException;

public class SaveNewUserCommand implements Command {
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final UserService userService = serviceFactory.getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ControllerException {

        UserDTO userDTO;
        boolean isRegistrationSuccessfully;

        try {
            if (request.getParameter("password").equals(request.getParameter("password_repeat"))) {
                userDTO = new UserDTO();

                userDTO.setLogin(request.getParameter("login"));
                userDTO.setPassword(request.getParameter("password"));
                userDTO.setSurname(request.getParameter("surname"));
                userDTO.setName(request.getParameter("name"));
                userDTO.setPassportIdNumber(request.getParameter("passport_Id_Number"));
                userDTO.setDriverLicense(request.getParameter("driver_license"));
                userDTO.setDateOfBirth(LocalDate.parse(request.getParameter("date_of_birth")));
                userDTO.seteMail(request.getParameter("e_mail"));
                userDTO.setPhone(request.getParameter("phone"));

                isRegistrationSuccessfully = userService.registration(userDTO);
            } else {
                throw new ControllerException("Пароли не совпадают!");
            }
        } catch (ServiceException e) {
            throw new ControllerException("НЕ РАБОТАЕТ from SaveNewUserCommand", e);
        }

        if (isRegistrationSuccessfully) {
            HttpSession session = request.getSession();

            session.setAttribute("userRegData", getUserRegistrationData(userDTO));
            response.sendRedirect("userController?command=show_user_reg_data");
        } else {
            throw new ControllerException("Invalid data");
        }
    }

    private User getUserRegistrationData(UserDTO userDTO) {
        User user = new User();

        user.setId(userDTO.getId());
        user.setSurname(userDTO.getSurname());
        user.setName(userDTO.getName());
        user.setPassportIdNumber(userDTO.getPassportIdNumber());
        user.setDriverLicense(userDTO.getDriverLicense());
        user.setDateOfBirth(userDTO.getDateOfBirth());
        user.seteMail(userDTO.geteMail());
        user.setPhone(userDTO.getPhone());
        user.setRole(userDTO.getRole());

        return user;
    }
}
