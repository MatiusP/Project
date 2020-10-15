package by.epamtc.protsko.rentcar.service.validator;

import java.util.regex.Pattern;

public class PhoneValidatorUserCommand implements UserCommandValidator {
    private ValidatorManager validatorManager = ValidatorManager.getInstance();
    private Pattern pattern;

    @Override
    public boolean execute(String userPhone) {
        String validatorRegex = validatorManager.getValidatorRegex(ValidatorParameter.PHONE);
        pattern = Pattern.compile(validatorRegex);

        return pattern.matcher(userPhone).matches();
    }

    @Override
    public String getDataEntryRules() {
        return "";
    }
}
