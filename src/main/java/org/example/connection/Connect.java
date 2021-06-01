package org.example.connection;

import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.*;
import java.lang.String;

/**
 * Klasa Connect obsluguje polaczenie programu z baza danych
 */

public class Connect {
    private String driver;
    private String host;
    private String port;
    private String dbname;
    private String user;
    private String url;
    private String pass;
    private static Connection connection;
    private String arr[][];
    private int numCols;
    private int numRows;

    /**
     * Konstruktor klasy przypisuje do zmiennych wartosci pozwalajce polaczyc sie z
     * baza danych oraz uzywa metody makeConnection() w celu nawiazania polaczenia
     * @see #makeConnection
     */

    public Connect() {
        driver = "org.postgresql.Driver";
        host = "195.150.230.210";
        port = "5434";
        dbname = "2021_nytko_patryk";
        user = "2021_nytko_patryk";
        url = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;
        pass = "bazydanych2021";

        connection = makeConnection();
    }

    /**
     * Zwraca zmienna typu Connection w celu sprawdzenia polaczenia z baza
     * @return zmienna, do ktorej przypisana zostala metoda makeConnection()
     */

    public Connection getConnection() {
        return (connection);
    }

    /**
     * Metoda nawiazujaca polaczenie z baza uzywajac zmiennych przypisanych w konstruktorze,
     * wypisuje bledy nawiazywania polaczenia
     * @return zmienna typu connection lub null, kiedy wystapi blad
     */

    public Connection makeConnection() {
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, user, pass);
            return (connection);

        } catch (ClassNotFoundException cnfe) {
            System.err.println("Blad ladowania sterownika: " + cnfe);

            return (null);
        } catch (SQLException sqle) {
            System.err.println("Blad przy nawiązywaniu polaczenia: " + sqle);

            return (null);
        }
    }

    /**
     * Metoda sluzaca do wysylania emaila. Sprawdza poprawnosc emaila uzywajac metody statycznej
     * emailCheck, jesli ta zwroci True, to metoda wysyla email na podany adres korzystajac z konta,
     * do ktorego dane zostaly wczesniej podane
     * @param email adres email, na ktory wysylany bedzie email
     * @param subject temat emaila
     * @param text wiadomosc email
     * @see #emailCheck(String)
     */

    public static void sendEmailStatic(String email, String text, String subject) {

        final String username = "patryknytko33189@gmail.com";
        final String password = "ovcwwxgkstqtebfp";

        if (emailCheck(username)) {

            Properties prop = new Properties();
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "465");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.socketFactory.port", "465");
            prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

            Session session = Session.getInstance(prop,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });
            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("patryknytko33189@gmail.com"));
                message.setRecipients(
                        Message.RecipientType.TO,
                        InternetAddress.parse(email)
                );
                message.setSubject(subject);
                message.setText(text);

                Transport.send(message);

                System.out.println("Wiadomość została wysłana");

            } catch (MessagingException e) {
                System.out.println(e);
            }
        } else {
            System.out.println("Podano zły adres email");
        }

    }

    /**
     * Metoda sluzaca do sprawdzania poprawnosci emaila uzywajac zmiennej regex
     * @param username adres email, ktory chcemy sprawdzic
     * @return True, jesli email jest prawidlowy, False jesli jest nieprawidlowy
     * @see #sendEmailStatic(String, String, String)
     */

    public static boolean emailCheck(String username) {
        if (username == null) {
            System.out.println("Podano pustego emaila");
            return false;
        } else {
            String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
            return username.matches(regex);
        }
    }

    /**
     * Metoda sluzaca do pobrania zawartosci danej tabeli z bazy,
     * jesli to sie nie uda, to wypisywany jest blad w konsoli
     * @param schemaName nazwa schematu tabeli, z ktorej chcemy pobrac zawartosc
     * @param tableName nazwa tabeli, z ktorej chcemy pobrac zawartosc
     * @return tablica 2wymiarowa z pobranymi danymi
     */

    public String[][] getTableContent(String schemaName, String tableName) {
        try {
            int row = 0;
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("select * from " + schemaName + "." + tableName);
            int numCols = rs.getMetaData().getColumnCount();
            int rowCount = rs.last() ? rs.getRow() : 0;
            rs.beforeFirst();
            ResultSetMetaData rsmd = rs.getMetaData();
            arr = new String[rowCount + 1][numCols + 1];
            while (rs.next()) {
                row++;
                for (int col = 1; col <= numCols; col++) {
                    arr[0][col] = rsmd.getColumnName(col);
                    arr[row][col] = rs.getString(col);
                }
            }
            setNumCols(numCols);
            setNumRows(row);
        } catch (Exception e) {
            System.out.println(e);
        }
        return arr;
    }

    /**
     * Metoda sluzaca do wyswietlania powiadomien na ekranie
     * @param title tytul powiadomienia
     * @param text tresc powiaodmienia
     */

    public void showNotification(String title, String text) {
        Notifications notification = Notifications.create();
        notification.title(title);
        notification.text(text);
        notification.hideAfter(Duration.seconds(5));
        notification.position(Pos.TOP_LEFT);
        notification.show();
    }

    /**
     * Ustawia ilosc kolumn pobieranej tabeli
     * @param numCols ilosc kolumn
     */

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    /**
     * Ustawia ilosc wierszy pobieranej tabeli
     * @param numRows ilosc wierszy
     */

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    /**
     * Zwraca ilosc kolumn pobranej bazy
     * @return numCols ilosc kolumn
     */

    public int getNumCols() {
        return numCols;
    }

    /**
     * Zwraca ilosc wierszy pobranej bazy
     * @return numRows ilosc wierszy
     */

    public int getNumRows() {
        return numRows;
    }
}

