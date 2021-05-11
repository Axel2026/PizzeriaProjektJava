package org.example;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;

public class SummaryController implements Initializable {

    @FXML
    private String[][] products = App.getConnect().getTableContent("roznosci", "produkty");
    public GridPane summaryGridPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showProductSummary();
        showDeliverySummary();
    }

    @FXML
    public void goBack() throws IOException {
        App.setRoot("deliveryAndPayment");
    }

    @FXML
    public void order() throws IOException {
        insertToDatabase(App.getPaymentMethod(), App.getDeliveryMethod(), App.getCardNumber(),
                App.getCvv(), App.getCity(), App.getStreet(),
                App.getHouseNumber(), App.getPizzaIndex().toArray(new String[0]), App.getOrderSum());

        if (App.getDeliveryMethod().equals("Odbiór w lokalu")) {
            App.getConnect().showNotification("Pizzeria Na okrągło", "Zamówienie zostało złożone!\nCzas wykonania zamówienia to około: " +
                    App.getPizzaIndex().size() * 15 + "min.");
        } else {
            App.getConnect().showNotification("Pizzeria Na okrągło", "Zamówienie zostało złożone!\nCzas oczekiwania to około: " +
                    App.getPizzaIndex().size() * 20 + "min.");
        }
        App.setRoot("menu");
    }

    public void insertToDatabase(String paymentMethod, String deliveryMethod, String cardNum, String cvv, String city, String street,
                                 String houseNumber, String[] orderedProducts, float sum) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = App.getConnect().getConnection();
            stmt = conn.prepareStatement("INSERT INTO roznosci.zamowienia (nr_zamowienia, sposob_platnosci, sposob_dostawy, nr_karty, nr_cvv, miasto, ulica," +
                    "nr_domu, produkty, cena) values (default, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, paymentMethod);
            stmt.setString(2, deliveryMethod);
            stmt.setString(3, cardNum);
            stmt.setString(4, cvv);
            stmt.setString(5, city);
            stmt.setString(6, street);
            stmt.setString(7, houseNumber);
            stmt.setString(8, Arrays.toString(orderedProducts));
            stmt.setFloat(9, sum);
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void showProductSummary() {
        summaryGridPane.setHgap(100);
        summaryGridPane.setVgap(10);
        summaryGridPane.setPadding(new Insets(0, 15, 15, 15));
        Label label = new Label("Produkty:");
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 30px");
        label.setTextFill(Color.color(1, 0.65, 0));
        summaryGridPane.addRow(1, label);

        for (int i = 0; i < App.getPizzaIndex().size(); i++) {
            label = new Label(products[Integer.parseInt(App.getPizzaIndex().get(i))][2]);
            label.setTextFill(Color.color(1, 1, 1));
            label.setStyle("-fx-font-weight: bold; -fx-font-size: 20px");
            summaryGridPane.addRow(i + 2, label);
        }
    }

    public void showDeliverySummary() {

        Label label = new Label("Informacje:");
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 30px");
        label.setTextFill(Color.color(1, 0.65, 0));
        summaryGridPane.addRow(1, label);

        label = new Label("Miasto: " + App.getCity());
        label.setTextFill(Color.color(1, 1, 1));
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 20px");
        summaryGridPane.addRow(2, label);

        label = new Label("Ulica i numer domu: " + App.getStreet() + " " + App.getHouseNumber());
        label.setTextFill(Color.color(1, 1, 1));
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 20px");
        summaryGridPane.addRow(3, label);

        label = new Label("Sposób płatności: " + App.getPaymentMethod());
        label.setTextFill(Color.color(1, 1, 1));
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 20px");
        summaryGridPane.addRow(4, label);

        label = new Label("Sposób dostawy: " + App.getDeliveryMethod());
        label.setTextFill(Color.color(1, 1, 1));
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 20px");
        summaryGridPane.addRow(5, label);
    }
}
