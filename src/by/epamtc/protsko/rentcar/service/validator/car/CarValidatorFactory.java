package by.epamtc.protsko.rentcar.service.validator.car;

public class CarValidatorFactory {

    public CarParameterValidator getValidator(CarParameterType carParameter) {
        CarParameterValidator validator = null;

        switch (carParameter) {
            case VIN:
                validator = new VinValidator();
                break;
            case MANUFACTURE_DATE:
                validator = new ManufactureDateValidator();
                break;
            case ENGINE_POWER:
                validator = new EnginePowerValidator();
                break;
            case FUEL_CONSUMPTION:
                validator = new FuelConsumptionValidator();
                break;
            case TRANSMISSION_TYPE:
                validator = new TransmissionValidator();
                break;
            case CAR_CLASS:
                validator = new CarClassValidator();
                break;
            case CAR_BRAND:
                validator = new BrandValidator();
                break;
        }
        return validator;
    }
}
