package org.example;

import org.example.connection.Connect;

import java.io.IOException;


public class StartingButtonsController {

    /**
     * Obsluguje przycisk showMenu, nawiazuje polaczenie z baza
     * ustawia uzywany plik fxml
     */

    public void showMenu() throws IOException {
        if (App.getConnect() == null) {
            App.setConnect(new Connect());
        }
        App.setRoot("menu");
    }

    /**
     * Obsluguje przycisk contactUs,
     * ustawia uzywany plik fxml
     */

    public void contactUs() throws IOException {
        App.setRoot("contact");
    }

    /**
     * Obsluguje przycisk goToAdmin, nawiazuje polaczenie z baza
     * ustawia uzywany plik fxml
     */

    public void goToAdmin() throws IOException {
        if (App.getConnect() == null) {
            App.setConnect(new Connect());
        }
        App.setRoot("admin");
    }
}
