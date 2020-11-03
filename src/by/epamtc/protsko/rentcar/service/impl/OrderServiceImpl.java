package by.epamtc.protsko.rentcar.service.impl;

import by.epamtc.protsko.rentcar.bean.order.FinalRentActDTO;
import by.epamtc.protsko.rentcar.bean.order.OrderDTO;
import by.epamtc.protsko.rentcar.bean.order.OrderForShowDTO;
import by.epamtc.protsko.rentcar.dao.DAOFactory;
import by.epamtc.protsko.rentcar.dao.OrderDAO;
import by.epamtc.protsko.rentcar.dao.exception.OrderDAOException;
import by.epamtc.protsko.rentcar.service.OrderService;
import by.epamtc.protsko.rentcar.service.exception.OrderServiceException;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final DAOFactory factory = DAOFactory.getInstance();
    private final OrderDAO orderDAO = factory.getOrderDAO();


    @Override
    public boolean createOrder(OrderDTO order) throws OrderServiceException {
        return false;
    }

    @Override
    public boolean acceptOrder(int orderId) throws OrderServiceException {
        try {
            return orderDAO.acceptOrder(orderId);
        } catch (OrderDAOException e) {
            throw new OrderServiceException(e);
        }
    }

    @Override
    public boolean closeOrder(int orderId) throws OrderServiceException {

        try {
            return orderDAO.closeOrder(orderId);
        } catch (OrderDAOException e) {
            throw new OrderServiceException(e);
        }
    }

    @Override
    public List<OrderForShowDTO> getAllOrders() throws OrderServiceException {
        return null;
    }

    @Override
    public List<OrderForShowDTO> getUserOrders(int userId) throws OrderServiceException {
        return null;
    }

    @Override
    public FinalRentActDTO getFinalRentAct(int orderId) throws OrderServiceException {
        return null;
    }

    @Override
    public boolean updateFinalRentAct(FinalRentActDTO rentAct) throws OrderServiceException {
        return false;
    }


}
