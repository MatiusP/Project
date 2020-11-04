package by.epamtc.protsko.rentcar.service.util;

import by.epamtc.protsko.rentcar.dao.exception.OrderDAOException;

import java.time.LocalDate;

public class OrderUtil {

    private OrderUtil() {
    }

    public static void checkCorrectRentPeriod(LocalDate startRent, LocalDate endRent) throws OrderDAOException {
        final String INCORRECT_RENT_PERIOD_MESSAGE = "Start rent date can't be after end rent date";

        if (startRent.compareTo(endRent) > 0) {
            throw new OrderDAOException(INCORRECT_RENT_PERIOD_MESSAGE);
        }
    }

    public static int getLengthRentPeriod(String rentPeriod) throws OrderDAOException {
        final String INCORRECT_PERIOD_LENGTH_MESSAGE = "Sorry, but the rental period cannot exceed 30 days.";
        final String PERIOD_LENGTH_REGEX = "^[P][0-9]{1,2}[D]$";

        if (!rentPeriod.matches(PERIOD_LENGTH_REGEX)) {
            throw new OrderDAOException(INCORRECT_PERIOD_LENGTH_MESSAGE);
        }
        return Integer.parseInt(rentPeriod.substring(1, (rentPeriod.length() - 1)) + 1);
    }
}
