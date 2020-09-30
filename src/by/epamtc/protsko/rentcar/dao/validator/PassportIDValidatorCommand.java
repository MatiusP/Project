package by.epamtc.protsko.rentcar.dao.validator;

import java.util.regex.Pattern;

public class PassportIDValidatorCommand implements CommandValidator {
    private static final String VALIDATOR_REGEX = "^(?!^0+$)[a-zA-Z0-9]{3,14}$";
    private Pattern pattern;

    @Override
    public boolean execute(String userPassportIDNumber) {
        pattern = Pattern.compile(VALIDATOR_REGEX);

        return pattern.matcher(userPassportIDNumber).matches();
    }

    @Override
    public String getDataEntryRules() {
        return "";
    }
}
