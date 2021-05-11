package org.example;

import java.io.IOException;


public class StartingButtonsController {

    public void showMenu() throws IOException {
        if (App.getConnect() == null) {
            App.setConnect(new Connect());
        }
        App.setRoot("menu");
    }

    public void contactUs() throws IOException {
        App.setRoot("contact");
    }

    public void goToAdmin() throws IOException {
        if (App.getConnect() == null) {
            App.setConnect(new Connect());
        }
        App.setRoot("admin");
    }
}
