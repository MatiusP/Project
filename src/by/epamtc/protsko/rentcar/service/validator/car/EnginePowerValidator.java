package by.epamtc.protsko.rentcar.service.validator.car;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class {@code EnginePowerValidator} validates car's engine power
 *
 * @author Matvey Protsko
 */
public class EnginePowerValidator implements CarParameterValidator {
    private static final String ENGINE_POWER_REGEX = "^[0-9]{0,5}$";
    private static Pattern pattern = Pattern.compile(ENGINE_POWER_REGEX);

    @Override
    public boolean isValid(String carEnginePower) {
        Matcher matcher = pattern.matcher(carEnginePower);

        return matcher.matches();
    }
}
