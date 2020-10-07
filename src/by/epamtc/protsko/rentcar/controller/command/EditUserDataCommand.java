package by.epamtc.protsko.rentcar.controller.command;

import by.epamtc.protsko.rentcar.bean.user.EditUserDTO;
import by.epamtc.protsko.rentcar.bean.user.RegistrationUserDTO;
import by.epamtc.protsko.rentcar.controller.exception.ControllerException;
import by.epamtc.protsko.rentcar.service.ServiceFactory;
import by.epamtc.protsko.rentcar.service.UserService;
import by.epamtc.protsko.rentcar.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;

public class EditUserDataCommand implements Command {
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final UserService userService = serviceFactory.getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        EditUserDTO editUserData = null;
        boolean isEditUserDataSuccessfully = false;
        HttpSession session = request.getSession(false);
        String editUserDataError;

        try {
            editUserData = new EditUserDTO();

            editUserData.setId(Integer.parseInt(request.getParameter("currentUserID")));
            editUserData.setCurrentLogin(request.getParameter("currentLogin"));
            editUserData.setNewLogin(request.getParameter("login"));
            editUserData.setCurrentPassword(request.getParameter("currentPassword"));
            editUserData.setNewPassword(request.getParameter("newPassword"));
            editUserData.setSurname(request.getParameter("surname"));
            editUserData.setName(request.getParameter("name"));
            editUserData.setPassportIdNumber(request.getParameter("passportID"));
            editUserData.setDriverLicense(request.getParameter("driverLicense"));
            editUserData.setDateOfBirth(request.getParameter("dateOfBirth"));
            editUserData.seteMail(request.getParameter("eMail"));
            editUserData.setPhone(request.getParameter("phone"));
            editUserData.setRole(Integer.parseInt(request.getParameter("role")));

            isEditUserDataSuccessfully = userService.editUserData(editUserData);
        } catch (ServiceException e) {
            editUserDataError = e.getMessage();
            request.setAttribute("validationError", editUserDataError);
            response.sendRedirect("mainController?command=edit_user_data");
        }

        if (isEditUserDataSuccessfully) {
            RegistrationUserDTO registrationUserDTO = getUserRegistrationData(editUserData);

            session.setAttribute("userRegData", registrationUserDTO);
            session.setAttribute("currentUserLogin", editUserData.getNewLogin());
            session.setAttribute("currentUserRole", registrationUserDTO.getRole());
            session.setAttribute("currentUserID", registrationUserDTO.getId());
            editUserData = null;
            response.sendRedirect("mainController?command=show_user_reg_data");
        }
    }

    private RegistrationUserDTO getUserRegistrationData(EditUserDTO editUserDTO) {
        RegistrationUserDTO userRegData = new RegistrationUserDTO();

        userRegData.setId(editUserDTO.getId());
        userRegData.setSurname(editUserDTO.getSurname());
        userRegData.setName(editUserDTO.getName());
        userRegData.setPassportIdNumber(editUserDTO.getPassportIdNumber());
        userRegData.setDriverLicense(editUserDTO.getDriverLicense());
        userRegData.setDateOfBirth(LocalDate.parse(editUserDTO.getDateOfBirth()));
        userRegData.seteMail(editUserDTO.geteMail());
        userRegData.setPhone(editUserDTO.getPhone());
        userRegData.setRole(editUserDTO.getRole());

        return userRegData;
    }
}








