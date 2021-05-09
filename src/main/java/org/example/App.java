package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class App extends Application {

    private static Scene scene;
    private static Connect connect;
    private static List<String> pizzaIndex;
    private static float orderSum;
    private static String city, street, houseNumber, cardNumber, cvv;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("startingButtons"), 1280, 720);
        stage.setTitle("Na Okrągło");
        stage.setScene(scene);
        File file = new File("src\\main\\java\\org\\example\\style.css");
        scene.getStylesheets().clear();
        scene.getStylesheets().add("file:///" + file.getAbsolutePath().replace("\\", "/"));
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void setConnect(Connect c) {
        connect = c;
    }

    public static Connect getConnect() {
        return connect;
    }

    public static void setCity(String c) {
        city = c;
    }

    public static String getCity() {
        return city;
    }

    public static void setStreet(String s) {
        street = s;
    }

    public static String getStreet() {
        return street;
    }

    public static void setHouseNumber(String hn) {
        houseNumber = hn;
    }

    public static String getHouseNumber() {
        return houseNumber;
    }

    public static void setCardNumber(String cn) {
        cardNumber = cn;
    }

    public static String getCardNumber() {
        return cardNumber;
    }

    public static void setCvv(String cv) {
        cvv = cv;
    }

    public static String getCvv() {
        return cvv;
    }

    public static void setOrderSum(float sum) {
        orderSum = sum;
    }

    public static float getOrderSum() {
        return orderSum;
    }

    public static void setPizzaIndex(List<String> indexes) {
        pizzaIndex = indexes;
    }

    public static List<String> getPizzaIndex() {
        return pizzaIndex;
    }

    public static void main(String[] args) {
        launch();
    }

}