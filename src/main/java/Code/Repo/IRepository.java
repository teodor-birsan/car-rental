package Code.Repo;

import Code.Domain.Entity;
import Code.Repo.RepositoryException;
import java.util.Collection;

public interface IRepository<T extends Entity> extends Iterable<T> {
    public void add(T object) throws RepositoryException;
    public void remove(int id) throws RepositoryException;
    public void remove(T object) throws RepositoryException;
    public void update(int id, T newObject) throws RepositoryException;
    public void update(T oldObject, T newObject) throws  RepositoryException;
    public T find(int id);
    public boolean find(T object);
    public Collection<T> getAll();
}
