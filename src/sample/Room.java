package sample;

import java.time.LocalDate;
import java.util.ArrayList;

public class Room {
    public Room() {
    }

    enum RoomCategory {
        SINGLE,
        DOUBLE,
        FAMILY
    }

    enum RoomAmenities {
        TV,
        OCEAN_VIEW,
        BALCONY,
        REFRIGERATOR,
        ROOM_SERVICE
    }

    private int room_id;
    private int hotel_id;
    private RoomCategory room_category; // SINGLE, DOUBLE or FAMILY
    private double room_price_multiplier;
    private double room_price;
    private RoomAmenities[] room_amenities;
    private ArrayList<ArrayList<LocalDate>> room_occupancy;
    private int room_capacity;


    public Room(int room_id, RoomCategory room_category, double room_price_multiplier, RoomAmenities[] room_amenities, ArrayList<ArrayList<LocalDate>> room_occupancy, int hotel_id, RoomCategory room_capacity) {
        this.room_id = room_id;
        this.hotel_id = hotel_id;
        this.room_category = room_category;
        this.room_price_multiplier = room_price_multiplier;
        this.room_amenities = room_amenities;
        this.room_occupancy = room_occupancy;


        if (room_capacity == RoomCategory.SINGLE) {
            this.room_capacity = 1;
        } else if (room_capacity == RoomCategory.DOUBLE) {
            this.room_capacity = 2;
        } else {
            this.room_capacity = 4;
        }
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

    public RoomAmenities[] getRoom_amenities() {
        return room_amenities;
    }

    public void setRoom_amenities(RoomAmenities[] room_amenities) {
        this.room_amenities = room_amenities;
    }

    public ArrayList<ArrayList<LocalDate>> getRoom_occupancy() {
        return room_occupancy;
    }

    public void setRoom_occupancy(ArrayList<ArrayList<LocalDate>> room_occupancy) {
        this.room_occupancy = room_occupancy;
    }

    public int getRoom_capacity() {
        return room_capacity;
    }

    public void setRoom_capacity(RoomCategory room_capacity) {
        if (room_capacity == RoomCategory.SINGLE) {
            this.room_capacity = 1;
        } else if (room_capacity == RoomCategory.DOUBLE) {
            this.room_capacity = 2;
        } else {
            this.room_capacity = 4;
        }
    }

    public void setRoom_price(double hotel_base_price, double room_price_multiplier) {

        this.room_price = hotel_base_price * room_price_multiplier;
    }
}
