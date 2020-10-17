package by.epamtc.protsko.rentcar.service.validator;

import java.util.regex.Pattern;

public class DateOfBirthValidatorUserCommand implements UserCommandValidator {
    private ValidatorManager validatorManager = ValidatorManager.getInstance();
    private Pattern pattern;

    @Override
    public boolean execute(String userDateOfBirth) {
        String validatorRegex = validatorManager.getValidatorRegex(ValidatorParameter.DATE_OF_BIRTH);
        pattern = Pattern.compile(validatorRegex);

        return pattern.matcher(userDateOfBirth).matches();
    }

    @Override
    public String getDataEntryRules() {
        return "Date input rules: yyyy-mm-dd";
    }
}