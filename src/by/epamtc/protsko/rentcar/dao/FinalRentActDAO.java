package by.epamtc.protsko.rentcar.dao;

import by.epamtc.protsko.rentcar.entity.order.FinalRentAct;
import by.epamtc.protsko.rentcar.dao.exception.FinalActDAOException;

public interface FinalRentActDAO {

    boolean create(int orderId) throws FinalActDAOException;

    FinalRentAct find(int orderId) throws FinalActDAOException;

    boolean update(FinalRentAct rentAct) throws FinalActDAOException;
}
