package org.example.agentvanzari.repository;

import org.example.agentvanzari.domain.Comanda;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;

public class RepoComandaDB extends MemoryRepository<Comanda> {

    private Connection conn = null;
    private final String JDBC_URL;

    public RepoComandaDB(String dbLocation) {
        this.JDBC_URL = "jdbc:sqlite:" + dbLocation;
        openConnection();
        createSchema();
        loadData();

    }

    private void loadData() {
        ArrayList<Comanda> comenzi = this.getAll();
        for (Comanda c : comenzi) {
            try {
                super.add(c);
            } catch (RepositoryException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void openConnection() {
        try {
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(JDBC_URL);
            if (conn == null || conn.isClosed())
                conn = ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void createSchema() {
        try {
            try (final Statement stmt = conn.createStatement()) {
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Comenzi(id int,nume varchar(100),prenume varchar(100),adresa varchar(100),email varchar(100),nr_telefon varchar(100),cantitate int,id_produs int);");
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] createSchema : " + e.getMessage());
        }
    }

    @Override
    public void add(Comanda o) throws RepositoryException {
        try {
            super.add(o);
        } catch (RepositoryException e) {
            throw new DuplicateRepository("Exista deja un element cu acest id!");
        }
        try {
            try (PreparedStatement statement = conn.prepareStatement("INSERT INTO Comenzi VALUES (?,?,?,?,?,?,?,?)")) {
                statement.setInt(1, o.getId());
                statement.setString(2, o.getNume());
                statement.setString(3, o.getPrenume());
                statement.setString(4, o.getAdresa());
                statement.setString(5, o.getEmail());
                statement.setString(6, o.getNr_telefon());
                statement.setInt(7, o.getCantitate());
                statement.setInt(8, o.getId_produs());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(int id) throws RepositoryException {
        try {
            super.remove(id);
        } catch (RepositoryException e) {
            throw new RepositoryException("Nu exista elementul cu id-ul cautat");
        }
        try {
            try (PreparedStatement statement = conn.prepareStatement("DELETE FROM Comenzi WHERE id=?")) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(int id, Comanda o) throws RepositoryException {
        try {
            super.update(id, o);
        } catch (RepositoryException e) {
            throw new RepositoryException("Nu exista elementul cu id-ul cautat");
        }
        try {
            conn.setAutoCommit(false);

            try (PreparedStatement updateComanda = conn.prepareStatement("UPDATE Comenzi SET nume = ?,prenume = ?,adresa = ?,email = ?,nr_telefon = ?,cantitate = ?,id_produs = ? WHERE id = ?")) {
                updateComanda.setString(2, o.getNume());
                updateComanda.setString(3, o.getPrenume());
                updateComanda.setString(4, o.getAdresa());
                updateComanda.setString(5, o.getEmail());
                updateComanda.setString(6, o.getNr_telefon());
                updateComanda.setInt(7, o.getCantitate());
                updateComanda.setInt(8, o.getId_produs());
                updateComanda.executeUpdate();
                conn.commit();
                conn.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public ArrayList<Comanda> getAll() {
        ArrayList<Comanda> comenzi = new ArrayList<>();
        try {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * from Comenzi "); ResultSet rs = statement.executeQuery();) {
                while (rs.next()) {
                    Comanda c = new Comanda(rs.getInt("id"), rs.getString("nume"),rs.getString("prenume"),rs.getString("adresa"),rs.getString("email"),rs.getString("nr_telefon"), rs.getInt("cantitate"),rs.getInt("id_produs"));
                    comenzi.add(c);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return comenzi;
    }

}
