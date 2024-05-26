package org.example.agentvanzari.service;

import org.example.agentvanzari.domain.Comanda;
import org.example.agentvanzari.repository.IRepository;
import org.example.agentvanzari.repository.MemoryRepository;
import org.example.agentvanzari.repository.RepoComandaDB;
import org.example.agentvanzari.repository.RepositoryException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class ServiceComanda
{
    private IRepository<Comanda> repoComanda = new MemoryRepository<Comanda>();
    public ServiceComanda()
    {
        this.repoComanda = new RepoComandaDB("C:\\MAP\\AgentVanzari\\comenzi.db");
    }

    public void addComanda(int id ,String nume,String prenume,String adresa,String email,String nr_telefon,int cantitate,int id_produs) throws RepositoryException
    {
        Comanda c= new Comanda(id,nume,prenume,adresa,email,nr_telefon,cantitate,id_produs);
        repoComanda.add(c);
    }

    public void removeComanda(int id) throws RepositoryException
    {
        repoComanda.remove(id);
    }

    public void updateComanda(int id1,int id2 , String nume,String prenume,String adresa,String email,String nr_telefon,int cantitate,int id_produs) throws RepositoryException
    {
        Comanda c = new Comanda(id2,nume,prenume,adresa,email,nr_telefon,cantitate,id_produs);
        repoComanda.update(id1,c);
    }

    public Collection<Comanda> getAllC() throws RepositoryException
    {
        return repoComanda.getAll();
    }
}

