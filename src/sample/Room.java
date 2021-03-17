package sample;

import java.time.LocalDate;
import java.util.ArrayList;

public class Room {
    private int room_id;
    private int room_category; // economy, comfort
    private double room_price_multiplier;
    private boolean[] room_amenities;
    private ArrayList<LocalDate> room_occupancy;

    public Room(int room_id, int room_category, double room_price_multiplier, boolean[] room_amenities, ArrayList<LocalDate> room_occupancy) {
        this.room_id = room_id;
        this.room_category = room_category;
        this.room_price_multiplier = room_price_multiplier;
        this.room_amenities = room_amenities;
        this.room_occupancy = room_occupancy;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getRoom_category() {
        return room_category;
    }

    public void setRoom_category(int room_category) {
        this.room_category = room_category;
    }

    public double getRoom_price_multiplier() {
        return room_price_multiplier;
    }

    public void setRoom_price_multiplier(double room_price_multiplier) {
        this.room_price_multiplier = room_price_multiplier;
    }

    public boolean[] getRoom_amenities() {
        return room_amenities;
    }

    public void setRoom_amenities(boolean[] room_amenities) {
        this.room_amenities = room_amenities;
    }

    public ArrayList<LocalDate> getRoom_occupancy() {
        return room_occupancy;
    }

    public void setRoom_occupancy(ArrayList<LocalDate> room_occupancy) {
        this.room_occupancy = room_occupancy;
    }
}
