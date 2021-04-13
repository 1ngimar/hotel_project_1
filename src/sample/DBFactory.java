package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBFactory {
    // Ykkur vantar að setja upp .jar file fyrir jdbc sqlite
    private final static String DATABASE_URL = "jdbc:sqlite:hotelDataBase.db";
    Connection connection = null;

    public DBFactory() {

    }

    public Connection connect() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(DATABASE_URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
}
