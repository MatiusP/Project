package by.epamtc.protsko.rentcar.dao.validator;

import java.util.regex.Pattern;

public class EMailValidatorCommand implements CommandValidator {
    private static final String VALIDATOR_REGEX = "^[a-zA-Z]{1}[a-zA-Z\\d\\u002E\\u005F]+@([a-zA-Z]+\\u002E){1,2}[a-z]{1,3}$";
    private Pattern pattern;

    @Override
    public boolean execute(String userEMail) {
        pattern = Pattern.compile(VALIDATOR_REGEX);

        return pattern.matcher(userEMail).matches();
    }

    @Override
    public String getDataEntryRules() {
        return "";
    }
}
