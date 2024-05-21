package program.Users.Economist.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import program.Connect.Connecting;
import program.Users.Data.organisationData;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class organisationControl implements Initializable {
    @FXML
    private Button addButton;

    @FXML
    private TableColumn<organisationData, Timestamp> createdColumn;

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<organisationData, Integer> indexColumn;

    @FXML
    private TableColumn<organisationData, String> nameColumn;

    @FXML
    private TableView<organisationData> tableOrg;

    String db="ISED";
    private Connection connect= Connecting.Connect(db);
    private PreparedStatement prepare;

    @FXML
    void add(ActionEvent event) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add New Entry");
        dialog.initOwner(addButton.getScene().getWindow());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/orgDP.fxml"));
            DialogPane dialogPane = loader.load();
            dialog.setDialogPane(dialogPane);



            // Set default value for executor choice box

        } catch (IOException _) {

            return;
        }

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Retrieve entered information
            DialogPane pane = dialog.getDialogPane();
            TextField nameTextField = (TextField) pane.lookup("#nameTextField");


            String name = nameTextField.getText();



            // Execute SQL query to insert the new record into the MySQL table
            String sql = "INSERT INTO orgfolder (name, created) VALUES ( ?, NOW())";
            try {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, name);
                prepare.executeUpdate();
                showData();

            } catch (SQLException _) {}



        }
    }



    @FXML
    void delete(ActionEvent event) {

        organisationData selectedData = tableOrg.getSelectionModel().getSelectedItem();
        if (selectedData != null) {

            int id = selectedData.getId(); // Retrieve the ID from the selected outcomData object

            String sql = "DELETE FROM orgfolder WHERE id=?";


            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this bank?");
                Optional<ButtonType> buttonType = alert.showAndWait();
                if (buttonType.isPresent() && buttonType.get() == ButtonType.OK) {

                    prepare = connect.prepareStatement(sql);
                    prepare.setInt(1, id);
                    prepare.executeUpdate();
                    showData();


                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            // If no item is selected, show a message to the user
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Please select a row to delete.");
            alert.showAndWait();
        }




    }


    public ObservableList<organisationData> dataList(){
        ObservableList<organisationData> orgData = FXCollections.observableArrayList();
        String sql="SELECT * FROM orgfolder";

        try{
            prepare=connect.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();
            organisationData data;
            while(result.next()){
                data = new organisationData(result.getInt("id"),result.getString("name"), result.getTimestamp("created"));
                orgData.add(data);
            }

        }catch (SQLException _){
        }

        return orgData;
    }

    public void showData(){
        ObservableList<organisationData> showList=dataList();
        indexColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        createdColumn.setCellValueFactory(new PropertyValueFactory<>("created"));

        tableOrg.setItems(showList);

    }






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connecting.Connect(db);
        showData();
    }



}
