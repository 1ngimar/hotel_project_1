package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;

public class DataFactory {
    public DataFactory() {
    }
    public ObservableList<User> getUsers(){
        ObservableList<User> users = FXCollections.observableArrayList();
        User user1 = new User(1,"KalliBjarna","password",8889999,"kb@hi.is","kallastræti 7");
        User user2 = new User(2,"Veddi","myPasswrd",9990000,"vsg@hi.is","Háalind 24");
        User user3 = new User(3,"SollaSæta","12345",1009999,"solla@hi.is","Hástræti 94");

        // Get all hotels
        ArrayList<Hotel> hotels = getHotels();

        // Get all rooms
        ArrayList<ArrayList<Room>> rooms = getRooms();


        // Booking for user1
        ArrayList<Booking> booking1 = new ArrayList<>();
        ArrayList<Room> booking_room_list1 = new ArrayList<>();
        // Get the rooms for this user and add them to a list for that one user
        booking_room_list1.add(rooms.get(0).get(0));
        booking1.add(new Booking(1, hotels.get(0), user1, LocalDate.of(2021,2,2),
                LocalDate.of(2021,3,3), booking_room_list1, 2, false  ));
        // end of booking for user1

        return users;
    }

    // , int hotel_type, int hotel_base_price
    public ArrayList<Hotel> getHotels(){
        // Get all rooms!!
        ArrayList<ArrayList<Room>> all_rooms = getRooms();

        // Listinn af ollum hotelunum okkar
        ArrayList<Hotel> hotels = new ArrayList<>();

        // Eitt eintak af hotel
        boolean[] h_amenities1 = {false,false,false};
        hotels.add(new Hotel(1,"Hotel Edda RVK","Reykjavík", 5550000,
                4, h_amenities1, all_rooms.get(0), 3, 10000 ));

        return hotels;

    }

    ArrayList<LocalDate> room_occupancy_setup = new ArrayList<>();
    public ArrayList<ArrayList<Room>> getRooms() {

        // Listi af listum af herbergjum
        ArrayList<ArrayList<Room>> all_rooms = new ArrayList<>();

        // Listi númer 1 af herbergjum
        ArrayList<Room> rooms_for_hotel_1 = new ArrayList<>();
        // Eitt eintak af herbergi
        boolean[] amenities1 = {false,false,false};
        rooms_for_hotel_1.add(new Room(1, 1, 1.5, amenities1, room_occupancy_setup));

        // Annad eintak af herbergi
        boolean[] amenities2 = {false,false,false};
        rooms_for_hotel_1.add(new Room(2, 2, 2.5, amenities2, room_occupancy_setup));

        // Thridja eintak af herbergi
        boolean[] amenities3 = {false,false,false};
        rooms_for_hotel_1.add(new Room(3, 2, 2.5, amenities3, room_occupancy_setup));

        // Setjum oll herbergi fyrir hotel 1 inn i adallistann
        all_rooms.add(rooms_for_hotel_1);
        return all_rooms;
    }

}
