package by.epamtc.protsko.rentcar.service;

import by.epamtc.protsko.rentcar.dto.OrderDTO;
import by.epamtc.protsko.rentcar.bean.order.OrderForClientAccept;
import by.epamtc.protsko.rentcar.dto.OrderForShowDTO;
import by.epamtc.protsko.rentcar.service.exception.OrderServiceException;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {

    boolean add(OrderDTO order) throws OrderServiceException;

    OrderForClientAccept createOrderForClientAccept(int carId, LocalDate startRent, LocalDate endRent) throws OrderServiceException;

    boolean accept(int orderId) throws OrderServiceException;

    boolean close(int orderId) throws OrderServiceException;

    boolean cancel(int orderId) throws OrderServiceException;

    List<OrderForShowDTO> findAll() throws OrderServiceException;

    List<OrderForShowDTO> findByUserId(int userId) throws OrderServiceException;
}
