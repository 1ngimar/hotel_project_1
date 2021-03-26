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
    @FXML //test
    private Button user_1;
    @FXML //test
    private Button user_2;
    @FXML //test
    private Button user_3;
    @FXML //test
    private Button user_4;
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
    private DataFactory dataFactory = new DataFactory();
    private ObservableList<User> users = FXCollections.observableArrayList();
    private String selected_location = "";
    private LocalDate selected_arr_date;
    private LocalDate selected_dep_date;
    private ObservableList<Hotel> locations = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        locations = dataFactory.getLocation();
        afangastadir.setItems(locations);
    }

    public void listSearchResults(MouseEvent mouseEvent) {
        ArrayList<ObservableList<Hotel>> master_list = new ArrayList<>();
        error_label.setText("");

        try { //Get hotels by location
            System.out.println("trying to find selected location");
            selected_location = afangastadir.getSelectionModel().getSelectedItem().toString();
            System.out.println("found selected location");
            error_label.setText("");
            master_list.add(get_hotels_by_location(selected_location));
            
            //hotelListView.setItems(getSelectedHotels(selectedLocation));
        } catch (NullPointerException e) {
            System.out.println("Engin staðsetning valin!");
            error_label.setText("Vinsamlegast veldu staðsetningu"); //er ekki ad virka
            //e.printStackTrace();
        }

        try {
            System.out.println("Trying to see if user chose arrival date");
            selected_arr_date = arr_date_selector.getValue();
        }catch (NullPointerException e){
            koma_label.setTextFill(Color.RED);
        }

        try {
            selected_arr_date = dep_date_selector.getValue();
        }catch (NullPointerException e){
            koma_label.setTextFill(Color.RED);
        }

        try { //Get hotels by dates
            error_label.setText("");
            master_list.add(getHotelsByDate(selected_arr_date,selected_dep_date));
            //hotelListView.setItems(getHotelsByDate(selected_arr_date,selected_dep_date));
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        try {
            boolean[] starRatingArray = new boolean[3];
            if (fimmStjornur.isSelected()) {
                starRatingArray[0] = true;
            }
            if (fjorarStjornur.isSelected()) {
                starRatingArray[1] = true;
            }
            if (trjarStrjornur.isSelected()) {
                starRatingArray[2] = true;
            }
            //kalla hér á getHotelsByStarRating
            master_list.add(getHotelsByStarRating(starRatingArray));

        }catch (NullPointerException e){
            e.printStackTrace();
        }
        /*
        for (int i = 0; i < master_list.size(); i++) {
            ObservableList<Hotel> listToPrint = master_list.get(i);
            System.out.println("Þetta er listi númer" + i);
            for (int j = 0; j < listToPrint.size() ; j++) {
                Hotel hotelToPrint = listToPrint.get(j);
                System.out.println(hotelToPrint.getHotel_name()); //fáum alltaf villu hér!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            }
        }
        */
        try {
            System.out.println("trying to get correct hotels from master_list");
            ObservableList<Hotel> searchResult = getCorrectHotels(master_list);
            //System.out.println(searchResult.size());

            for (int i = 0; i < searchResult.size(); i++) {
                System.out.println(searchResult.get(i));
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        //ObservableList<Hotel> correct_hotels = getCorrectHotels(master_list);
    }

    @Override
    public String toString() {
        return selected_location;
    }

    private ObservableList<Hotel> get_hotels_by_location(String location) {
        ObservableList<Hotel> listHotels = FXCollections.observableArrayList();
        ArrayList<Hotel> hotel_list_from_df = dataFactory.getHotels();
        if (location.equals("")){
            try {
                System.out.println("Engin staðsetning valin");
                //throw new Exception("No location selected");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for(Hotel hotels: hotel_list_from_df) {
            if (hotels.getHotel_location().equals(selected_location)) {
                listHotels.add(hotels.getHotel());
            }
        }
        return listHotels;
    }

    private ObservableList<Hotel> getHotelsByDate(LocalDate arrDate, LocalDate depDate) {
        ObservableList<Hotel> hotelsByDate = FXCollections.observableArrayList();
        ArrayList<Hotel> hotels = dataFactory.getHotels();
        int num_of_occupied_rooms = 0;
        Boolean room_occupied = false;

        for (int i = 0; i < hotels.size(); i++) {
            num_of_occupied_rooms = 0;
            Hotel hotel = hotels.get(i);
            ArrayList<Room> roomList = hotel.getHotel_room_list();
            for (int j = 0; j < roomList.size(); j++) {
                room_occupied = false;
                Room room = roomList.get(j);
                ArrayList<LocalDate> room_occupancy = room.getRoom_occupancy();
                for (int k = 0; k < room_occupancy.size(); k += 2) {
                    if (arrDate.isAfter(room_occupancy.get(k)) || arrDate.isBefore(room_occupancy.get(k + 1)) || depDate.isAfter(room_occupancy.get(k)) || depDate.isBefore(room_occupancy.get(k + 1))) {
                        room_occupied = true;
                    }
                }
                if (room_occupied) {
                    num_of_occupied_rooms++;
                }
            }
            if (num_of_occupied_rooms < roomList.size()) {
                hotelsByDate.add(hotel);
            }
        }
        return hotelsByDate;
    }

    private ObservableList<Hotel> getHotelsByStarRating(boolean[] starRatingArray) {
        ObservableList<Hotel> listByStarRating = FXCollections.observableArrayList();
        ArrayList<Hotel> hotelListFromDF = dataFactory.getHotels();
        if (starRatingArray[0]) {
            for (Hotel hotel : hotelListFromDF) {
                if (hotel.getHotel_star_rating() == 5) {
                    listByStarRating.add(hotel);
                }
            }
        }
        if (starRatingArray[1]) {
            for (Hotel hotel : hotelListFromDF) {
                if (hotel.getHotel_star_rating() == 4) {
                    listByStarRating.add(hotel);
                }
            }
        }
        if (starRatingArray[2]) {
            for (Hotel hotel : hotelListFromDF) {
                if (hotel.getHotel_star_rating() == 3) {
                    listByStarRating.add(hotel);
                }
            }
        }
        return listByStarRating;
    }

    private ObservableList<Hotel> getCorrectHotels(ArrayList<ObservableList<Hotel>> master_list){
        ObservableList<Hotel> correct_hotel_list = null;
        //Tvöföld for-lykkja til að meta oll valin leitarskilyrdi
        ObservableList<Hotel> list1 = master_list.get(0);
        int num_of_matches = 1;
        for (int i = 0; i < list1.size(); i++) {
            Hotel hotel_i = list1.get(i);
            for (int j = 1; j < master_list.size(); j++) {
                ObservableList<Hotel> list2 = master_list.get(j);
                for (int k = 0; k < list2.size(); k++) {
                    if (hotel_i == list2.get(k)){
                        num_of_matches++;
                    }
                }
            }
            if (num_of_matches == master_list.size()) {
                correct_hotel_list.add(hotel_i);
            }
        }
        System.out.println("Trying to return correct_hotel_list");
        return correct_hotel_list;
    }
}
