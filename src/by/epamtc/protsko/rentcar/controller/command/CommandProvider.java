package by.epamtc.protsko.rentcar.controller.command;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
	private Map<ParameterName, Command> commands = new HashMap<>();

	public CommandProvider() {
		commands.put(ParameterName.SAVE_NEW_USER, new SaveNewUserCommand());
		commands.put(ParameterName.WELCOME_NEW_USER, new WelcomeNewUserCommand());
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
