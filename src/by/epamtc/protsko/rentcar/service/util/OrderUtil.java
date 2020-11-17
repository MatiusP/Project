package by.epamtc.protsko.rentcar.service.util;

import by.epamtc.protsko.rentcar.entity.car.Car;
import by.epamtc.protsko.rentcar.entity.order.Order;
import by.epamtc.protsko.rentcar.dao.CarDAO;
import by.epamtc.protsko.rentcar.dao.DAOFactory;
import by.epamtc.protsko.rentcar.dao.OrderDAO;
import by.epamtc.protsko.rentcar.dao.exception.CarDAOException;
import by.epamtc.protsko.rentcar.dao.exception.OrderDAOException;
import by.epamtc.protsko.rentcar.service.exception.OrderServiceException;

import java.time.LocalDate;
import java.util.List;

/**
 * {@code OrderUtil} is a util class for {@code OrderServiceIml} class.
 *
 * @author Matvey Protsko
 */
public class OrderUtil {
    private static final DAOFactory factory = DAOFactory.getInstance();
    private static final CarDAO carDAO = factory.getCarDAO();
    private static final OrderDAO orderDAO = factory.getOrderDAO();

    private static final String INCORRECT_RENT_PERIOD_MESSAGE = "Start rent date can't be after end rent date";
    private static final String INCORRECT_PERIOD_LENGTH_MESSAGE = "Sorry, but the rental period cannot exceed 30 days.";
    private static final String PERIOD_LENGTH_REGEX = "^[P][0-9]{1,2}[D]$";
    private static final String CAR_ID_CRITERIA_NAME = "car_id=";
    private static final String CAR_NOT_FOUND_ERROR_MESSAGE = "Car not found. Please, select another car";

    private OrderUtil() {
    }

    /**
     * The method {@code checkCorrectRentPeriod} validation the input
     * start and end rental period.
     *
     * @param startRent start rental period date.
     * @param endRent   end rental period date.
     * @throws OrderServiceException if rental period invalid.
     */
    public static void checkCorrectRentPeriod(LocalDate startRent, LocalDate endRent) throws OrderServiceException {
        if (startRent.compareTo(endRent) > 0) {
            throw new OrderServiceException(INCORRECT_RENT_PERIOD_MESSAGE);
        }
    }

    /**
     * The method {@code getLengthRentPeriod} validate and calculates the length of rental period.
     *
     * @param rentPeriod start rental period date.
     * @return length of rental period if entered rental period valid. Otherwise method
     * throw OrderServiceException.
     * @throws OrderServiceException if rental period invalid.
     */
    public static int getLengthRentPeriod(String rentPeriod) throws OrderServiceException {
        if (!rentPeriod.matches(PERIOD_LENGTH_REGEX)) {
            throw new OrderServiceException(INCORRECT_PERIOD_LENGTH_MESSAGE);
        }
        return Integer.parseInt(rentPeriod.substring(1, (rentPeriod.length() - 1)));
    }

    /**
     * The method {@code getSelectedCar} finding a car by carId.
     *
     * @param carId id of the car we want to find.
     * @return {@link Car} object if a car was find. Otherwise method
     * throw OrderServiceException.
     * @throws OrderServiceException if a car has been not found.
     */
    public static Car getSelectedCar(final int carId) throws OrderServiceException {
        Car car;

        try {
            String searchCriteria = CAR_ID_CRITERIA_NAME + carId;
            List<Car> carList = carDAO.findBySearchCriteria(searchCriteria);

            if (carList.isEmpty()) {
                throw new OrderServiceException(CAR_NOT_FOUND_ERROR_MESSAGE);
            }

            car = carList.get(0);
        } catch (CarDAOException e) {
            throw new OrderServiceException(e);
        }
        return car;
    }

    /**
     * The method {@code isOrderAvailableForCreate} searches for all car's orders
     * and checks if the order is available for create.
     *
     * @param carId     id of the car, order of which we want to create.
     * @param startRent start rental period date.
     * @param endRent   end rental period date.
     * @return true if the order is available for creation. Otherwise method
     * return false.
     * @throws OrderServiceException when problems in OrderDAO {@code OrderDAO} layer or
     *                               if the car not available for create new order.
     */
    public static boolean isOrderAvailableForCreate(int carId, final LocalDate startRent, final LocalDate endRent)
            throws OrderServiceException {
        List<Order> carOrders;
        boolean isOrderAvailableToCreate = true;

        try {
            carOrders = orderDAO.findByCarId(carId);

            for (Order carOrder : carOrders) {
                if (carOrder.isOrderAccepted() & !carOrder.isOrderClosed() & !carOrder.isOrderCanceled()) {
                    isOrderAvailableToCreate = (startRent.isAfter(carOrder.getEndRent()) || endRent.isBefore(carOrder.getStartRent()));
                    if (!isOrderAvailableToCreate) {
                        return isOrderAvailableToCreate;
                    }
                }
            }
        } catch (OrderDAOException e) {
            throw new OrderServiceException(e);
        }
        return isOrderAvailableToCreate;
    }

    /**
     * The method {@code getCarOrdersCount} return count of car's orders.
     *
     * @param carId id of the car, orders of which we want to get.
     * @return count of car's orders.
     * @throws OrderServiceException when problems in OrderDAO {@code OrderDAO}.
     */
    public static int getCarOrdersCount(int carId) throws OrderServiceException {
        int carOrdersCount = 0;
        List<Order> carOrders;

        try {
            carOrders = orderDAO.findByCarId(carId);
            for (Order order : carOrders) {
                if (order.isOrderAccepted() & !order.isOrderClosed() & !order.isOrderCanceled())
                    carOrdersCount++;
            }
        } catch (OrderDAOException e) {
            throw new OrderServiceException(e);
        }
        return carOrdersCount;
    }
}
