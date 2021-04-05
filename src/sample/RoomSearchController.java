// TODO ALLT


package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class RoomSearchController implements Initializable {
    @FXML
    private Label congratLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        congratLabel.setText("Vel gert!");
        //hotelListView.setAccessibleRole(AccessibleRole.TABLE_VIEW);
        AppState state = AppState.getInstance();
        ObservableList<Hotel> searchResult = state.getSearchResult();
        for (Hotel hotel : searchResult) {
            System.out.print(hotel.getHotel_name());
        }
    }


}
