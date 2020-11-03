package by.epamtc.protsko.rentcar.bean.order;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class OrderDTO implements Serializable {
    private static final long serialVersionUID = -8743192320173556685L;

    private int id;
    private LocalDateTime orderDate;
    private Date startRent;
    private Date endRent;
    private String totalPrice;
    private String isOrderAccepted;
    private String isOrderClosed;
    private String userId;
    private String carId;

    public OrderDTO() {
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

    public Date getStartRent() {
        return startRent;
    }

    public void setStartRent(Date startRent) {
        this.startRent = startRent;
    }

    public Date getEndRent() {
        return endRent;
    }

    public void setEndRent(Date endRent) {
        this.endRent = endRent;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getIsOrderAccepted() {
        return isOrderAccepted;
    }

    public void setIsOrderAccepted(String isOrderAccepted) {
        this.isOrderAccepted = isOrderAccepted;
    }

    public String getIsOrderClosed() {
        return isOrderClosed;
    }

    public void setIsOrderClosed(String isOrderClosed) {
        this.isOrderClosed = isOrderClosed;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDTO)) return false;

        OrderDTO orderDTO = (OrderDTO) o;

        if (id != orderDTO.id) return false;
        if (orderDate != null ? !orderDate.equals(orderDTO.orderDate) : orderDTO.orderDate != null) return false;
        if (startRent != null ? !startRent.equals(orderDTO.startRent) : orderDTO.startRent != null) return false;
        if (endRent != null ? !endRent.equals(orderDTO.endRent) : orderDTO.endRent != null) return false;
        if (totalPrice != null ? !totalPrice.equals(orderDTO.totalPrice) : orderDTO.totalPrice != null) return false;
        if (isOrderAccepted != null ? !isOrderAccepted.equals(orderDTO.isOrderAccepted) : orderDTO.isOrderAccepted != null)
            return false;
        if (isOrderClosed != null ? !isOrderClosed.equals(orderDTO.isOrderClosed) : orderDTO.isOrderClosed != null)
            return false;
        if (userId != null ? !userId.equals(orderDTO.userId) : orderDTO.userId != null) return false;
        return carId != null ? carId.equals(orderDTO.carId) : orderDTO.carId == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + (startRent != null ? startRent.hashCode() : 0);
        result = 31 * result + (endRent != null ? endRent.hashCode() : 0);
        result = 31 * result + (totalPrice != null ? totalPrice.hashCode() : 0);
        result = 31 * result + (isOrderAccepted != null ? isOrderAccepted.hashCode() : 0);
        result = 31 * result + (isOrderClosed != null ? isOrderClosed.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (carId != null ? carId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", orderDate=" + orderDate +
                ", startRent=" + startRent +
                ", endRent=" + endRent +
                ", totalPrice='" + totalPrice + '\'' +
                ", isOrderAccepted='" + isOrderAccepted + '\'' +
                ", isOrderClosed='" + isOrderClosed + '\'' +
                ", userId='" + userId + '\'' +
                ", carId='" + carId + '\'' +
                '}';
    }
}
