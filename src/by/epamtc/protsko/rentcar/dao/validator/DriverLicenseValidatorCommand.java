package by.epamtc.protsko.rentcar.dao.validator;

import java.util.regex.Pattern;

public class DriverLicenseValidatorCommand implements CommandValidator {
    private ValidatorManager validatorManager = ValidatorManager.getInstance();
    private Pattern pattern;

    @Override
    public boolean execute(String userDriverLicense) {
        String validatorRegex = validatorManager.getValidatorRegex(ValidatorParameter.DRIVER_LICENSE);
        pattern = Pattern.compile(validatorRegex);

        return pattern.matcher(userDriverLicense).matches();
    }

    @Override
    public String getDataEntryRules() {
        return "";
    }
}
