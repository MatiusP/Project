package by.epamtc.protsko.rentcar.service.validator.car;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VinValidator implements CarParameterValidator {
    private static final String VIN_REGEX = "^[a-zA-Z0-9]{11}[0-9]{6}$";
    private static Pattern pattern = Pattern.compile(VIN_REGEX);

    @Override
    public boolean isValid(String carVin) {
        Matcher matcher = pattern.matcher(carVin);

        return matcher.matches();
    }
}
