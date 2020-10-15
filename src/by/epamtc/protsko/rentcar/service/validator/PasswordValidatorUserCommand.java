package by.epamtc.protsko.rentcar.service.validator;

import java.util.regex.Pattern;

public class PasswordValidatorUserCommand implements UserCommandValidator {
    private ValidatorManager validatorManager = ValidatorManager.getInstance();
    private Pattern pattern;

    @Override
    public boolean execute(String userPassword) {
        String validatorRegex = validatorManager.getValidatorRegex(ValidatorParameter.PASSWORD);
        pattern = Pattern.compile(validatorRegex);

        return pattern.matcher(userPassword).matches();
    }

    @Override
    public String getDataEntryRules() {
        return "Пароль должен содержать только латинские буквы (минимум одна строчная и одна прописная), минимум одну цифру"
                + " и минимум один спецсимвол. Длина пароля: от 5 до 45 символов";
    }
}
