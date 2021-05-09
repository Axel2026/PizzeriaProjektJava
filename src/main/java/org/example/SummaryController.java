package org.example;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class SummaryController implements Initializable {

    @FXML
    private String[][] products = App.getConnect().getTableContent("roznosci", "produkty");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showSummary();
    }

    public void showSummary(){

    }
}
