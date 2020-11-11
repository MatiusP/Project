package by.epamtc.protsko.rentcar.controller.command.orderlayer;

import by.epamtc.protsko.rentcar.dto.FinalRentActDTO;
import by.epamtc.protsko.rentcar.controller.command.Command;
import by.epamtc.protsko.rentcar.controller.exception.ControllerException;
import by.epamtc.protsko.rentcar.service.FinalActService;
import by.epamtc.protsko.rentcar.service.OrderService;
import by.epamtc.protsko.rentcar.service.ServiceFactory;
import by.epamtc.protsko.rentcar.service.exception.FinalRentActServiceException;
import by.epamtc.protsko.rentcar.service.exception.OrderServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CloseOrderCommand implements Command {
    private final ServiceFactory factory = ServiceFactory.getInstance();
    private final OrderService orderService = factory.getOrderService();
    private final FinalActService finalRentActService = factory.getFinalActService();
    private static final String BACK_TO_ALL_ORDERS_PAGE_MAPPING = "mainController?command=get_all_orders";
    private static final String FINAL_ACT_ID_PARAMETER_NAME = "finalActId";
    private static final String ORDER_ID_PARAMETER_NAME = "id";
    private static final String OVERDUE_PERIOD_PARAMETER_NAME = "overduePeriod";
    private static final String COST_BY_FUEL_PARAMETER_NAME = "fuelCost";
    private static final String COST_BY_MILEAGE_PARAMETER_NAME = "mileage";
    private static final String COST_BY_PARKING_PARAMETER_NAME = "parking";
    private static final String COST_BY_POLICE_PENALTY_PARAMETER_NAME = "police";
    private static final String COST_BY_DAMAGE_PARAMETER_NAME = "damage";
    private static final String COST_BY_OTHER_PENALTY_PARAMETER_NAME = "other";
    private static final String CLOSE_ORDER_ERROR_ATTRIBUTE_NAME = "closeOrderError";
    private static final String CLOSE_ORDER_ERROR_ATTRIBUTE_VALUE = "You can't close the order while order has penalties";
    private static final String UPDATE_FINAL_ACT_ERROR_ATTRIBUTE_VALUE = "The final act is not updated";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ControllerException {
        final int orderId = Integer.parseInt(request.getParameter(ORDER_ID_PARAMETER_NAME));
        final double costOverduePeriod = Double.parseDouble(request.getParameter(OVERDUE_PERIOD_PARAMETER_NAME));
        final double costByFuel = Double.parseDouble(request.getParameter(COST_BY_FUEL_PARAMETER_NAME));
        final double costByMileage = Double.parseDouble(request.getParameter(COST_BY_MILEAGE_PARAMETER_NAME));
        final double costByParking = Double.parseDouble(request.getParameter(COST_BY_PARKING_PARAMETER_NAME));
        final double costByPolicePenalty = Double.parseDouble(request.getParameter(COST_BY_POLICE_PENALTY_PARAMETER_NAME));
        final double costByDamage = Double.parseDouble(request.getParameter(COST_BY_DAMAGE_PARAMETER_NAME));
        final double costByOtherPenalty = Double.parseDouble(request.getParameter(COST_BY_OTHER_PENALTY_PARAMETER_NAME));
        boolean isOrderClosed = false;
        String closedError;

        final double penaltySum = costOverduePeriod + costByFuel + costByMileage + costByParking
                + costByPolicePenalty + costByDamage + costByOtherPenalty;

        try {
            finalRentActService.update(finalAct(request));

            if (penaltySum > 0) {
                request.setAttribute(CLOSE_ORDER_ERROR_ATTRIBUTE_NAME, CLOSE_ORDER_ERROR_ATTRIBUTE_VALUE);
                request.setAttribute(ORDER_ID_PARAMETER_NAME, orderId);
                request.getRequestDispatcher(BACK_TO_ALL_ORDERS_PAGE_MAPPING).forward(request, response);
                return;
            }

            isOrderClosed = orderService.close(orderId);
        } catch (OrderServiceException e) {
            closedError = e.getMessage();
            request.setAttribute(CLOSE_ORDER_ERROR_ATTRIBUTE_NAME, closedError);
            request.setAttribute(ORDER_ID_PARAMETER_NAME, orderId);
            request.getRequestDispatcher(BACK_TO_ALL_ORDERS_PAGE_MAPPING).forward(request, response);
        } catch (FinalRentActServiceException e) {
            closedError = e.getMessage();
            request.setAttribute(UPDATE_FINAL_ACT_ERROR_ATTRIBUTE_VALUE, closedError);
            request.setAttribute(ORDER_ID_PARAMETER_NAME, orderId);
            request.getRequestDispatcher(BACK_TO_ALL_ORDERS_PAGE_MAPPING).forward(request, response);
        }
        if (isOrderClosed) {
            response.sendRedirect(BACK_TO_ALL_ORDERS_PAGE_MAPPING);
        }
    }

    private FinalRentActDTO finalAct(HttpServletRequest request) {
        FinalRentActDTO finalRentAct = new FinalRentActDTO();

        finalRentAct.setId(Integer.parseInt(request.getParameter(FINAL_ACT_ID_PARAMETER_NAME)));
        finalRentAct.setCostOverduePeriod(Double.parseDouble(request.getParameter(OVERDUE_PERIOD_PARAMETER_NAME)));
        finalRentAct.setCostByFuel(Double.parseDouble(request.getParameter(COST_BY_FUEL_PARAMETER_NAME)));
        finalRentAct.setCostByMileage(Double.parseDouble(request.getParameter(COST_BY_MILEAGE_PARAMETER_NAME)));
        finalRentAct.setCostByParkingPenalty(Double.parseDouble(request.getParameter(COST_BY_PARKING_PARAMETER_NAME)));
        finalRentAct.setCostByPolicePenalty(Double.parseDouble(request.getParameter(COST_BY_POLICE_PENALTY_PARAMETER_NAME)));
        finalRentAct.setCostByDamagePenalty(Double.parseDouble(request.getParameter(COST_BY_DAMAGE_PARAMETER_NAME)));
        finalRentAct.setCostByOtherPenalty(Double.parseDouble(request.getParameter(COST_BY_OTHER_PENALTY_PARAMETER_NAME)));
        finalRentAct.setOrderId(Integer.parseInt(request.getParameter(ORDER_ID_PARAMETER_NAME)));

        return finalRentAct;
    }
}
