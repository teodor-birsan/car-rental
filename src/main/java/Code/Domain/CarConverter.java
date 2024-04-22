package Code.Domain;

public class CarConverter  implements EntityConverter<Car>{
    @Override
    public String toString(Car object) {
        return String.valueOf(object.getId()) + ',' + object.getModel() + ',' + object.getBrand();
    }

    @Override
    public Car fromString(String line) {
        String[] tokens = line.split(",");
        return new Car(Integer.parseInt(tokens[0]), tokens[1], tokens[2]);
    }
}
