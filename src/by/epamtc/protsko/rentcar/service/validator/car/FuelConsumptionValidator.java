package by.epamtc.protsko.rentcar.service.validator.car;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class {@code FuelConsumptionValidator} validates car's fuel consumption
 *
 * @author Matvey Protsko
 */
public class FuelConsumptionValidator implements CarParameterValidator {
    private static final String FUEL_CONSUMPTION_REGEX = "^(\\d{1,2}\\.\\d+)$";
    private static Pattern pattern = Pattern.compile(FUEL_CONSUMPTION_REGEX);

    @Override
    public boolean isValid(String carFuelConsumption) {
        Matcher matcher = pattern.matcher(carFuelConsumption);

        return matcher.matches();
    }
}
