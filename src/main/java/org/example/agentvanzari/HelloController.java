package org.example.agentvanzari;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.agentvanzari.domain.Comanda;
import org.example.agentvanzari.domain.PasswordUtils;
import org.example.agentvanzari.domain.Produs;
import org.example.agentvanzari.repository.RepositoryException;
import org.example.agentvanzari.service.ServiceComanda;
import org.example.agentvanzari.service.ServiceProdus;
import org.example.agentvanzari.service.ServiceUser;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    private ServiceProdus serviceProdus= new ServiceProdus();
    private ServiceComanda serviceComanda = new ServiceComanda();
    private ServiceUser serviceUser = new ServiceUser();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public boolean login(String username, String password) {
        String query = "SELECT * FROM Useri WHERE username = ? ";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:useri.db");
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String storedHash = rs.getString("parola");
                return PasswordUtils.checkPassword(password, storedHash);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @FXML
    private void onLogIn(ActionEvent event) throws IOException {
        try {
            String user = username.getText();
            String passwd = password.getText();
            if (user.isEmpty() || passwd.isEmpty()) {
                showAlert("Trebuie sa introduci un username si/sau o parola.");
            } else if (user.length() > 25) {
                showAlert("Username-ul poate avea maxim 25 de caractere.");
            } else if (passwd.length() < 7) {
                showAlert("Parola trebuie sa aiba minim 7 caractere.");
            } else if ("admin".equals(user) && "inventar10".equals(passwd)) {
                loadAdminPage(event);
            } else if (!(login(user, passwd))){
                showAlert("Username-ul sau parola nu sunt corecte/nu apar in baza de date.");
            }
            else {
                loadAgentPage(event);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAdminPage(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admin-home.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 320, 500);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void loadAgentPage(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("agent-home.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 320, 500);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void onMakeOrder(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("make-order.fxml"));
            Parent root = loader.load();
            MakeOrderController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Plasare comandă");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onViewProducts(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view-products.fxml"));
            Parent root = loader.load();
            ViewProductsController controller = loader.getController();
            ServiceProdus serviceProdus = new ServiceProdus();
            List<Produs> productList = new ArrayList<>();
            try {
                productList.addAll(serviceProdus.getAllP());
            } catch (RepositoryException e) {
                showAlert("Eroare la obținerea listei de produse din bază de date.");
                e.printStackTrace();
                return;
            }
            controller.setProductList(productList);
            Scene scene = new Scene(root, 320, 500);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void onViewStock(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view-products.fxml"));
            Parent root = loader.load();
            ViewProductsController controller = loader.getController();
            ServiceProdus serviceProdus = new ServiceProdus();
            List<Produs> productList = new ArrayList<>();
            try {
                productList.addAll(serviceProdus.getAllP());
            } catch (RepositoryException e) {
                showAlert("Eroare la obținerea listei de produse din bază de date.");
                e.printStackTrace();
                return;
            }
            controller.setProductList(productList);
            Scene scene = new Scene(root, 320, 500);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void onUpdateStock(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("update-stock.fxml"));
            Parent root = loader.load();
            UpdateStockController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Update stock");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onHelpNeeded(ActionEvent event) {
        try {
            String filePath = "C:\\ISS\\help.txt";
            Runtime.getRuntime().exec("cmd /c start notepad " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}