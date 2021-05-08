package org.example;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    public GridPane pizzaMenu;
    public GridPane cart;
    private String[][] products;
    private String[] pizzaIndex;
    private float sum = 69;

    @FXML
    public void goBack() throws IOException {
        App.setRoot("startingButtons");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        products = App.getConnect().getTableContent("roznosci", "produkty");
        pizzaMenu.getChildren().clear();
        pizzaMenu.setHgap(100);
        pizzaMenu.setVgap(10);

        for (int j = 1; j < products.length; j++) {
            Label label = new Label(products[j][2]);
            label.setTextFill(Color.color(1, 0.65, 0));
            label.setStyle("-fx-font-weight: bold; -fx-font-size: 15px");
            pizzaMenu.addRow(j+2, label);
        }
    }

    public void addToCart(){
        Label label = new Label("Suma" + sum + "zł.");
        label.setTextFill(Color.color(1, 0.65, 0));
        cart.addRow(1, label);
    }
}
