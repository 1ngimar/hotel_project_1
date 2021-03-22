package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;

import java.net.URL;
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
    private ObservableList<String> borgir = FXCollections.observableArrayList("Reykjavík", "Akureyri", "Egilsstaðir");
    private ObservableList<User> users = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        afangastadir.setItems(borgir);

    }


}
