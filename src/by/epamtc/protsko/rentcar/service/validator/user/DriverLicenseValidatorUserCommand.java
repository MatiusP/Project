package by.epamtc.protsko.rentcar.service.validator.user;

import java.util.regex.Pattern;

public class DriverLicenseValidatorUserCommand implements UserCommandValidator {
    private ValidatorManager validatorManager = ValidatorManager.getInstance();
    private Pattern pattern;

    @Override
    public boolean execute(String userDriverLicense) {
        String validatorRegex = validatorManager.getValidatorRegex(ValidatorParameter.DRIVER_LICENSE);
        pattern = Pattern.compile(validatorRegex);

        return pattern.matcher(userDriverLicense).matches();
    }
}
