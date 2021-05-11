package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeliveryAndPaymentController {

    @FXML
    public ComboBox comboBoxDelivery, comboBoxPayment;
    public TextField cardNumber, cvv, city, street, houseNumber;

    @FXML
    public void goBack() throws IOException {
        App.setRoot("menu");
    }

    @FXML
    public void goToSummary() throws IOException {
        if (comboBoxPayment.getValue() != null && comboBoxDelivery.getValue() != null &&
                (((comboBoxPayment.getValue().equals("Karta kredytowa") || comboBoxPayment.getValue().equals("Karta debetowa")) &&
                        validateCreditCardNumber(cardNumber.getText())) || comboBoxPayment.getValue().equals("W lokalu"))) {
            if (comboBoxDelivery.getValue().equals("Odbiór w lokalu") ||
                    (comboBoxDelivery.getValue().equals("Dowóz") && !city.getText().equals("") && !street.getText().equals("") &&
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

    @FXML
    private void comboActionDelivery(ActionEvent event) {
        if (comboBoxDelivery.getValue().equals("Odbiór w lokalu")) {
            city.setVisible(false);
            street.setVisible(false);
            houseNumber.setVisible(false);
        } else if (comboBoxDelivery.getValue().equals("Dowóz")) {
            city.setVisible(true);
            street.setVisible(true);
            houseNumber.setVisible(true);
        }
    }

    @FXML
    private void comboActionPayment(ActionEvent event) {
        if (comboBoxPayment.getValue().equals("Karta kredytowa") || comboBoxPayment.getValue().equals("Karta debetowa")) {
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
}

