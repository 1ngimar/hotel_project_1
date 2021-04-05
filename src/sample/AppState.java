package sample;

import javafx.collections.ObservableList;

public class AppState {
    private ObservableList<Hotel> searchResult;
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
}
