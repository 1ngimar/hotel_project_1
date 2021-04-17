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
    @FXML
    private Button loginUserButton;
    @FXML
    private Label userNameLabel;
    @FXML
    private Label userEmailLabel;
    @FXML
    private TextField userNameTF;
    @FXML
    private TextField userEmailTF;
    @FXML
    private Label pleaseLoginFirstLabel;

    private ObservableList<Room> availableRoomsForSelectedHotel = FXCollections.observableArrayList();
    private Hotel selectedHotel;
    private ObservableList<Room> newRoomList = FXCollections.observableArrayList();
    private LocalDate arrDate;
    private LocalDate depDate;
    private ArrayList<Room> bookingRooms = new ArrayList<>();
    private NonUIHotelSearchController nonUIHotelSearchController = new NonUIHotelSearchController();
    private int numOfGuests;
    private HotelDatabaseManager databaseManager = new HotelDatabaseManager();

    @Override

    public void initialize(URL location, ResourceBundle resources) {
        // Allow user to go back to previous scene
        goBackButton.setOnAction(e -> goBack(e));
        AppState state = AppState.getInstance();
        selectedHotel = state.getSelectedHotel(); //Getting the selected hotel
        availableRoomsForSelectedHotel = state.getAvailableRoomsForSelectedHotel(); // getting the available rooms for the selected hotel
        arrDate = state.getArrDate();
        depDate = state.getDepDate();
        numOfGuests = state.getNumOfGuests();

        bookingButton.setDisable(true);
        loginUserButton.setVisible(false);
        userEmailLabel.setVisible(false);
        userNameLabel.setVisible(false);
        userEmailTF.setVisible(false);
        userNameTF.setVisible(false);
        pleaseLoginFirstLabel.setVisible(false);

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
                    initializeLoginUserstuff();
                    Room currentRoom = newRoomList.get(itemIndex);
                    currentRoom.setIsChecked(new SimpleBooleanProperty(isNowSelected));
                });

                return observable;
            }
        }));

        loginUserButton.setOnAction(event -> {
            try {
                String userNameString = userNameTF.getText();
                String userEmailString = userEmailTF.getText();
                databaseManager.addNewUser(userNameString, userEmailString);
                bookingButton.setDisable(false);
                int userID = databaseManager.getMaxUserID();
                loggedInUser = new User(userID, userNameString, userEmailString);
            } catch (Exception e) {
                System.out.println("Error logging in");
                e.printStackTrace();
            }
        });

        bookingButton.setOnAction(event -> {
            for (Room room : newRoomList) {
                if (room.getIsChecked() != null && room.getIsChecked().getValue()) {
                    bookingRooms.add(room);
                }
            }
            HotelBooking booking = nonUIHotelSearchController.createNewBooking(selectedHotel, loggedInUser, arrDate, depDate, bookingRooms, numOfGuests);

            // Test fyrir getBookingsByUserName
            ObservableList<HotelBooking> bookingList = nonUIHotelSearchController.getBookingsByUserName(loggedInUser.getUserName());
            for (HotelBooking hb : bookingList) {
                System.out.print(hb.getBooking_user().getUserName());
                System.out.print(hb.getBooking_arr_date().toString());
                System.out.print(hb.getBooking_dep_date().toString());
                System.out.print(hb.getBooking_hotel().getHotel_name());
                System.out.print(hb.getBooking_rooms().get(0).getRoom_id());
                System.out.print(hb.getBooking_id());
                System.out.print(hb.getBooking_num_of_guests());
                System.out.println("--");
            }

            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            try {
                AppState bookingState = AppState.getInstance();
                bookingState.setBooking(booking);
                Parent root = FXMLLoader.load(getClass().getResource("BookingConfirmation.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void initializeLoginUserstuff() {
        loginUserButton.setVisible(true);
        userEmailLabel.setVisible(true);
        userNameLabel.setVisible(true);
        userEmailTF.setVisible(true);
        userNameTF.setVisible(true);
        pleaseLoginFirstLabel.setVisible(true);
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
            Room newRoom = new Room(r.getRoom_id(), r.getRoom_category(), r.getRoom_price_multiplier(),
                    r.getRoom_capacity(), r.getRoom_price(), r.getHotel_id(), r.getRoom_occupancy(),
                    r.getRoom_amenities(), roomAmenityString);
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
