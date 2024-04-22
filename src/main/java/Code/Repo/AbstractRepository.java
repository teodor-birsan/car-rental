package Code.Repo;

import Code.Domain.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractRepository<T extends Entity> implements IRepository<T> {
    protected List<T> data = new ArrayList<>();
    @Override
    public Iterator<T> iterator(){
        return new ArrayList<>(data).iterator();
    }
    public abstract void add(T object) throws RepositoryException;
    public abstract void remove(int id) throws RepositoryException;
    public abstract void remove(T object) throws RepositoryException;
    public abstract void update(int id, T newObject) throws RepositoryException;
    public abstract void update(T oldObject, T newObject) throws RepositoryException;
    public abstract T find(int id);
    public abstract boolean find(T object);
    public abstract Collection<T> getAll();
}
