package by.epamtc.protsko.rentcar.controller.command;

import by.epamtc.protsko.rentcar.controller.command.carlayer.*;
import by.epamtc.protsko.rentcar.controller.command.orderlayer.*;
import by.epamtc.protsko.rentcar.controller.command.userlayer.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private Map<ParameterName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(ParameterName.GO_TO_MAIN_PAGE, new GoToMainPageCommand());
        commands.put(ParameterName.GO_TO_EDIT_PROFILE_PAGE, new GoToEditProfilePageCommand());
        commands.put(ParameterName.GO_TO_EDIT_USER_DATA_PAGE, new GoToEditUserDataPageCommand());
        commands.put(ParameterName.GO_TO_USER_PROFILE, new GoToUserProfileCommand());
        commands.put(ParameterName.GO_TO_USER_MANAGEMENT_PAGE, new GoToUserManagementPageCommand());
        commands.put(ParameterName.GO_TO_CAR_MANAGEMENT_PAGE, new GoToCarManagementPageCommand());
        commands.put(ParameterName.GO_TO_CONTACT_PAGE, new GoToContactPage());
        commands.put(ParameterName.GO_TO_OUR_CARS_PAGE, new GoToOurCarsPageCommand());
        commands.put(ParameterName.GO_TO_CAR_PAGE, new GoToCarPageCommand());
        commands.put(ParameterName.GO_TO_EDIT_CAR_PAGE, new GoToEditCarPage());
        commands.put(ParameterName.GO_TO_USER_ORDERS_PAGE, new GoToUserOrdersPageCommand());
        commands.put(ParameterName.GO_TO_ORDER_MANAGEMENT_PAGE, new GoToOrderManagementPageCommand());
        commands.put(ParameterName.CHANGE_LOCALE, new ChangeLocaleCommand());
        commands.put(ParameterName.SAVE_NEW_USER, new SaveNewUserCommand());
        commands.put(ParameterName.AUTHENTICATION, new GoToAuthPageCommand());
        commands.put(ParameterName.REGISTRATION, new GoToRegistrationPageCommand());
        commands.put(ParameterName.CHECK_AUTH_DATA, new CheckAuthDataCommand());
        commands.put(ParameterName.EDIT_PROFILE, new EditProfileCommand());
        commands.put(ParameterName.EDIT_USER_DATA, new EditUserDataCommand());
        commands.put(ParameterName.FIND_USER, new FindUserCommand());
        commands.put(ParameterName.DELETE_USER, new DeleteUserCommand());
        commands.put(ParameterName.ADD_CAR, new AddCarCommand());
        commands.put(ParameterName.EDIT_CAR, new EditCarCommand());
        commands.put(ParameterName.FIND_CAR, new FindCarCommand());
        commands.put(ParameterName.DELETE_CAR, new DeleteCarCommand());
        commands.put(ParameterName.GET_CARS, new GetCarsCommand());
        commands.put(ParameterName.CHECK_ORDER_DATA, new CheckOrderDataCommand());
        commands.put(ParameterName.CREATE_ORDER, new CreateOrderCommand());
        commands.put(ParameterName.GET_ALL_ORDERS, new GetAllOrdersCommand());
        commands.put(ParameterName.SIGN_OUT, new SignOutCommand());
    }

    public Command getCommand(String commandName) {
        Command command = null;
        try {
            if (!commandName.isEmpty() || commandName != null) {
                commandName = commandName.toUpperCase();
                ParameterName valueName = ParameterName.valueOf(commandName);

                command = commands.get(valueName);
            }
        } catch (IllegalArgumentException e) {
            // logger
            System.out.println("Unknown command!"); ///////////////////// обработать
        }
        return command;
    }
}
