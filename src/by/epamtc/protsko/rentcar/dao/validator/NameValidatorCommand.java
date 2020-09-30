package by.epamtc.protsko.rentcar.dao.validator;

import java.util.regex.Pattern;

public class NameValidatorCommand implements CommandValidator {
    private static final String VALIDATOR_REGEX = "^.{1,75}$";
    private Pattern pattern;

    @Override
    public boolean execute(String userName) {
        pattern = Pattern.compile(VALIDATOR_REGEX);

        return pattern.matcher(userName).matches();
    }

    @Override
    public String getDataEntryRules() {
        return "";
    }
}
