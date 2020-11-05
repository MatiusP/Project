package by.epamtc.protsko.rentcar.service.util;

import by.epamtc.protsko.rentcar.service.exception.OrderServiceException;

import java.time.LocalDate;

public class OrderUtil {
    private static final String INCORRECT_RENT_PERIOD_MESSAGE = "Start rent date can't be after end rent date";
    private static final String INCORRECT_PERIOD_LENGTH_MESSAGE = "Sorry, but the rental period cannot exceed 30 days.";
    private static final String PERIOD_LENGTH_REGEX = "^[P][0-9]{1,2}[D]$";

    private OrderUtil() {
    }

    public static void checkCorrectRentPeriod(LocalDate startRent, LocalDate endRent) throws OrderServiceException {
        if (startRent.compareTo(endRent) > 0) {
            throw new OrderServiceException(INCORRECT_RENT_PERIOD_MESSAGE);
        }
    }

    public static int getLengthRentPeriod(String rentPeriod) throws OrderServiceException {
        if (!rentPeriod.matches(PERIOD_LENGTH_REGEX)) {
            throw new OrderServiceException(INCORRECT_PERIOD_LENGTH_MESSAGE);
        }
        return Integer.parseInt(rentPeriod.substring(1, (rentPeriod.length() - 1)));
    }
}
