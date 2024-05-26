package org.example.agentvanzari.domain;

import java.io.Serializable;

public class Comanda extends Entity implements Serializable
{
    private String nume;
    private String prenume;
    private String adresa;
    private String email;
    private String nr_telefon;
    private int cantitate;
    private int id_produs;
    public Comanda(int id, String nume, String prenume, String adresa, String email, String nr_telefon, int cantitate, int id_produs)
    {
        super(id);
        this.nume = nume;
        this.prenume = prenume;
        this.adresa = adresa;
        this.email = email;
        this.nr_telefon = nr_telefon;
        this.cantitate = cantitate;
        this.id_produs = id_produs;
    }

    @Override
    public int getId()
    {
        return super.getId();
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getAdresa() {
        return adresa;
    }

    public String getEmail() {
        return email;
    }

    public String getNr_telefon() {
        return nr_telefon;
    }

    public int getCantitate() {
        return cantitate;
    }

    public int getId_produs() {
        return id_produs;
    }
}
