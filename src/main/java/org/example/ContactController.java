package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.example.connection.Connect;

import java.io.IOException;

public class ContactController {

    @FXML
    public TextField emailText;
    public TextField emailSubject;

    /**
     * Obsluguje przycisk goBack,
     * ustawia uzywany plik fxml
     */

    @FXML
    public void goBack() throws IOException {
        App.setRoot("startingButtons");
    }

    /**
     * Metoda wysylajaca email, uzywajac zczytanych z pol danych,
     * wyswietla powiadomienie o wyslaniu
     */

    @FXML
    public void sendEmail() {
        String txtEmail = emailText.getText();
        String subEmail = emailSubject.getText();
        Connect.sendEmailStatic("pnytko20@gmail.com", txtEmail, subEmail);
        App.getConnect().showNotification("Pizzeria Peperoni", "Wiadomość została wysłana!");
    }
}

