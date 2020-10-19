package by.epamtc.protsko.rentcar.service.validator.car;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CarClassValidator implements CarParameterValidator {
    private static final String CAR_CLASS_REGEX = "^(economy|middle|premium)$";
    private static Pattern pattern = Pattern.compile(CAR_CLASS_REGEX);

    @Override
    public boolean isValid(String carClass) {
        Matcher matcher = pattern.matcher(carClass);

        return matcher.matches();
    }
}
