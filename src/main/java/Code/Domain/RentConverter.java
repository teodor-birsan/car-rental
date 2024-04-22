package Code.Domain;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class RentConverter implements EntityConverter<Rent> {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
    @Override
    public String toString(Rent object) {
        return object.getId() + ',' + object.getCar().toString() + ',' + object.getStartDate() + ',' + object.getEndDate();
    }

    @Override
    public Rent fromString(String line) {
        String[] tokens = line.split(",");
        LocalDate date1 = LocalDate.parse(tokens[4], formatter);
        LocalDate date2 = LocalDate.parse(tokens[5], formatter);
        return new Rent(Integer.parseInt(tokens[0]), new Car(Integer.parseInt(tokens[1]), tokens[2], tokens[3]),
                Date.valueOf(date1), Date.valueOf(date2));

    }
}
