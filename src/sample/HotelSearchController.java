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
    private TextField numOfGuestsTextField;
    private DataFactory dataFactory = new DataFactory();
    private ObservableList<User> users = FXCollections.observableArrayList();
    private String selectedLocation;
    private int selectedNoOfGuests;
    //private ObservableList<Hotel> hotels = FXCollections.observableArrayList();
    private String selected_location = "";
    private LocalDate selected_arr_date;
    private LocalDate selected_dep_date;
    private int selectedNumOfGuests;
    private ObservableList<Hotel> locations = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        locations = dataFactory.getLocation();
        afangastadir.setItems(locations);
    }

    public void listSearchResults(MouseEvent mouseEvent) {
        selectedNoOfGuests = Integer.parseInt(noOfGuests.getText());
        hotelListView.setItems(getNoOfGuests(selectedNoOfGuests));
        selectedLocation = afangastadir.getSelectionModel().getSelectedItem().toString();
        hotelListView.setItems(getSelectedHotels(selectedLocation));
        ArrayList<ObservableList<Hotel>> master_list = new ArrayList<>();
        error_label.setText("");

        try { //Get hotels by required search options
            try { //Get hotels by location
                selected_location = afangastadir.getSelectionModel().getSelectedItem().toString();
            } catch (NullPointerException eLocationError) {
                stadsetningLabel.setTextFill(Color.RED);
                //eLocationError.printStackTrace();
            }
            try { //Get hotels by arrival date
                selected_arr_date = arr_date_selector.getValue();
            } catch (NullPointerException eDepDateError) {
                koma_label.setTextFill(Color.RED);
                //eDepDateError.printStackTrace();
            }
            try { //Get hotels by departure date
                selected_dep_date = dep_date_selector.getValue();
            } catch (NullPointerException eDepDateError) {
                brottfor_label.setTextFill(Color.RED);
                //eDepDateError.printStackTrace();
            }

        } catch (NullPointerException e) {
            error_label.setText("Vinsamlegast fylltu rauða reiti"); //er ekki ad virka
            //System.out.println("Engin staðsetning valin!");
            //e.printStackTrace();
        }

        if (fimmStjornur.isSelected() || fjorarStjornur.isSelected() || trjarStrjornur.isSelected()) {
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
            // Add all hotels that have chosen star rating by user
            master_list.add(getHotelsByStarRating(starRatingArray));
        }

        if (!numOfGuestsTextField.getAccessibleText().equals("")) {

        }

        // // Add required search oprtions (hotels to master_list)
        if (selected_location != null && selected_arr_date != null && selected_dep_date != null) {
            //Clean up all error messages
            error_label.setText("");
            //stadsetningLabel.setTextFill(Color.BLACK);
            koma_label.setTextFill(Color.BLACK);
            brottfor_label.setTextFill(Color.BLACK);

            master_list.add(get_hotels_by_location(selected_location));


            if (!numOfGuestsTextField.getAccessibleText().equals("")) {
                try {
                    selectedNumOfGuests = Integer.parseInt(numOfGuestsTextField.getAccessibleText());
                } catch (Exception notIntExpection) {
                    //deal with this later
                }
                master_list.add(getHotelsByNumOfGuestsAndDate(selectedNumOfGuests, selected_arr_date, selected_dep_date));
            } else {
                master_list.add(getHotelsByDate(selected_arr_date, selected_dep_date));
            }


            System.out.println("trying to get correct hotels from master_list");
            ObservableList<Hotel> searchResult = getCorrectHotels(master_list);
            System.out.println(searchResult.size());

            for (int i = 0; i < searchResult.size(); i++) {
                System.out.println(searchResult.get(i));
            }

        } else {
            //error_label.setText("Vinsamlegast fylltu rauða reiti");
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


    }

    @Override
    public String toString() {
        return selected_location;
    }

    private ObservableList<String> getSelectedHotels(String location) {
        ObservableList<String> listHotels = FXCollections.observableArrayList();
        ArrayList<Hotel> hotelList = dataFactory.getHotels();
        //Setjum oll hotel med sama location og er valid i stadsetningar "drop-down" glugganum inn i leitarnidurstodur
        for(Hotel hotels: hotelList) {
            if (hotels.getHotel_location().equals(selectedLocation)) {
                listHotels.add(hotels.getHotel_name());

    private ObservableList<Hotel> get_hotels_by_location(String location) {
        ObservableList<Hotel> listHotels = FXCollections.observableArrayList();
        ArrayList<Hotel> hotel_list_from_df = dataFactory.getHotels();
        if (location.equals("")) {
            try {
                System.out.println("Engin staðsetning valin");
                //throw new Exception("No location selected");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (Hotel hotels : hotel_list_from_df) {
            if (hotels.getHotel_location().equals(selected_location)) {
                listHotels.add(hotels.getHotel());
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

    private ObservableList<Hotel> getHotelsByNumOfGuestsAndDate(int numOfGuests, LocalDate arrDate, LocalDate depDate) {
        ObservableList<Hotel> listByNumOfGuestsAndDate = FXCollections.observableArrayList();
        ArrayList<Room> roomList;
        ArrayList<Room> typeRoomList = new ArrayList<>();
        //ArrayList<ArrayList<Room>> listOfListOfRooms = dataFactory.getRooms();
        ArrayList<Hotel> hotelList = dataFactory.getHotels();
        ArrayList<LocalDate> room_occupancy;
        Boolean room_occupied = false;
        int num_of_occupied_rooms = 0;

        //selectedNumOfGuests = numOfGuestsTextField.
        for (Hotel h : hotelList) {
            roomList = h.getHotel_room_list();
            if (selectedNumOfGuests == 1) {
                for (Room r : roomList) {
                    if (r.getRoom_category() == Room.RoomCategory.SINGLE) {
                        typeRoomList.add(r);
                    }
                }
                for (Room t : typeRoomList) {
                    room_occupancy = t.getRoom_occupancy();
                    for (int k = 0; k < room_occupancy.size(); k++) {
                        if (arrDate.isAfter(room_occupancy.get(k)) || arrDate.isBefore(room_occupancy.get(k + 1)) || depDate.isAfter(room_occupancy.get(k)) || depDate.isBefore(room_occupancy.get(k + 1))) {
                            room_occupied = true;
                        }
                    }
                    if (room_occupied) {
                        num_of_occupied_rooms++;
                    }
                }
                if (num_of_occupied_rooms < typeRoomList.size()) {
                    listByNumOfGuestsAndDate.add(h);
                }
            }
            if (selectedNumOfGuests == 2) {
                for (Room r : roomList) {
                    if (r.getRoom_category() == Room.RoomCategory.DOUBLE) {
                        typeRoomList.add(r);
                    }
                }
                for (Room t : typeRoomList) {
                    room_occupancy = t.getRoom_occupancy();
                    for (int k = 0; k < room_occupancy.size(); k++) {
                        if (arrDate.isAfter(room_occupancy.get(k)) || arrDate.isBefore(room_occupancy.get(k + 1)) || depDate.isAfter(room_occupancy.get(k)) || depDate.isBefore(room_occupancy.get(k + 1))) {
                            room_occupied = true;
                        }
                    }
                    if (room_occupied) {
                        num_of_occupied_rooms++;
                    }
                }
                if (num_of_occupied_rooms < typeRoomList.size()) {
                    listByNumOfGuestsAndDate.add(h);
                }
            }
            if (selectedNumOfGuests == 3 || selectedNumOfGuests == 4) {
                for (Room r : roomList) {
                    if (r.getRoom_category() == Room.RoomCategory.FAMILY) {
                        typeRoomList.add(r);
                    }
                }
                for (Room t : typeRoomList) {
                    room_occupancy = t.getRoom_occupancy();
                    for (int k = 0; k < room_occupancy.size(); k++) {
                        if (arrDate.isAfter(room_occupancy.get(k)) || arrDate.isBefore(room_occupancy.get(k + 1)) || depDate.isAfter(room_occupancy.get(k)) || depDate.isBefore(room_occupancy.get(k + 1))) {
                            room_occupied = true;
                        }
                    }
                    if (room_occupied) {
                        num_of_occupied_rooms++;
                    }
                }
                if (num_of_occupied_rooms < typeRoomList.size()) {
                    listByNumOfGuestsAndDate.add(h);
                }
            }
        }
        return listByNumOfGuestsAndDate;
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

    private ObservableList<Hotel> getCorrectHotels(ArrayList<ObservableList<Hotel>> master_list) {
        ObservableList<Hotel> correct_hotel_list = FXCollections.observableArrayList();
        ObservableList<Hotel> list1 = FXCollections.observableArrayList();
        ObservableList<Hotel> list2 = FXCollections.observableArrayList();

        list1.addAll(master_list.get(0));
        int num_of_matches, hotel_i_id, hotel_j_id;

        for (Hotel hotel_i : list1) {
            num_of_matches = 1;
            hotel_i_id = hotel_i.getHotel_id();     // Hérna kemur alltaf villa því að hotel_i er null
            for (int j = 1; j < master_list.size(); j++) {
                list2.addAll(master_list.get(j));
                for (Hotel hotel_j : list2) {
                    hotel_j_id = hotel_j.getHotel_id();
                    if (hotel_i_id == hotel_j_id) {
                        num_of_matches++;
                    }
                }
            }
            if (num_of_matches == master_list.size()) {
                correct_hotel_list.add(hotel_i);
            }
        }
        System.out.println("Trying to return correct_hotel_list");
        System.out.println("Stærð correct listans er " + correct_hotel_list.size());
        return correct_hotel_list;
    }
}
