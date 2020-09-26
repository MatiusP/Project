package by.epamtc.protsko.rentcar.controller.command;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
			throws ServletException, IOException, ControllerException {

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
			session.setAttribute("userRole", userDTO.getRole());

			request.setAttribute("user", userDTO);
			response.sendRedirect("registration?command=welcome_new_user");
			//request.getRequestDispatcher("WEB-INF/jsp/userRegistrationParameter.jsp").forward(request, response);
			userDTO = null;
		} else {
			throw new ControllerException("Invalid data");
		}
	}
}
