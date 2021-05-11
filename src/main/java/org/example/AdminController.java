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
    private String[][] orders;

    @FXML
    public void goBack() throws IOException {
        App.setRoot("startingButtons");
    }

    @FXML
    public void showOrdersButton() {
        if (passwordField.getText().equals("1234")) {
            orders = App.getConnect().getTableContent("roznosci", "zamowienia");
            adminGridPane.getChildren().clear();
            adminGridPane.setHgap(10);
            adminGridPane.setVgap(10);
            adminGridPane.setPadding(new Insets(0, 0, 0, 0));
            Label label;

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < orders.length; j++) {
                    if (j == 0) {
                        label = new Label(orders[j][i]);
                        label.setTextFill(Color.color(1, 0.65, 0));
                        label.setStyle("-fx-font-weight: bold; -fx-font-size: 30px");
                        adminGridPane.addRow(j + 1, label);
                        adminGridPane.setHalignment(label, HPos.CENTER);
                    } else {
                        label = new Label(orders[j][i]);
                        label.setTextFill(Color.color(1, 1, 1));
                        label.setStyle("-fx-font-weight: bold; -fx-font-size: 15px");
                        adminGridPane.addRow(j + 1, label);
                        adminGridPane.setHalignment(label, HPos.CENTER);
                    }
                }
            }
        } else {
            App.getConnect().showNotification("Pizzeria Peperoni", "Podano złe hasło pracownika");
        }
    }
}
