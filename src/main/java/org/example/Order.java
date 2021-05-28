package org.example;

import java.util.Arrays;

public class Order {
    private String city, street, houseNumber, cardNumber, cvv, deliveryMethod, paymentMethod, products, orderSum, orderNumber;
    //private double orderSum;
    //private int orderNumber;
    private String[] productsArray, pizzaNames;

    public Order(String orderNumber, String paymentMethod, String deliveryMethod, String cardNumber, String cvv, String city, String street, String houseNumber, String products, String orderSum) {
        this.orderNumber = orderNumber;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.deliveryMethod = deliveryMethod;
        this.paymentMethod = paymentMethod;
        this.products = products;
        this.orderSum = orderSum;
        productsToArray(this.products);
    }

    public void productsToArray(String products){
        products = products.replaceAll("\\[", "");
        products = products.replaceAll("]", "");
        products = products.replaceAll("]", "");
        products = products.replaceAll(" ", "");
        productsArray = products.split(",");
        pizzaNames = new String[productsArray.length];
        for(int i = 0; i < productsArray.length; i++){
            pizzaNames[i] = App.getPizzaName(productsArray[i]);
        }
    }

    public String toString() {
        return "Numer zamówienia: " + this.orderNumber + "     Płatność: " + this.paymentMethod + "     Dostawa: " + this.deliveryMethod + "     Nr karty: " + this.cardNumber +
                "     CVV: " + this.cvv + "     Miasto: " + this.city + "     Ulica: " + this.street + "     Nr domu: " + this.houseNumber + "     Produkty: " + Arrays.toString(pizzaNames) +
                "     Cena: " + this.orderSum;
    }
}
