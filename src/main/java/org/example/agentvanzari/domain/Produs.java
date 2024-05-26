package org.example.agentvanzari.domain;

import java.io.Serializable;

public class Produs extends Entity implements Serializable
{
    private String nume;
    private double pret;
    private int nr_in_stock;

    public Produs(int id, String nume, double pret, int nr_in_stock) {
        super(id);
        this.nume = nume;
        this.pret = pret;
        this.nr_in_stock = nr_in_stock;
    }

    @Override
    public int getId()
    {
        return super.getId();
    }

    public String getNume() {
        return nume;
    }

    public double getPret() {
        return pret;
    }

    public int getNr_in_stock() {
        return nr_in_stock;
    }

    public void updateAttributes(Produs p) {
        this.nume = p.getNume();
        this.pret = p.getPret();
        this.nr_in_stock = p.getNr_in_stock();
    }
}
