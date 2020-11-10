package by.epamtc.protsko.rentcar.entity.car;

import java.io.Serializable;

public class CarModel implements Serializable {
    private static final long serialVersionUID = -6625604514007259630L;

    private int id;
    private String modelName;
    private CarBrand carBrand;

    public CarModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public CarBrand getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(CarBrand carBrand) {
        this.carBrand = carBrand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarModel)) return false;

        CarModel carModel = (CarModel) o;

        if (id != carModel.id) return false;
        if (modelName != null ? !modelName.equals(carModel.modelName) : carModel.modelName != null) return false;
        return carBrand != null ? carBrand.equals(carModel.carBrand) : carModel.carBrand == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (modelName != null ? modelName.hashCode() : 0);
        result = 31 * result + (carBrand != null ? carBrand.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CarModel{" +
                "id=" + id +
                ", modelName='" + modelName + '\'' +
                ", carBrand=" + carBrand +
                '}';
    }
}
