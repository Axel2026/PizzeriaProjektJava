package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class DeliveryAndPaymentController {

    @FXML
    public ComboBox comboBoxDelivery, comboBoxPayment;
    public TextField cardNumber, cvv, city, street, houseNumber;
    public WebView mapView;
    private WebEngine webEngine;


    /**
     * Obsluguje przycisk goBack,
     * ustawia uzywany plik fxml
     */

    @FXML
    public void goBack() throws IOException {
        App.setRoot("menu");
    }

    /**
     * Przechodzi do nastepnego ekranu, jesli spelnione sa warunki
     * wpisanych wartosci karty kredytowej oraz adresu
     * wartosci te zczytywane sa z pol tekstowych
     */

    @FXML
    public void goToSummary() throws IOException {
        if (comboBoxPayment.getValue() != null && comboBoxDelivery.getValue() != null &&
                (((comboBoxPayment.getValue().equals("Karta kredytowa (VISA)") || comboBoxPayment.getValue().equals("Karta debetowa (VISA)")) &&
                        validateCreditCardNumber(cardNumber.getText())) || comboBoxPayment.getValue().equals("W lokalu"))) {
            if (comboBoxDelivery.getValue().equals("Odbiór w lokalu") ||
                    (comboBoxDelivery.getValue().equals("Dowóz (cena + 10 zł)") && !city.getText().equals("") && !street.getText().equals("") &&
                            !houseNumber.getText().equals(""))) {
                App.setPaymentMethod(comboBoxPayment.getValue().toString());
                App.setDeliveryMethod(comboBoxDelivery.getValue().toString());
                App.setCity(city.getText());
                App.setStreet(street.getText());
                App.setHouseNumber(houseNumber.getText());
                App.setCardNumber(cardNumber.getText());
                App.setCvv(cvv.getText());
                App.setRoot("summary");
            } else {
                App.getConnect().showNotification("Pizzeria Peperoni", "Proszę wypełnić wszystkie pola!");
            }
        } else {
            App.getConnect().showNotification("Pizzeria Peperoni", "Proszę wypełnić wszystkie pola!");
        }
    }

    /**
     * Metoda ustawiajaca widocznosc pol tekstowych
     * w zaleznosci od wybranego sposobu dostawy
     */

    @FXML
    private void comboActionDelivery() {
        if (comboBoxDelivery.getValue().equals("Odbiór w lokalu")) {
            city.setVisible(false);
            street.setVisible(false);
            houseNumber.setVisible(false);
        } else if (comboBoxDelivery.getValue().equals("Dowóz (cena + 10 zł)")) {
            city.setVisible(true);
            street.setVisible(true);
            houseNumber.setVisible(true);
        }
    }

    /**
     * Metoda ustawiajaca widocznosc pol tekstowych
     * w zaleznosci od wybranego sposobu platnosci
     */

    @FXML
    private void comboActionPayment() {
        if (comboBoxPayment.getValue().equals("Karta kredytowa (VISA)") || comboBoxPayment.getValue().equals("Karta debetowa (VISA)")) {
            cvv.setVisible(true);
            cardNumber.setVisible(true);
            comboBoxDelivery.setDisable(false);
        } else if (comboBoxPayment.getValue().equals("W lokalu")) {
            cvv.setVisible(false);
            cardNumber.setVisible(false);
            comboBoxDelivery.setValue("Odbiór w lokalu");
            comboBoxDelivery.setDisable(true);
        }
    }

    /**
     * Metoda sprawdzajaca poprawnosc wprowadzonego numery karty kredytowej/debetowej
     * Jesli odpowiada ona numerowi karty Visa, to zwracana jest wartosc True
     *
     * @param cardNumber numer karty, ktory chcemy sprawdzic
     * @return True jesli numer karty jest prawidlowy, False jesli nie jest
     */

    private static Boolean validateCreditCardNumber(String cardNumber) {
        String regex = "^4[0-9]{12}(?:[0-9]{3})?$";

        Pattern pattern = Pattern.compile(regex);
        cardNumber = cardNumber.replaceAll("-", "");
        cardNumber = cardNumber.replaceAll(" ", "");

        Matcher matcher = pattern.matcher(cardNumber);

        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metoda ładująca lokalizacje restauracji na google maps
     */
    public void loadSite() {
        this.webEngine = mapView.getEngine();
        File f = new File("src/main/java/simple_map.html");
        this.webEngine.load(f.toURI().toString());
        System.out.println("Załadowano stronę\n");
        mapView.setVisible(true);
    }
}

