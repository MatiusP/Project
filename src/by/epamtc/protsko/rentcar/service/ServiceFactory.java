package by.epamtc.protsko.rentcar.service;

import by.epamtc.protsko.rentcar.service.impl.CarServiceImpl;
import by.epamtc.protsko.rentcar.service.impl.FinalActServiceImpl;
import by.epamtc.protsko.rentcar.service.impl.OrderServiceImpl;
import by.epamtc.protsko.rentcar.service.impl.UserServiceImpl;

/**
 * Factory class that provides different implementation of Service interfaces.
 *
 * @author Matvey Protsko
 */
public class ServiceFactory {
    /**
     * A single instance of the class (pattern Singleton)
     */
    private static final ServiceFactory instance = new ServiceFactory();
    /**
     * An object of {@link UserServiceImpl}
     */
    private static UserService userService = new UserServiceImpl();
    /**
     * An object of {@link CarServiceImpl}
     */
    private static CarService carService = new CarServiceImpl();
    /**
     * An object of {@link OrderServiceImpl}
     */
    private static OrderService orderService = new OrderServiceImpl();
    /**
     * An object of {@link FinalActServiceImpl}
     */
    private static FinalActService finalActService = new FinalActServiceImpl();

    private ServiceFactory() {
    }

    /**
     * Returns singleton object of the class
     *
     * @return Object of {@link ServiceFactory}
     */
    public static ServiceFactory getInstance() {
        return instance;
    }

    /**
     * Returns field of {@link UserService} object
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     * Returns field of {@link CarService} object
     */
    public CarService getCarService() {
        return carService;
    }

    /**
     * Returns field of {@link OrderService} object
     */
    public OrderService getOrderService() {
        return orderService;
    }

    /**
     * Returns field of {@link FinalActService} object
     */
    public FinalActService getFinalActService() {
        return finalActService;
    }
}
