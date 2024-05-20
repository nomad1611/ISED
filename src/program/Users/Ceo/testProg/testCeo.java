package program.Users.Ceo.testProg;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class testCeo extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
           Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ceoFXML.fxml")));
           Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }
}
