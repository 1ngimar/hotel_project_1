package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class NonUIHotelSearchController {

    //Mock object of hotels
    private DataFactory dataFactory = new DataFactory();
    private ArrayList<Hotel> hotels = dataFactory.getHotels();

    private ObservableList<Room> availableRoomsForSelectedHotel = FXCollections.observableArrayList();

    public static ObservableList<Hotel> getHotelSearchResults(ArrayList<Hotel> hotels, String location,
                                                              LocalDate selectedArrDate, LocalDate selectedDepDate,
                                                              int selectedNumOfGuests, int selectedNumOfRooms,
                                                              boolean threeStar, boolean fourStar, boolean fiveStar) {
        try {
            if (hotels.isEmpty() || location.equals("")) {
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
        ArrayList<Hotel> filteredHotels = filterHotelsByLocation(hotels, location);

        filteredHotels = filterHotelsByDatesGuestsRooms(filteredHotels, selectedArrDate, selectedDepDate,
                selectedNumOfGuests, selectedNumOfRooms);

        filteredHotels = filterHotelsByStarRating(filteredHotels, threeStar, fourStar, fiveStar);

        searchResults = FXCollections.observableArrayList(filteredHotels);
        return searchResults;
    }

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

    private static ArrayList<Hotel> filterHotelsByStarRating(ArrayList<Hotel> hotels, boolean threeStar, boolean fourStar,
                                                             boolean fiveStar) {
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

    private static ArrayList<Hotel> filterHotelsByDatesGuestsRooms(ArrayList<Hotel> hotels, LocalDate selectedArrDate,
                                                                   LocalDate selectedDepDate, int selectedNumOfGuests,
                                                                   int selectedNumOfRooms) {
        ArrayList<Hotel> hotelsByDates = new ArrayList<Hotel>();

        for (Hotel hotel : hotels) {
            ObservableList<Room> availableRooms = filterRooms(hotel, selectedNumOfGuests, selectedNumOfRooms,
                    selectedArrDate, selectedDepDate);

            if (availableRooms != null && availableRooms.size() > 0) {
                hotelsByDates.add(hotel);
            }
        }

        return hotelsByDates;
    }

    public static ObservableList<Room> filterRooms(Hotel hotel, int selectedNumOfGuests, int selectedNumOfRooms,
                                                   LocalDate selectedArrDate, LocalDate selectedDepDate) {
        ObservableList<Room> availableRooms = FXCollections.observableArrayList();
        ArrayList<Room> roomList = hotel.getHotel_room_list();
        int hotelCapacity = 0;

        // Make a list of available rooms
        for (Room room : roomList) {
            ArrayList<ArrayList<LocalDate>> roomOccupancy = room.getRoom_occupancy();

            for (ArrayList<LocalDate> occupancyDates : roomOccupancy) {
                LocalDate arrivalDate = occupancyDates.get(0);
                LocalDate departureDate = occupancyDates.get(1);

                if (((selectedArrDate.isAfter(departureDate) && selectedDepDate.isAfter(departureDate)) ||
                        (selectedArrDate.isBefore(arrivalDate) && selectedDepDate.isBefore(arrivalDate)))) {
                    availableRooms.add(room);
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

        // Filter list of available rooms to list of available rooms of SINGLE category
        if (selectedNumOfGuests == selectedNumOfRooms) {
            availableRooms = filterRoomsByCategory(availableRooms, Room.RoomCategory.SINGLE);
            // Filter list of available rooms to list of available rooms of DOUBLE category
        } else if (selectedNumOfGuests / selectedNumOfRooms == 2) {
            availableRooms = filterRoomsByCategory(availableRooms, Room.RoomCategory.DOUBLE);
            // Filter list of available rooms to list of available rooms of FAMILY category
        } else if (selectedNumOfGuests / selectedNumOfRooms == 4) {
            availableRooms = filterRoomsByCategory(availableRooms, Room.RoomCategory.FAMILY);
        }
        return availableRooms;
    }

    private static ObservableList<Room> filterRoomsByCategory(ObservableList<Room> rooms, Room.RoomCategory category) {
        return rooms.stream()
                // Filter rooms by category type (SINGLE, DOUBLE or FAMILY)
                .filter((room -> room.getRoom_category() == category))
                // Convert Stream to ObservableList
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }


}
