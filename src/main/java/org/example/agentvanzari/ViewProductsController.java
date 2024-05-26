package org.example.agentvanzari;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.agentvanzari.domain.Produs;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ViewProductsController implements Initializable {
    @FXML
    private TableView<Produs> tableView;

    @FXML
    private TableColumn<Produs, Integer> idColumn;

    @FXML
    private TableColumn<Produs, String> nameColumn;

    @FXML
    private TableColumn<Produs, Double> priceColumn;

    @FXML
    private TableColumn<Produs, Integer> stockColumn;

    private ObservableList<Produs> productList = FXCollections.observableArrayList();

    public void setProductList(List<Produs> productList) {
        this.productList.clear();
        this.productList.addAll(productList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nume"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("pret"));
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("nr_in_stock"));
        tableView.setItems(productList);
    }
}
