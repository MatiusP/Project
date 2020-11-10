package by.epamtc.protsko.rentcar.entity.order;

import java.io.Serializable;

public class FinalRentAct implements Serializable {
    private static final long serialVersionUID = 222700091903811058L;

    private int id;
    private double costOverduePeriod;
    private double costByFuel;
    private double costByMileage;
    private double costByParkingPenalty;
    private double costByPolicePenalty;
    private double costByDamagePenalty;
    private double costByOtherPenalty;
    private int orderId;

    public FinalRentAct() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCostOverduePeriod() {
        return costOverduePeriod;
    }

    public void setCostOverduePeriod(double costOverduePeriod) {
        this.costOverduePeriod = costOverduePeriod;
    }

    public double getCostByFuel() {
        return costByFuel;
    }

    public void setCostByFuel(double costByFuel) {
        this.costByFuel = costByFuel;
    }

    public double getCostByMileage() {
        return costByMileage;
    }

    public void setCostByMileage(double costByMileage) {
        this.costByMileage = costByMileage;
    }

    public double getCostByParkingPenalty() {
        return costByParkingPenalty;
    }

    public void setCostByParkingPenalty(double costByParkingPenalty) {
        this.costByParkingPenalty = costByParkingPenalty;
    }

    public double getCostByPolicePenalty() {
        return costByPolicePenalty;
    }

    public void setCostByPolicePenalty(double costByPolicePenalty) {
        this.costByPolicePenalty = costByPolicePenalty;
    }

    public double getCostByDamagePenalty() {
        return costByDamagePenalty;
    }

    public void setCostByDamagePenalty(double costByDamagePenalty) {
        this.costByDamagePenalty = costByDamagePenalty;
    }

    public double getCostByOtherPenalty() {
        return costByOtherPenalty;
    }

    public void setCostByOtherPenalty(double costByOtherPenalty) {
        this.costByOtherPenalty = costByOtherPenalty;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FinalRentAct)) return false;

        FinalRentAct that = (FinalRentAct) o;

        if (id != that.id) return false;
        if (Double.compare(that.costOverduePeriod, costOverduePeriod) != 0) return false;
        if (Double.compare(that.costByFuel, costByFuel) != 0) return false;
        if (Double.compare(that.costByMileage, costByMileage) != 0) return false;
        if (Double.compare(that.costByParkingPenalty, costByParkingPenalty) != 0) return false;
        if (Double.compare(that.costByPolicePenalty, costByPolicePenalty) != 0) return false;
        if (Double.compare(that.costByDamagePenalty, costByDamagePenalty) != 0) return false;
        if (Double.compare(that.costByOtherPenalty, costByOtherPenalty) != 0) return false;
        return orderId == that.orderId;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        temp = Double.doubleToLongBits(costOverduePeriod);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(costByFuel);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(costByMileage);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(costByParkingPenalty);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(costByPolicePenalty);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(costByDamagePenalty);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(costByOtherPenalty);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + orderId;
        return result;
    }

    @Override
    public String toString() {
        return "FinalRentAct{" +
                "id=" + id +
                ", costOverduePeriod=" + costOverduePeriod +
                ", costByFuel=" + costByFuel +
                ", costByMileage=" + costByMileage +
                ", costByParkingPenalty=" + costByParkingPenalty +
                ", costByPolicePenalty=" + costByPolicePenalty +
                ", costByDamagePenalty=" + costByDamagePenalty +
                ", costByOtherPenalty=" + costByOtherPenalty +
                ", orderId=" + orderId +
                '}';
    }
}
