package by.epamtc.protsko.rentcar.bean.car;

import java.io.Serializable;
import java.util.List;

public class CarDTO implements Serializable {
    private static final long serialVersionUID = -5668015365181927122L;

    private int id;
    private String carVIN;
    private int manufactureDate;
    private int enginePower;
    private double fuelConsumption;
    private boolean isAvailableToRent;
    private boolean isDeleted;
    private String transmissionType;
    private String carClassType;
    private String carModel;
    private String carBrand;
    private List<CarPhoto> carPhotos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarVIN() {
        return carVIN;
    }

    public void setCarVIN(String carVIN) {
        this.carVIN = carVIN;
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

    public String getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(String transmissionType) {
        this.transmissionType = transmissionType;
    }

    public String getCarClassType() {
        return carClassType;
    }

    public void setCarClassType(String carClassType) {
        this.carClassType = carClassType;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public List<CarPhoto> getCarPhotos() {
        return carPhotos;
    }

    public void setCarPhotos(List<CarPhoto> carPhotos) {
        this.carPhotos = carPhotos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarDTO)) return false;

        CarDTO carDTO = (CarDTO) o;

        if (id != carDTO.id) return false;
        if (manufactureDate != carDTO.manufactureDate) return false;
        if (enginePower != carDTO.enginePower) return false;
        if (Double.compare(carDTO.fuelConsumption, fuelConsumption) != 0) return false;
        if (isAvailableToRent != carDTO.isAvailableToRent) return false;
        if (isDeleted != carDTO.isDeleted) return false;
        if (carVIN != null ? !carVIN.equals(carDTO.carVIN) : carDTO.carVIN != null) return false;
        if (transmissionType != null ? !transmissionType.equals(carDTO.transmissionType) : carDTO.transmissionType != null)
            return false;
        if (carClassType != null ? !carClassType.equals(carDTO.carClassType) : carDTO.carClassType != null)
            return false;
        if (carModel != null ? !carModel.equals(carDTO.carModel) : carDTO.carModel != null) return false;
        if (carBrand != null ? !carBrand.equals(carDTO.carBrand) : carDTO.carBrand != null) return false;
        return carPhotos != null ? carPhotos.equals(carDTO.carPhotos) : carDTO.carPhotos == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (carVIN != null ? carVIN.hashCode() : 0);
        result = 31 * result + manufactureDate;
        result = 31 * result + enginePower;
        temp = Double.doubleToLongBits(fuelConsumption);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (isAvailableToRent ? 1 : 0);
        result = 31 * result + (isDeleted ? 1 : 0);
        result = 31 * result + (transmissionType != null ? transmissionType.hashCode() : 0);
        result = 31 * result + (carClassType != null ? carClassType.hashCode() : 0);
        result = 31 * result + (carModel != null ? carModel.hashCode() : 0);
        result = 31 * result + (carBrand != null ? carBrand.hashCode() : 0);
        result = 31 * result + (carPhotos != null ? carPhotos.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CarDTO{" +
                "id=" + id +
                ", carVIN='" + carVIN + '\'' +
                ", manufactureDate=" + manufactureDate +
                ", enginePower=" + enginePower +
                ", fuelConsumption=" + fuelConsumption +
                ", isAvailableToRent=" + isAvailableToRent +
                ", isDeleted=" + isDeleted +
                ", transmissionType='" + transmissionType + '\'' +
                ", carClassType='" + carClassType + '\'' +
                ", carModel='" + carModel + '\'' +
                ", carBrand='" + carBrand + '\'' +
                ", carPhotos=" + carPhotos +
                '}';
    }
}
