package by.epamtc.protsko.rentcar.service;

import by.epamtc.protsko.rentcar.service.impl.CarServiceImpl;
import by.epamtc.protsko.rentcar.service.impl.FinalRentActServiceImpl;
import by.epamtc.protsko.rentcar.service.impl.OrderServiceImpl;
import by.epamtc.protsko.rentcar.service.impl.UserServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();
    private static UserService userService = new UserServiceImpl();
    private static CarService carService = new CarServiceImpl();
    private static OrderService orderService = new OrderServiceImpl();
    private static FinalRentActService finalRentActService = new FinalRentActServiceImpl();

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public CarService getCarService() {
        return carService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public FinalRentActService getFinalRentActService() {
        return finalRentActService;
    }
}
