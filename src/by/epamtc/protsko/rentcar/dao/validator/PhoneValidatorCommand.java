package by.epamtc.protsko.rentcar.dao.validator;

import java.util.regex.Pattern;

public class PhoneValidatorCommand implements CommandValidator {
    private static final String VALIDATOR_REGEX = "^(\\+?\\d{1,3})([- .]?\\d{2,3})([- .]?\\d{3})([- .]?\\d{1,2})([- .]?\\d{1,2})$";
    private Pattern pattern;

    @Override
    public boolean execute(String userPhone) {
        pattern = Pattern.compile(VALIDATOR_REGEX);

        return pattern.matcher(userPhone).matches();
    }

    @Override
    public String getDataEntryRules() {
        return "";
    }
}
