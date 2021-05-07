package org.example;

import javafx.fxml.FXML;

import java.io.IOException;

public class StartingButtonsController {

    public void showMenu() throws IOException {
        App.setRoot("menu");
    }

    public void contactUs() throws IOException {
        App.setRoot("contact");
    }
}
