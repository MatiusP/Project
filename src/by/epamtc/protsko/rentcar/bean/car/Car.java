package by.epamtc.protsko.rentcar.bean.car;

import java.io.Serializable;
import java.util.List;

public class Car implements Serializable {
    private static final long serialVersionUID = -366766068992637127L;

    private int id;
    private String VIN;
    private int manufactureDate;
    private int enginePower;
    private double fuelConsumption;
    private boolean isAvailableToRent;
    private boolean isDeleted;
    private Transmission transmissionType;
    private CarClass classType;
    private String model;
    private String brand;
    private List<String> photos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public int getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(int manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public int getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(int enginePower) {
        this.enginePower = enginePower;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public boolean isAvailableToRent() {
        return isAvailableToRent;
    }

    public void setAvailableToRent(boolean availableToRent) {
        isAvailableToRent = availableToRent;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Transmission getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(Transmission transmissionType) {
        this.transmissionType = transmissionType;
    }

    public CarClass getClassType() {
        return classType;
    }

    public void setClassType(CarClass classType) {
        this.classType = classType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car)) return false;

        Car car = (Car) o;

        if (id != car.id) return false;
        if (manufactureDate != car.manufactureDate) return false;
        if (enginePower != car.enginePower) return false;
        if (Double.compare(car.fuelConsumption, fuelConsumption) != 0) return false;
        if (isAvailableToRent != car.isAvailableToRent) return false;
        if (isDeleted != car.isDeleted) return false;
        if (VIN != null ? !VIN.equals(car.VIN) : car.VIN != null) return false;
        if (transmissionType != car.transmissionType) return false;
        if (classType != car.classType) return false;
        if (model != null ? !model.equals(car.model) : car.model != null) return false;
        if (brand != null ? !brand.equals(car.brand) : car.brand != null) return false;
        return photos != null ? photos.equals(car.photos) : car.photos == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (VIN != null ? VIN.hashCode() : 0);
        result = 31 * result + manufactureDate;
        result = 31 * result + enginePower;
        temp = Double.doubleToLongBits(fuelConsumption);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (isAvailableToRent ? 1 : 0);
        result = 31 * result + (isDeleted ? 1 : 0);
        result = 31 * result + (transmissionType != null ? transmissionType.hashCode() : 0);
        result = 31 * result + (classType != null ? classType.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (photos != null ? photos.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", carVIN='" + VIN + '\'' +
                ", manufactureDate=" + manufactureDate +
                ", enginePower=" + enginePower +
                ", fuelConsumption=" + fuelConsumption +
                ", isAvailableToRent=" + isAvailableToRent +
                ", isDeleted=" + isDeleted +
                ", transmissionType=" + transmissionType +
                ", carClassType=" + classType +
                ", carModel='" + model + '\'' +
                ", carBrand='" + brand + '\'' +
                ", carPhotos=" + photos +
                '}';
    }
}
