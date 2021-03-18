package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Button searchButton;
    @FXML
    private ChoiceBox afangaStadir;
    @FXML
    private CheckBox fimmStjornur;
    @FXML
    private CheckBox fjorarStjornur;
    @FXML
    private CheckBox trjarStrjornur;

    private DataFactory dataFactory = new DataFactory();

    public Controller(ChoiceBox afangaStadir) {
        this.afangaStadir = afangaStadir;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        afangaStadir.getItems().add("Reykjavík");
        afangaStadir.getItems().add("Akureyri");
        afangaStadir.getItems().add("Ísafjörður");
    }
}
