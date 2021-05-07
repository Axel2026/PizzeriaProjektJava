package org.example;

import javafx.fxml.FXML;

import java.io.IOException;

public class MenuController {

    @FXML
    public void goBack() throws IOException {
        App.setRoot("startingButtons");
    }
}
