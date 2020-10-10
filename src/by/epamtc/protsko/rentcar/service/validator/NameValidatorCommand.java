package by.epamtc.protsko.rentcar.service.validator;

import java.util.regex.Pattern;

public class NameValidatorCommand implements CommandValidator {
    private ValidatorManager validatorManager = ValidatorManager.getInstance();
    private Pattern pattern;

    @Override
    public boolean execute(String userName) {
        String validatorRegex = validatorManager.getValidatorRegex(ValidatorParameter.NAME);
        pattern = Pattern.compile(validatorRegex);

        return pattern.matcher(userName).matches();
    }

    @Override
    public String getDataEntryRules() {
        return "";
    }
}
