package by.epamtc.protsko.rentcar.service.validator.car;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManufactureDateValidator implements CarParameterValidator {
    private static final String MANUFACTURE_DATE_REGEX = "^(([1][9][4-9]\\d)|([2][0][0-2][0-9]))$";
    private static Pattern pattern = Pattern.compile(MANUFACTURE_DATE_REGEX);

    @Override
    public boolean isValid(String carManufactureDate) {
        Matcher matcher = pattern.matcher(carManufactureDate);

        return matcher.matches();
    }
}
