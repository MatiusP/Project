package by.epamtc.protsko.rentcar.controller.command;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private Map<ParameterName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(ParameterName.GO_TO_MAIN_PAGE, new GoToMainPageCommand());
        commands.put(ParameterName.SAVE_NEW_USER, new SaveNewUserCommand());
        commands.put(ParameterName.AUTHENTICATION, new GoToAuthPageCommand());
        commands.put(ParameterName.CHECK_USER_AUTH_DATA, new CheckUserAuthData());
        commands.put(ParameterName.REGISTRATION, new GoToRegistrationPageCommand());
        commands.put(ParameterName.GO_TO_SHOW_USER_PROFILE_PAGE, new GoToShowUserProfileCommand());
        commands.put(ParameterName.CHANGE_LOCALE, new ChangeLocaleCommand());
        commands.put(ParameterName.GO_TO_EDIT_USER_DATA_PAGE, new GoToEditUserDataCommand());
        commands.put(ParameterName.EDIT_USER_DATA, new EditUserDataCommand());
        commands.put(ParameterName.SIGN_OUT, new SignOutCommand());
        commands.put(ParameterName.GET_ALL_USERS, new GetAllUsersCommand());
        commands.put(ParameterName.GO_TO_FIND_USER_PAGE, new GoToFindUserCommand());
        commands.put(ParameterName.FIND_USER, new FindUserCommand());
        commands.put(ParameterName.DELETE_USER, new DeleteUserCommand());
        commands.put(ParameterName.GO_TO_CONTACT_PAGE, new GoToContactPage());
        commands.put(ParameterName.GO_TO_OUR_CARS_PAGE, new GoToOurCarsPageCommand());
        commands.put(ParameterName.GET_ALL_CARS, new GetAllCarsCommand());
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
