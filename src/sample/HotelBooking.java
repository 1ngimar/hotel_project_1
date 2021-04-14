package sample;

import java.time.LocalDate;
import java.util.ArrayList;

public class HotelBooking {
    private int booking_id = -1;
    private Hotel booking_hotel;
    private User booking_user;
    private LocalDate booking_arr_date;
    private LocalDate booking_dep_date;
    private ArrayList<Room> booking_rooms;
    private int booking_num_of_guests;
    private boolean booking_payment_finalized;

    public HotelBooking(Hotel booking_hotel, User booking_user, LocalDate booking_arr_date, LocalDate booking_dep_date, ArrayList<Room> booking_rooms, int booking_num_of_guests, boolean booking_payment_finalized) {
        this.booking_hotel = booking_hotel;
        this.booking_user = booking_user;
        this.booking_arr_date = booking_arr_date;
        this.booking_dep_date = booking_dep_date;
        this.booking_rooms = booking_rooms;
        this.booking_num_of_guests = booking_num_of_guests;
        this.booking_payment_finalized = booking_payment_finalized;
    }

    public int getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(int booking_id) {
        this.booking_id = booking_id;
    }

    public Hotel getBooking_hotel() {
        return booking_hotel;
    }

    public void setBooking_hotel(Hotel booking_hotel) {
        this.booking_hotel = booking_hotel;
    }

    public User getBooking_user() {
        return booking_user;
    }

    public void setBooking_user(User booking_user) {
        this.booking_user = booking_user;
    }

    public LocalDate getBooking_arr_date() {
        return booking_arr_date;
    }

    public void setBooking_arr_date(LocalDate booking_arr_date) {
        this.booking_arr_date = booking_arr_date;
    }

    public LocalDate getBooking_dep_date() {
        return booking_dep_date;
    }

    public void setBooking_dep_date(LocalDate booking_dep_date) {
        this.booking_dep_date = booking_dep_date;
    }

    public ArrayList<Room> getBooking_rooms() {
        return booking_rooms;
    }

    public void setBooking_rooms(ArrayList<Room> booking_rooms) {
        this.booking_rooms = booking_rooms;
    }

    public int getBooking_num_of_guests() {
        return booking_num_of_guests;
    }

    public void setBooking_num_of_guests(int booking_num_of_guests) {
        this.booking_num_of_guests = booking_num_of_guests;
    }

    public boolean isBooking_payment_finalized() {
        return booking_payment_finalized;
    }

    public void setBooking_payment_finalized(boolean booking_payment_finalized) {
        this.booking_payment_finalized = booking_payment_finalized;
    }

    public HotelBooking() {
    }
}
