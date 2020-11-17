package by.epamtc.protsko.rentcar.service.impl;

import by.epamtc.protsko.rentcar.dao.DAOFactory;
import by.epamtc.protsko.rentcar.dao.FinalRentActDAO;
import by.epamtc.protsko.rentcar.dao.exception.FinalActDAOException;
import by.epamtc.protsko.rentcar.dto.FinalRentActDTO;
import by.epamtc.protsko.rentcar.entity.order.FinalRentAct;
import by.epamtc.protsko.rentcar.service.FinalActService;
import by.epamtc.protsko.rentcar.service.exception.FinalRentActServiceException;

/**
 * This class implementation of {@link FinalActService}. Methods execute business logic
 * for working with final rent act layer.
 *
 * @author Matvey Protsko
 */

public class FinalActServiceImpl implements FinalActService {
    private DAOFactory daoFactory = DAOFactory.getInstance();
    private FinalRentActDAO finalRentActDAO = daoFactory.getFinalRentActDAO();
    private static final String CREATE_FINAL_ACT_ERROR_MESSAGE = "Error while creating final rent act";

    /**
     * Method {@code create} provides business logic for creating
     * final rental act for a specific order.
     *
     * @param orderId - id of the order, final rent act of which we want to create.
     * @return true if final rental act was successfully created. Otherwise method
     * throw FinalRentActServiceException.
     * @throws FinalRentActServiceException when problems in FinalRentActDAO
     *                                      {@code FinalRentActDAO}.
     */
    @Override
    public boolean create(int orderId) throws FinalRentActServiceException {
        boolean isCreateFinalActSuccessfully = false;

        try {
            isCreateFinalActSuccessfully = finalRentActDAO.create(orderId);
            if (!isCreateFinalActSuccessfully) {
                throw new FinalRentActServiceException(CREATE_FINAL_ACT_ERROR_MESSAGE);
            }
        } catch (FinalActDAOException e) {
            throw new FinalRentActServiceException(e);
        }
        return true;
    }

    /**
     * Method {@code find} provides business logic for finding
     * final rental act by orderId.
     *
     * @param orderId - id of the order, final rent act of which we want to find.
     * @return {@link FinalRentActDTO} object, whose contains
     * all information about final rental act.
     * @throws FinalRentActServiceException when problems in FinalRentActDAO
     *                                      {@code FinalRentActDAO}.
     */
    @Override
    public FinalRentActDTO find(int orderId) throws FinalRentActServiceException {
        FinalRentActDTO finalRentAct;

        try {
            final FinalRentAct act = finalRentActDAO.find(orderId);
            finalRentAct = buildFinalRentActDTO(act);
        } catch (FinalActDAOException e) {
            throw new FinalRentActServiceException(e);
        }
        return finalRentAct;
    }

    /**
     * Method {@code updatev} provides business logic for updating
     * final rental act.
     *
     * @param rentAct contains entered new final rent act's data.
     * @return true if final rental act was successfully updated, false -
     * if final rent act has not been updated.
     * @throws FinalRentActServiceException when problems in FinalRentActDAO
     *                                      {@code FinalRentActDAO} layer.
     */
    @Override
    public boolean update(FinalRentActDTO rentAct) throws FinalRentActServiceException {
        FinalRentAct finalRentAct = buildFinalRentAct(rentAct);

        try {
            return finalRentActDAO.update(finalRentAct);
        } catch (FinalActDAOException e) {
            throw new FinalRentActServiceException(e);
        }
    }

    private FinalRentAct buildFinalRentAct(FinalRentActDTO finalRentActDTO) {
        FinalRentAct finalRentAct = new FinalRentAct();

        finalRentAct.setId(finalRentActDTO.getId());
        finalRentAct.setCostOverduePeriod(finalRentActDTO.getCostOverduePeriod());
        finalRentAct.setCostByFuel(finalRentActDTO.getCostByFuel());
        finalRentAct.setCostByMileage(finalRentActDTO.getCostByMileage());
        finalRentAct.setCostByParkingPenalty(finalRentActDTO.getCostByParkingPenalty());
        finalRentAct.setCostByPolicePenalty(finalRentActDTO.getCostByPolicePenalty());
        finalRentAct.setCostByDamagePenalty(finalRentActDTO.getCostByDamagePenalty());
        finalRentAct.setCostByOtherPenalty(finalRentActDTO.getCostByOtherPenalty());
        finalRentAct.setOrderId(finalRentActDTO.getOrderId());

        return finalRentAct;
    }

    private FinalRentActDTO buildFinalRentActDTO(FinalRentAct finalRentAct) {
        FinalRentActDTO finalAct = new FinalRentActDTO();

        finalAct.setId(finalRentAct.getId());
        finalAct.setCostOverduePeriod(finalRentAct.getCostOverduePeriod());
        finalAct.setCostByFuel(finalRentAct.getCostByFuel());
        finalAct.setCostByMileage(finalRentAct.getCostByMileage());
        finalAct.setCostByParkingPenalty(finalRentAct.getCostByParkingPenalty());
        finalAct.setCostByPolicePenalty(finalRentAct.getCostByPolicePenalty());
        finalAct.setCostByDamagePenalty(finalRentAct.getCostByDamagePenalty());
        finalAct.setCostByOtherPenalty(finalRentAct.getCostByOtherPenalty());
        finalAct.setOrderId(finalRentAct.getOrderId());

        return finalAct;
    }
}
