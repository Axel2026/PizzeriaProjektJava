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
    public String city, street, houseNumber;
    public float deliveryPrice=0;

    /**
     * Metoda korzystajaca z interfejsu Initializable
     * wywoluje funkcje showProductSummary() oraz showDeliverySummary od razu po zainicjalizowaniu sceny
     * @param location  lokalizacja sciezki relatywnej do obiektu roota
     * @param resources zasoby potrzebne do znalezienia lokazliacji obiektu roota
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showProductSummary();
        showDeliverySummary();
    }

    /**
     * Obsluguje przycisk goBack,
     * ustawia uzywany plik fxml
     */

    @FXML
    public void goBack() throws IOException {
        App.setRoot("deliveryAndPayment");
    }

    /**
     * Metoda wyswietlajaca powiadomienia o zamowieniu
     * oraz przechodzaca do innego ekranu
     */

    @FXML
    public void order() throws IOException {
        insertToDatabase(App.getPaymentMethod(), App.getDeliveryMethod(), App.getCardNumber(),
                App.getCvv(), App.getCity(), App.getStreet(),
                App.getHouseNumber(), App.getPizzaIndex().toArray(new String[0]), App.getOrderSum());

        if (App.getDeliveryMethod().equals("Odbiór w lokalu")) {
            App.getConnect().showNotification("Pizzeria Peperoni", "Zamówienie zostało złożone!\nCzas wykonania zamówienia to około: " +
                    App.getPizzaIndex().size() * 15 + "min.");
        } else {
            App.getConnect().showNotification("Pizzeria Peperoni", "Zamówienie zostało złożone!\nCzas oczekiwania to około: " +
                    App.getPizzaIndex().size() * 20 + "min.");
        }
        App.setRoot("menu");
    }

    /**
     * Metoda wpisujaca dane do bazy, wykorzystujaca wczesniej utworzone zapytanie,
     * aby zapobiec wstrzyknieciu przez uzytkownika
     * @param cardNum numer karty
     * @param city nazwa miasta
     * @param cvv numer cvv
     * @param deliveryMethod metoda dostawy
     * @param houseNumber numer domu
     * @param orderedProducts zamowione produkty
     * @param paymentMethod metoda platnosci
     * @param street nazwa ulicy
     * @param sum cena zamowienia
     */

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

    /**
     * Metoda wyswietlajaca na ekranie zamowione produkty
     */

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

    /**
     * Metoda wyswietlajaca na ekranie informacje o dostawie oraz platnosci
     */

    public void showDeliverySummary() {

        Label label = new Label("Informacje:");
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 30px");
        label.setTextFill(Color.color(1, 0.65, 0));
        summaryGridPane.addRow(1, label);

        if (App.getCity().equals("")){
            this.city="Warszawa";
        }else {
            this.city=App.getCity();
            this.deliveryPrice=10;
        }
        label = new Label("Miasto: " + city);
        label.setTextFill(Color.color(1, 1, 1));
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 20px");
        summaryGridPane.addRow(2, label);

        if (App.getStreet().equals("")){
            this.street="Warszawska";
        }else {
            this.street=App.getStreet();
        }
        if (App.getHouseNumber().equals("")){
            this.houseNumber="12";
        }else {
            this.houseNumber=App.getHouseNumber();
        }
        label = new Label("Ulica i numer domu: " + street + " " + houseNumber);
        label.setTextFill(Color.color(1, 1, 1));
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 20px");
        summaryGridPane.add(label,1,3);

        label = new Label("Sposób płatności: " + App.getPaymentMethod());
        label.setTextFill(Color.color(1, 1, 1));
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 20px");
        summaryGridPane.add(label,1,4);

        label = new Label("Sposób dostawy: " + App.getDeliveryMethod());
        label.setTextFill(Color.color(1, 1, 1));
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 20px");
        summaryGridPane.add(label,1,5);

        label = new Label("Cena zamówienia: " + (App.getOrderSum()+deliveryPrice) + " zł ");
        label.setTextFill(Color.color(1, 1, 1));
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 20px");
        summaryGridPane.add(label,1,9);
    }
}
