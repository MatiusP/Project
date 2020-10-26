package by.epamtc.protsko.rentcar.service.validator.user;

import java.util.regex.Pattern;

public class PassportIDValidatorUserCommand implements UserCommandValidator {
    private ValidatorManager validatorManager = ValidatorManager.getInstance();
    private Pattern pattern;

    @Override
    public boolean execute(String userPassportIDNumber) {
        String validatorRegex = validatorManager.getValidatorRegex(ValidatorParameter.PASSPORT_ID);
        pattern = Pattern.compile(validatorRegex);

        return pattern.matcher(userPassportIDNumber).matches();
    }

    @Override
    public String getDataEntryRules() {
        return "";
    }
}
