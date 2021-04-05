// TODO ALLT


package sample;

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
    }
}
