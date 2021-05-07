package org.example;


import com.sun.mail.smtp.SMTPAddressFailedException;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.lang.String;

public class Connect {
    private List dane = new ArrayList();

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

    public Connect(String[] lines) {
        driver = lines[0];
        host = lines[1];
        port = lines[2];
        dbname = lines[3];
        user = lines[4];
        url = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;
        pass = lines[5];

        connection = makeConnection();
    }

    public Connection getConnection() {
        return (connection);
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Connection makeConnection() {
        try {
            Class.forName(driver);
            System.out.println(url + " " + user + " " + pass);
            Connection connection = DriverManager.getConnection(url, user, pass);
            System.out.println("Połączono z bazą\n");
            return (connection);

        } catch (ClassNotFoundException cnfe) {
            System.err.println("Blad ladowania sterownika: " + cnfe);

            return (null);
        } catch (SQLException sqle) {
            System.err.println("Blad przy nawiązywaniu polaczenia: " + sqle);

            return (null);
        }
    }

    public String[] getTablesDb() {
        try {
            String[] tablesArr;
            List<String> tablesList = new ArrayList<>();
            DatabaseMetaData metaData = connection.getMetaData();
            String[] types = {"TABLE"};
            ResultSet tables = metaData.getTables(null, "dziekanat", "%", types);
            System.out.println("Tablice w bazie: \n");
            while (tables.next()) {
                tablesList.add(tables.getString("TABLE_NAME"));
                System.out.println(tables.getString("TABLE_NAME"));
            }
            tablesArr = new String[tablesList.size()];
            tablesList.toArray(tablesArr);
            System.out.println(Arrays.toString(tablesArr));

            return tablesArr;
        } catch (Exception e) {
            System.out.print(e);
        }
        return null;
    }

/*    public void sendEmail(String email, String text, String subject) {

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
                showNotification("BazaEmail", "Wysłano!");

            } catch (MessagingException e) {
                System.out.println(e);
                showNotification("BazaEmail", "Nie udało się wysłać e-maila");
            }
        } else {
            System.out.println("Podano zły adres email");
        }

    }*/

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

    public String[][] getTableContent(String nazwaBazy, String nazwatabeli) {
        try {
            int row = 0;
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("select * from " + nazwaBazy + "." + nazwatabeli);
            int numCols = rs.getMetaData().getColumnCount();
            int rowCount = rs.last() ? rs.getRow() : 0;
            rs.beforeFirst();
            System.out.println("\nElementy tabeli :  \n");
            ResultSetMetaData rsmd = rs.getMetaData();
            arr = new String[rowCount + 1][numCols + 1];
            while (rs.next()) {
                row++;
                for (int col = 1; col <= numCols; col++) {
                    String name = rsmd.getColumnName(col);
                    arr[0][col] = rsmd.getColumnName(col);
                    arr[row][col] = rs.getString(col);
                    if (col % numCols == 0) {
                        System.out.print(" " + name + " " + arr[row][col] + "\n\n");
                    } else {
                        System.out.print(" " + name + " " + arr[row][col]);
                    }
                }
            }
            setNumCols(numCols);
            setNumRows(row);
        } catch (Exception e) {
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

/*    public void Sender(String nazwaTabeli) {
        try {
            int row = 0;
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery("select * from roznosci." + nazwaTabeli);
            int numCols = rs.getMetaData().getColumnCount();
            int rowCount = rs.last() ? rs.getRow() : 0;
            rs.beforeFirst();
            System.out.println("\nElementy tabeli :  \n");
            arr = new String[rowCount + 1][numCols + 1];
            while (rs.next()) {
                row++;
                for (int col = 1; col <= numCols; col++) {
                    arr[row][col] = rs.getString(col);
                    if (col % 2 == 0) {
                        System.out.print(arr[row][1] + " ");
                        System.out.print(arr[row][2] + "\n");
                        if (arr[row][2].equals("t")) {
                            sendEmail(arr[row][1], "Przykładowa wiadomość", "Patryk Nytko");
                        } else {
                            System.out.println("Wiadomość nie została wysłana");
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
    }*/


    public void Reader() {
        try {
            Scanner scanner = new Scanner(new File("F:\\Programy\\BazaEmail\\src\\main\\resources\\org\\dane.txt"));
            int i = 0;
            while (scanner.hasNextLine()) {
                dane.add(scanner.nextLine());
                i++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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

