package program.loginUser.Login;
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
import program.loginUser.Connect.Connecting;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private final Connection connect = Connecting.Connect();
    @FXML
    private Button loginButton;

    @FXML
    private TextField txtLogin;

    @FXML
    private TextField txtPassword;

    @FXML
    void login(ActionEvent event) {
        Connecting.Connect();
        String login = txtLogin.getText();
        String password = txtPassword.getText();
        String sqlLog = STR."SELECT COUNT(1)  FROM usersAndPasswords where name='\{login}'";
        String sqlPass = STR."SELECT COUNT(1)  FROM usersAndPasswords where password='\{password}'";

        int log = 0;
        int pass = 0;
        try {
            if (login.isEmpty() | password.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please enter all the fields");
                alert.showAndWait();
            } else {
                assert connect != null;
                PreparedStatement prepare = connect.prepareStatement(sqlLog);
                ResultSet result = prepare.executeQuery();
                if (result.next()) {
                    log = result.getInt(1);
                }


                prepare = connect.prepareStatement(sqlPass);
                result = prepare.executeQuery();
                if (result.next()) {
                    pass = result.getInt(1);
                }
                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.close();

                if (log == 1 && pass == 1) {
                    switch (login) {

                        case "CEO":
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Users/Ceo/testProg/ceoFXML.fxml"));
                            try {
                                Parent root = loader.load();
                                Stage stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                Scene scene = new Scene(root);
                                stage1.setScene(scene);
                                stage1.show();

                            } catch (IOException _) {
                            }

                            break;
                        case "Economist":
                            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("economistFXML.fxml"));
                            try {
                                Parent root = loader2.load();
                                Stage stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                Scene scene = new Scene(root);
                                stage1.setScene(scene);
                                stage1.show();

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                    }
                }

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connecting.Connect();
    }
}



