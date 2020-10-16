package by.epamtc.protsko.rentcar.service.impl;

import by.epamtc.protsko.rentcar.bean.car.Car;
import by.epamtc.protsko.rentcar.bean.car.CarClass;
import by.epamtc.protsko.rentcar.bean.car.CarDTO;
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

    @Override
    public boolean addCar(CarDTO newCar) throws CarServiceException {
        Car car;

        try {
            car = buildCarFromCarDTO(newCar);

            return carDAO.addCar(car);
        } catch (CarDAOException e) {
            throw new CarServiceException(e);
        }
    }

    @Override
    public boolean editCarData(CarDTO carForEdit) throws CarServiceException {
        Car car;

        try {
            car = buildCarFromCarDTO(carForEdit);

            return carDAO.editCarData(car);
        } catch (CarDAOException e) {
            throw new CarServiceException(e);
        }
    }


    @Override
    public boolean deleteCarFromDatabase(int carId) throws CarServiceException {
        try {
            return carDAO.deleteCarFromDatabase(carId);
        } catch (CarDAOException e) {
            throw new CarServiceException(e);
        }
    }

    @Override
    public boolean deleteCarFromSystem(int carId) throws CarServiceException {
        try {
            return carDAO.deleteCarFromSystem(carId);
        } catch (CarDAOException e) {
            throw new CarServiceException(e);
        }
    }

    @Override
    public List<CarDTO> findCar(CarDTO criteria) throws CarServiceException {
        List<CarDTO> foundCarList = new ArrayList<>();
        CarDTO foundCar;
        String searchCriteria = CarUtil.createSearchCarQuery(criteria);

        try {
            final List<Car> foundCars = carDAO.findCar(searchCriteria);

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
    public List<CarDTO> getAllCars() throws CarServiceException {
        List<CarDTO> allCars = new ArrayList<>();
        CarDTO carDTO = null;

        try {
            final List<Car> cars = carDAO.getAllCars();

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
        carDTO.setVIN(car.getVIN());
        carDTO.setManufactureDate(car.getManufactureDate());
        carDTO.setEnginePower(car.getEnginePower());
        carDTO.setFuelConsumption(car.getFuelConsumption());
        carDTO.setAvailableToRent(car.isAvailableToRent());
        carDTO.setDeleted(car.isDeleted());
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
        car.setVIN(carDTO.getVIN());
        car.setManufactureDate(carDTO.getManufactureDate());
        car.setEnginePower(carDTO.getEnginePower());
        car.setFuelConsumption(carDTO.getFuelConsumption());
        car.setAvailableToRent(carDTO.isAvailableToRent());
        car.setDeleted(carDTO.isDeleted());
        car.setTransmissionType(Transmission.valueOf(carDTO.getTransmissionType()));
        car.setClassType(CarClass.valueOf(carDTO.getClassType()));
        car.setModel(carDTO.getModel());
        car.setBrand(carDTO.getBrand());
        car.setPhotos(carDTO.getPhotos());

        return car;
    }
}


class Main {
    public static void main(String[] args) throws CarServiceException {
        CarServiceImpl o = new CarServiceImpl();
        CarDTO car = new CarDTO();
        car.setVIN("123");
        car.setTransmissionType("automatic");

        o.findCar(car);
    }

}
