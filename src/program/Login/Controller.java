
package program.Login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import program.Connect.Connecting;

import javafx.scene.input.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    String db = "ISED";
    private final Connection connect = Connecting.Connect(db);

    @FXML
    private Button loginButton;

    @FXML
    private TextField txtLogin;

    @FXML
    private TextField txtPassword;

    @FXML
    void login(KeyEvent keyEvent) {
        handleLogin(keyEvent);
    }

    @FXML
    void login(ActionEvent event) {
        handleLogin(event);
    }

    private void handleLogin(Object event) {
        Connecting.Connect(db);
        String login = txtLogin.getText();
        String password = txtPassword.getText();
        String sqlLog = "SELECT COUNT(1) FROM usersAndPasswords WHERE name=?";
        String sqlPass = "SELECT COUNT(1) FROM usersAndPasswords WHERE password=?";

        int log = 0;
        int pass = 0;
        try {
            if (login.isEmpty() || password.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter all the fields");
                alert.showAndWait();
            } else {
                assert connect != null;
                PreparedStatement prepare = connect.prepareStatement(sqlLog);
                prepare.setString(1, login);
                ResultSet result = prepare.executeQuery();
                if (result.next()) {
                    log = result.getInt(1);
                }

                prepare = connect.prepareStatement(sqlPass);
                prepare.setString(1, password);
                result = prepare.executeQuery();
                if (result.next()) {
                    pass = result.getInt(1);
                }

                if (log == 1 && pass == 1) {
                    Stage stage = (Stage) loginButton.getScene().getWindow();
                    stage.close();

                    switch (login.toLowerCase()) {
                        case "ceo":
                            loadFXML("../Users/Ceo/FXML/ceoFXML.fxml", event, "User CEO");
                            break;
                        case "economist":
                            loadFXML("../Users/Economist/testProg/economistFXML.fxml", event, "User Economist");
                            break;
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid login or password");
                    alert.showAndWait();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void loadFXML(String fxmlPath, Object event, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = null;
            if (event instanceof ActionEvent) {
                stage = (Stage) ((Node) ((ActionEvent) event).getSource()).getScene().getWindow();
            } else if (event instanceof KeyEvent) {
                stage = (Stage) ((Node) ((KeyEvent) event).getTarget()).getScene().getWindow();
            }
            Scene scene = new Scene(root);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connecting.Connect(db);
    }
}






