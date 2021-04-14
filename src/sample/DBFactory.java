package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBFactory {
    // Ykkur vantar að setja upp .jar file fyrir jdbc sqlite
    private final static String DATABASE_URL = "jdbc:sqlite:sample/hotelDataBase.db";
    Connection connection = null;

    public DBFactory() {

    }

    public Connection connect() {
        Connection conn = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(DATABASE_URL);
            System.out.println("Connected!");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
}
