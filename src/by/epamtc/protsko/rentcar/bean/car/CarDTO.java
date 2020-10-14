package by.epamtc.protsko.rentcar.bean.car;

import java.io.Serializable;
import java.util.List;

public class CarDTO implements Serializable {
    private static final long serialVersionUID = -5668015365181927122L;

    private int id;
    private String carVIN;
    private int manufactureDate;
    private int enginePower;
    private int fuelConsumption;
    private boolean isValidateToRent;
    private boolean isDeleted;
    private Transmission transmissionType;
    private CarClass carClassType;
    private String carModel;
    private String carBrand;
    private List<String> carPhotos;

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

    public int getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(int fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public boolean isValidateToRent() {
        return isValidateToRent;
    }

    public void setValidateToRent(boolean validateToRent) {
        isValidateToRent = validateToRent;
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

    public CarClass getCarClassType() {
        return carClassType;
    }

    public void setCarClassType(CarClass carClassType) {
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

    public List<String> getCarPhotos() {
        return carPhotos;
    }

    public void setCarPhotos(List<String> carPhotos) {
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
        if (fuelConsumption != carDTO.fuelConsumption) return false;
        if (isValidateToRent != carDTO.isValidateToRent) return false;
        if (isDeleted != carDTO.isDeleted) return false;
        if (carVIN != null ? !carVIN.equals(carDTO.carVIN) : carDTO.carVIN != null) return false;
        if (transmissionType != carDTO.transmissionType) return false;
        if (carClassType != carDTO.carClassType) return false;
        if (carModel != null ? !carModel.equals(carDTO.carModel) : carDTO.carModel != null) return false;
        if (carBrand != null ? !carBrand.equals(carDTO.carBrand) : carDTO.carBrand != null) return false;
        return carPhotos != null ? carPhotos.equals(carDTO.carPhotos) : carDTO.carPhotos == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (carVIN != null ? carVIN.hashCode() : 0);
        result = 31 * result + manufactureDate;
        result = 31 * result + enginePower;
        result = 31 * result + fuelConsumption;
        result = 31 * result + (isValidateToRent ? 1 : 0);
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
                ", isValidateToRent=" + isValidateToRent +
                ", isDeleted=" + isDeleted +
                ", transmissionType=" + transmissionType +
                ", carClassType=" + carClassType +
                ", carModel='" + carModel + '\'' +
                ", carBrand='" + carBrand + '\'' +
                ", carPhotos=" + carPhotos +
                '}';
    }
}
