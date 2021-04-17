package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
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
    private Label noResultsErrorMsg;
    @FXML
    private Label numOfGuestsLabel;
    @FXML
    private ChoiceBox numOfRooms;
    @FXML
    private Label numOfRoomsLabel;


    private HotelDatabaseManager databaseManager = new HotelDatabaseManager();
    private ArrayList<Hotel> hotels = databaseManager.getAllHotels();
    private String selectedLocation;
    private LocalDate selected_arr_date;
    private LocalDate selected_dep_date;
    private int selectedNumOfGuests;
    private int selectedNumOfRooms;
    private ObservableList<String> locations = FXCollections.observableArrayList();
    private ObservableList<Hotel> searchResults = FXCollections.observableArrayList();
    private ObservableList<String> searchResultsHotelNames = FXCollections.observableArrayList();
    private ObservableList<String> searchResultsHotelLocations = FXCollections.observableArrayList();
    private Hotel selectedHotel;
    private ObservableList<Room> availableRooms = FXCollections.observableArrayList();
    private boolean fiveStar, fourStar, threeStar;
    private NonUIHotelSearchController nonUIHotelSearchController = new NonUIHotelSearchController();

    @Override
    public String toString() {
        return selectedLocation;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        noResultsErrorMsg.setVisible(false);
        locations = databaseManager.getLocations();
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
        numOfGuestsLabel.setTextFill(Color.BLACK);
        numOfRoomsLabel.setTextFill(Color.BLACK);
        error_label.setTextFill(Color.BLACK);
        error_label.setText("");

        try { //Get hotels by required search options
            //Get hotels by location
            if (afangastadir.getSelectionModel().getSelectedItem() != null && arr_date_selector.getValue() != null && dep_date_selector.getValue() != null) {
                selectedLocation = afangastadir.getSelectionModel().getSelectedItem().toString();
                //Get hotels by arrival date
                selected_arr_date = arr_date_selector.getValue();
                //Get hotels by departure date
                selected_dep_date = dep_date_selector.getValue();
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            if (afangastadir.getSelectionModel().getSelectedItem() != null) {
                stadsetningLabel.setTextFill(Color.BLACK);
            } else {
                stadsetningLabel.setTextFill(Color.RED);
            }
            if (arr_date_selector.getValue() != null) {
                koma_label.setTextFill(Color.BLACK);
            } else {
                koma_label.setTextFill(Color.RED);
            }
            if (dep_date_selector.getValue() != null) {
                brottfor_label.setTextFill(Color.BLACK);
            } else {
                brottfor_label.setTextFill(Color.RED);
            }
            error_label.setText("Vinsamlegast fylltu rauða reiti");
        }

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

                //Test for NonUIHotelSearchController

                if (fimmStjornur.isSelected()) {
                    fiveStar = true;
                }
                if (fjorarStjornur.isSelected()) {
                    fourStar = true;
                }
                if (trjarStrjornur.isSelected()) {
                    threeStar = true;
                }

                searchResults = nonUIHotelSearchController.getHotelSearchResults(
                        selectedLocation, selected_arr_date, selected_dep_date, selectedNumOfGuests, selectedNumOfRooms,
                        threeStar, fourStar, fiveStar);
                fiveStar = false;
                fourStar = false;
                threeStar = false;

                for (Hotel hotel : searchResults) {
                    searchResultsHotelNames.add(hotel.getHotel_name());
                    searchResultsHotelLocations.add(hotel.getHotel_location());
                }
                hotelListView.setItems(searchResultsHotelNames);


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
            // Valin komudagsetning er á eftir valinni brottfarardagsetningu
            if (arrivalDate.isAfter(departureDate)) {
                koma_label.setTextFill(Color.RED);
                isValid = false;
            }

            // Valin dagsetning er liðin
            if (arrivalDate.isBefore(LocalDate.now())) {
                koma_label.setTextFill(Color.RED);
                isValid = false;
            }

            // Valin dagsetning er liðin
            if (departureDate.isBefore(LocalDate.now())) {
                brottfor_label.setTextFill(Color.RED);
                isValid = false;
            }
        }

        if (!isValid) {
            error_label.setTextFill(Color.RED);
            error_label.setText("Villa í rauðum reit/um");
        }

        return isValid;
    }


    public Hotel getHotelByName(String hotelName, ObservableList<Hotel> hotelList) {
        for (Hotel h : hotelList) {
            if (hotelName.equals(h.getHotel_name())) {
                return h;
            }
        }
        return null;
    }

    @FXML
    public void getSelectedHotel(MouseEvent clickOnHotel) {
        String selectedHotelName = (String) hotelListView.getSelectionModel().getSelectedItem();
        selectedHotel = getHotelByName(selectedHotelName, searchResults);

        Node node = (Node) clickOnHotel.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        try {


            availableRooms = NonUIHotelSearchController.filterRooms(selectedHotel, selected_arr_date, selected_dep_date, selectedNumOfGuests, selectedNumOfRooms);

            AppState state = AppState.getInstance();
            state.setSearchResult(searchResults);
            state.setSelectedHotel(selectedHotel);
            state.setAvailableRoomsForSelectedHotel(availableRooms);
            state.setArrDate(selected_arr_date);
            state.setDepDate(selected_dep_date);
            state.setNumOfGuests(selectedNumOfGuests);

            Parent root = FXMLLoader.load(getClass().getResource("RoomSearch.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
