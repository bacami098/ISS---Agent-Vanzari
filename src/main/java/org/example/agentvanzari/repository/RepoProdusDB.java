package org.example.agentvanzari.repository;

import org.example.agentvanzari.domain.Produs;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;

public class RepoProdusDB extends MemoryRepository<Produs>  {

    private Connection conn = null;
    private final String JDBC_URL;

    public RepoProdusDB(String dbLocation){
        this.JDBC_URL= "jdbc:sqlite:"+dbLocation;
        openConnection();
        createSchema();
        loadData();

    }

    private void loadData() {
        ArrayList<Produs> produse = this.getAll();
        for(Produs p : produse) {
            try {
                super.add(p);
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
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Produse(id int,nume varchar(100),pret double,nr_in_stock int);");
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] createSchema : " + e.getMessage());
        }
    }

    @Override
    public void add(Produs o) throws RepositoryException {
        try {
            super.add(o);
        } catch (RepositoryException e) {
            throw new DuplicateRepository("Exista deja un element cu acest id!");
        }
        try {
            try (PreparedStatement statement = conn.prepareStatement("INSERT INTO Produse VALUES (?,?,?,?)")) {
                statement.setInt(1, o.getId());
                statement.setString(2, o.getNume());
                statement.setDouble(3,o.getPret());
                statement.setInt(4, o.getNr_in_stock());
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
            try (PreparedStatement statement = conn.prepareStatement("DELETE FROM Produse WHERE id=?")) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(int id, Produs o) throws RepositoryException {
        try {
            super.update(id, o);
        } catch (RepositoryException e) {
            throw new RepositoryException("Nu exista elementul cu id-ul cautat");
        }
        try {
            conn.setAutoCommit(false);

            try (PreparedStatement updateProdus = conn.prepareStatement("UPDATE Produse SET nume = ?,pret = ?,nr_in_stock = ? WHERE id = ?")){
                updateProdus.setString(2, o.getNume());
                updateProdus.setDouble(3, o.getPret());
                updateProdus.setInt(3, o.getNr_in_stock());
                updateProdus.executeUpdate();
                conn.commit();
                conn.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public ArrayList<Produs> getAll() {
        ArrayList<Produs> produse = new ArrayList<>();
        try {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * from Produse "); ResultSet rs = statement.executeQuery();) {
                while (rs.next()) {
                    Produs p = new Produs(rs.getInt("id"),rs.getString("nume"),rs.getDouble("pret"),rs.getInt("nr_in_stock"));
                    produse.add(p);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return produse;
    }
}
