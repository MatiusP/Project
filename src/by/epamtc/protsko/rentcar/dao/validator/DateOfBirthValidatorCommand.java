package by.epamtc.protsko.rentcar.dao.validator;

import java.util.regex.Pattern;

public class DateOfBirthValidatorCommand implements CommandValidator {
    private static final String VALIDATOR_REGEX = "^(([1][9][0-9]\\d)|([2][0][0-1]\\d))((\\/)|(-))(((0)[0-9])|((1)[0-2]))((\\/)|(-))([0-2][0-9]|(3)[0-1])$";
    private Pattern pattern;

    @Override
    public boolean execute(String userDateOfBirth) {
        pattern = Pattern.compile(VALIDATOR_REGEX);

        return pattern.matcher(userDateOfBirth).matches();
    }

    @Override
    public String getDataEntryRules() {
        return "Ввод даты в формате dd-mm-yyyy";
    }
}
