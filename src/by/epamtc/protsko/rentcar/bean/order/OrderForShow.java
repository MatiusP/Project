package by.epamtc.protsko.rentcar.bean.order;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class OrderForShow implements Serializable {
    private static final long serialVersionUID = -6538357744717138881L;

    private int orderId;
    private LocalDateTime orderDate;
    private Date orderStartRent;
    private Date orderEndRent;
    private double orderTotalPrice;
    private boolean isOrderAccepted;
    private boolean isOrderClosed;
    private boolean isOrderCanceled;
    private String orderCarBrand;
    private String orderCarModel;
    private int orderCarId;
    private int orderUserId;

    public OrderForShow() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Date getOrderStartRent() {
        return orderStartRent;
    }

    public void setOrderStartRent(Date orderStartRent) {
        this.orderStartRent = orderStartRent;
    }

    public Date getOrderEndRent() {
        return orderEndRent;
    }

    public void setOrderEndRent(Date orderEndRent) {
        this.orderEndRent = orderEndRent;
    }

    public double getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(double orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
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

    public boolean isOrderCanceled() {
        return isOrderCanceled;
    }

    public void setOrderCanceled(boolean orderCanceled) {
        isOrderCanceled = orderCanceled;
    }

    public String getOrderCarBrand() {
        return orderCarBrand;
    }

    public void setOrderCarBrand(String orderCarBrand) {
        this.orderCarBrand = orderCarBrand;
    }

    public String getOrderCarModel() {
        return orderCarModel;
    }

    public void setOrderCarModel(String orderCarModel) {
        this.orderCarModel = orderCarModel;
    }

    public int getOrderCarId() {
        return orderCarId;
    }

    public void setOrderCarId(int orderCarId) {
        this.orderCarId = orderCarId;
    }

    public int getOrderUserId() {
        return orderUserId;
    }

    public void setOrderUserId(int orderUserId) {
        this.orderUserId = orderUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderForShow)) return false;

        OrderForShow order = (OrderForShow) o;

        if (orderId != order.orderId) return false;
        if (Double.compare(order.orderTotalPrice, orderTotalPrice) != 0) return false;
        if (isOrderAccepted != order.isOrderAccepted) return false;
        if (isOrderClosed != order.isOrderClosed) return false;
        if (isOrderCanceled != order.isOrderCanceled) return false;
        if (orderCarId != order.orderCarId) return false;
        if (orderUserId != order.orderUserId) return false;
        if (orderDate != null ? !orderDate.equals(order.orderDate) : order.orderDate != null) return false;
        if (orderStartRent != null ? !orderStartRent.equals(order.orderStartRent) : order.orderStartRent != null)
            return false;
        if (orderEndRent != null ? !orderEndRent.equals(order.orderEndRent) : order.orderEndRent != null) return false;
        if (orderCarBrand != null ? !orderCarBrand.equals(order.orderCarBrand) : order.orderCarBrand != null)
            return false;
        return orderCarModel != null ? orderCarModel.equals(order.orderCarModel) : order.orderCarModel == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = orderId;
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + (orderStartRent != null ? orderStartRent.hashCode() : 0);
        result = 31 * result + (orderEndRent != null ? orderEndRent.hashCode() : 0);
        temp = Double.doubleToLongBits(orderTotalPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (isOrderAccepted ? 1 : 0);
        result = 31 * result + (isOrderClosed ? 1 : 0);
        result = 31 * result + (isOrderCanceled ? 1 : 0);
        result = 31 * result + (orderCarBrand != null ? orderCarBrand.hashCode() : 0);
        result = 31 * result + (orderCarModel != null ? orderCarModel.hashCode() : 0);
        result = 31 * result + orderCarId;
        result = 31 * result + orderUserId;
        return result;
    }

    @Override
    public String toString() {
        return "OrderForShow{" +
                "orderId=" + orderId +
                ", orderDate=" + orderDate +
                ", orderStartRent=" + orderStartRent +
                ", orderEndRent=" + orderEndRent +
                ", orderTotalPrice=" + orderTotalPrice +
                ", isOrderAccepted=" + isOrderAccepted +
                ", isOrderClosed=" + isOrderClosed +
                ", isOrderCanceled=" + isOrderCanceled +
                ", orderCarBrand='" + orderCarBrand + '\'' +
                ", orderCarModel='" + orderCarModel + '\'' +
                ", orderCarId=" + orderCarId +
                ", orderUserId=" + orderUserId +
                '}';
    }
}
