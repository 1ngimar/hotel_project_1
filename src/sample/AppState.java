package sample;

import javafx.collections.ObservableList;

public class AppState {
    private ObservableList<Hotel> searchResult;
    private Hotel selectedHotel;
    private ObservableList<Room> availableRoomsForSelectedHotel;
    private final static AppState INSTANCE = new AppState();

    private AppState() {
    }

    public static AppState getInstance() {
        return INSTANCE;
    }

    public void setSearchResult(ObservableList<Hotel> searchResult) {
        this.searchResult = searchResult;
    }

    public ObservableList<Hotel> getSearchResult() {
        return this.searchResult;
    }

    public void setSelectedHotel(Hotel selectedHotel){
        this.selectedHotel = selectedHotel;
    }

    public Hotel getSelectedHotel() {
        return this.selectedHotel;
    }

    public void setAvailableRoomsForSelectedHotel(ObservableList<Room> availableRoomsForSelectedHotel) {
        this.availableRoomsForSelectedHotel = availableRoomsForSelectedHotel;
    }

    public ObservableList<Room> getAvailableRoomsForSelectedHotel() {
        return this.availableRoomsForSelectedHotel;
    }
}
