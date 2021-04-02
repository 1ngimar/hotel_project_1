package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class HotelSearchController implements Initializable {
    @FXML
    private Button searchButton;
    @FXML
    private ChoiceBox afangastadir;
    @FXML
    private ChoiceBox numOfGuests;
    @FXML
    private CheckBox fimmStjornur;
    @FXML
    private CheckBox fjorarStjornur;
    @FXML
    private CheckBox trjarStrjornur;
    @FXML
    private ListView hotelListView;
    @FXML
    private Label error_label;
    @FXML
    private DatePicker arr_date_selector;
    @FXML
    private DatePicker dep_date_selector;
    @FXML
    private Label koma_label;
    @FXML
    private Label brottfor_label;
    @FXML
    private Label stadsetningLabel;
    @FXML
    private TextField numOfGuestsTextField;
    @FXML
    private Label noResultsErrorMsg;

    private DataFactory dataFactory = new DataFactory();
    private String selectedLocation;
    private LocalDate selected_arr_date;
    private LocalDate selected_dep_date;
    private int selectedNumOfGuests;
    private ObservableList<String> locations = FXCollections.observableArrayList();
    private ObservableList<Hotel> searchResults = FXCollections.observableArrayList();
    private ObservableList<String> searchResultsHotelNames = FXCollections.observableArrayList();
    private ObservableList<String> searchResultsHotelLocations = FXCollections.observableArrayList();

    @Override
    public String toString() {
        return selectedLocation;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        noResultsErrorMsg.setVisible(false);
        locations = dataFactory.getLocation();
        afangastadir.setItems(locations);
        int maxPeeps = 30;
        ObservableList<Integer> maxlist = FXCollections.observableArrayList();
        for (int i = 1; i <= maxPeeps; i++) {
            maxlist.add(i);
        }
        numOfGuests.setItems(maxlist);
    }

    public void resetLabels() {
        // Reset labels
        stadsetningLabel.setTextFill(Color.BLACK);
        koma_label.setTextFill(Color.BLACK);
        brottfor_label.setTextFill(Color.BLACK);
        error_label.setTextFill(Color.BLACK);
        error_label.setText("");
    }

    public void listSearchResults(MouseEvent mouseEvent) {
        ArrayList<Hotel> hotels = dataFactory.getHotels();

        try {
            // Clear list view for hotels
            hotelListView.getItems().clear();
            // Reset all labels
            resetLabels();
            // Validate user input
            validateInputs();

            // Filter hotels
            hotels = filterHotelsByLocation(hotels);
            hotels = filterHotelsByDates(hotels);
            hotels = filterHotelsByStarRating(hotels);
            // TODO filter hotels by number of guests
            // TODO færa inn virkni til að leita í hótelum eftir völdum amenities, setja upp check box í viðmóti sem birtist eftir fyrstu leit að hótelum


            searchResults = FXCollections.observableArrayList(hotels);

            if (hotels.isEmpty()) {
                noResultsErrorMsg.setVisible(true);
            } else {
                for (Hotel hotel : hotels) {
                    searchResultsHotelNames.add(hotel.getHotel_name());
                    searchResultsHotelLocations.add(hotel.getHotel_location());
                }
                noResultsErrorMsg.setVisible(false);
                hotelListView.setItems(searchResultsHotelNames);
            }
        } catch (Exception e) {
            error_label.setTextFill(Color.RED);
            error_label.setText(e.getMessage().toString());
        }
    }

    // Validated user input interaction
    private void validateInputs() {
        boolean isValid = true;

        if (afangastadir.getSelectionModel().getSelectedItem() != null) {
            //Get hotels by location
            selectedLocation = afangastadir.getSelectionModel().getSelectedItem().toString();
        } else {
            isValid = false;
            stadsetningLabel.setTextFill(Color.RED);
            // TODO breyta í error message label og restarta í resetLabels þegar búið er að bæta við
            // stadsetningLabel.setText("Engin staðsetning valin");
        }

        if (arr_date_selector.getValue() != null) {
            //Get hotels by arrival date
            selected_arr_date = arr_date_selector.getValue();
        } else {
            koma_label.setTextFill(Color.RED);
            // TODO breyta í error message label og restarta í resetLabels þegar búið er að bæta við
            // koma_label.setText("Engin komu dagsetning valin");
            isValid = false;
        }

        if (dep_date_selector.getValue() != null) {
            selected_dep_date = dep_date_selector.getValue();
        } else {
            brottfor_label.setTextFill(Color.RED);
            // TODO breyta í error message label og restarta í resetLabels þegar búið er að bæta við
            //brottfor_label.setText("Engin brottfarar dagsetning valin");
            isValid = false;
        }

        if (arr_date_selector.getValue().isAfter(dep_date_selector.getValue())) {
            koma_label.setTextFill(Color.RED);
            // TODO breyta í error message label og restarta í resetLabels þegar búið er að bæta við
            //koma_label.setText("Valin komudagsetning er á eftir valinni brottfarardagsetningu");
            isValid = false;
        }

        if (arr_date_selector.getValue().isBefore(LocalDate.now())) {
            koma_label.setTextFill(Color.RED);
            // TODO breyta í error message label og restarta í resetLabels þegar búið er að bæta við
            // koma_label.setText("Valin dagsetning eru liðinn");
            isValid = false;
        }

        if (dep_date_selector.getValue().isBefore(LocalDate.now())) {
            brottfor_label.setTextFill(Color.RED);
            // TODO breyta í error message label og restarta í resetLabels þegar búið er að bæta við
            // brottfor_label.setText("Valin dagsetning eru liðinn");
            isValid = false;
        }


        if (!isValid) {
            error_label.setTextFill(Color.RED);
            error_label.setText("Vinsamlegast fylltu rauða reiti");
        }
    }

    private ArrayList<Hotel> filterHotelsByStarRating(ArrayList<Hotel> hotels) {
        ArrayList<Hotel> starRatedHotels = new ArrayList<Hotel>();

        if (fimmStjornur.isSelected() || fjorarStjornur.isSelected() || trjarStrjornur.isSelected()) {
            for (Hotel hotel : hotels) {
                if (fimmStjornur.isSelected() && hotel.getHotel_star_rating() == Hotel.StarRating.FIVE) {
                    starRatedHotels.add(hotel);
                }

                if (fjorarStjornur.isSelected() && hotel.getHotel_star_rating() == Hotel.StarRating.FOUR) {
                    starRatedHotels.add(hotel);
                }

                if (trjarStrjornur.isSelected() && hotel.getHotel_star_rating() == Hotel.StarRating.THREE) {
                    starRatedHotels.add(hotel);
                }
            }

            return starRatedHotels;
        }

        return hotels;
    }

    private ArrayList<Hotel> filterHotelsByLocation(ArrayList<Hotel> hotels) {
        ArrayList<Hotel> hotelsByLocation = new ArrayList<Hotel>();

        for (Hotel hotel : hotels) {
            if (hotel.getHotel_location().equals(selectedLocation)) {
                hotelsByLocation.add(hotel);
            }
        }

        if (hotelsByLocation.size() > 0) {
            return hotelsByLocation;
        }

        return hotels;
    }

    private ArrayList<Hotel> filterHotelsByDates(ArrayList<Hotel> hotels) {
        ArrayList<Hotel> hotelsByDates = new ArrayList<Hotel>();

        for (Hotel hotel : hotels) {
            int numOfOccupiedRooms = 0;

            for (Room room : hotel.getHotel_room_list()) {
                boolean roomOccupied = false;

                ArrayList<ArrayList<LocalDate>> roomOccupancy = room.getRoom_occupancy();

                for (ArrayList<LocalDate> occupancyDates : roomOccupancy) {
                    LocalDate arrivalDate = occupancyDates.get(0);
                    LocalDate departureDate = occupancyDates.get(1);

                    // TODO logic is incorrect. Fix it
                    if ((selected_arr_date.equals(arrivalDate) || selected_arr_date.isAfter(arrivalDate)) && (selected_dep_date.equals(departureDate) || selected_dep_date.isBefore(departureDate))) {
                        roomOccupied = true;
                        break;
                    }
                }

                if (roomOccupied) {
                    numOfOccupiedRooms++;
                }
            }

            if (numOfOccupiedRooms <= hotel.getHotel_room_list().size()) {
                hotelsByDates.add(hotel);
            }
        }

        if (hotelsByDates.size() > 0) {
            return hotelsByDates;
        }

        return hotels;
    }

/*
    private ObservableList<Hotel> getHotelsByNumOfGuestsAndDate(int selectedNumOfGuests, LocalDate
            arrDate, LocalDate depDate) {
        ObservableList<Hotel> listByNumOfGuestsAndDate = FXCollections.observableArrayList();
        ArrayList<Room> roomList;
        ArrayList<Room> typeRoomList = new ArrayList<>();
        ArrayList<Hotel> hotelList = dataFactory.getHotels();
        ArrayList<LocalDate> room_occupancy;
        int hotel_capacity;
        int num_of_occupied_rooms;


        //selectedNumOfGuests = numOfGuestsTextField.
        for (Hotel h : hotelList) {
            roomList = h.getHotel_room_list();
            hotel_capacity = 0;
            num_of_occupied_rooms = 0;
            typeRoomList.clear();
            // If selected number of guests is 1 then add all rooms w/SINGLE as roomtype to masterlist
            if (selectedNumOfGuests == 1) {
                for (Room r : roomList) {
                    if (r.getRoom_category() == Room.RoomCategory.SINGLE) {
                        typeRoomList.add(r);
                    }
                }
                for (Room t : typeRoomList) {
                    room_occupancy = t.getRoom_occupancy();
                    for (int k = 0; k < room_occupancy.size(); k += 2) {
                        if (arrDate.isAfter(room_occupancy.get(k)) || arrDate.isBefore(room_occupancy.get(k + 1)) || depDate.isAfter(room_occupancy.get(k)) || depDate.isBefore(room_occupancy.get(k + 1))) {
                            num_of_occupied_rooms++;
                        }
                    }
                }
                if (num_of_occupied_rooms < typeRoomList.size()) {
                    listByNumOfGuestsAndDate.add(h);
                }
            }

            // If selected number of guests is 2 then add all rooms w/DOUBLE as roomtype to masterlist
            if (selectedNumOfGuests == 2) {
                for (Room r : roomList) {
                    if (r.getRoom_category() == Room.RoomCategory.DOUBLE) {
                        typeRoomList.add(r);
                    }
                }
                for (Room t : typeRoomList) {
                    room_occupancy = t.getRoom_occupancy();
                    int ro = room_occupancy.size();
                    for (int k = 0; k < ro; k++) {
                        if (arrDate.isAfter(room_occupancy.get(k)) || arrDate.isBefore(room_occupancy.get(k + 1)) || depDate.isAfter(room_occupancy.get(k)) || depDate.isBefore(room_occupancy.get(k + 1))) {
                            num_of_occupied_rooms++;
                        }
                    }
                    if (num_of_occupied_rooms < typeRoomList.size()) {
                        listByNumOfGuestsAndDate.add(h);
                    }
                }
            }
            // If selected number of guests is 3 to 4 then add all rooms w/FAMILY as roomtype to masterlist
            if (selectedNumOfGuests == 3 || selectedNumOfGuests == 4) {
                for (Room r : roomList) {
                    if (r.getRoom_category() == Room.RoomCategory.FAMILY) {
                        typeRoomList.add(r);
                    }
                }
                for (Room t : typeRoomList) {
                    room_occupancy = t.getRoom_occupancy();
                    for (int k = 0; k < room_occupancy.size(); k += 2) {
                        if (arrDate.isAfter(room_occupancy.get(k)) || arrDate.isBefore(room_occupancy.get(k + 1)) || depDate.isAfter(room_occupancy.get(k)) || depDate.isBefore(room_occupancy.get(k + 1))) {
                            num_of_occupied_rooms++;
                        }
                    }
                }
                if (num_of_occupied_rooms < typeRoomList.size()) {
                    listByNumOfGuestsAndDate.add(h);
                }
            }

            // If selected number of guests is more than 4 then add all rooms w/any roomtype to masterlist
            if (selectedNumOfGuests > 4) {
                for (Room t : roomList) {
                    room_occupancy = t.getRoom_occupancy();
                    if (room_occupancy.size() == 0) {
                        hotel_capacity += t.getRoom_capacity();
                    } else {
                        for (int k = 0; k < room_occupancy.size(); k += 2) {
                            if (!(arrDate.isAfter(room_occupancy.get(k)) || arrDate.isBefore(room_occupancy.get(k + 1)) || depDate.isAfter(room_occupancy.get(k)) || depDate.isBefore(room_occupancy.get(k + 1)))) {
                                hotel_capacity += t.getRoom_capacity();
                            }
                        }
                    }
                }
                if (selectedNumOfGuests <= hotel_capacity) {
                    listByNumOfGuestsAndDate.add(h);
                }
            }
        }

        return listByNumOfGuestsAndDate;
    }

 */

// TODO taka inn gildi úr boolean fylki sem var sett upp fyrir ofan og skila hótelum sem hafa öll þau amenities í boði
//private ObservableList<Hotel> getHotelsByAmenities(boolean[] selectedHotelAmenities) {

//}
    /*
    private ObservableList<Room> getRoomsByNumOfGuestsAndDate(int numOfGuests, LocalDate arrDate, LocalDate
            depDate, Hotel hotel) {
        ObservableList<Room> listByNumOfGuestsAndDate = FXCollections.observableArrayList();
        ArrayList<Room> roomList = hotel.getHotel_room_list();
        ArrayList<Room> typeRoomList = new ArrayList<>();
        ArrayList<LocalDate> room_occupancy;
        int num_of_occupied_rooms = 0;


        if (arrDate.isAfter(depDate)) {
            throw new IllegalArgumentException();
        }
        if (arrDate.isBefore(LocalDate.now()) || depDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException();
        } else {

            //selectedNumOfGuests = numOfGuestsTextField.
            // If selected number of guests is 1 then add all rooms w/SINGLE as roomtype to masterlist
            if (selectedNumOfGuests == 1) {
                for (Room r : roomList) {
                    if (r.getRoom_category() == Room.RoomCategory.SINGLE) {
                        typeRoomList.add(r);
                    }
                }
                for (Room t : typeRoomList) {
                    room_occupancy = t.getRoom_occupancy();
                    for (int k = 0; k < room_occupancy.size(); k++) {
                        if (!(arrDate.isAfter(room_occupancy.get(k)) || arrDate.isBefore(room_occupancy.get(k + 1)) || depDate.isAfter(room_occupancy.get(k)) || depDate.isBefore(room_occupancy.get(k + 1)))) {
                            listByNumOfGuestsAndDate.add(t);
                        }
                    }
                }
            }

            // If selected number of guests is 2 then add all rooms w/DOUBLE as roomtype to masterlist
            if (selectedNumOfGuests == 2) {
                for (Room r : roomList) {
                    if (r.getRoom_category() == Room.RoomCategory.DOUBLE) {
                        typeRoomList.add(r);
                    }
                }
                for (Room t : typeRoomList) {
                    room_occupancy = t.getRoom_occupancy();
                    int ro = room_occupancy.size();
                    for (int k = 0; k < ro; k++) {
                        if (!(arrDate.isAfter(room_occupancy.get(k)) || arrDate.isBefore(room_occupancy.get(k + 1)) || depDate.isAfter(room_occupancy.get(k)) || depDate.isBefore(room_occupancy.get(k + 1)))) {
                            listByNumOfGuestsAndDate.add(t);
                        }
                    }
                }
            }
            // If selected number of guests is 3 to 4 then add all rooms w/FAMILY as roomtype to masterlist
            if (selectedNumOfGuests == 3 || selectedNumOfGuests == 4) {
                for (Room r : roomList) {
                    if (r.getRoom_category() == Room.RoomCategory.FAMILY) {
                        typeRoomList.add(r);
                    }
                }
                for (Room t : typeRoomList) {
                    room_occupancy = t.getRoom_occupancy();
                    for (int k = 0; k < room_occupancy.size(); k++) {
                        if (!(arrDate.isAfter(room_occupancy.get(k)) || arrDate.isBefore(room_occupancy.get(k + 1)) || depDate.isAfter(room_occupancy.get(k)) || depDate.isBefore(room_occupancy.get(k + 1)))) {
                            listByNumOfGuestsAndDate.add(t);
                        }
                    }
                }
            }

            // If selected number of guests is more than 4 then add all rooms w/any roomtype to masterlist
            if (selectedNumOfGuests > 4) {
                for (Room t : roomList) {
                    room_occupancy = t.getRoom_occupancy();
                    for (int k = 0; k < room_occupancy.size(); k++) {
                        if (!(arrDate.isAfter(room_occupancy.get(k)) || arrDate.isBefore(room_occupancy.get(k + 1)) || depDate.isAfter(room_occupancy.get(k)) || depDate.isBefore(room_occupancy.get(k + 1)))) {
                            listByNumOfGuestsAndDate.add(t);
                        }
                    }
                }
            }
        }
        return listByNumOfGuestsAndDate;
    }
    */


}
