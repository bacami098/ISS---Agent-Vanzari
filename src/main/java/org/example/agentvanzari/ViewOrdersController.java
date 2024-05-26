package org.example.agentvanzari;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.agentvanzari.domain.Comanda;
import org.example.agentvanzari.domain.Produs;
import org.example.agentvanzari.service.ServiceComanda;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ViewOrdersController implements Initializable {
    @FXML
    private TableView<Comanda> tableView;

    @FXML
    private TableColumn<Comanda, String> numeColumn;
    @FXML
    private TableColumn<Comanda, String> prenumeColumn;
    @FXML
    private TableColumn<Comanda, String> adresaColumn;
    @FXML
    private TableColumn<Comanda, String> emailColumn;

    @FXML
    private TableColumn<Comanda, String> nr_telefonColumn;

    @FXML
    private TableColumn<Comanda, Integer> idColumn;
    @FXML
    private TableColumn<Comanda, Integer> cantitateColumn;
    @FXML
    private TableColumn<Comanda, Integer> id_produsColumn;

    private ObservableList<Comanda> ordersList = FXCollections.observableArrayList();

    public void setOrdersList(List<Comanda> comandaList) {
        this.ordersList.clear();
        this.ordersList.addAll(comandaList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        numeColumn.setCellValueFactory(new PropertyValueFactory<>("nume"));
        prenumeColumn.setCellValueFactory(new PropertyValueFactory<>("prenume"));
        adresaColumn.setCellValueFactory(new PropertyValueFactory<>("adresa"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        nr_telefonColumn.setCellValueFactory(new PropertyValueFactory<>("nr_telefon"));
        cantitateColumn.setCellValueFactory(new PropertyValueFactory<>("cantitate"));
        id_produsColumn.setCellValueFactory(new PropertyValueFactory<>("id_produs"));
        tableView.setItems(ordersList);
    }
}
