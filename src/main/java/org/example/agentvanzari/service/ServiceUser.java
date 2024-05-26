package org.example.agentvanzari.service;

import org.example.agentvanzari.domain.Produs;
import org.example.agentvanzari.domain.User;
import org.example.agentvanzari.repository.*;

import java.util.Collection;

public class ServiceUser
{
    private IRepository<User> repoUser = new MemoryRepository<User>();
    public ServiceUser()
    {
        this.repoUser = new RepoUserDB("C:\\MAP\\AgentVanzari\\useri.db");
    }

    public void addUser(int id ,String username,String parola) throws RepositoryException
    {
        User u = new User(id,username,parola);
        repoUser.add(u);
    }

    public void removeUser(int id) throws RepositoryException
    {
        repoUser.remove(id);
    }

    public void updateUser(int id1,int id2 ,String username,String parola) throws RepositoryException
    {
        User u = new User(id2,username,parola);
        repoUser.update(id1,u);
    }

    public Collection<User> getAllU() throws RepositoryException
    {
        return repoUser.getAll();
    }
}
