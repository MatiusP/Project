package by.epamtc.protsko.rentcar.dao;

import by.epamtc.protsko.rentcar.dao.exception.FinalActDAOException;
import by.epamtc.protsko.rentcar.entity.order.FinalRentAct;

/**
 * This interface provides methods for working with final rent act's data.
 *
 * @author Matvey Protsko
 */

public interface FinalRentActDAO {

    /**
     * Method {@code create} create a new final rent act.
     * All orders in the system have their own final rent acts.
     *
     * @param orderId - id of the order we want to create final rent act.
     * @return true if the final rent act was successfully created, false -
     * if final rent act has not been created.
     * @throws FinalActDAOException when problems with database access occur.
     */
    boolean create(int orderId) throws FinalActDAOException;

    /**
     * Method {@code find} finds a final rent act by order id.
     *
     * @param orderId - id of the order, final rent act of which we want to find.
     * @return FinalRentAct object which id matches orderId parameter value.
     * @throws FinalActDAOException when problems with database access occur.
     */
    FinalRentAct find(int orderId) throws FinalActDAOException;

    /**
     * Method {@code update} updates final rent act data in database.
     *
     * @param rentAct contains entered new final rent act's data.
     * @return true if the final rent act data was successfully updated, false -
     * if has not been updated.
     * @throws FinalActDAOException when problems with database access occur.
     */
    boolean update(FinalRentAct rentAct) throws FinalActDAOException;
}
