package Code.Domain;

import java.sql.Date;

public class Rent extends Entity {
    private Car car;
    private Date startDate;
    private Date endDate;
    public Rent(int id, Car car, Date startDate, Date endDate) {
        super(id);
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Car getCar() {
        return car;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return String.valueOf(id) + ',' + car.toString() + ',' +startDate + ',' + endDate;
    }
}
