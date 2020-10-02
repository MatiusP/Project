package by.epamtc.protsko.rentcar.dao.validator;

import java.util.regex.Pattern;

public class EMailValidatorCommand implements CommandValidator {
    private ValidatorManager validatorManager = ValidatorManager.getInstance();
    private Pattern pattern;

    @Override
    public boolean execute(String userEMail) {
        String validatorRegex = validatorManager.getValidatorRegex(ValidatorParameter.E_MAIL);
        pattern = Pattern.compile(validatorRegex);

        return pattern.matcher(userEMail).matches();
    }

    @Override
    public String getDataEntryRules() {
        return "";
    }
}
