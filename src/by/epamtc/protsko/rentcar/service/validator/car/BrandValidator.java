package by.epamtc.protsko.rentcar.service.validator.car;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BrandValidator implements CarParameterValidator {
    private static final String CAR_BRAND_REGEX = "^[A-Za-zА-ЯЁа-яё]*$";
    private static Pattern pattern = Pattern.compile(CAR_BRAND_REGEX);

    @Override
    public boolean isValid(String carBrand) {
        Matcher matcher = pattern.matcher(carBrand);

        return matcher.matches();
    }
}
