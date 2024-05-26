package org.example.agentvanzari;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.agentvanzari.domain.Comanda;
import org.example.agentvanzari.domain.Produs;
import org.example.agentvanzari.repository.RepoComandaDB;
import org.example.agentvanzari.repository.RepositoryException;
import org.example.agentvanzari.service.ServiceComanda;
import org.example.agentvanzari.service.ServiceProdus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MakeOrderController {

    @FXML
    private TextField idField;
    @FXML
    private TextField numeField;

    @FXML
    private TextField prenumeField;

    @FXML
    private TextField adresaField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField telefonField;

    @FXML
    private TextField cantitateField;

    @FXML
    private TextField idProdusField;

    private ServiceComanda serviceComanda;

    public void initialize() {
        this.serviceComanda = new ServiceComanda();
    }

    @FXML
    private void onMakeOrder(ActionEvent event) {
        int id = Integer.parseInt(idField.getText());
        String nume = numeField.getText();
        String prenume = prenumeField.getText();
        String adresa = adresaField.getText();
        String email = emailField.getText();
        String telefon = telefonField.getText();
        int cantitate = Integer.parseInt(cantitateField.getText());
        int id_produs = Integer.parseInt(idProdusField.getText());
        try {
            serviceComanda.addComanda(id,nume,prenume,adresa,email,telefon,cantitate,id_produs);
            showAlert("Comanda a fost adăugată cu succes!");
        } catch (RepositoryException e) {
            showAlert("Eroare la adăugarea comenzii în bază de date.");
            e.printStackTrace();
        }
    }

    @FXML
    private void onViewOrders(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view-orders.fxml"));
            Parent root = loader.load();
            ViewOrdersController controller = loader.getController();
            List<Comanda> ordersList = new ArrayList<>();
            try {
                ordersList.addAll(serviceComanda.getAllC());
            } catch (RepositoryException e) {
                showAlert("Eroare la obținerea listei de comenzi din bază de date.");
                e.printStackTrace();
                return;
            }
            controller.setOrdersList(ordersList);
            Scene scene = new Scene(root, 320, 500);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Eroare");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
