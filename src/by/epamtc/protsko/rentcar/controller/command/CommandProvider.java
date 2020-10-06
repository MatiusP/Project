package by.epamtc.protsko.rentcar.controller.command;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private Map<ParameterName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(ParameterName.GO_TO_MAIN_PAGE, new GoToMainPageCommand());
        commands.put(ParameterName.SAVE_NEW_USER, new SaveNewUserCommand());
        commands.put(ParameterName.WELCOME_NEW_USER, new WelcomeNewUserCommand());
        commands.put(ParameterName.AUTHENTICATION, new GoToAuthPageCommand());
        commands.put(ParameterName.CHECK_USER_AUTH_DATA, new CheckUserAuthData());
        commands.put(ParameterName.REGISTRATION, new GoToRegistrationPageCommand());
        commands.put(ParameterName.SHOW_USER_REG_DATA, new ShowUserRegDataCommand());
        commands.put(ParameterName.CHANGE_LOCALE, new ChangeLocaleCommand());
        commands.put(ParameterName.GO_TO_EDIT_USER_DATA_PAGE, new GoToEditUserDataCommand());
        commands.put(ParameterName.EDIT_USER_DATA, new EditUserDataCommand());
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
