package by.epamtc.protsko.rentcar.service;

import by.epamtc.protsko.rentcar.dto.FinalRentActDTO;
import by.epamtc.protsko.rentcar.service.exception.FinalRentActServiceException;

/**
 * This interface provides methods for final rent act data business logic
 * and providing access to final rent act's information.
 *
 * @author Matvey Protsko
 */

public interface FinalActService {

    /**
     * Method {@code create} provides business logic for creating
     * final rental act for a specific order.
     *
     * @param orderId - id of the order, final rent act of which we want to create.
     * @return true if final rental act was successfully created, false -
     * if final rent act has not been created.
     * @throws FinalRentActServiceException when problems in FinalRentActDAO
     *                                      {@code FinalRentActDAO} layer or
     *                                      in business logic.
     */
    boolean create(int orderId) throws FinalRentActServiceException;

    /**
     * Method {@code find} provides business logic for finding
     * final rental act by orderId.
     *
     * @param orderId - id of the order, final rent act of which we want to find.
     * @return {@link FinalRentActDTO} object, whose contains
     * all information about final rental act.
     * @throws FinalRentActServiceException when problems in FinalRentActDAO
     *                                      {@code FinalRentActDAO} layer or
     *                                      in business logic.
     */
    FinalRentActDTO find(int orderId) throws FinalRentActServiceException;

    /**
     * Method {@code updatev} provides business logic for updating
     * final rental act.
     *
     * @param rentAct contains entered new final rent act's data.
     * @return true if final rental act was successfully updated, false -
     * if final rent act has not been updated.
     * @throws FinalRentActServiceException when problems in FinalRentActDAO
     *                                      {@code FinalRentActDAO} layer or
     *                                      in business logic.
     */
    boolean update(FinalRentActDTO rentAct) throws FinalRentActServiceException;

}
