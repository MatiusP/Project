package by.epamtc.protsko.rentcar.service.validator.user;

import java.util.ResourceBundle;

/**
 * The {@code ValidatorManager} class provides values from the .properties file using the supplied key
 *
 * @author Matvey Protsko
 */
public class ValidatorManager {
    private static final ValidatorManager instance = new ValidatorManager();
    private ResourceBundle validatorBundle = ResourceBundle.getBundle("property/validator_regex");

    private ValidatorManager() {
    }

    public static ValidatorManager getInstance() {
        return instance;
    }

    public String getValidatorRegex(String userCredentionalType) {
        return validatorBundle.getString(userCredentionalType);
    }
}
