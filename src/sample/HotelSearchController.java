package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class HotelSearchController implements Initializable {
    @FXML
    private Button searchButton;
    @FXML
    private ChoiceBox afangastadir;
    @FXML
    private CheckBox fimmStjornur;
    @FXML
    private CheckBox fjorarStjornur;
    @FXML
    private CheckBox trjarStrjornur;
    @FXML
    private ListView hotelListView;
    @FXML
    private TextField noOfGuests;
    @FXML //test
    private Button user_1;
    @FXML //test
    private Button user_2;
    @FXML //test
    private Button user_3;
    @FXML //test
    private Button user_4;
    private DataFactory dataFactory = new DataFactory();
    private ObservableList<User> users = FXCollections.observableArrayList();
    private String selectedLocation;
    private int selectedNoOfGuests;
    //private ObservableList<Hotel> hotels = FXCollections.observableArrayList();
    private ObservableList<Hotel> locations = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //hotels  = dataFactory.getHotels();
        //hotelListView.setItems(hotels);
        locations = dataFactory.getLocation();
        afangastadir.setItems(locations);

    }


    public void listSearchResults(MouseEvent mouseEvent) {
        selectedNoOfGuests = Integer.parseInt(noOfGuests.getText());
        hotelListView.setItems(getNoOfGuests(selectedNoOfGuests));
        selectedLocation = afangastadir.getSelectionModel().getSelectedItem().toString();
        hotelListView.setItems(getSelectedHotels(selectedLocation));
    }

    @Override
    public String toString() {
        return selectedLocation;
    }

    private ObservableList<String> getSelectedHotels(String location) {
        ObservableList<String> listHotels = FXCollections.observableArrayList();
        ArrayList<Hotel> hotelList = dataFactory.getHotels();
        //Setjum oll hotel med sama location og er valid i stadsetningar "drop-down" glugganum inn i leitarnidurstodur
        for(Hotel hotels: hotelList) {
            if (hotels.getHotel_location().equals(selectedLocation)) {
                listHotels.add(hotels.getHotel_name());
            }
        }
        return listHotels;
    }

    private ObservableList<Room> getNoOfGuests(int noOfGuests) {
        ObservableList<String> hotelsByNoOfGuests = FXCollections.observableArrayList();
        ArrayList<ArrayList<Room>> roomList = dataFactory.getRooms();
        ArrayList<Hotel> hotelRoomList = dataFactory.getHotels();
        for(Hotel hotels: hotelRoomList) {
            for (Room rooms : roomList) {
                if (rooms.getRoom_capacity() == selectedNoOfGuests){
                    hotelsByNoOfGuests.add(hotels.getHotel_name());
                }

            }
        }
    }

}
