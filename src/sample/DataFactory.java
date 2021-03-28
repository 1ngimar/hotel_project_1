package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;

import static sample.Room.RoomAmmenities;
import static sample.Room.RoomAmmenities.*;
import static sample.Room.RoomCategory.*;

public class DataFactory {
    // Create all rooms
    public ArrayList<Room> all_rooms = this.createRooms();

    public DataFactory() {
    }

    public ObservableList<User> getUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();
        User user1 = new User(1, "KalliBjarna", "password", 8889999, "kb@hi.is", "kallastræti 7");
        User user2 = new User(2, "Veddi", "myPasswrd", 9990000, "vsg@hi.is", "Háalind 24");
        User user3 = new User(3, "SollaSæta", "12345", 1009999, "solla@hi.is", "Hástræti 94");
        User user4 = new User(4, "Helga Björk", "egerhelga", 4443333, "helga@hotmail.com", "Reynimel 4");
        User user5 = new User(5, "Stjáni Blái", "PWmitt", 5665665, "stjanarinn@yahoo.com", "Laugateig 67");
        User user6 = new User(6, "Gísli Súrsson", "utlaginn", 8965665, "gislisursson@gmail.com", "Sæból 1");


        // Test filter
        ArrayList<Room> hotel_one_rooms = getRoomsByHotelId(1);

        for (Room room : hotel_one_rooms) {
            System.out.println(room.getRoom_id());
        }


        // Get all hotels
        ArrayList<Hotel> hotels = getHotels();

        // Get all rooms
        ArrayList<Room> rooms = getRooms();

        // Booking for user1
        ArrayList<Booking> booking1 = new ArrayList<>();

        ArrayList<Room> booking_room_list1 = new ArrayList<>();
        // Get the rooms for this user and add them to a list for that one user
        booking_room_list1.add(rooms.get(0));
        booking1.add(new Booking(1, hotels.get(0), user1, LocalDate.of(2021, 2, 2),
                LocalDate.of(2021, 3, 3), booking_room_list1, 2, false));
        // end of booking for user1

        return users;
    }

    public ObservableList<Hotel> getLocation() {
        ObservableList<Hotel> locations = FXCollections.observableArrayList();
        Hotel location1 = new Hotel("Reykjavík");
        Hotel location2 = new Hotel("Akureyri");
        Hotel location3 = new Hotel("Egilsstaðir");
        Hotel location4 = new Hotel("Ísafjörður");

        locations.add(location1);
        locations.add(location2);
        locations.add(location3);
        locations.add(location4);
        return locations;
    }

    // , int hotel_type, int hotel_base_price
    public ArrayList<Hotel> getHotels() {
        // Get all rooms!!
        ArrayList<Room> all_rooms = getRooms();

        // Listinn af ollum hotelunum okkar
        ArrayList<Hotel> hotels = new ArrayList();

        // Hotel Reykjavik
        boolean[] h_amenities1 = {false, false, false};
        hotels.add(new Hotel(1, "Hotel Edda Reykjavik_1", "Reykjavík", 5550000,
                2, h_amenities1, getRoomsByHotelId(1), 3, 10000));

        // Hotel Reykjavik
        boolean[] h_amenities2 = {false, false, false};
        hotels.add(new Hotel(2, "Hotel Icelandair Reykjavik_2", "Reykjavík", 5550001,
                4, h_amenities2, getRoomsByHotelId(2), 3, 10000));

        // Hotel Egilstaðir
        boolean[] h_amenities3 = {false, false, false};
        hotels.add(new Hotel(3, "Hotel Edda Egilstadir_1", "Egilsstaðir", 4550000,
                1, h_amenities3, getRoomsByHotelId(3), 3, 10000));

        // Hotel Egilstaðir
        boolean[] h_amenities4 = {false, false, false};
        hotels.add(new Hotel(4, "Hotel Icelandair Egilstadir_2", "Egilsstaðir", 4550001,
                5, h_amenities4, getRoomsByHotelId(4), 3, 10000));

        // Hotel Akureyri
        boolean[] h_amenities5 = {false, false, false};
        hotels.add(new Hotel(5, "Hotel Edda Akureyri_1", "Akureyri", 4560000,
                3, h_amenities5, getRoomsByHotelId(5), 3, 10000));

        // Hotel Akureyri
        boolean[] h_amenities6 = {false, false, false};
        hotels.add(new Hotel(6, "Hotel Icelandair Akureyri_2", "Akureyri", 4560001,
                4, h_amenities6, getRoomsByHotelId(6), 3, 10000));

        // Hotel Ísafjörður
        boolean[] h_amenities7 = {false, false, false};
        hotels.add(new Hotel(7, "Hotel Edda Isafjordur_1", "Ísafjörður", 4500000,
                3, h_amenities7, getRoomsByHotelId(7), 3, 10000));

        // Hotel Ísafjörður
        boolean[] h_amenities8 = {false, false, false};
        hotels.add(new Hotel(8, "Hotel Icelandair Isafjordur_2", "Ísafjörður", 4500001,
                5, h_amenities8, getRoomsByHotelId(8), 3, 10000));

        return hotels;

    }

    ArrayList<LocalDate> room_occupancy_setup = new ArrayList<>();

    public ArrayList<Room> createRooms() {
        ArrayList<Room> all_rooms = new ArrayList<>();

        all_rooms.add(new Room(1, SINGLE, 1.5, new RoomAmmenities[]{TV, BALCONY}, room_occupancy_setup, 1, 1));
        all_rooms.add(new Room(2, DOUBLE, 1.5, new RoomAmmenities[]{TV, OCEAN_VIEW}, room_occupancy_setup, 2, 2));
        all_rooms.add(new Room(3, FAMILY, 1.5, new RoomAmmenities[]{TV, BALCONY}, room_occupancy_setup, 1, 4));
        all_rooms.add(new Room(4, DOUBLE, 1.5, new RoomAmmenities[]{OCEAN_VIEW, BALCONY}, room_occupancy_setup, 3, 2));
        all_rooms.add(new Room(5, DOUBLE, 1.5, new RoomAmmenities[]{TV, BALCONY}, room_occupancy_setup, 2, 2));

        return all_rooms;
    }

    public ArrayList<Room> getRoomsByHotelId(int hotel_id) {
        ArrayList<Room> filtered_rooms = new ArrayList<Room>();

        for (Room room : this.all_rooms) {
            if (room.getHotel_id() == hotel_id) {
                filtered_rooms.add(room);
            }
        }

        return filtered_rooms;
    }


    public ArrayList<Room> getRooms() {
        return all_rooms;
    }

}
