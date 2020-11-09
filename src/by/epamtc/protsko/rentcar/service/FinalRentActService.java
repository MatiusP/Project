package by.epamtc.protsko.rentcar.service;

import by.epamtc.protsko.rentcar.dto.FinalRentActDTO;
import by.epamtc.protsko.rentcar.service.exception.FinalRentActServiceException;

public interface FinalRentActService {

    boolean create(int orderId) throws FinalRentActServiceException;

    FinalRentActDTO find(int orderId) throws FinalRentActServiceException;

    boolean update(FinalRentActDTO rentAct) throws FinalRentActServiceException;
}
