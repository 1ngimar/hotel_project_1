package sample;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RoomSearchController implements Initializable {
    private User loggedInUser;
    @FXML
    private Label hotelNameLabel;
    @FXML
    private Label hotelAddressLabel;
    @FXML
    private Label hotelPhoneNumberLabel;
    @FXML
    private TableView roomTableView;
    @FXML
    private TableColumn checkBoxColumn;
    @FXML
    private TableColumn tegundColumn;
    @FXML
    private TableColumn rumarColumn;
    @FXML
    private TableColumn verdColumn;
    @FXML
    private TableColumn amenColumn;
    @FXML
    private Button bookingButton;

    private ObservableList<Room> availableRoomsForSelectedHotel = FXCollections.observableArrayList();
    private ObservableList<Hotel> searchResult = FXCollections.observableArrayList();
    private Hotel selectedHotel;
    private ObservableList<Room> newRoomList = FXCollections.observableArrayList();





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //hotelListView.setAccessibleRole(AccessibleRole.TABLE_VIEW);
        AppState state = AppState.getInstance();
        searchResult = state.getSearchResult(); //Getting the observable list of hotels in searchResult
        //AppState state2 = AppState.getInstance();
        selectedHotel = state.getSelectedHotel(); //Getting the selected hotel
        //AppState state3 = AppState.getInstance();
        availableRoomsForSelectedHotel = state.getAvailableRoomsForSelectedHotel(); // getting the available rooms for the selected hotel

        // Fill all labels in the scene
        hotelNameLabel.setText(selectedHotel.getHotel_name());
        String hotelAddressString = selectedHotel.getHotel_address();
        String hotelLocationString = selectedHotel.getHotel_location();
        String hotelPostalCodeString = String.valueOf(selectedHotel.getHotel_postal_code());
        hotelAddressLabel.setText(hotelAddressString + ", " + hotelPostalCodeString + " " + hotelLocationString);
        String hotelPhoneNumberString = String.valueOf(selectedHotel.getHotel_phone_number());
        hotelPhoneNumberLabel.setText(hotelPhoneNumberString);
        roomTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tegundColumn.setCellValueFactory(new PropertyValueFactory<>("room_category"));//return gildið í Room.java fyrir getRoom_category
        rumarColumn.setCellValueFactory(new PropertyValueFactory<>("room_capacity"));
        verdColumn.setCellValueFactory(new PropertyValueFactory<>("room_price_multiplier"));
        amenColumn.setCellValueFactory(new PropertyValueFactory<>("roomAmenityString"));
        //checkBoxColumn.setCellValueFactory(new PropertyValueFactory<>("checkBoxColumn"));
        initializeRoomListForSelectedHotel();

        //TODO -------------------------------------------------Siggi bað um þetta ----------------------------------
        //DataFactory dataFactory = new DataFactory();
        //loggedInUser = dataFactory.getUsers().get(0);
        //TODO -------------------------------------------------Siggi bað um þetta ----------------------------------

        //ObservableList<String> newRoomListAsStrings = getNewRoomList();

        //ObservableList<Room> newRoomList = getNewRoomList();
        //roomTableView.setItems(newRoomList);
    }
    private void initializeRoomListForSelectedHotel() {
        //ObservableList<ArrayList<String>> newRoomListAsStrings = FXCollections.observableArrayList();

        for(Room r: availableRoomsForSelectedHotel) {

            String roomAmenityString = createRoomAmenityString(r);
            Room newRoom = new Room(r.getRoom_category(),r.getRoom_capacity(),r.getRoom_price_multiplier(), roomAmenityString);
            newRoomList.add(newRoom);
        }
        roomTableView.setItems(newRoomList);
        //return newRoomList;
    }

    private String createRoomAmenityString(Room r) {
        String roomAmenityString = "";
        int n = r.getRoom_amenities().length;
        // for loop to create a string out of the amenities that the room has.
        for(int i = 0; i < n; i++) {
            Room.RoomAmenities[] amen = r.getRoom_amenities();
            roomAmenityString += amen[i];
            if (i+1 != n){
                roomAmenityString += ", ";
            }
        }
        return roomAmenityString;
    }



}
