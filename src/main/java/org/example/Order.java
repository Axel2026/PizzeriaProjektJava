package org.example;

import java.util.Arrays;

public class Order {
    private String city, street, houseNumber, cardNumber, cvv, deliveryMethod, paymentMethod, products, orderSum, orderNumber;
    private String[] productsArray, pizzaNames;

    /**
     * Klasa opisujaca zamowienie poprzez przypisanie wartosci numeru zamowienia, metody platnosci, metody dostawy
     * numeru karty kredytowej/debetowej, numeru cvv karty, wybranego przez klienta adresu, nazw produktow oraz ceny
     * @param cardNumber numer karty
     * @param city nazwa miasta
     * @param cvv numer cvv
     * @param deliveryMethod metoda dostawy
     * @param houseNumber numer domu
     * @param products zamowione produkty
     * @param paymentMethod metoda platnosci
     * @param street nazwa ulicy
     * @param orderSum cena zamowienia
     */

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

    /**
     * Metoda wypelniajaca tablice nazwami produktow, poprzez uzycie metody klasy App
     * oraz indexow pizz zamowieonych przez klienta
     * @param products produkty zapisane w typie tekstowym
     */

    public void productsToArray(String products) {
        products = products.replaceAll("\\[", "");
        products = products.replaceAll("]", "");
        products = products.replaceAll("]", "");
        products = products.replaceAll(" ", "");
        productsArray = products.split(",");
        pizzaNames = new String[productsArray.length];
        for (int i = 0; i < productsArray.length; i++) {
            pizzaNames[i] = App.getPizzaName(productsArray[i]);
        }
    }

    /**
     * Metoda zwracajaca obiekt w sformatowany sposob
     * @return wartosci obiektu klasy Order
     */

    @Override
    public String toString() {
        return "Numer zamówienia: " + this.orderNumber + "   |   Płatność: " + this.paymentMethod + "   |   Dostawa: " + this.deliveryMethod + "   |   Nr karty: " + this.cardNumber +
                "   |   CVV: " + this.cvv + "   |   Miasto: " + this.city + "   |   Ulica: " + this.street + "   |   Nr domu: " + this.houseNumber + "   |   Produkty: " + Arrays.toString(pizzaNames) +
                "   |   Cena: " + this.orderSum;
    }
}
