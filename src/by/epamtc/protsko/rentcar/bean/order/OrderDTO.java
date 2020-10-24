package by.epamtc.protsko.rentcar.bean.order;

import java.io.Serializable;

public class OrderDTO implements Serializable {
    private static final long serialVersionUID = -5577647165678236921L;

    private int id;
    private String startRent;
    private String endRent;
    private String totalPrice;
    private String isOrderCanceled;
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

    public String getStartRent() {
        return startRent;
    }

    public void setStartRent(String startRent) {
        this.startRent = startRent;
    }

    public String getEndRent() {
        return endRent;
    }

    public void setEndRent(String endRent) {
        this.endRent = endRent;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getIsOrderCanceled() {
        return isOrderCanceled;
    }

    public void setIsOrderCanceled(String isOrderCanceled) {
        this.isOrderCanceled = isOrderCanceled;
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
        if (startRent != null ? !startRent.equals(orderDTO.startRent) : orderDTO.startRent != null) return false;
        if (endRent != null ? !endRent.equals(orderDTO.endRent) : orderDTO.endRent != null) return false;
        if (totalPrice != null ? !totalPrice.equals(orderDTO.totalPrice) : orderDTO.totalPrice != null) return false;
        if (isOrderCanceled != null ? !isOrderCanceled.equals(orderDTO.isOrderCanceled) : orderDTO.isOrderCanceled != null)
            return false;
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
        result = 31 * result + (startRent != null ? startRent.hashCode() : 0);
        result = 31 * result + (endRent != null ? endRent.hashCode() : 0);
        result = 31 * result + (totalPrice != null ? totalPrice.hashCode() : 0);
        result = 31 * result + (isOrderCanceled != null ? isOrderCanceled.hashCode() : 0);
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
                ", startRent='" + startRent + '\'' +
                ", endRent='" + endRent + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                ", isOrderCanceled='" + isOrderCanceled + '\'' +
                ", isOrderAccepted='" + isOrderAccepted + '\'' +
                ", isOrderClosed='" + isOrderClosed + '\'' +
                ", userId='" + userId + '\'' +
                ", carId='" + carId + '\'' +
                '}';
    }
}
