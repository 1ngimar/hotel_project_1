package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class RoomSearchController implements Initializable {
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
    private ObservableList<Room> availableRooms = FXCollections.observableArrayList();
    private ObservableList<Hotel> searchResult = FXCollections.observableArrayList();
    private Hotel selectedHotel;





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //hotelListView.setAccessibleRole(AccessibleRole.TABLE_VIEW);
        AppState state = AppState.getInstance();
        searchResult = state.getSearchResult(); //Getting the observable list of hotels in searchResult
        AppState state2 = AppState.getInstance();
        selectedHotel = state2.getSelectedHotel(); //Getting
        AppState state3 = AppState.getInstance();
        availableRooms = state3.getAvailableRooms();

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
        amenColumn.setCellValueFactory(new PropertyValueFactory<>("room_amenities"));
        ObservableList<Room> newRoomList = getNewRoomList();
        roomTableView.setItems(newRoomList);
    }
    private ObservableList<Room> getNewRoomList() {
        ObservableList<Room> newRoomList = FXCollections.observableArrayList();
        for(Room r: availableRooms) {
            for(int i = 0; i < r.getRoom_amenities().length; i++) {
                Room.RoomAmenities[] amen = r.getRoom_amenities();
                System.out.println(amen[i]);
            }
            Room newRoom = new Room(r.getRoom_category(),r.getRoom_capacity(),r.getRoom_price_multiplier(), r.getRoom_amenities());
            newRoomList.add(newRoom);
        }
        return newRoomList;
    }


}
