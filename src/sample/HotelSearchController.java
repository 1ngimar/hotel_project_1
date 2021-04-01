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
    private ChoiceBox numOfGuestsCB;
    @FXML
    private CheckBox fimmStjornur;
    @FXML
    private CheckBox fjorarStjornur;
    @FXML
    private CheckBox trjarStrjornur;
    @FXML
    private ListView hotelListView;
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
    @FXML
    private Label noResultsErrorMsg;

    private DataFactory dataFactory = new DataFactory();
    private ObservableList<User> users = FXCollections.observableArrayList();
    private String selectedLocation;
    private String selectedNoOfGuests = "";
    //private ObservableList<Hotel> hotels = FXCollections.observableArrayList();

    //private String selected_location = "";

    private LocalDate selected_arr_date;
    private LocalDate selected_dep_date;
    private int selectedNumOfGuests;
    private ObservableList<String> locations = FXCollections.observableArrayList();
    private ObservableList<Hotel> searchResults = FXCollections.observableArrayList();
    private ObservableList<String> searchResultsHotelNames = FXCollections.observableArrayList();
    private ObservableList<String> searchResultsHotelLocations = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        noResultsErrorMsg.setVisible(false);
        locations = dataFactory.getLocation();
        afangastadir.setItems(locations);
        int maxPeeps = 30;
        ObservableList<Integer> maxlist = FXCollections.observableArrayList();
        for (int i = 1; i <= maxPeeps; i++) {
            maxlist.add(i);
        }
        numOfGuestsCB.setItems(maxlist);
    }

    public void listSearchResults(MouseEvent mouseEvent) {
        try {
            hotelListView.getItems().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //selectedNoOfGuests = Integer.parseInt(numOfGuestsTextField.getText());
        //hotelListView.setItems(getNoOfGuests(selectedNoOfGuests));
        //selectedLocation = afangastadir.getSelectionModel().getSelectedItem().toString();
        //hotelListView.setItems(getSelectedHotels(selectedLocation));
        ArrayList<ObservableList<Hotel>> master_list = new ArrayList<>();
        error_label.setText("");
        try { //Get hotels by required search options

            //Get hotels by location
            if (afangastadir.getSelectionModel().getSelectedItem() != null && arr_date_selector.getValue() != null && dep_date_selector.getValue() != null) {
                selectedLocation = afangastadir.getSelectionModel().getSelectedItem().toString();
                //Get hotels by arrival date
                selected_arr_date = arr_date_selector.getValue();
                //Get hotels by departure date
                selected_dep_date = dep_date_selector.getValue();
            } else {
                throw new NullPointerException();
            }
        } catch (NullPointerException e) {
            if (afangastadir.getSelectionModel().getSelectedItem() != null) {
                stadsetningLabel.setTextFill(Color.BLACK);
            } else {
                stadsetningLabel.setTextFill(Color.RED);
            }
            if (arr_date_selector.getValue() != null) {
                koma_label.setTextFill(Color.BLACK);
            } else {
                koma_label.setTextFill(Color.RED);
            }
            if (dep_date_selector.getValue() != null) {
                brottfor_label.setTextFill(Color.BLACK);
            } else {
                brottfor_label.setTextFill(Color.RED);
            }
            error_label.setText("Vinsamlegast fylltu rauða reiti");
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
            //ArrayList<Hotel> startListToAddToMaster = new ArrayList<Hotel>(getHotelsByStarRating(starRatingArray));
        }

        if (selectedLocation != null && selected_arr_date != null && selected_dep_date != null) {
            //Clean up all error messages
            error_label.setText("");
            stadsetningLabel.setTextFill(Color.BLACK);
            koma_label.setTextFill(Color.BLACK);
            brottfor_label.setTextFill(Color.BLACK);

            master_list.add(get_hotels_by_location(selectedLocation));

            if (!numOfGuestsTextField.getText().equals("")) {
                try {
                    selectedNumOfGuests = Integer.parseInt(numOfGuestsTextField.getText());
                } catch (Exception notIntExpection) {
                    //deal with this later
                    notIntExpection.printStackTrace();
                }
                master_list.add(getHotelsByNumOfGuestsAndDate(selectedNumOfGuests, selected_arr_date, selected_dep_date));
            } else {
                master_list.add(getHotelsByDate(selected_arr_date, selected_dep_date));
            }


            // Get the final ("correct") search results by checking each list in master_list
            searchResults = getCorrectHotels(master_list);
            // call a function to show all the search results

            if (searchResults.isEmpty()) {
                noResultsErrorMsg.setVisible(true);
            } else {
                for (Hotel sr : searchResults) {
                    searchResultsHotelNames.add(sr.getHotel_name());
                    searchResultsHotelLocations.add(sr.getHotel_location());
                }
                noResultsErrorMsg.setVisible(false);
                hotelListView.setItems(searchResultsHotelNames);
            }


            // Show only the names of the hotels in the listView


            // delete the search results so that if the user would like to search again
            //searchResults.removeAll();
            //searchResultsHotelNames.removeAll();
            //searchResultsHotelLocations.removeAll();


        } else {
            //error_label.setText("Vinsamlegast fylltu rauða reiti");
        }
    }

    @Override
    public String toString() {
        return selectedLocation;
    }

    private ObservableList<Hotel> get_hotels_by_location(String location) {
        ObservableList<Hotel> hotelsByLocation = FXCollections.observableArrayList();
        ArrayList<Hotel> hotel_list_from_df = dataFactory.getHotels();
        if (location.equals("")) {
            System.out.println("Engin staðsetning valin");
        }

        for (Hotel h : hotel_list_from_df) {
            if (h.getHotel_location().equals(selectedLocation)) {
                hotelsByLocation.add(h);
            }
        }
        return hotelsByLocation;
    }

    private ObservableList<Hotel> getHotelsByDate(LocalDate arrDate, LocalDate depDate) {
        ObservableList<Hotel> hotelsByDate = FXCollections.observableArrayList();
        ArrayList<Hotel> hotels = dataFactory.getHotels();
        int num_of_occupied_rooms = 0;
        boolean room_occupied;

        if (arrDate.isAfter(depDate)) {
            throw new IllegalArgumentException();
        }
        if (arrDate.isBefore(LocalDate.now()) || depDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException();
        } else {

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
                if (num_of_occupied_rooms <= roomList.size()) {
                    hotelsByDate.add(hotel);
                }
            }
        }
        return hotelsByDate;
    }

    private ObservableList<Hotel> getHotelsByNumOfGuestsAndDate(int selectedNumOfGuests, LocalDate
            arrDate, LocalDate depDate) {
        ObservableList<Hotel> listByNumOfGuestsAndDate = FXCollections.observableArrayList();
        ArrayList<Room> roomList;
        ArrayList<Room> typeRoomList = new ArrayList<>();
        ArrayList<Hotel> hotelList = dataFactory.getHotels();
        ArrayList<LocalDate> room_occupancy;
        int hotel_capacity;
        int num_of_occupied_rooms;

        if (arrDate.isAfter(depDate)) {
            throw new IllegalArgumentException();
        }
        if (arrDate.isBefore(LocalDate.now()) || depDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException();
        } else {

            //selectedNumOfGuests = numOfGuestsTextField.
            for (Hotel h : hotelList) {
                roomList = h.getHotel_room_list();
                hotel_capacity = 0;
                num_of_occupied_rooms = 0;
                typeRoomList.clear();
                // If selected number of guests is 1 then add all rooms w/SINGLE as roomtype to masterlist
                if (selectedNumOfGuests == 1) {
                    for (Room r : roomList) {
                        if (r.getRoom_category() == Room.RoomCategory.SINGLE) {
                            typeRoomList.add(r);
                        }
                    }
                    for (Room t : typeRoomList) {
                        room_occupancy = t.getRoom_occupancy();
                        for (int k = 0; k < room_occupancy.size(); k += 2) {
                            if (arrDate.isAfter(room_occupancy.get(k)) || arrDate.isBefore(room_occupancy.get(k + 1)) || depDate.isAfter(room_occupancy.get(k)) || depDate.isBefore(room_occupancy.get(k + 1))) {
                                num_of_occupied_rooms++;
                            }
                        }
                    }
                    if (num_of_occupied_rooms < typeRoomList.size()) {
                        listByNumOfGuestsAndDate.add(h);
                    }
                }

                // If selected number of guests is 2 then add all rooms w/DOUBLE as roomtype to masterlist
                if (selectedNumOfGuests == 2) {
                    for (Room r : roomList) {
                        if (r.getRoom_category() == Room.RoomCategory.DOUBLE) {
                            typeRoomList.add(r);
                        }
                    }
                    for (Room t : typeRoomList) {
                        room_occupancy = t.getRoom_occupancy();
                        int ro = room_occupancy.size();
                        for (int k = 0; k < ro; k++) {
                            if (arrDate.isAfter(room_occupancy.get(k)) || arrDate.isBefore(room_occupancy.get(k + 1)) || depDate.isAfter(room_occupancy.get(k)) || depDate.isBefore(room_occupancy.get(k + 1))) {
                                num_of_occupied_rooms++;
                            }
                        }
                        if (num_of_occupied_rooms < typeRoomList.size()) {
                            listByNumOfGuestsAndDate.add(h);
                        }
                    }
                }
                // If selected number of guests is 3 to 4 then add all rooms w/FAMILY as roomtype to masterlist
                if (selectedNumOfGuests == 3 || selectedNumOfGuests == 4) {
                    for (Room r : roomList) {
                        if (r.getRoom_category() == Room.RoomCategory.FAMILY) {
                            typeRoomList.add(r);
                        }
                    }
                    for (Room t : typeRoomList) {
                        room_occupancy = t.getRoom_occupancy();
                        for (int k = 0; k < room_occupancy.size(); k += 2) {
                            if (arrDate.isAfter(room_occupancy.get(k)) || arrDate.isBefore(room_occupancy.get(k + 1)) || depDate.isAfter(room_occupancy.get(k)) || depDate.isBefore(room_occupancy.get(k + 1))) {
                                num_of_occupied_rooms++;
                            }
                        }
                    }
                    if (num_of_occupied_rooms < typeRoomList.size()) {
                        listByNumOfGuestsAndDate.add(h);
                    }
                }

                // If selected number of guests is more than 4 then add all rooms w/any roomtype to masterlist
                if (selectedNumOfGuests > 4) {
                    for (Room t : roomList) {
                        room_occupancy = t.getRoom_occupancy();
                        if (room_occupancy.size() == 0) {
                            hotel_capacity += t.getRoom_capacity();
                        } else {
                            for (int k = 0; k < room_occupancy.size(); k += 2) {
                                if (!(arrDate.isAfter(room_occupancy.get(k)) || arrDate.isBefore(room_occupancy.get(k + 1)) || depDate.isAfter(room_occupancy.get(k)) || depDate.isBefore(room_occupancy.get(k + 1)))) {
                                    hotel_capacity += t.getRoom_capacity();
                                }
                            }
                        }
                    }
                    if (selectedNumOfGuests <= hotel_capacity) {
                        listByNumOfGuestsAndDate.add(h);
                    }
                }
            }
        }
        return listByNumOfGuestsAndDate;
    }

    private ObservableList<Hotel> getHotelsByStarRating(boolean[] starRatingArray) {
        //private ArrayList<Hotel> getHotelsByStarRating(boolean[] starRatingArray) {
        ObservableList<Hotel> listByStarRating = FXCollections.observableArrayList();
        //Hotel h = new Hotel();
        //ArrayList<Hotel> listOfStarHotels = new ArrayList<>();
        ArrayList<Hotel> hotelListFromDF = dataFactory.getHotels();

        if (starRatingArray[0]) {
            for (Hotel hotel_a : hotelListFromDF) {
                if (hotel_a.getHotel_star_rating() == 5) {
                    listByStarRating.add(hotel_a);
                    //listOfStarHotels.add(hotel_a);
                }
            }
        }
        if (starRatingArray[1]) {
            for (Hotel hotel_a : hotelListFromDF) {
                if (hotel_a.getHotel_star_rating() == 4) {
                    listByStarRating.add(hotel_a);
                    //listOfStarHotels.add(hotel_a);
                }
            }
        }
        if (starRatingArray[2]) {
            for (Hotel hotel_a : hotelListFromDF) {
                if (hotel_a.getHotel_star_rating() == 3) {
                    listByStarRating.add(hotel_a);
                    //listOfStarHotels.add(hotel_a);
                }
            }
        }
        return listByStarRating;
        //return listOfStarHotels;
    }

    private ObservableList<Hotel> getCorrectHotels(ArrayList<ObservableList<Hotel>> master_list) {
        ObservableList<Hotel> correct_hotel_list = FXCollections.observableArrayList();
        ObservableList<Hotel> list1 = FXCollections.observableArrayList();
        ObservableList<Hotel> list2 = FXCollections.observableArrayList();

        list1.addAll(master_list.get(0));
        //System.out.println(list1);
        int num_of_matches, hotel_i_id, hotel_j_id;

        for (Hotel hotel_i : list1) {
            num_of_matches = 1;
            hotel_i_id = hotel_i.getHotel_id();
            for (int j = 1; j < master_list.size(); j++) {
                list2.clear();
                list2.addAll(master_list.get(j));
                for (Hotel hotel_j : list2) {
                    hotel_j_id = hotel_j.getHotel_id();
                    if (hotel_i_id == hotel_j_id) {
                        num_of_matches++;
                        break;
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

    private ObservableList<Room> getRoomsByDate(LocalDate arrDate, LocalDate depDate, Hotel hotel) {
        ObservableList<Room> roomsByDate = FXCollections.observableArrayList();
        ArrayList<Room> roomList = hotel.getHotel_room_list();

        if (arrDate.isAfter(depDate)) {
            throw new IllegalArgumentException();
        }
        if (arrDate.isBefore(LocalDate.now()) || depDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException();
        } else {
            for (Room r : roomList) {
                ArrayList<LocalDate> room_occupancy = r.getRoom_occupancy();
                for (int k = 0; k < room_occupancy.size(); k += 2) {
                    if (!(arrDate.isAfter(room_occupancy.get(k)) || arrDate.isBefore(room_occupancy.get(k + 1)) || depDate.isAfter(room_occupancy.get(k)) || depDate.isBefore(room_occupancy.get(k + 1)))) {
                        roomsByDate.add(r);
                    }
                }
            }
        }
        return roomsByDate;
    }

    private ObservableList<Room> getRoomsByNumOfGuestsAndDate(int numOfGuests, LocalDate arrDate, LocalDate depDate, Hotel hotel) {
        ObservableList<Room> listByNumOfGuestsAndDate = FXCollections.observableArrayList();
        ArrayList<Room> roomList = hotel.getHotel_room_list();
        ArrayList<Room> typeRoomList = new ArrayList<>();
        ArrayList<LocalDate> room_occupancy;
        int num_of_occupied_rooms = 0;


        if (arrDate.isAfter(depDate)) {
            throw new IllegalArgumentException();
        }
        if (arrDate.isBefore(LocalDate.now()) || depDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException();
        } else {

            //selectedNumOfGuests = numOfGuestsTextField.
            // If selected number of guests is 1 then add all rooms w/SINGLE as roomtype to masterlist
            if (selectedNumOfGuests == 1) {
                for (Room r : roomList) {
                    if (r.getRoom_category() == Room.RoomCategory.SINGLE) {
                        typeRoomList.add(r);
                    }
                }
                for (Room t : typeRoomList) {
                    room_occupancy = t.getRoom_occupancy();
                    for (int k = 0; k < room_occupancy.size(); k++) {
                        if (!(arrDate.isAfter(room_occupancy.get(k)) || arrDate.isBefore(room_occupancy.get(k + 1)) || depDate.isAfter(room_occupancy.get(k)) || depDate.isBefore(room_occupancy.get(k + 1)))) {
                            listByNumOfGuestsAndDate.add(t);
                        }
                    }
                }
            }

            // If selected number of guests is 2 then add all rooms w/DOUBLE as roomtype to masterlist
            if (selectedNumOfGuests == 2) {
                for (Room r : roomList) {
                    if (r.getRoom_category() == Room.RoomCategory.DOUBLE) {
                        typeRoomList.add(r);
                    }
                }
                for (Room t : typeRoomList) {
                    room_occupancy = t.getRoom_occupancy();
                    int ro = room_occupancy.size();
                    for (int k = 0; k < ro; k++) {
                        if (!(arrDate.isAfter(room_occupancy.get(k)) || arrDate.isBefore(room_occupancy.get(k + 1)) || depDate.isAfter(room_occupancy.get(k)) || depDate.isBefore(room_occupancy.get(k + 1)))) {
                            listByNumOfGuestsAndDate.add(t);
                        }
                    }
                }
            }
            // If selected number of guests is 3 to 4 then add all rooms w/FAMILY as roomtype to masterlist
            if (selectedNumOfGuests == 3 || selectedNumOfGuests == 4) {
                for (Room r : roomList) {
                    if (r.getRoom_category() == Room.RoomCategory.FAMILY) {
                        typeRoomList.add(r);
                    }
                }
                for (Room t : typeRoomList) {
                    room_occupancy = t.getRoom_occupancy();
                    for (int k = 0; k < room_occupancy.size(); k++) {
                        if (!(arrDate.isAfter(room_occupancy.get(k)) || arrDate.isBefore(room_occupancy.get(k + 1)) || depDate.isAfter(room_occupancy.get(k)) || depDate.isBefore(room_occupancy.get(k + 1)))) {
                            listByNumOfGuestsAndDate.add(t);
                        }
                    }
                }
            }

            // If selected number of guests is more than 4 then add all rooms w/any roomtype to masterlist
            if (selectedNumOfGuests > 4) {
                for (Room t : roomList) {
                    room_occupancy = t.getRoom_occupancy();
                    for (int k = 0; k < room_occupancy.size(); k++) {
                        if (!(arrDate.isAfter(room_occupancy.get(k)) || arrDate.isBefore(room_occupancy.get(k + 1)) || depDate.isAfter(room_occupancy.get(k)) || depDate.isBefore(room_occupancy.get(k + 1)))) {
                            listByNumOfGuestsAndDate.add(t);
                        }
                    }
                }
            }
        }
        return listByNumOfGuestsAndDate;
    }

}
