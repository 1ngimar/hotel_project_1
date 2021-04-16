package sample;

import javafx.collections.ObservableList;

import java.time.LocalDate;

public class AppState {
    private ObservableList<Hotel> searchResult;
    private Hotel selectedHotel;
    private ObservableList<Room> availableRoomsForSelectedHotel;
    private LocalDate arrDate, depDate;
    private final static AppState INSTANCE = new AppState();
    private int numOfGuests;

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

    public void setSelectedHotel(Hotel selectedHotel) {
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

    public LocalDate getArrDate() {
        return arrDate;
    }

    public void setArrDate(LocalDate arrDate) {
        this.arrDate = arrDate;
    }

    public LocalDate getDepDate() {
        return depDate;
    }

    public void setDepDate(LocalDate depDate) {
        this.depDate = depDate;
    }

    public int getNumOfGuests() {
        return numOfGuests;
    }

    public void setNumOfGuests(int numOfGuests) {
        this.numOfGuests = numOfGuests;
    }
}
