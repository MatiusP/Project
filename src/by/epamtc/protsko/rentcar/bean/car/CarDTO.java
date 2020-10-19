package by.epamtc.protsko.rentcar.bean.car;

import java.io.Serializable;
import java.util.List;

public class CarDTO implements Serializable {
    private static final long serialVersionUID = -4109135156656430026L;

    private int id;
    private String vin;
    private String manufactureDate;
    private String enginePower;
    private String fuelConsumption;
    private String isAvailableToRent;
    private String isDeleted;
    private String transmissionType;
    private String classType;
    private String model;
    private String brand;
    private String pricePerDay;
    private List<String> photos;

    public CarDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(String manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public String getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(String enginePower) {
        this.enginePower = enginePower;
    }

    public String getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(String fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public String getIsAvailableToRent() {
        return isAvailableToRent;
    }

    public void setIsAvailableToRent(String isAvailableToRent) {
        this.isAvailableToRent = isAvailableToRent;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(String transmissionType) {
        this.transmissionType = transmissionType;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
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

    public String getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(String pricePerDay) {
        this.pricePerDay = pricePerDay;
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
        if (!(o instanceof CarDTO)) return false;

        CarDTO carDTO = (CarDTO) o;

        if (id != carDTO.id) return false;
        if (vin != null ? !vin.equals(carDTO.vin) : carDTO.vin != null) return false;
        if (manufactureDate != null ? !manufactureDate.equals(carDTO.manufactureDate) : carDTO.manufactureDate != null)
            return false;
        if (enginePower != null ? !enginePower.equals(carDTO.enginePower) : carDTO.enginePower != null) return false;
        if (fuelConsumption != null ? !fuelConsumption.equals(carDTO.fuelConsumption) : carDTO.fuelConsumption != null)
            return false;
        if (isAvailableToRent != null ? !isAvailableToRent.equals(carDTO.isAvailableToRent) : carDTO.isAvailableToRent != null)
            return false;
        if (isDeleted != null ? !isDeleted.equals(carDTO.isDeleted) : carDTO.isDeleted != null) return false;
        if (transmissionType != null ? !transmissionType.equals(carDTO.transmissionType) : carDTO.transmissionType != null)
            return false;
        if (classType != null ? !classType.equals(carDTO.classType) : carDTO.classType != null) return false;
        if (model != null ? !model.equals(carDTO.model) : carDTO.model != null) return false;
        if (brand != null ? !brand.equals(carDTO.brand) : carDTO.brand != null) return false;
        if (pricePerDay != null ? !pricePerDay.equals(carDTO.pricePerDay) : carDTO.pricePerDay != null) return false;
        return photos != null ? photos.equals(carDTO.photos) : carDTO.photos == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (vin != null ? vin.hashCode() : 0);
        result = 31 * result + (manufactureDate != null ? manufactureDate.hashCode() : 0);
        result = 31 * result + (enginePower != null ? enginePower.hashCode() : 0);
        result = 31 * result + (fuelConsumption != null ? fuelConsumption.hashCode() : 0);
        result = 31 * result + (isAvailableToRent != null ? isAvailableToRent.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        result = 31 * result + (transmissionType != null ? transmissionType.hashCode() : 0);
        result = 31 * result + (classType != null ? classType.hashCode() : 0);
        result = 31 * result + (model != null ? model.hashCode() : 0);
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (pricePerDay != null ? pricePerDay.hashCode() : 0);
        result = 31 * result + (photos != null ? photos.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CarDTO{" +
                "id=" + id +
                ", vin='" + vin + '\'' +
                ", manufactureDate='" + manufactureDate + '\'' +
                ", enginePower='" + enginePower + '\'' +
                ", fuelConsumption='" + fuelConsumption + '\'' +
                ", isAvailableToRent='" + isAvailableToRent + '\'' +
                ", isDeleted='" + isDeleted + '\'' +
                ", transmissionType='" + transmissionType + '\'' +
                ", classType='" + classType + '\'' +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", pricePerDay='" + pricePerDay + '\'' +
                ", photos=" + photos +
                '}';
    }
}
