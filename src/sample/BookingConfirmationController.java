package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BookingConfirmationController implements Initializable {
    @FXML
    private TableView<Room> BookedRoomsTableView;
    @FXML
    private Label BookedHotelNameLabel;
    @FXML
    private Label BookedArrDateLabel;
    @FXML
    private Label BookedDepDateLabel;
    @FXML
    private Label BookedPriceLabel;
    @FXML
    private TableColumn RoomIDColumn;
    @FXML
    private TableColumn RoomTypeColumn;
    @FXML
    private Label UserNameLabel;
    @FXML
    private Label BookingNumberLabel;

    private HotelBooking booking = new HotelBooking();
    private ArrayList<Room> bookingRooms = new ArrayList<>();
    private ObservableList<Room> roomsForTableView = FXCollections.observableArrayList();
    private String hotelName, arrDateString, depDateString, userName;
    private int totalPrice, bookingID;

    @Override

    public void initialize(URL location, ResourceBundle resources) {
        AppState bookingState = AppState.getInstance();
        booking = bookingState.getBooking();
        hotelName = booking.getBooking_hotel().getHotel_name();
        arrDateString = booking.getBooking_arr_date().toString();
        depDateString = booking.getBooking_dep_date().toString();
        userName = booking.getBooking_user().getUserName();
        bookingRooms = booking.getBooking_rooms();
        for (Room r : bookingRooms) {
            totalPrice += r.getRoom_price();
            roomsForTableView.add(r);
        }


        bookingID = booking.getBooking_id();
        BookedHotelNameLabel.setText(hotelName);
        BookedArrDateLabel.setText(arrDateString);
        BookedDepDateLabel.setText((depDateString));
        BookedPriceLabel.setText(String.valueOf(totalPrice));
        BookingNumberLabel.setText(String.valueOf(booking.getBooking_id()));
        UserNameLabel.setText(booking.getBooking_user().getUserName());
        RoomIDColumn.setCellValueFactory(new PropertyValueFactory("room_id"));
        RoomTypeColumn.setCellValueFactory(new PropertyValueFactory("room_category"));
        BookedRoomsTableView.setItems(roomsForTableView);

    }
}
