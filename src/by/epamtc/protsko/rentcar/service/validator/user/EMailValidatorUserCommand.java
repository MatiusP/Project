package by.epamtc.protsko.rentcar.service.validator.user;

import java.util.regex.Pattern;

/**
 * The class {@code EMailValidatorUserCommand} validates
 * user's e-mail.
 *
 * @author Matvey Protsko
 */
public class EMailValidatorUserCommand implements UserCommandValidator {
    private ValidatorManager validatorManager = ValidatorManager.getInstance();
    private Pattern pattern;

    @Override
    public boolean execute(String userEMail) {
        String validatorRegex = validatorManager.getValidatorRegex(ValidatorParameter.E_MAIL);
        pattern = Pattern.compile(validatorRegex);

        return pattern.matcher(userEMail).matches();
    }
}
