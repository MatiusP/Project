package by.epamtc.protsko.rentcar.dao;

import by.epamtc.protsko.rentcar.dao.exception.FinalActDAOException;
import by.epamtc.protsko.rentcar.entity.order.FinalRentAct;

public interface FinalRentActDAO {

    boolean create(int orderId) throws FinalActDAOException;

    FinalRentAct find(int orderId) throws FinalActDAOException;

    boolean update(FinalRentAct rentAct) throws FinalActDAOException;
}
