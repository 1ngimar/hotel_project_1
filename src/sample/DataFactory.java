package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static sample.Hotel.HotelAmenities.*;
import static sample.Hotel.StarRating.*;
import static sample.Room.RoomAmenities.*;
import static sample.Room.RoomCategory.*;

public class DataFactory {
    // Create all rooms
    public ArrayList<Room> all_rooms = this.createRooms();

    public DataFactory() {
    }

    public ObservableList<User> getUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();
        User user1 = new User(1, "KalliBjarna",  "kb@hi.is");
        User user2 = new User(2, "Veddi", "vsg@hi.is");
        User user3 = new User(3, "SollaSæta","solla@hi.is");
        User user4 = new User(4, "Helga Björk", "helga@hotmail.com");
        User user5 = new User(5, "Stjáni Blái",  "stjanarinn@yahoo.com");
        User user6 = new User(6, "Gísli Súrsson", "gislisursson@gmail.com");


        // Test filter
        ArrayList<Room> hotel_one_rooms = getRoomsByHotelId(1);

        // Get all hotels
        ArrayList<Hotel> hotels = getHotels();

        // Get all rooms
        ArrayList<Room> rooms = getRooms();

        // HotelBooking for user1
        ArrayList<HotelBooking> hotelBooking1 = new ArrayList<>();

