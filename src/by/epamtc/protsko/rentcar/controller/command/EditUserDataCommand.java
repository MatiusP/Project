package by.epamtc.protsko.rentcar.controller.command;

import by.epamtc.protsko.rentcar.bean.user.EditUserDTO;
import by.epamtc.protsko.rentcar.bean.user.RegistrationUserDTO;
import by.epamtc.protsko.rentcar.service.ServiceFactory;
import by.epamtc.protsko.rentcar.service.UserService;
import by.epamtc.protsko.rentcar.service.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class EditUserDataCommand implements Command {
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final UserService userService = serviceFactory.getUserService();
    private static final String EDIT_USER_DATA_MAPPING = "mainController?command=go_to_edit_user_data_page";
    private static final String SHOW_USER_REG_DATA_MAPPING = "mainController?command=show_user_reg_data";
    private static final String VALIDATION_ERROR = "validationError";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
            editUserData.setDateOfBirth(LocalDate.parse(request.getParameter("dateOfBirth")));
            editUserData.seteMail(request.getParameter("eMail"));
            editUserData.setPhone(request.getParameter("phone"));
            editUserData.setRole(Integer.parseInt(request.getParameter("role")));

            isEditUserDataSuccessfully = userService.editUserData(editUserData);
        } catch (ServiceException e) {
            editUserDataError = e.getMessage();
            session.setAttribute(VALIDATION_ERROR, editUserDataError);
            response.sendRedirect(EDIT_USER_DATA_MAPPING);
        } catch (DateTimeParseException e) {
            session.setAttribute(VALIDATION_ERROR, "Incorrect date");
            response.sendRedirect(EDIT_USER_DATA_MAPPING);
        }

        if (isEditUserDataSuccessfully) {
            RegistrationUserDTO registrationUserDTO = getUserRegistrationData(editUserData);
            session.setAttribute("userRegData", registrationUserDTO);
            session.setAttribute("currentUserLogin", editUserData.getNewLogin());
            editUserData = null;
            response.sendRedirect(SHOW_USER_REG_DATA_MAPPING);
        }
    }

    private RegistrationUserDTO getUserRegistrationData(EditUserDTO editUserDTO) {
        RegistrationUserDTO userRegData = new RegistrationUserDTO();

        userRegData.setId(editUserDTO.getId());
        userRegData.setSurname(editUserDTO.getSurname());
        userRegData.setName(editUserDTO.getName());
        userRegData.setPassportIdNumber(editUserDTO.getPassportIdNumber());
        userRegData.setDriverLicense(editUserDTO.getDriverLicense());
        userRegData.setDateOfBirth(editUserDTO.getDateOfBirth());
        userRegData.seteMail(editUserDTO.geteMail());
        userRegData.setPhone(editUserDTO.getPhone());
        userRegData.setRole(editUserDTO.getRole());

        return userRegData;
    }
}
