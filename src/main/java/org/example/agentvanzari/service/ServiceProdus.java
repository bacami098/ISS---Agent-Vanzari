package org.example.agentvanzari.service;
import org.example.agentvanzari.domain.Produs;
import org.example.agentvanzari.repository.*;

import java.util.Collection;

public class ServiceProdus
{
    private IRepository<Produs> repoProdus = new MemoryRepository<Produs>();
    public ServiceProdus()
    {
        this.repoProdus = new RepoProdusDB("C:\\MAP\\AgentVanzari\\produse.db");
    }

    public void addProdus(int id ,String nume,double pret,int nr_in_stock) throws RepositoryException
    {
        Produs p= new Produs(id,nume,pret,nr_in_stock);
        repoProdus.add(p);
    }

    public void removeProdus(int id) throws RepositoryException
    {
        repoProdus.remove(id);
    }

    public void updateProdus(int id1,int id2 , String nume,double pret,int nr_in_stock) throws RepositoryException
    {
        Produs p = new Produs(id2,nume,pret,nr_in_stock);
        repoProdus.update(id1,p);
    }

    public Collection<Produs> getAllP() throws RepositoryException
    {
        return repoProdus.getAll();
    }

    public Produs getProdus(int id) throws RepositoryException
    {
        return this.repoProdus.getById(id);
    }
}
