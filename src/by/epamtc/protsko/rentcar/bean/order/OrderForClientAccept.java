package by.epamtc.protsko.rentcar.bean.order;

import java.io.Serializable;
import java.util.Date;

public class OrderForClientAccept implements Serializable {
    private static final long serialVersionUID = 7010771555285113393L;

    private String carBrand;
    private String carModel;
    private String carVin;
    private Date startRent;
    private Date endRent;
    private double totalPrice;

    public OrderForClientAccept() {
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarVin() {
        return carVin;
    }

    public void setCarVin(String carVin) {
        this.carVin = carVin;
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderForClientAccept)) return false;

        OrderForClientAccept that = (OrderForClientAccept) o;

        if (Double.compare(that.totalPrice, totalPrice) != 0) return false;
        if (carBrand != null ? !carBrand.equals(that.carBrand) : that.carBrand != null) return false;
        if (carModel != null ? !carModel.equals(that.carModel) : that.carModel != null) return false;
        if (carVin != null ? !carVin.equals(that.carVin) : that.carVin != null) return false;
        if (startRent != null ? !startRent.equals(that.startRent) : that.startRent != null) return false;
        return endRent != null ? endRent.equals(that.endRent) : that.endRent == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = carBrand != null ? carBrand.hashCode() : 0;
        result = 31 * result + (carModel != null ? carModel.hashCode() : 0);
        result = 31 * result + (carVin != null ? carVin.hashCode() : 0);
        result = 31 * result + (startRent != null ? startRent.hashCode() : 0);
        result = 31 * result + (endRent != null ? endRent.hashCode() : 0);
        temp = Double.doubleToLongBits(totalPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "OrderForClientAccept{" +
                "carBrand='" + carBrand + '\'' +
                ", carModel='" + carModel + '\'' +
                ", carVin='" + carVin + '\'' +
                ", startRent=" + startRent +
                ", endRent=" + endRent +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
