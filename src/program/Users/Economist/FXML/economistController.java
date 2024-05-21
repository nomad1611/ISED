package program.Users.Economist.FXML;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class economistController implements Initializable {
    @FXML
    private AnchorPane ap;

    @FXML
    private BorderPane bp;

    @FXML
    private Button home;

    @FXML
    private VBox vb;

    @FXML
    void Incoming(MouseEvent event) {
        loadPage("incomingFXML");
    }

    @FXML
    void Outcoming(MouseEvent event) {
        loadPage("outFXML");
    }

    @FXML
    void home(MouseEvent event) {
        bp.setCenter(ap);

    }

    @FXML
    void organisationFolder(MouseEvent event) {
        loadPage("organisationFXML");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void loadPage(String page) {
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(page + ".fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        bp.setCenter(root);

    }
}
