package by.epamtc.protsko.rentcar.service.validator.car;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FuelConsumptionValidator implements CarParameterValidator {
    private static final String FUEL_CONSUMPTION_REGEX = "^[0-9]{0,2}$";
    private static Pattern pattern = Pattern.compile(FUEL_CONSUMPTION_REGEX);

    @Override
    public boolean isValid(String carFuelConsumption) {
        Matcher matcher = pattern.matcher(carFuelConsumption);

        return matcher.matches();
    }
}
