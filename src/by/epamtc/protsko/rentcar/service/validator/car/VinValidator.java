package by.epamtc.protsko.rentcar.service.validator.car;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class {@code VinValidator} validates car's vin number
 *
 * @author Matvey Protsko
 */
public class VinValidator implements CarParameterValidator {
    private static final String VIN_REGEX = "^[a-zA-Z0-9]{14,18}$";
    private static Pattern pattern = Pattern.compile(VIN_REGEX);

    @Override
    public boolean isValid(String carVin) {
        Matcher matcher = pattern.matcher(carVin);

        return matcher.matches();
    }
}
