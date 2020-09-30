package by.epamtc.protsko.rentcar.dao.validator;

import java.util.regex.Pattern;

public class PasswordValidatorCommand implements CommandValidator {
    private static final String VALIDATOR_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{5,45}$";
    private Pattern pattern;

    @Override
    public boolean execute(String userPassword) {
        pattern = Pattern.compile(VALIDATOR_REGEX);

        return pattern.matcher(userPassword).matches();
    }

    @Override
    public String getDataEntryRules() {
        return "Пароль должен содержать только латинские буквы (минимум одна строчная и одна прописная), минимум одну цифру"
                + " и минимум один спецсимвол. Длина пароля: от 5 до 45 символов";
    }
}
