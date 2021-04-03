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
import java.util.stream.Collectors;


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
    private Label noResultsErrorMsg;
    @FXML
    private Label numOfGuestsLabel;
    @FXML
    private ChoiceBox numOfRooms;
    @FXML
    private Label numOfRoomsLabel;

    private DataFactory dataFactory = new DataFactory();
    private ArrayList<Hotel> hotels = dataFactory.getHotels();
    private String selectedLocation;
    private LocalDate selected_arr_date;
    private LocalDate selected_dep_date;
    private int selectedNumOfGuests;
    private int selectedNumOfRooms;
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
        int max = 30;
        ObservableList<Integer> maxGuestsList = FXCollections.observableArrayList();
        ObservableList<Integer> maxRoomList = FXCollections.observableArrayList();

        for (int i = 1; i <= max; i++) {
            maxGuestsList.add(i);
            maxRoomList.add(i);
        }

        numOfGuests.setItems(maxGuestsList);
        numOfRooms.setItems(maxRoomList);
    }

    // Resets labels colours and error messages
    public void resetErrorsAndLabels() {
        stadsetningLabel.setTextFill(Color.BLACK);
        koma_label.setTextFill(Color.BLACK);
        brottfor_label.setTextFill(Color.BLACK);
        error_label.setTextFill(Color.BLACK);
        error_label.setText("");
        noResultsErrorMsg.setVisible(false);
    }

    public void listSearchResults(MouseEvent mouseEvent) {
        try {
            // Clear list view for hotels
            hotelListView.getItems().clear();
            // Reset all labels
            resetErrorsAndLabels();

            // Validate user input
            if (validateInputs()) {
                // Filter hotels
                ArrayList<Hotel> filteredHotels = filterHotelsByLocation(hotels);
                filteredHotels = filterHotelsByDates(filteredHotels);
                filteredHotels = filterHotelsByStarRating(filteredHotels);
                // TODO færa inn virkni til að leita í hótelum eftir völdum amenities, setja upp check box í viðmóti sem birtist eftir fyrstu leit að hótelum

                searchResults = FXCollections.observableArrayList(filteredHotels);

                if (searchResults.isEmpty()) {
                    noResultsErrorMsg.setVisible(true);
                } else {
                    for (Hotel hotel : searchResults) {
                        searchResultsHotelNames.add(hotel.getHotel_name());
                        searchResultsHotelLocations.add(hotel.getHotel_location());
                    }

                    hotelListView.setItems(searchResultsHotelNames);
                }
            }
        } catch (Exception e) {
            // Global error handler.
            e.printStackTrace();
            error_label.setTextFill(Color.RED);
            error_label.setText("INTERNAL SYSTEM ERROR");
        }
    }

    // Validated user input interaction
    private boolean validateInputs() {
        boolean isValid = true;

        // Validate location checkbox
        if (afangastadir.getSelectionModel().getSelectedItem() != null) {
            //Get hotels by location
            selectedLocation = afangastadir.getSelectionModel().getSelectedItem().toString();
        } else {
            isValid = false;
            stadsetningLabel.setTextFill(Color.RED);
            // TODO breyta í error message label og restarta í resetLabels þegar búið er að bæta við
            // stadsetningLabel.setText("Engin staðsetning valin");
        }

        if (numOfGuests.getSelectionModel().getSelectedItem() != null) {
            selectedNumOfGuests = Integer.parseInt(numOfGuests.getSelectionModel().getSelectedItem().toString());
        } else {
            numOfGuestsLabel.setTextFill(Color.RED);
            isValid = false;
        }

        if (numOfRooms.getSelectionModel().getSelectedItem() != null) {
            selectedNumOfRooms = Integer.parseInt(numOfRooms.getSelectionModel().getSelectedItem().toString());
        } else {
            numOfRoomsLabel.setTextFill(Color.RED);
            isValid = false;
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

        if (dep_date_selector != null && dep_date_selector.getValue() != null) {
            selected_dep_date = dep_date_selector.getValue();
        } else {
            brottfor_label.setTextFill(Color.RED);
            // TODO breyta í error message label og restarta í resetLabels þegar búið er að bæta við
            //brottfor_label.setText("Engin brottfarar dagsetning valin");
            isValid = false;
        }

        LocalDate arrivalDate = arr_date_selector.getValue();
        LocalDate departureDate = dep_date_selector.getValue();

        if (arrivalDate == null) {
            koma_label.setTextFill(Color.RED);
        }

        if (departureDate == null) {
            brottfor_label.setTextFill(Color.RED);
        }

        if (arrivalDate != null && departureDate != null) {
            if (arrivalDate.isAfter(departureDate)) {
                koma_label.setTextFill(Color.RED);
                // TODO breyta í error message label og restarta í resetLabels þegar búið er að bæta við
                //koma_label.setText("Valin komudagsetning er á eftir valinni brottfarardagsetningu");
                isValid = false;
            }

            if (arrivalDate.isBefore(LocalDate.now())) {
                koma_label.setTextFill(Color.RED);
                // TODO breyta í error message label og restarta í resetLabels þegar búið er að bæta við
                // koma_label.setText("Valin dagsetning eru liðinn");
                isValid = false;
            }

            if (departureDate.isBefore(LocalDate.now())) {
                brottfor_label.setTextFill(Color.RED);
                // TODO breyta í error message label og restarta í resetLabels þegar búið er að bæta við
                // brottfor_label.setText("Valin dagsetning eru liðinn");
                isValid = false;
            }
        }

        // TODO útfæra skilyrði þannig að þú getir ekki leitað af herbergjum fyrir fleiri en herbergjafjöldinn rýmir
        /*
        if (selectedNumOfGuests > selectedNumOfRooms && (selectedNumOfGuests % 4 != selectedNumOfGuests)) {
        }
         */

        if (!isValid) {
            error_label.setTextFill(Color.RED);
            error_label.setText("Vinsamlegast fylltu rauða reiti");
        }

        return isValid;
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
            ObservableList<Room> availableRooms = filterRooms(hotel);

            if (availableRooms != null && availableRooms.size() > 0) {
                hotelsByDates.add(hotel);
            }
        }

        return hotelsByDates;
    }

    private ObservableList<Room> filterRooms(Hotel hotel) {
        ObservableList<Room> availableRooms = FXCollections.observableArrayList();
        ArrayList<Room> roomList = hotel.getHotel_room_list();
        int hotelCapacity = 0;
        int selectedNumOfGuests = Integer.parseInt(numOfGuests.getSelectionModel().getSelectedItem().toString());
        int selectedNumOfRooms = Integer.parseInt(numOfRooms.getSelectionModel().getSelectedItem().toString());

        // Make a list of available rooms
        for (Room room : roomList) {
            ArrayList<ArrayList<LocalDate>> roomOccupancy = room.getRoom_occupancy();

            for (ArrayList<LocalDate> occupancyDates : roomOccupancy) {
                LocalDate arrivalDate = occupancyDates.get(0);
                LocalDate departureDate = occupancyDates.get(1);

                if (((selected_arr_date.isAfter(departureDate) && selected_dep_date.isAfter(departureDate)) ||
                        (selected_arr_date.isBefore(arrivalDate) && selected_dep_date.isBefore(arrivalDate)))) {
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

    private ObservableList<Room> filterRoomsByCategory(ObservableList<Room> rooms, Room.RoomCategory category) {
        return rooms.stream()
                // Filter rooms by category type (SINGLE, DOUBLE or FAMILY)
                .filter((room -> room.getRoom_category() == category))
                // Convert Stream to ObservableList
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }
}
