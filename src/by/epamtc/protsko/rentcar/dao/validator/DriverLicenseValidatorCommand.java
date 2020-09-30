package by.epamtc.protsko.rentcar.dao.validator;

import java.util.regex.Pattern;

public class DriverLicenseValidatorCommand implements CommandValidator {
    private static final String VALIDATOR_REGEX = "^(?!^0+$)[a-zA-Z0-9]{3,14}$";
    private Pattern pattern;

    @Override
    public boolean execute(String userDriverLicense) {
        pattern = Pattern.compile(VALIDATOR_REGEX);

        return pattern.matcher(userDriverLicense).matches();
    }

    @Override
    public String getDataEntryRules() {
        return "";
    }
}
