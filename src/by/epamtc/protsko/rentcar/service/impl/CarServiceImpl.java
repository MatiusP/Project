package by.epamtc.protsko.rentcar.service.impl;

import by.epamtc.protsko.rentcar.bean.car.Car;
import by.epamtc.protsko.rentcar.bean.car.CarClass;
import by.epamtc.protsko.rentcar.dto.CarDTO;
import by.epamtc.protsko.rentcar.bean.car.Transmission;
import by.epamtc.protsko.rentcar.dao.CarDAO;
import by.epamtc.protsko.rentcar.dao.DAOFactory;
import by.epamtc.protsko.rentcar.dao.exception.CarDAOException;
import by.epamtc.protsko.rentcar.service.CarService;
import by.epamtc.protsko.rentcar.service.exception.CarServiceException;
import by.epamtc.protsko.rentcar.service.util.CarUtil;

import java.util.ArrayList;
import java.util.List;

public class CarServiceImpl implements CarService {
    private DAOFactory daoFactory = DAOFactory.getInstance();
    private CarDAO carDAO = daoFactory.getCarDAO();
    private static final String CAR_EXISTS_MESSAGE = "Car VIN already exists in database";
    private static final String IS_CAR_AVAILABLE = "AVAILABLE";
    private static final String IS_CAR_NOT_AVAILABLE = "NOT_AVAILABLE";
    private static final String IS_CAR_DELETED = "DELETED";
    private static final String IS_CAR_NOT_DELETED = "ACTIVE";

    @Override
    public boolean add(CarDTO newCar) throws CarServiceException {
        Car car;

        try {
            if (carDAO.isVinExists(newCar.getVin())) {
                throw new CarServiceException(CAR_EXISTS_MESSAGE);
            }

            if (CarUtil.isEnteredDataValid(newCar)) {
                car = buildCarFromCarDTO(newCar);
                return carDAO.add(car);
            }
        } catch (CarDAOException e) {
            throw new CarServiceException(e);
        }
        return false;
    }

    @Override
    public boolean edit(CarDTO carForEdit) throws CarServiceException {
        Car car;

        try {
            if (CarUtil.isEnteredDataValid(carForEdit)) {
                car = buildCarFromCarDTO(carForEdit);
                return carDAO.edit(car);
            }
        } catch (CarDAOException e) {
            throw new CarServiceException(e);
        }
        return false;
    }

    @Override
    public boolean delete(int carId) throws CarServiceException {
        try {
            return carDAO.delete(carId);
        } catch (CarDAOException e) {
            throw new CarServiceException(e);
        }
    }

    @Override
    public List<CarDTO> findByCriteria(CarDTO criteria) throws CarServiceException {
        List<CarDTO> foundCarList = new ArrayList<>();
        CarDTO foundCar;
        String searchCriteria = CarUtil.createSearchCarQuery(criteria);

        try {
            final List<Car> foundCars = carDAO.findBySearchCriteria(searchCriteria);

            for (Car car : foundCars) {
                foundCar = buildCarDTOFromCar(car);
                foundCarList.add(foundCar);
            }
        } catch (CarDAOException e) {
            throw new CarServiceException(e);
        }
        return foundCarList;
    }

    @Override
    public List<CarDTO> findAll() throws CarServiceException {
        List<CarDTO> allCars = new ArrayList<>();
        CarDTO carDTO = null;

        try {
            final List<Car> cars = carDAO.findAll();

            for (Car car : cars) {
                carDTO = buildCarDTOFromCar(car);

                allCars.add(carDTO);
            }
        } catch (CarDAOException e) {
            throw new CarServiceException(e);
        }
        return allCars;
    }

    private CarDTO buildCarDTOFromCar(Car car) {
        CarDTO carDTO = new CarDTO();

        carDTO.setId(car.getId());
        carDTO.setVin(car.getVin());
        carDTO.setManufactureDate(String.valueOf(car.getManufactureDate()));
        carDTO.setEnginePower(String.valueOf(car.getEnginePower()));
        carDTO.setFuelConsumption(String.valueOf(car.getFuelConsumption()));
        if (car.isAvailableToRent()) {
            carDTO.setIsAvailableToRent(IS_CAR_AVAILABLE);
        } else {
            carDTO.setIsAvailableToRent(IS_CAR_NOT_AVAILABLE);
        }
        if (car.isDeleted()) {
            carDTO.setIsDeleted(IS_CAR_DELETED);
        } else {
            carDTO.setIsDeleted(IS_CAR_NOT_DELETED);
        }
        carDTO.setPricePerDay(String.valueOf(car.getPricePerDay()));
        carDTO.setTransmissionType(car.getTransmissionType().toString());
        carDTO.setClassType(car.getClassType().toString());
        carDTO.setModel(car.getModel());
        carDTO.setBrand(car.getBrand());
        carDTO.setPhotos(car.getPhotos());

        return carDTO;
    }

    private Car buildCarFromCarDTO(CarDTO carDTO) {
        Car car = new Car();

        car.setId(carDTO.getId());
        car.setVin(carDTO.getVin());
        car.setManufactureDate(Integer.parseInt(carDTO.getManufactureDate()));
        car.setEnginePower(Integer.parseInt(carDTO.getEnginePower()));
        car.setFuelConsumption(Double.parseDouble(carDTO.getFuelConsumption()));
        car.setPricePerDay(Double.parseDouble(carDTO.getPricePerDay()));
        car.setTransmissionType(Transmission.valueOf(carDTO.getTransmissionType().toUpperCase()));
        car.setClassType(CarClass.valueOf(carDTO.getClassType().toUpperCase()));
        car.setModel(carDTO.getModel());
        car.setBrand(carDTO.getBrand());
        car.setPhotos(carDTO.getPhotos());
        if (carDTO.getIsAvailableToRent().equalsIgnoreCase(IS_CAR_AVAILABLE)) {
            car.setAvailableToRent(true);
        } else {
            car.setAvailableToRent(false);
        }
        if (carDTO.getIsDeleted() == null) {
            car.setDeleted(false);
        } else if (carDTO.getIsDeleted().equalsIgnoreCase(IS_CAR_DELETED)) {
            car.setDeleted(true);
        } else {
            car.setDeleted(false);
        }
        return car;
    }
}
