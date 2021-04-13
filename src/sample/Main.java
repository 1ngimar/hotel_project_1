package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public Stage window;
    Scene hotelSearchScene, roomSearchScene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        // Scene fyrir hótelleitarvél
        hotelSearchScene = new Scene(root, 600, 400);
        window.setTitle("Hótel leitarvél");
        window.setScene(hotelSearchScene);
        window.setResizable(false);
        window.show();
    }


    public static void main(String[] args) {
        DBFactory db = new DBFactory();
        db.connect();
        launch(args);
    }
}
