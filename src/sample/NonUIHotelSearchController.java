package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;

import static java.time.temporal.ChronoUnit.DAYS;

public class NonUIHotelSearchController {

    //Mock object of hotels
    //private DataFactory dataFactory = new DataFactory();
    private HotelDatabaseManager databaseManager = new HotelDatabaseManager();
    private ArrayList<Hotel> hotels = databaseManager.getAllHotels();
    //private ArrayList<HotelBooking> allBookingsPerRoom = databaseManager.getBookingsPerRoom();

    private ObservableList<Room> availableRoomsForSelectedHotel = FXCollections.observableArrayList();

    /**
     * Returns all hotels that are in database.
     * No parameters.
     * @return all hotels in database
     */
    /*
    public ArrayList<Hotel> getAllHotelsFromDB(){
        DataBase db = new DataBase();
        ArrayList<Hotel> AllHotels = db.getHotels();
        return AllHotels;
    }
     */

    /**
     * Return an ObservableList of hotels filtered by the parameters.
     *
     * @param hotels              An ArrayList of hotels that will be filtered.
     * @param selectedLocation    String that has to be relative to the available hotel locations
     * @param selectedArrDate     LocalDate of the arrival date (When a user will arrive to the hotel)
     * @param selectedDepDate     LocalDate of the departure date (When a user will leave the hotel)
     * @param selectedNumOfGuests Integer of the number of guests
     * @param selectedNumOfRooms  Integer of the number of rooms
     * @param threeStar           boolean, true if the hotel should have three stars, otherwise false
     * @param fourStar            boolean, true if the hotel should have four stars, otherwise false
     * @param fiveStar            boolean, true if the hotel should have five stars, otherwise false
     * @return the filtered list of hotels according to the parameters
     */
    public static ObservableList<Hotel> getHotelSearchResults(ArrayList<Hotel> hotels, String selectedLocation,
                                                              LocalDate selectedArrDate, LocalDate selectedDepDate,
                                                              int selectedNumOfGuests, int selectedNumOfRooms,
                                                              boolean threeStar, boolean fourStar, boolean fiveStar) {
        try {
            if (hotels.isEmpty() || selectedLocation.equals("")) {
                throw new NullPointerException();
            } else if (selectedArrDate.isAfter(selectedDepDate)) {
                throw new IllegalArgumentException();
            } else if (selectedArrDate.isBefore(LocalDate.now()) || selectedDepDate.isBefore(LocalDate.now())) {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ObservableList<Hotel> searchResults = FXCollections.observableArrayList();
        ArrayList<Hotel> filteredHotels = filterHotelsByLocation(hotels, selectedLocation);

        filteredHotels = filterHotelsByDatesGuestsRooms(filteredHotels, selectedArrDate, selectedDepDate,
                selectedNumOfGuests, selectedNumOfRooms);

        filteredHotels = filterHotelsByStarRating(filteredHotels, threeStar, fourStar, fiveStar);

        searchResults = FXCollections.observableArrayList(filteredHotels);
        return searchResults;
    }

    /**
     * Returns an ArrayList of hotels filtered by location
     *
     * @param hotels   An ArrayList of hotels that will be filtered.
     * @param location String that has to be relative to the available hotel locations
     * @return ArrayList of hotels filtered by location
     */
    private static ArrayList<Hotel> filterHotelsByLocation(ArrayList<Hotel> hotels, String location) {
        ArrayList<Hotel> hotelsByLocation = new ArrayList<Hotel>();

        for (Hotel hotel : hotels) {
            if (hotel.getHotel_location().equals(location)) {
                hotelsByLocation.add(hotel);
            }
        }

        if (hotelsByLocation.size() > 0) {
            return hotelsByLocation;
        }

        return hotels;
    }


    /**
     * Returns an ArrayList of hotels filtered by star-rating
     *
     * @param hotels    An ArrayList of hotels that will be filtered.
     * @param threeStar boolean, true if the hotel should have three stars, otherwise false
     * @param fourStar  boolean, true if the hotel should have four stars, otherwise false
     * @param fiveStar  boolean, true if the hotel should have five stars, otherwise false
     * @return ArrayList of hotels filtered by star-rating
     */
    private static ArrayList<Hotel> filterHotelsByStarRating(ArrayList<Hotel> hotels, boolean threeStar,
                                                             boolean fourStar, boolean fiveStar) {
        ArrayList<Hotel> starRatedHotels = new ArrayList<Hotel>();

        if (fiveStar || fourStar || threeStar) {
            for (Hotel hotel : hotels) {
                if (fiveStar && hotel.getHotel_star_rating() == Hotel.StarRating.FIVE) {
                    starRatedHotels.add(hotel);
                }

                if (fourStar && hotel.getHotel_star_rating() == Hotel.StarRating.FOUR) {
                    starRatedHotels.add(hotel);
                }

                if (threeStar && hotel.getHotel_star_rating() == Hotel.StarRating.THREE) {
                    starRatedHotels.add(hotel);
                }
            }
            return starRatedHotels;
        }
        return hotels;
    }

    /**
     * Returns an ArrayList of hotels filtered by dates, number of guests and number of rooms
     *
     * @param hotels              An ArrayList of hotels that will be filtered.
     * @param selectedArrDate     LocalDate of the arrival date (When a user will arrive to the hotel)
     * @param selectedDepDate     LocalDate of the departure date (When a user will leave the hotel)
     * @param selectedNumOfGuests Integer of the number of guests
     * @param selectedNumOfRooms  Integer of the number of rooms
     * @return ArrayList of hotels filtered by dates, number of guests and number of rooms
     */
    private static ArrayList<Hotel> filterHotelsByDatesGuestsRooms(ArrayList<Hotel> hotels, LocalDate selectedArrDate,
                                                                   LocalDate selectedDepDate, int selectedNumOfGuests,
                                                                   int selectedNumOfRooms) {
        ArrayList<Hotel> hotelsByDates = new ArrayList<Hotel>();

        for (Hotel hotel : hotels) {
            ObservableList<Room> availableRooms = filterRooms(hotel, selectedArrDate, selectedDepDate,
                    selectedNumOfGuests, selectedNumOfRooms);

            if (availableRooms != null && availableRooms.size() > 0) {
                hotelsByDates.add(hotel);
            }
        }

        return hotelsByDates;
    }

    /**
     * Returns an ObservableList of available rooms in the hotel according to the parameters
     *
     * @param hotel               Hotel that holds the rooms
     * @param selectedArrDate     LocalDate of the arrival date (When a user will arrive to the hotel)
     * @param selectedDepDate     LocalDate of the departure date (When a user will leave the hotel)
     * @param selectedNumOfGuests Integer of the number of guests
     * @param selectedNumOfRooms  Integer of the number of rooms
     * @return ObservableList of available rooms in the hotel
     */
    public static ObservableList<Room> filterRooms(Hotel hotel, LocalDate selectedArrDate, LocalDate selectedDepDate,
                                                   int selectedNumOfGuests, int selectedNumOfRooms) {
        ObservableList<Room> availableRooms = FXCollections.observableArrayList();
        ArrayList<Room> roomList = hotel.getHotel_room_list();
        int hotelCapacity = 0;


        // Make a list of available rooms
        for (Room room : roomList) {
            ArrayList<ArrayList<LocalDate>> roomOccupancy = room.getRoom_occupancy();

            if (roomOccupancy.isEmpty()) {
                availableRooms.add(room);
            } else {
                for (ArrayList<LocalDate> occupancyDates : roomOccupancy) {
                    LocalDate arrivalDate = occupancyDates.get(0);
                    LocalDate departureDate = occupancyDates.get(1);

                    if (((selectedArrDate.isAfter(departureDate) && selectedDepDate.isAfter(departureDate)) ||
                            (selectedArrDate.isBefore(arrivalDate) && selectedDepDate.isBefore(arrivalDate)))) {
                        availableRooms.add(room);
                    }
                }
            }
        }

        for (Room room : availableRooms) {
            hotelCapacity += room.getRoom_capacity();
        }

        // Check if total room capacity of available rooms is enough for selected number of guests
        // and if number of available rooms is enough for selected number of rooms
        if (hotelCapacity < selectedNumOfGuests || availableRooms.size() < selectedNumOfRooms) {
            return null;
            //return;
        }



        /*
        // TODO útfæra skilyrði þannig að þú getir ekki leitað af herbergjum fyrir fleiri en herbergjafjöldinn rýmir
        //if ((selectedNumOfGuests > selectedNumOfRooms) || (selectedNumOfGuests % selectedNumOfRooms) )
        int tempCapacity;
        // Filter list of available rooms to list of available rooms of SINGLE category
        if (selectedNumOfGuests == selectedNumOfRooms) {
            availableRooms = filterRoomsByCategory(availableRooms, Room.RoomCategory.SINGLE);
            tempCapacity = 0;
            for (Room r: availableRooms) {
                tempCapacity += r.getRoom_capacity();
            }
            if (tempCapacity < selectedNumOfGuests){
                return null;
            }
            // Filter list of available rooms to list of available rooms of DOUBLE category
        } else if (selectedNumOfGuests / selectedNumOfRooms == 2) {
            availableRooms = filterRoomsByCategory(availableRooms, Room.RoomCategory.DOUBLE);
            tempCapacity = 0;
            //for (Room r:availableRooms) {

            //}

            // Filter list of available rooms to list of available rooms of FAMILY category
        } else if ((selectedNumOfGuests / selectedNumOfRooms == 4) || (selectedNumOfGuests / selectedNumOfRooms == 3)) {
            availableRooms = filterRoomsByCategory(availableRooms, Room.RoomCategory.FAMILY);
        }

         */

        // update the prices for the rooms
        int hotel_base_price = hotel.hotel_base_price;
        if (hotel_base_price == 0) {
            //handle this later
        }
        for (Room r : availableRooms) {
            int tempPrice = (int) (r.getRoom_price_multiplier() * hotel_base_price);
            int daysBetween = (int) (DAYS.between(selectedArrDate, selectedDepDate));
            r.setRoom_price(tempPrice * daysBetween);
        }
        return availableRooms;
    }

    /*
    public HotelBooking createNewBooking(Hotel hotel, User user, LocalDate arrDate, LocalDate depDate, ArrayList<Room> listOfRooms, int numOfGuests) {
        HotelBooking booking = new HotelBooking(hotel, user, arrDate, depDate, listOfRooms, numOfGuests);
        databaseManager.addNewBooking(booking);
        return booking;
    }
     */

    // Breytti fallinu að ofan í void því það á ekki að skila Booking, bara búa hana til
    public void createNewBooking(Hotel hotel, User user, LocalDate arrDate, LocalDate depDate, ArrayList<Room> listOfRooms, int numOfGuests) {
        HotelBooking booking = new HotelBooking(hotel, user, arrDate, depDate, listOfRooms, numOfGuests);
        databaseManager.addNewBooking(booking);
    }


    /**
     * Return an ObservableList of rooms
     * @param rooms     An ObservableList of rooms
     * @param category  Room.RoomCategory enum-objects that hold the rooms' category
     * @return ObservableList of rooms
     */
    /*
    private static ObservableList<Room> filterRoomsByCategory(ObservableList<Room> rooms, Room.RoomCategory category) {
        return rooms.stream()
                // Filter rooms by category type (SINGLE, DOUBLE or FAMILY)
                .filter((room -> room.getRoom_category() == category))
                // Convert Stream to ObservableList
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }
    */

}
