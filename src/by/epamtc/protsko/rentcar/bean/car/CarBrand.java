package by.epamtc.protsko.rentcar.bean.car;

import java.io.Serializable;

public class CarBrand implements Serializable {
    private static final long serialVersionUID = 6487907625856990719L;

    private int id;
    private String brandName;

    public CarBrand() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CarBrand)) return false;

        CarBrand carBrand = (CarBrand) o;

        if (id != carBrand.id) return false;
        return brandName != null ? brandName.equals(carBrand.brandName) : carBrand.brandName == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (brandName != null ? brandName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CarBrand{" +
                "id=" + id +
                ", brandName='" + brandName + '\'' +
                '}';
    }
}
