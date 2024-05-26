package org.example.agentvanzari;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.agentvanzari.domain.Produs;
import org.example.agentvanzari.repository.RepositoryException;
import org.example.agentvanzari.service.ServiceProdus;

public class UpdateStockController
{
    @FXML
    private TextField idField;
    @FXML
    private TextField nr_stockField;
    private ServiceProdus serviceProdus;

    public void initialize() {
        this.serviceProdus = new ServiceProdus();
    }

    @FXML
    private void onUpdateStock(ActionEvent event) {
        int id = Integer.parseInt(idField.getText());
        int cantitate = Integer.parseInt(nr_stockField.getText());
        try {
            Produs p = this.serviceProdus.getProdus(id);
            this.serviceProdus.removeProdus(id);
            this.serviceProdus.addProdus(p.getId(),p.getNume(),p.getPret(),cantitate);
            showAlert("Stock-ul pentru acest produs a fost actualizat!");
        } catch (RepositoryException e) {
            showAlert("Eroare la modificarea stockului în bază de date.");
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
