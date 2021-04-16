package sample;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HotelBookingController implements Initializable {
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
    @FXML
    private Button goBackButton;

    private ObservableList<Room> availableRoomsForSelectedHotel = FXCollections.observableArrayList();
    private ObservableList<Hotel> searchResult = FXCollections.observableArrayList();
    private Hotel selectedHotel;
    private ObservableList<Room> newRoomList = FXCollections.observableArrayList();
    private LocalDate arrDate;
    private LocalDate depDate;
    private ArrayList<Room> bookingRooms = new ArrayList<>();
    private NonUIHotelSearchController nonUIHotelSearchController = new NonUIHotelSearchController();
    private int numOfGuests;

    @Override

    public void initialize(URL location, ResourceBundle resources) {
        // Allow user to go back to previous scene
        goBackButton.setOnAction(e -> goBack(e));
        AppState state = AppState.getInstance();
        searchResult = state.getSearchResult(); //Getting the observable list of hotels in searchResult
        selectedHotel = state.getSelectedHotel(); //Getting the selected hotel
        availableRoomsForSelectedHotel = state.getAvailableRoomsForSelectedHotel(); // getting the available rooms for the selected hotel
        arrDate = state.getArrDate();
        depDate = state.getDepDate();
        numOfGuests = state.getNumOfGuests();

        // Fill all labels in the scene
        hotelNameLabel.setText(selectedHotel.getHotel_name());
        String hotelAddressString = selectedHotel.getHotel_address();
        String hotelLocationString = selectedHotel.getHotel_location();
        String hotelPostalCodeString = String.valueOf(selectedHotel.getHotel_postal_code());
        hotelAddressLabel.setText(hotelAddressString + ", " + hotelPostalCodeString + " " + hotelLocationString);
        String hotelPhoneNumberString = String.valueOf(selectedHotel.getHotel_phone_number());
        hotelPhoneNumberLabel.setText(hotelPhoneNumberString);
        roomTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        roomTableView.setEditable(true);
        tegundColumn.setCellValueFactory(new PropertyValueFactory("room_category"));//return gildið í Room.java fyrir getRoom_category
        rumarColumn.setCellValueFactory(new PropertyValueFactory("room_capacity"));
        verdColumn.setCellValueFactory(new PropertyValueFactory("room_price"));
        amenColumn.setCellValueFactory(new PropertyValueFactory("roomAmenityString"));
        // Checkbox column
        checkBoxColumn.setCellValueFactory(new PropertyValueFactory<Room, Boolean>("isChecked"));
        checkBoxColumn.setEditable(true);
        // Populate newRoomList with available rooms
        initializeRoomListForSelectedHotel();

        checkBoxColumn.setCellFactory(CheckBoxTableCell.forTableColumn(new Callback<Integer, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(Integer itemIndex) {
                BooleanProperty observable = new SimpleBooleanProperty();

                observable.addListener((obs, wasSelected, isNowSelected) -> {
                    Room currentRoom = newRoomList.get(itemIndex);
                    currentRoom.setIsChecked(new SimpleBooleanProperty(isNowSelected));
                    System.out.println("Checkbox for " + itemIndex + " changed from " + wasSelected + "  to " + isNowSelected);
                    System.out.println(currentRoom.getIsChecked().getValue());
                });

                return observable;
            }
        }));

        bookingButton.setOnAction(event -> {
            for (Room room : newRoomList) {
                // TODO store in database
                if (room.getIsChecked() != null) {
                    bookingRooms.add(room);
                }
                System.out.println(room.getIsChecked());
            }
            nonUIHotelSearchController.createNewBooking(selectedHotel, loggedInUser, arrDate, depDate, bookingRooms, numOfGuests);
        });


        //TODO -------------------------------------------------Siggi bað um þetta ----------------------------------
        //DataFactory dataFactory = new DataFactory();
        HotelDatabaseManager databaseManager = new HotelDatabaseManager();
        ObservableList<User> allUsers = databaseManager.getUsers();
        loggedInUser = allUsers.get(0);
        //TODO -------------------------------------------------Siggi bað um þetta ----------------------------------
    }

    @FXML
    private void goBack(ActionEvent e) {
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    private void initializeRoomListForSelectedHotel() {
        for (Room r : availableRoomsForSelectedHotel) {

            String roomAmenityString = createRoomAmenityString(r);
            Room newRoom = new Room(r.getRoom_category(), r.getRoom_capacity(), r.getRoom_price(), roomAmenityString);
            newRoomList.add(newRoom);
        }

        roomTableView.setItems(newRoomList);
    }

    private String createRoomAmenityString(Room r) {
        String roomAmenityString = "";
        int n = r.getRoom_amenities().length;
        // for loop to create a string out of the amenities that the room has.
        for (int i = 0; i < n; i++) {
            Room.RoomAmenities[] amen = r.getRoom_amenities();
            roomAmenityString += amen[i];
            if (i + 1 != n) {
                roomAmenityString += ", ";
            }
        }
        return roomAmenityString;
    }
}
