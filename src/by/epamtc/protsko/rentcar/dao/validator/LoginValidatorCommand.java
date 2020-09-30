package by.epamtc.protsko.rentcar.dao.validator;

import java.util.regex.Pattern;

public class LoginValidatorCommand implements CommandValidator {
    private static final String VALIDATOR_REGEX = "^(?=[a-zA-ZА-ЯЁа-яё0-9._]{5,45}$)(?!.*[_.]{2})[^_.].*[^_.]$";
    private Pattern pattern;

    @Override
    public boolean execute(String userLogin) {
        pattern = Pattern.compile(VALIDATOR_REGEX);

        return pattern.matcher(userLogin).matches();
    }

    @Override
    public String getDataEntryRules() {
        return "Логин может содержать латинские либо русские буквы и цифры. Длина логина: от 5 до 45 символов";
    }
}
