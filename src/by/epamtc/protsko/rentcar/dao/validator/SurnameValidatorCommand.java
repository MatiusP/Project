package by.epamtc.protsko.rentcar.dao.validator;

import java.util.regex.Pattern;

public class SurnameValidatorCommand implements CommandValidator {
    private static final String VALIDATOR_REGEX = "^.{1,75}$";
    private Pattern pattern;

    @Override
    public boolean execute(String userSurname) {
        pattern = Pattern.compile(VALIDATOR_REGEX);

        return pattern.matcher(userSurname).matches();
    }

    @Override
    public String getDataEntryRules() {
        return "";
    }
}
