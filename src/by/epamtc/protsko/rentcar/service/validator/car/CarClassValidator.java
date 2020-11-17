package by.epamtc.protsko.rentcar.service.validator.car;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class {@code CarClassValidator} validates car's class type
 *
 * @author Matvey Protsko
 */
public class CarClassValidator implements CarParameterValidator {
    private static final String CAR_CLASS_REGEX = "^(Economy|Middle|Premium)$";
    private static Pattern pattern = Pattern.compile(CAR_CLASS_REGEX);

    @Override
    public boolean isValid(String carClass) {
        Matcher matcher = pattern.matcher(carClass);

        return matcher.matches();
    }
}
