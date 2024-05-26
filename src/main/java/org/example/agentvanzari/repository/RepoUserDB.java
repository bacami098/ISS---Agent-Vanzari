package org.example.agentvanzari.repository;

import org.example.agentvanzari.domain.PasswordUtils;
import org.example.agentvanzari.domain.User;
import org.sqlite.SQLiteDataSource;
import java.sql.*;
import java.util.ArrayList;

public class RepoUserDB extends MemoryRepository<User>  {

    private Connection conn = null;
    private final String JDBC_URL;

    public RepoUserDB(String dbLocation){
        this.JDBC_URL= "jdbc:sqlite:"+dbLocation;
        openConnection();
        createSchema();
        loadData();

    }

    private void loadData() {
        ArrayList<User> useri = this.getAll();
        for(User u : useri) {
            try {
                super.add(u);
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
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Useri(id int,username varchar(100),parola varchar(100));");
            }
        } catch (SQLException e) {
            System.err.println("[ERROR] createSchema : " + e.getMessage());
        }
    }

    @Override
    public void add(User o) throws RepositoryException {
        try {
            super.add(o);
        } catch (RepositoryException e) {
            throw new DuplicateRepository("Exista deja un element cu acest id!");
        }
        String hashedPassword = PasswordUtils.hashPassword(o.getParola());
        try {
            try (PreparedStatement statement = conn.prepareStatement("INSERT INTO Useri VALUES (?,?,?)")) {
                statement.setInt(1, o.getId());
                statement.setString(2, o.getUsername());
                statement.setString(3, hashedPassword);
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
            try (PreparedStatement statement = conn.prepareStatement("DELETE FROM Useri WHERE id=?")) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(int id, User o) throws RepositoryException {
        try {
            super.update(id, o);
        } catch (RepositoryException e) {
            throw new RepositoryException("Nu exista elementul cu id-ul cautat");
        }
        try {
            conn.setAutoCommit(false);
            try (PreparedStatement updateUser = conn.prepareStatement("UPDATE Useri SET username = ?, parola = ? WHERE id = ?")){
                updateUser.setString(1, o.getUsername());
                updateUser.setString(2, o.getParola());
                updateUser.setInt(3, o.getId());
                updateUser.executeUpdate();
                conn.commit();
                conn.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public ArrayList<User> getAll() {
        ArrayList<User> useri = new ArrayList<>();
        try {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * from Useri "); ResultSet rs = statement.executeQuery();) {
                while (rs.next()) {
                    User u = new User(rs.getInt("id"),rs.getString("username"),rs.getString("parola"));
                    useri.add(u);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return useri;
    }
}
