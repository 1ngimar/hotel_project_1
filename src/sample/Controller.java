package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
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
    //private ObservableList<Hotel> hotels = FXCollections.observableArrayList();
    private ObservableList<Hotel> locations = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //hotels  = dataFactory.getHotels();
        //hotelListView.setItems(hotels);
        locations = dataFactory.getLocation();
        afangastadir.setItems(locations);

    }


    public void listLocationHotels(MouseEvent mouseEvent) {
        Hotel selectedLocation = (Hotel) afangastadir.getSelectionModel().getSelectedItem();

        hotelListView.setItems(getSelectedHotels(selectedLocation));
    }

    private ObservableList<Hotel> getSelectedHotels(Hotel hotel) {
        ObservableList<Hotel> listHotels = FXCollections.observableArrayList();
        ArrayList<Hotel> hotelList = dataFactory.getHotels();
        for(Hotel hotels: hotelList) {
            listHotels.add(hotels.getHotel());
        }
        return listHotels;
    }
}
