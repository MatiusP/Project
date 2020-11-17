package by.epamtc.protsko.rentcar.service.validator.car;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class {@code TransmissionValidator} validates car's transmission type
 *
 * @author Matvey Protsko
 */
public class TransmissionValidator implements CarParameterValidator {
    private static final String TRANSMISSION_REGEX = "^(Manual|Automatic)$";
    private static Pattern pattern = Pattern.compile(TRANSMISSION_REGEX);

    @Override
    public boolean isValid(String carTransmission) {
        Matcher matcher = pattern.matcher(carTransmission);

        return matcher.matches();
    }
}
