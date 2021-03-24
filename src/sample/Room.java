package sample;

import java.time.LocalDate;
import java.util.ArrayList;

public class Room {
    enum RoomCategory {
        SINGLE,
        DOUBLE,
        FAMILY
    }

    enum RoomAmmenities {
        TV,
        OCEAN_VIEW,
        BALCONY
        // TODO add ammenities
    }

    private int room_id;
    private int hotel_id;
    private RoomCategory room_category; // SINGLE, DOUBLE or FAMILY
    private double room_price_multiplier;
    private RoomAmmenities[] room_amenities;
    private ArrayList<LocalDate> room_occupancy;

    public Room(int room_id, RoomCategory room_category, double room_price_multiplier, RoomAmmenities[] room_amenities, ArrayList<LocalDate> room_occupancy, int hotel_id) {
        this.room_id = room_id;
        this.hotel_id = hotel_id;
        this.room_category = room_category;
        this.room_price_multiplier = room_price_multiplier;
        this.room_amenities = room_amenities;
        this.room_occupancy = room_occupancy;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public RoomCategory getRoom_category() {
        return room_category;
    }

    public void setRoom_category(RoomCategory room_category) {
        this.room_category = room_category;
    }

    public double getRoom_price_multiplier() {
        return room_price_multiplier;
    }

    public void setRoom_price_multiplier(double room_price_multiplier) {
        this.room_price_multiplier = room_price_multiplier;
    }

    public RoomAmmenities[] getRoom_amenities() {
        return room_amenities;
    }

    public void setRoom_amenities(RoomAmmenities[] room_amenities) {
        this.room_amenities = room_amenities;
    }

    public ArrayList<LocalDate> getRoom_occupancy() {
        return room_occupancy;
    }

    public void setRoom_occupancy(ArrayList<LocalDate> room_occupancy) {
        this.room_occupancy = room_occupancy;
    }
}
