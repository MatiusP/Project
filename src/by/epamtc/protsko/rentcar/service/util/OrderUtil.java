package by.epamtc.protsko.rentcar.service.util;

import by.epamtc.protsko.rentcar.bean.car.Car;
import by.epamtc.protsko.rentcar.bean.order.Order;
import by.epamtc.protsko.rentcar.dao.CarDAO;
import by.epamtc.protsko.rentcar.dao.DAOFactory;
import by.epamtc.protsko.rentcar.dao.OrderDAO;
import by.epamtc.protsko.rentcar.dao.exception.CarDAOException;
import by.epamtc.protsko.rentcar.dao.exception.OrderDAOException;
import by.epamtc.protsko.rentcar.service.exception.OrderServiceException;

import java.time.LocalDate;
import java.util.List;

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
