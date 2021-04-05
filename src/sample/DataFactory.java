package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;

import static sample.Hotel.HotelAmenities.*;
import static sample.Room.RoomAmenities.*;
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

    public ObservableList<String> getLocation() {
        ObservableList<String> locations = FXCollections.observableArrayList();
        String location1 = "Reykjavík";
        String location2 = "Akureyri";
        String location3 = "Egilsstaðir";
        String location4 = "Ísafjörður";

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
        hotels.add(new Hotel(1, "Economy Hotel Reykjavik", "Reykjavík", 5550000,
                2, new Hotel.HotelAmenities[]{BREAKFAST_INCLUDED, PARKING}, getRoomsByHotelId(1), 1, 10000));

        // Hotel Reykjavik
        hotels.add(new Hotel(2, "Comfort Hotel Reykjavik", "Reykjavík", 5550001,
                4, new Hotel.HotelAmenities[]{SPA, FREE_WIFI}, getRoomsByHotelId(2), 2, 10000));

        // Hotel Egilstaðir
        hotels.add(new Hotel(3, "Economy Hotel Egilstadir", "Egilsstaðir", 4550000,
                1, new Hotel.HotelAmenities[]{BREAKFAST_INCLUDED, HANDICAP_ACCESSIBLE}, getRoomsByHotelId(3), 1, 10000));

        // Hotel Egilstaðir
        hotels.add(new Hotel(4, "Comfort Hotel Egilstadir", "Egilsstaðir", 4550001,
                5, new Hotel.HotelAmenities[]{RESTAURANT, SPA, PARKING}, getRoomsByHotelId(4), 2, 10000));

        // Hotel Akureyri
        hotels.add(new Hotel(5, "Economy Hotel Akureyri", "Akureyri", 4560000,
                3, new Hotel.HotelAmenities[]{FREE_WIFI}, getRoomsByHotelId(5), 1, 10000));

        // Hotel Akureyri
        hotels.add(new Hotel(6, "Comfort Hotel Akureyri", "Akureyri", 4560001,
                4, new Hotel.HotelAmenities[]{RESTAURANT, PARKING, FREE_WIFI}, getRoomsByHotelId(6), 2, 10000));

        // Hotel Ísafjörður
        hotels.add(new Hotel(7, "Economy Hotel Isafjordur", "Ísafjörður", 4500000,
                3, new Hotel.HotelAmenities[]{BREAKFAST_INCLUDED, PARKING}, getRoomsByHotelId(7), 1, 10000));

        // Hotel Ísafjörður
        hotels.add(new Hotel(8, "Comfort Hotel Isafjordur", "Ísafjörður", 4500001,
                5, new Hotel.HotelAmenities[]{SPA, PARKING, HANDICAP_ACCESSIBLE, FREE_WIFI}, getRoomsByHotelId(8), 2, 10000));

        return hotels;

    }

    //ArrayList<LocalDate> room_occupancy_setup = new ArrayList<>();

    public ArrayList<Room> createRooms() {
        ArrayList<Room> all_rooms = new ArrayList<>();
        ArrayList<LocalDate> room_occupancy_setup = new ArrayList<>();

        // Economy Hotel Reykjavík
        all_rooms.add(new Room(1, SINGLE, 1.5, new Room.RoomAmenities[]{TV}, room_occupancy_setup, 1, 1));
        all_rooms.add(new Room(2, DOUBLE, 1.5, new Room.RoomAmenities[]{TV, REFRIGERATOR}, room_occupancy_setup, 1, 2));
        all_rooms.add(new Room(3, FAMILY, 1.5, new Room.RoomAmenities[]{TV, REFRIGERATOR, BALCONY}, room_occupancy_setup, 1, 4));

        // Comfort Hotel Reykjavík
        all_rooms.add(new Room(4, SINGLE, 1.5, new Room.RoomAmenities[]{TV, BALCONY, ROOM_SERVICE}, room_occupancy_setup, 2, 1));
        all_rooms.add(new Room(5, DOUBLE, 1.5, new Room.RoomAmenities[]{TV, OCEAN_VIEW, ROOM_SERVICE}, room_occupancy_setup, 2, 2));
        all_rooms.add(new Room(6, FAMILY, 1.5, new Room.RoomAmenities[]{TV, OCEAN_VIEW, ROOM_SERVICE}, room_occupancy_setup, 2, 4));

        // Economy Hotel Egilstaðir
        all_rooms.add(new Room(7, SINGLE, 1.5, new Room.RoomAmenities[]{ROOM_SERVICE}, room_occupancy_setup, 3, 1));
        all_rooms.add(new Room(8, DOUBLE, 1.5, new Room.RoomAmenities[]{BALCONY, ROOM_SERVICE}, room_occupancy_setup, 3, 2));
        all_rooms.add(new Room(9, FAMILY, 1.5, new Room.RoomAmenities[]{BALCONY, ROOM_SERVICE}, room_occupancy_setup, 3, 4));

        // Comfort Hotel Egilstaðir
        all_rooms.add(new Room(10, SINGLE, 1.5, new Room.RoomAmenities[]{ROOM_SERVICE, OCEAN_VIEW}, room_occupancy_setup, 4, 1));
        all_rooms.add(new Room(11, DOUBLE, 1.5, new Room.RoomAmenities[]{BALCONY, OCEAN_VIEW, ROOM_SERVICE, TV}, room_occupancy_setup, 4, 2));
        all_rooms.add(new Room(12, FAMILY, 1.5, new Room.RoomAmenities[]{BALCONY, OCEAN_VIEW, ROOM_SERVICE, TV}, room_occupancy_setup, 4, 4));

        // Economy Hotel Akureyri
        all_rooms.add(new Room(13, SINGLE, 1.5, new Room.RoomAmenities[]{TV, REFRIGERATOR}, room_occupancy_setup, 5, 1));
        all_rooms.add(new Room(14, DOUBLE, 1.5, new Room.RoomAmenities[]{TV, REFRIGERATOR, BALCONY}, room_occupancy_setup, 5, 2));
        all_rooms.add(new Room(15, FAMILY, 1.5, new Room.RoomAmenities[]{TV, REFRIGERATOR, BALCONY}, room_occupancy_setup, 5, 4));

        // Comfort Hotel Akureyri
        all_rooms.add(new Room(16, SINGLE, 1.5, new Room.RoomAmenities[]{TV, ROOM_SERVICE, BALCONY, REFRIGERATOR}, room_occupancy_setup, 6, 1));
        all_rooms.add(new Room(17, DOUBLE, 1.5, new Room.RoomAmenities[]{TV, ROOM_SERVICE, BALCONY, REFRIGERATOR}, room_occupancy_setup, 6, 2));
        all_rooms.add(new Room(18, FAMILY, 1.5, new Room.RoomAmenities[]{TV, ROOM_SERVICE, BALCONY, REFRIGERATOR}, room_occupancy_setup, 6, 4));

        // Economy Hotel Ísafjörður
        all_rooms.add(new Room(19, SINGLE, 1.5, new Room.RoomAmenities[]{TV, OCEAN_VIEW}, room_occupancy_setup, 7, 1));
        all_rooms.add(new Room(20, DOUBLE, 1.5, new Room.RoomAmenities[]{TV, REFRIGERATOR, OCEAN_VIEW}, room_occupancy_setup, 7, 2));
        all_rooms.add(new Room(21, FAMILY, 1.5, new Room.RoomAmenities[]{TV, REFRIGERATOR, OCEAN_VIEW}, room_occupancy_setup, 7, 4));

        // Comfort Hotel Ísafjörður
        all_rooms.add(new Room(22, SINGLE, 1.5, new Room.RoomAmenities[]{TV, OCEAN_VIEW, ROOM_SERVICE, REFRIGERATOR}, room_occupancy_setup, 8, 1));
        all_rooms.add(new Room(23, DOUBLE, 1.5, new Room.RoomAmenities[]{TV, OCEAN_VIEW, ROOM_SERVICE, REFRIGERATOR}, room_occupancy_setup, 8, 2));
        all_rooms.add(new Room(24, FAMILY, 1.5, new Room.RoomAmenities[]{TV, OCEAN_VIEW, ROOM_SERVICE, REFRIGERATOR}, room_occupancy_setup, 8, 4));

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
