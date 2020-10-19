package by.epamtc.protsko.rentcar.bean.order;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    private static final long serialVersionUID = 6162847055282723195L;

    private int id;
    private Date startRent;
    private Date end_rent;
    private double totalPrice;
    boolean isOrderCanceled;
    boolean isOrderAccepted;
    boolean isOrderClosed;
    private FinalRentAct finalAct;
    private int userId;
    private int carId;

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartRent() {
        return startRent;
    }

    public void setStartRent(Date startRent) {
        this.startRent = startRent;
    }

    public Date getEnd_rent() {
        return end_rent;
    }

    public void setEnd_rent(Date end_rent) {
        this.end_rent = end_rent;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isOrderCanceled() {
        return isOrderCanceled;
    }

    public void setOrderCanceled(boolean orderCanceled) {
        isOrderCanceled = orderCanceled;
    }

    public boolean isOrderAccepted() {
        return isOrderAccepted;
    }

    public void setOrderAccepted(boolean orderAccepted) {
        isOrderAccepted = orderAccepted;
    }

    public boolean isOrderClosed() {
        return isOrderClosed;
    }

    public void setOrderClosed(boolean orderClosed) {
        isOrderClosed = orderClosed;
    }

    public FinalRentAct getFinalAct() {
        return finalAct;
    }

    public void setFinalAct(FinalRentAct finalAct) {
        this.finalAct = finalAct;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (Double.compare(order.totalPrice, totalPrice) != 0) return false;
        if (isOrderCanceled != order.isOrderCanceled) return false;
        if (isOrderAccepted != order.isOrderAccepted) return false;
        if (isOrderClosed != order.isOrderClosed) return false;
        if (userId != order.userId) return false;
        if (carId != order.carId) return false;
        if (startRent != null ? !startRent.equals(order.startRent) : order.startRent != null) return false;
        if (end_rent != null ? !end_rent.equals(order.end_rent) : order.end_rent != null) return false;
        return finalAct != null ? finalAct.equals(order.finalAct) : order.finalAct == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (startRent != null ? startRent.hashCode() : 0);
        result = 31 * result + (end_rent != null ? end_rent.hashCode() : 0);
        temp = Double.doubleToLongBits(totalPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (isOrderCanceled ? 1 : 0);
        result = 31 * result + (isOrderAccepted ? 1 : 0);
        result = 31 * result + (isOrderClosed ? 1 : 0);
        result = 31 * result + (finalAct != null ? finalAct.hashCode() : 0);
        result = 31 * result + userId;
        result = 31 * result + carId;
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", startRent=" + startRent +
                ", end_rent=" + end_rent +
                ", totalPrice=" + totalPrice +
                ", isOrderCanceled=" + isOrderCanceled +
                ", isOrderAccepted=" + isOrderAccepted +
                ", isOrderClosed=" + isOrderClosed +
                ", finalAct=" + finalAct +
                ", userId=" + userId +
                ", carId=" + carId +
                '}';
    }
}
