
package org.example;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import java.io.IOException;

public class ContactController {

    @FXML
    public TextField emailText;
    public TextField emailSubject;

    @FXML
    public void goBack() throws IOException {
        App.setRoot("startingButtons");
    }

    @FXML
    public void sendEmail() {
        String txtEmail = emailText.getText();
        String subEmail = emailSubject.getText();
        Connect.sendEmailStatic("pnytko20@gmail.com", txtEmail, subEmail);
    }

    @FXML
    public void showNotification(String title, String text){
        Notifications notification = Notifications.create();
        notification.title(title);
        notification.text(text);
        notification.hideAfter(Duration.seconds(5));
        notification.position(Pos.TOP_LEFT);
        notification.show();
    }
}

