package org.example;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
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
    private static String[][] products;
    private List<String> pizzaIndex = new ArrayList<String>();
    private CheckBox checkBox;
    private List<CheckBox> arrCb = new ArrayList<>();
    private float sum;

    /**
     * Metoda korzystajaca z interfejsu Initializable
     * wywoluje funkcje showMenu() od razu po zainicjalizowaniu sceny
     * @param location  lokalizacja sciezki relatywnej do obiektu roota
     * @param resources zasoby potrzebne do znalezienia lokazliacji obiektu roota
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showMenu();
    }

    /**
     * Obsluguje przycisk goBack,
     * ustawia uzywany plik fxml
     */

    @FXML
    public void goBack() throws IOException {
        App.setRoot("startingButtons");
    }

    /**
     * Metoda ladujaca nowa scene jesli koszyk nie jest pusty
     */

    @FXML
    public void order() throws IOException {
        if (pizzaIndex.size() > 0 && sum > 0) {
            App.setRoot("deliveryAndPayment");
        } else {
            App.getConnect().showNotification("Pizzeria Peperoni", "Koszyk jest pusty");
        }
    }

    /**
     * Metoda wyswietlajaca menu pizzerii, pobierajac produkty z bazy danych
     */

    @FXML
    public void showMenu() {
        products = App.getConnect().getTableContent("roznosci", "produkty");
        pizzaMenu.getChildren().clear();
        pizzaMenu.setHgap(100);
        pizzaMenu.setVgap(10);
        pizzaMenu.setPadding(new Insets(0, 15, 15, 15));

        for (int j = 0; j < products.length; j++) {
            if (j == 0) {
                Label label = new Label("Nazwa");
                label.setTextFill(Color.color(1, 0.65, 0));
                label.setStyle("-fx-font-weight: bold; -fx-font-size: 30px");
                pizzaMenu.addRow(j + 1, label);
            } else {
                checkBox = new CheckBox(products[j][2]);
                checkBox.setTextFill(Color.color(1, 0.65, 0));
                checkBox.setStyle("-fx-font-weight: bold; -fx-font-size: 15px");
                arrCb.add(checkBox);
                checkBox.setTextFill(Color.color(1, 1, 1));
                pizzaMenu.addRow(j + 2, checkBox);
            }
        }

        for (int j = 0; j < products.length; j++) {
            if (j == 0) {
                Label label = new Label("Cena");
                label.setTextFill(Color.color(1, 0.65, 0));
                label.setStyle("-fx-font-weight: bold; -fx-font-size: 30px");
                pizzaMenu.addRow(j + 1, label);
            } else {
                Label label = new Label(products[j][4] + "zł.");
                label.setTextFill(Color.color(1, 0.65, 0));
                label.setStyle("-fx-font-weight: bold; -fx-font-size: 15px");
                pizzaMenu.addRow(j + 2, label);
            }
        }
    }

    /**
     * Metoda dodajaca zaznaczone produkty do koszyka wyswietlanego w prawej czesci sceny,
     * liczona jest rowniez suma produktow
     */

    @FXML
    public void addToCart() {
        cart.getChildren().clear();
        cart.setHgap(100);
        cart.setVgap(10);
        cart.setPadding(new Insets(15, 15, 15, 15));
        sum = 0;

        for (int i = 0; i < arrCb.size(); i++) {
            if (arrCb.get(i).isSelected()) {
                pizzaIndex.add(products[i + 1][1]);
            }
            arrCb.get(i).setSelected(false);
        }

        if (pizzaIndex.size() > 0) {
            for (int i = 0; i < pizzaIndex.size(); i++) {
                sum += Float.parseFloat(products[Integer.parseInt(pizzaIndex.get(i))][4]);
            }
            Label label = new Label("Suma: " + sum + "zł.");
            label.setStyle("-fx-font-weight: bold; -fx-font-size: 30px");
            label.setTextFill(Color.color(1, 0.65, 0));
            cart.addRow(1, label);

            for (int i = 0; i < pizzaIndex.size(); i++) {
                Label label1 = new Label(products[Integer.parseInt(pizzaIndex.get(i))][2]);
                label1.setStyle("-fx-font-weight: bold; -fx-font-size: 20px");
                label1.setTextFill(Color.color(1, 1, 1));
                cart.addRow(i + 3, label1);
            }

            App.setOrderSum(sum);
            App.setPizzaIndex(pizzaIndex);
        }
    }

    /**
     * Metoda czyszczaca koszyk
     */

    @FXML
    public void clearCart() {
        cart.getChildren().clear();
        sum = 0;
        pizzaIndex = new ArrayList<String>();
        App.setPizzaIndex(pizzaIndex);
    }
}
