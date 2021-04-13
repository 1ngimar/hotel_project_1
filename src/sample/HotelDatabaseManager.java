package sample;

import java.sql.*;
import java.util.ArrayList;

public class HotelDatabaseManager {

    public HotelDatabaseManager() {

    }

    private Connection connect() {
        String url = "jdbc:sqlite:sample/hotelDataBase.db"; // maybe fix later
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private ArrayList<Hotel> allHotels;
    private User dummyUser;
    private ArrayList<HotelBooking> allBookings;
    Connection connection = null;

    public ArrayList<Hotel> getAllHotels() {
        allHotels.clear();
        String sqlAllHotels = "SELECT * FROM HOTEL";

        try
                (Connection conn = this.connect();
                 Statement stmtHotels = conn.createStatement();
                 ResultSet rsHotels = stmtHotels.executeQuery(sqlAllHotels);) {
            while (rsHotels.next()) {
                ArrayList<ArrayList<Room>> allFreeRooms;
                String sqlRoomsForHotel = "SELECT * FROM ROOM WHERE RoomHotelID = HotelID";
                Statement stmtRooms = conn.createStatement();
                ResultSet rsRooms = stmtRooms.executeQuery(sqlRoomsForHotel);
                while (rsRooms.next()) {
                    Room r = new Room();
                    int roomID = rsRooms.getInt("RoomID");
                    r.setRoom_id(roomID);
                    int hotelID = rsRooms.getInt("RoomHotelID");
                    r.setHotel_id(hotelID);
                    // TODO roomCategory
                    /*
                    int roomCapacity = rsRooms.getInt("RoomCapacity");
                    r.setRoom_capacity(roomCapacity);
                       */
                    double roomPriceMultiplier = rsRooms.getDouble("RoomPriceMultiplier");
                    r.setRoom_price_multiplier(roomPriceMultiplier);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in SQL Selectall()");
            System.out.println(e.getMessage());
        }
        return null;
    }
}
