package Code.Domain;

import java.util.Objects;

public class Car extends Entity{

    private  String brand;
    private String model;
    public Car(int id, String brand, String model) {
        super(id);
        this.brand = brand;
        this.model = model;
    }
    public Car(Car another){
        this(another.id, another.brand, another.model);
    }
    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car car)) return false;
        return Objects.equals(getId(), car.getId()) && Objects.equals(getBrand(), car.getBrand()) && Objects.equals(getModel(), car.getModel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBrand(), getModel());
    }

    @Override
    public String toString() {
        return String.valueOf(id) + ',' + brand + "," + model;
    }
}
