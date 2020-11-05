package by.epamtc.protsko.rentcar.bean.order;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Order implements Serializable {
    private static final long serialVersionUID = -4752793807982782517L;

    private int id;
    private LocalDateTime orderDate;
    private LocalDate startRent;
    private LocalDate endRent;
    private double totalPrice;
    private boolean isOrderAccepted;
    private boolean isOrderCanceled;
    private boolean isOrderClosed;
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

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getStartRent() {
        return startRent;
    }

    public void setStartRent(LocalDate startRent) {
        this.startRent = startRent;
    }

    public LocalDate getEndRent() {
        return endRent;
    }

    public void setEndRent(LocalDate endRent) {
        this.endRent = endRent;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isOrderAccepted() {
        return isOrderAccepted;
    }

    public void setOrderAccepted(boolean orderAccepted) {
        isOrderAccepted = orderAccepted;
    }

    public boolean isOrderCanceled() {
        return isOrderCanceled;
    }

    public void setOrderCanceled(boolean orderCanceled) {
        isOrderCanceled = orderCanceled;
    }

    public boolean isOrderClosed() {
        return isOrderClosed;
    }

    public void setOrderClosed(boolean orderClosed) {
        isOrderClosed = orderClosed;
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
        if (isOrderAccepted != order.isOrderAccepted) return false;
        if (isOrderCanceled != order.isOrderCanceled) return false;
        if (isOrderClosed != order.isOrderClosed) return false;
        if (userId != order.userId) return false;
        if (carId != order.carId) return false;
        if (orderDate != null ? !orderDate.equals(order.orderDate) : order.orderDate != null) return false;
        if (startRent != null ? !startRent.equals(order.startRent) : order.startRent != null) return false;
        return endRent != null ? endRent.equals(order.endRent) : order.endRent == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + (startRent != null ? startRent.hashCode() : 0);
        result = 31 * result + (endRent != null ? endRent.hashCode() : 0);
        temp = Double.doubleToLongBits(totalPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (isOrderAccepted ? 1 : 0);
        result = 31 * result + (isOrderCanceled ? 1 : 0);
        result = 31 * result + (isOrderClosed ? 1 : 0);
        result = 31 * result + userId;
        result = 31 * result + carId;
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", startRent=" + startRent +
                ", endRent=" + endRent +
                ", totalPrice=" + totalPrice +
                ", isOrderAccepted=" + isOrderAccepted +
                ", isOrderCanceled=" + isOrderCanceled +
                ", isOrderClosed=" + isOrderClosed +
                ", userId=" + userId +
                ", carId=" + carId +
                '}';
    }
}
