package sample;

import javafx.collections.ObservableList;

public class AppState {
    private ObservableList<Hotel> searchResult;
    private Hotel selectedHotel;
    private ObservableList<Room> availableRooms;
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

    public ObservableList<Room> getAvailableRooms() {
        return this.availableRooms;
    }

    public void setAvailableRooms(ObservableList<Room> availableRooms) {
        this.availableRooms = availableRooms;
    }
}
