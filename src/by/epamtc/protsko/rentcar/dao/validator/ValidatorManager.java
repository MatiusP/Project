package by.epamtc.protsko.rentcar.dao.validator;

import java.util.ResourceBundle;

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
