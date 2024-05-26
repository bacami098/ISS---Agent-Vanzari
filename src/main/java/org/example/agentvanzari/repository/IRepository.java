package org.example.agentvanzari.repository;
import org.example.agentvanzari.domain.Entity;
import java.util.Collection;

public interface IRepository<T extends Entity> extends Iterable<T>
{
    public void add(T o) throws RepositoryException;

    public void remove(int id) throws RepositoryException;

    public void update(int id,T o) throws RepositoryException;

    public T find(int id);

    public Collection<T> getAll();

    public int getSize();

    public T getById(int id) throws RepositoryException;
}
