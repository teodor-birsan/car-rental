package Code.Repo;
import Code.Domain.Entity;

import java.util.ArrayList;
import java.util.Collection;

public class MemoryRepository<T extends Entity> extends AbstractRepository<T> {
    @Override
    public void add(T o) throws RepositoryException {
        if (o == null)
            throw new RepositoryException("Obiectul introdus nu poate fi null.\n");
        if (!data.isEmpty() && find(o.getId()) != null)
            throw new DuplicateObjectException("Exista deja un obiect cu id-ul introdus!\n");
        this.data.add(o);
    }

    @Override
    public void remove(int id) throws RepositoryException {
        if(find(id) == null)
            throw new RepositoryException("Obiectul cu id-ul dat nu exista.\n");

        this.data.removeIf(e -> e.getId() == id);
    }

    @Override
    public void remove(T object) throws RepositoryException {
        if(object == null){
            throw new RepositoryException("Obiectul nu poate fi null!\n");
        }
        if(!find(object)){
            throw new RepositoryException("Obiectul nu exista!\n");
        }
        this.data.remove(object);
    }

    @Override
    public void update(int id, T newObject) throws RepositoryException {
        if(find(id) == null)
            throw new RepositoryException("Obiectul cu id-ul dat nu exista!\n");
        for(T e : this.data){
            if(e.getId() == newObject.getId()){
                data.set(data.indexOf(find(id)),newObject);
            }
        }
    }

    @Override
    public void update(T oldObject, T newObject) throws RepositoryException {
        if(!find(oldObject))
            throw new RepositoryException("Obiectul nu exista!\n");
        for(T e: this.data){
            if(e.equals(oldObject)){
                data.set(data.indexOf(oldObject), newObject);
            }
        }
    }

    @Override
    public T find(int id){
        for (T e : this.data) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
    }

    @Override
    public boolean find(T object) {
        for(T e: this.data){
            if(e.equals(object))
                return true;
        }
        return false;
    }

    @Override
    public Collection<T> getAll() {
        return new ArrayList<>(this.data);
    }
}