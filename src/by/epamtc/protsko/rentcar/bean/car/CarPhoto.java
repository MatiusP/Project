package by.epamtc.protsko.rentcar.bean.car;

import java.io.Serializable;

public class CarPhoto implements Serializable {
    private static final long serialVersionUID = 7070514962708383215L;

    private int id;
    private String pictureName;
    private int carId;

    public CarPhoto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
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
        if (!(o instanceof CarPhoto)) return false;

        CarPhoto carPhoto = (CarPhoto) o;

        if (id != carPhoto.id) return false;
        if (carId != carPhoto.carId) return false;
        return pictureName != null ? pictureName.equals(carPhoto.pictureName) : carPhoto.pictureName == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (pictureName != null ? pictureName.hashCode() : 0);
        result = 31 * result + carId;
        return result;
    }

    @Override
    public String toString() {
        return "CarPhoto{" +
                "id=" + id +
                ", pictureName='" + pictureName + '\'' +
                ", carId=" + carId +
                '}';
    }
}
