package sample;

import java.util.ArrayList;

public class Hotel {
    private int hotel_id;
    private String hotel_name;
    private String hotel_location;
    private int hotel_phone_number;
    private int hotel_star_rating;
    private boolean[] hotel_amenities;
    private ArrayList<Room> hotel_room_list;
    private int hotel_type;
    private int hotel_base_price;


    public Hotel(int hotel_id, String hotel_name, String hotel_location, int hotel_phone_number, int hotel_star_rating, boolean[] hotel_amenities, ArrayList<Room> hotel_room_list, int hotel_type, int hotel_base_price) {
        this.hotel_id = hotel_id;
        this.hotel_name = hotel_name;
        this.hotel_location = hotel_location;
        this.hotel_phone_number = hotel_phone_number;
        this.hotel_star_rating = hotel_star_rating;
        this.hotel_amenities = hotel_amenities;
        this.hotel_room_list = hotel_room_list;
        this.hotel_type = hotel_type;
        this.hotel_base_price = hotel_base_price;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getHotel_location() {
        return hotel_location;
    }

    public void setHotel_location(String hotel_location) {
        this.hotel_location = hotel_location;
    }

    public int getHotel_phone_number() {
        return hotel_phone_number;
    }

    public void setHotel_phone_number(int hotel_phone_number) {
        this.hotel_phone_number = hotel_phone_number;
    }

    public int getHotel_star_rating() {
        return hotel_star_rating;
    }

    public void setHotel_star_rating(int hotel_star_rating) {
        this.hotel_star_rating = hotel_star_rating;
    }

    public boolean[] isHotel_amenities() {
        return hotel_amenities;
    }

    public void setHotel_amenities(boolean[] hotel_amenities) {
        this.hotel_amenities = hotel_amenities;
    }

    public ArrayList<Room> getHotel_room_list() {
        return hotel_room_list;
    }

    public void setHotel_room_list(ArrayList<Room> hotel_room_list) {
        this.hotel_room_list = hotel_room_list;
    }

    public int getHotel_type() {
        return hotel_type;
    }

    public void setHotel_type(int hotel_type) {
        this.hotel_type = hotel_type;
    }

    public int getHotel_base_price() {
        return hotel_base_price;
    }

    public void setHotel_base_price(int hotel_base_price) {
        this.hotel_base_price = hotel_base_price;
    }
}
