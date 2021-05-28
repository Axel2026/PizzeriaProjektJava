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

    public Connection getConnection() {
        return (connection);
    }

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

    public static boolean emailCheck(String username) {
        if (username == null) {
            System.out.println("Podano pustego emaila");
            return false;
        } else {
            String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
            return username.matches(regex);
        }
    }

    public String[][] getTableContent(String nazwaBazy, String nazwaTabeli) {
        try {
            int row = 0;
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs;
            if(nazwaTabeli.equals("produkty")){
                rs = stmt.executeQuery("select * from " + nazwaBazy + "." + nazwaTabeli + " ORDER BY index_produktu");
            }else{
                rs = stmt.executeQuery("select * from " + nazwaBazy + "." + nazwaTabeli);
            }
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

    public void showNotification(String title, String text) {
        Notifications notification = Notifications.create();
        notification.title(title);
        notification.text(text);
        notification.hideAfter(Duration.seconds(5));
        notification.position(Pos.TOP_LEFT);
        notification.show();
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public int getNumRows() {
        return numRows;
    }
}

