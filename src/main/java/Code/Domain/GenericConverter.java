package Code.Domain;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class GenericConverter implements EntityConverter<Entity>{
    private final EntityConverter<Car>  carConverter = new CarConverter();
    private final EntityConverter<Rent> rentConverter = new RentConverter();
    @Override
    public String toString(Entity object) {
        return object.toString();
    }

    @Override
    public Entity fromString(String line) {
        String[] tokens = line.split(",");
        if(tokens.length == 3)
            return carConverter.fromString(line);
        else if (tokens.length == 6) {
            return rentConverter.fromString(line);
        }
        return null;
    }
}
