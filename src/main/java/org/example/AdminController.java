package org.example;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.io.IOException;


public class AdminController {

    @FXML
    public PasswordField passwordField;
    public GridPane adminGridPane;
    private String[][] ordersArray;
    private Order[] orderObjects;

    @FXML
    public void goBack() throws IOException {
        App.setRoot("startingButtons");
    }

    @FXML
    public void showOrdersButton() {
        if (passwordField.getText().equals("1234")) {
            ordersArray = App.getConnect().getTableContent("roznosci", "zamowienia");
            orderObjects = new Order[ordersArray.length - 1];
            for (int i = 1; i < ordersArray.length; i++) {
                Order order = new Order(ordersArray[i][1], ordersArray[i][2], ordersArray[i][3], ordersArray[i][4], ordersArray[i][5], ordersArray[i][6],
                        ordersArray[i][7], ordersArray[i][8], ordersArray[i][9], ordersArray[i][10]);
                orderObjects[i - 1] = order;
            }

            adminGridPane.getChildren().clear();
            adminGridPane.setHgap(10);
            adminGridPane.setVgap(10);
            adminGridPane.setPadding(new Insets(0, 0, 0, 0));
            Label label;

            for (int i = 0; i < ordersArray.length - 1; i++) {
                label = new Label(orderObjects[i].toString());
                label.setTextFill(Color.color(1, 1, 1));
                label.setStyle("-fx-font-weight: bold; -fx-font-size: 15px");
                adminGridPane.addRow(i, label);
                adminGridPane.setHalignment(label, HPos.LEFT);
            }
        } else {
            App.getConnect().showNotification("Pizzeria Peperoni", "Podano złe hasło pracownika");
        }
    }
}
