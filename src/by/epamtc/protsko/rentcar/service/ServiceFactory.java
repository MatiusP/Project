package by.epamtc.protsko.rentcar.service;

import by.epamtc.protsko.rentcar.service.impl.CarServiceImpl;
import by.epamtc.protsko.rentcar.service.impl.UserServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();
    private static UserService userService = new UserServiceImpl();
    private static CarService carService = new CarServiceImpl();

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
}