        ArrayList<Room> booking_room_list1 = new ArrayList<>();
        // Get the rooms for this user and add them to a list for that one user
        booking_room_list1.add(rooms.get(0));
        hotelBooking1.add(new HotelBooking(1, hotels.get(0), user1, LocalDate.of(2021, 4, 2),
                LocalDate.of(2021, 4, 3), booking_room_list1, 2, false));
        // end of booking for user1
        users.addAll(user1, user2, user3, user4, user5, user6);
        return users;
    }

    public ObservableList<String> getLocation() {
        ObservableList<String> locations = FXCollections.observableArrayList();
        locations.add("Reykjavík");
        locations.add("Akureyri");
        locations.add("Egilsstaðir");
        locations.add("Ísafjörður");

        return locations;
    }

    // , int hotel_type, int hotel_base_price
    public ArrayList<Hotel> getHotels() {
        // Get all rooms!!
        ArrayList<Room> all_rooms = getRooms();

        // Listinn af ollum hotelunum okkar
        ArrayList<Hotel> hotels = new ArrayList();

        // Hotel Reykjavik
        hotels.add(new Hotel(1, "Economy Hotel Reykjavik", "Reykjavík", "Þórunnartún 1", 105, 5550000,
                THREE, new Hotel.HotelAmenities[]{BREAKFAST_INCLUDED, PARKING}, getRoomsByHotelId(1), 1, 10000));

        // Hotel Reykjavik
        hotels.add(new Hotel(2, "Comfort Hotel Reykjavik", "Reykjavík", "Mýrargata 2", 102, 5550001,
                FOUR, new Hotel.HotelAmenities[]{SPA, FREE_WIFI}, getRoomsByHotelId(2), 2, 14000));

        // Hotel Egilstaðir
        hotels.add(new Hotel(3, "Economy Hotel Egilstadir", "Egilsstaðir", "Lyngás 5-7", 700, 4550000,
                THREE, new Hotel.HotelAmenities[]{BREAKFAST_INCLUDED, HANDICAP_ACCESSIBLE}, getRoomsByHotelId(3), 1, 10000));

        // Hotel Egilstaðir
        hotels.add(new Hotel(4, "Comfort Hotel Egilstadir", "Egilsstaðir", "Kaupvangur 17", 700, 4550001,
                FIVE, new Hotel.HotelAmenities[]{RESTAURANT, SPA, PARKING}, getRoomsByHotelId(4), 2, 14000));

        // Hotel Akureyri
        hotels.add(new Hotel(5, "Economy Hotel Akureyri", "Akureyri", "Þingvallastræti 23", 600, 4560000,
                THREE, new Hotel.HotelAmenities[]{FREE_WIFI}, getRoomsByHotelId(5), 1, 10000));

        // Hotel Akureyri
        hotels.add(new Hotel(6, "Comfort Hotel Akureyri", "Akureyri", "Hafnarstræti 67", 600, 4560001,
                FOUR, new Hotel.HotelAmenities[]{RESTAURANT, PARKING, FREE_WIFI}, getRoomsByHotelId(6), 2, 14000));

        // Hotel Ísafjörður
        hotels.add(new Hotel(7, "Economy Hotel Isafjordur", "Ísafjörður", "Silfurtorgi 2", 400, 4500000,
                THREE, new Hotel.HotelAmenities[]{BREAKFAST_INCLUDED, PARKING}, getRoomsByHotelId(7), 1, 10000));

        // Hotel Ísafjörður
        hotels.add(new Hotel(8, "Comfort Hotel Isafjordur", "Ísafjörður", "Mánagata 3", 400, 4500001,
                FIVE, new Hotel.HotelAmenities[]{SPA, PARKING, HANDICAP_ACCESSIBLE, FREE_WIFI}, getRoomsByHotelId(8), 2, 23000));

        return hotels;

    }

    //ArrayList<LocalDate> room_occupancy_setup = new ArrayList<>();

    public ArrayList<Room> createRooms() {
        ArrayList<Room> all_rooms = new ArrayList<>();
        ArrayList<LocalDate> room_occupancy_setup = new ArrayList<>();
        ArrayList<ArrayList<LocalDate>> room_occupancy = new ArrayList<>();
        ArrayList<LocalDate> room_occupancy_user_one = new ArrayList<>();
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
        room_occupancy_user_one.add(today);
        room_occupancy_user_one.add(tomorrow);
        room_occupancy.add(room_occupancy_user_one);

        // Economy Hotel Reykjavík
        all_rooms.add(new Room(1, SINGLE, 1, new Room.RoomAmenities[]{TV}, room_occupancy, 1, SINGLE));
        all_rooms.add(new Room(2, DOUBLE, 1.5, new Room.RoomAmenities[]{TV, REFRIGERATOR}, room_occupancy, 1, DOUBLE));
        all_rooms.add(new Room(3, FAMILY, 2, new Room.RoomAmenities[]{TV, REFRIGERATOR, BALCONY}, room_occupancy, 1, FAMILY));

        // Comfort Hotel Reykjavík
        all_rooms.add(new Room(4, SINGLE, 1, new Room.RoomAmenities[]{TV, BALCONY, ROOM_SERVICE}, room_occupancy, 2, SINGLE));
        all_rooms.add(new Room(5, DOUBLE, 1.5, new Room.RoomAmenities[]{TV, OCEAN_VIEW, ROOM_SERVICE}, room_occupancy, 2, DOUBLE));
        all_rooms.add(new Room(6, FAMILY, 2, new Room.RoomAmenities[]{TV, OCEAN_VIEW, ROOM_SERVICE}, room_occupancy, 2, FAMILY));

        // Economy Hotel Egilstaðir
        all_rooms.add(new Room(7, SINGLE, 1, new Room.RoomAmenities[]{ROOM_SERVICE}, room_occupancy, 3, SINGLE));
        all_rooms.add(new Room(8, DOUBLE, 1.5, new Room.RoomAmenities[]{BALCONY, ROOM_SERVICE}, room_occupancy, 3, DOUBLE));
        all_rooms.add(new Room(9, FAMILY, 2, new Room.RoomAmenities[]{BALCONY, ROOM_SERVICE}, room_occupancy, 3, FAMILY));

        // Comfort Hotel Egilstaðir
        all_rooms.add(new Room(10, SINGLE, 1, new Room.RoomAmenities[]{ROOM_SERVICE, OCEAN_VIEW}, room_occupancy, 4, SINGLE));
        all_rooms.add(new Room(11, DOUBLE, 1.5, new Room.RoomAmenities[]{BALCONY, OCEAN_VIEW, ROOM_SERVICE, TV}, room_occupancy, 4, DOUBLE));
        all_rooms.add(new Room(12, FAMILY, 2, new Room.RoomAmenities[]{BALCONY, OCEAN_VIEW, ROOM_SERVICE, TV}, room_occupancy, 4, FAMILY));

        // Economy Hotel Akureyri
        all_rooms.add(new Room(13, SINGLE, 1, new Room.RoomAmenities[]{TV, REFRIGERATOR}, room_occupancy, 5, SINGLE));
        all_rooms.add(new Room(14, DOUBLE, 1.5, new Room.RoomAmenities[]{TV, REFRIGERATOR, BALCONY}, room_occupancy, 5, DOUBLE));
        all_rooms.add(new Room(15, FAMILY, 2, new Room.RoomAmenities[]{TV, REFRIGERATOR, BALCONY}, room_occupancy, 5, FAMILY));

        // Comfort Hotel Akureyri
        all_rooms.add(new Room(16, SINGLE, 1, new Room.RoomAmenities[]{TV, ROOM_SERVICE, BALCONY, REFRIGERATOR}, room_occupancy, 6, SINGLE));
        all_rooms.add(new Room(17, DOUBLE, 1.5, new Room.RoomAmenities[]{TV, ROOM_SERVICE, BALCONY, REFRIGERATOR}, room_occupancy, 6, DOUBLE));
        all_rooms.add(new Room(18, FAMILY, 2, new Room.RoomAmenities[]{TV, ROOM_SERVICE, BALCONY, REFRIGERATOR}, room_occupancy, 6, FAMILY));

        // Economy Hotel Ísafjörður
        all_rooms.add(new Room(19, SINGLE, 1, new Room.RoomAmenities[]{TV, OCEAN_VIEW}, room_occupancy, 7, SINGLE));
        all_rooms.add(new Room(20, DOUBLE, 1.5, new Room.RoomAmenities[]{TV, REFRIGERATOR, OCEAN_VIEW}, room_occupancy, 7, DOUBLE));
        all_rooms.add(new Room(21, FAMILY, 2, new Room.RoomAmenities[]{TV, REFRIGERATOR, OCEAN_VIEW}, room_occupancy, 7, FAMILY));

        // Comfort Hotel Ísafjörður
        all_rooms.add(new Room(22, SINGLE, 1, new Room.RoomAmenities[]{TV, OCEAN_VIEW, ROOM_SERVICE, REFRIGERATOR}, room_occupancy, 8, SINGLE));
        all_rooms.add(new Room(23, DOUBLE, 1.5, new Room.RoomAmenities[]{TV, OCEAN_VIEW, ROOM_SERVICE, REFRIGERATOR}, room_occupancy, 8, DOUBLE));
        all_rooms.add(new Room(24, FAMILY, 2, new Room.RoomAmenities[]{TV, OCEAN_VIEW, ROOM_SERVICE, REFRIGERATOR}, room_occupancy, 8, FAMILY));

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
