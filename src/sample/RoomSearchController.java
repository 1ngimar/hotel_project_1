package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

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
    private TableView<Room> roomTableView;

    private ObservableList<Hotel> searchResult = FXCollections.observableArrayList();
    private Hotel selectedHotel;





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //hotelListView.setAccessibleRole(AccessibleRole.TABLE_VIEW);
        AppState state = AppState.getInstance();
        searchResult = state.getSearchResult(); //Getting the observable list of hotels in searchResult
        AppState state2 = AppState.getInstance();
        selectedHotel = state2.getSelectedHotel(); //Getting

        // Fill all labels in the scene
        hotelNameLabel.setText(selectedHotel.getHotel_name());
        String hotelAddressString = selectedHotel.getHotel_address();
        String hotelLocationString = selectedHotel.getHotel_location();
        String hotelPostalCodeString = String.valueOf(selectedHotel.getHotel_postal_code());
        hotelAddressLabel.setText(hotelAddressString + ", " + hotelPostalCodeString + " " + hotelLocationString);
        String hotelPhoneNumberString = String.valueOf(selectedHotel.getHotel_phone_number());
        hotelPhoneNumberLabel.setText(hotelPhoneNumberString);

        



    }


}
