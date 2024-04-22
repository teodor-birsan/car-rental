package Code.Service;

import Code.Domain.Car;
import Code.Domain.Entity;
import Code.Domain.Rent;
import Code.Repo.*;

import java.util.*;
import java.util.stream.Collectors;


public class Service {
    private final IRepository<Entity> repo;

    public Service(IRepository<Entity> repo) {
        this.repo = repo;
    }

    public void add(Entity ent) throws RepositoryException {
        if(ent instanceof Car)
            repo.add(ent);
        else if (ent instanceof Rent) {
            if(!getAllRents().isEmpty())
                for(Rent obj: getAllRents() ){
                   if(((Rent) ent).getCar().equals(((Rent) obj).getCar())) {
                       if (((Rent) ent).getStartDate().after(((Rent) obj).getStartDate())
                               && ((Rent) ent).getStartDate().before(((Rent) obj).getEndDate()) ||
                               (((Rent) ent).getEndDate().after(((Rent) obj).getStartDate()) && ((Rent) ent).getEndDate().before(((Rent) obj).getEndDate())))
                           throw new RepositoryException("Aceeasi masina nu poate fi inchiriata de mai multe ori in acelasi interval de timp!\n");
                   }
           }
            repo.add(ent);
        }
    }

    public void remove(int id) throws RepositoryException {
        repo.remove(id);
    }

    public void remove(Entity ent) throws RepositoryException{
        repo.remove(ent);
    }

    public void update(int id, Entity ent) throws RepositoryException{
        repo.update(id, ent);
    }

    public void update(Entity oldEnt, Entity newEnt) throws RepositoryException{
        repo.update(oldEnt,newEnt);
    }

    public Entity find(int id){
        return repo.find(id);
    }

    public boolean find(Entity ent){
        return repo.find(ent);
    }

    public Collection<Entity> getAll(){
        return repo.getAll();
    }

    public Collection<Car> getAllCars(){
        Collection<Entity> entities = repo.getAll();
        Collection<Car> cars = new ArrayList<>();
        for(Entity entity: entities){
            if(entity instanceof Car){
                cars.add((Car)entity);
            }
        }
        return cars;
    }

    public Collection<Rent> getAllRents(){
        Collection<Entity> entities = repo.getAll();
        Collection<Rent> rents = new ArrayList<>();
        for(Entity entity: entities){
            if(entity instanceof Rent){
                rents.add((Rent)entity);
            }
        }
        return rents;
    }

    public Set<Car> mostRented(){
        Collection<Car> cars = getAllCars();
        Collection<Rent> rents = getAllRents();
        Set<Car> set = null;
        for(Rent rent: rents){
            set = cars.stream().filter(x->x.equals(rent.getCar())).collect(Collectors.toSet());
        }
        return set;
    }

}
