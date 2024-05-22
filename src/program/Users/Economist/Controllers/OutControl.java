
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
import program.Users.Data.outcomData;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

public class OutControl implements Initializable {
    private static final String DB_NAME = "economist";
    private Connection connect;
    private PreparedStatement prepare;

    @FXML
    private Button addButton;

    @FXML
    private TableColumn<outcomData, String> executorColumn;

    @FXML
    private TableColumn<outcomData, Timestamp> createdColumn;

    @FXML
    private TableColumn<outcomData, Integer> indexColumn;

    @FXML
    private TableColumn<outcomData, String> messegeColumn;

    @FXML
    private TableColumn<outcomData, String> nameColumn;

    @FXML
    private TableView<outcomData> tableOutcom;

    @FXML
    void add(ActionEvent ignoredEvent) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add New Entry");
        dialog.initOwner(addButton.getScene().getWindow());
        dialogController addDialogController = null;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/addDialogPane.fxml"));
            DialogPane dialogPane = loader.load();
            dialog.setDialogPane(dialogPane);

            addDialogController = loader.getController();

            // Set default value for executor choice box
            addDialogController.initExecutorChoiceBox("Ceo");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DialogPane pane = dialog.getDialogPane();
            TextField nameTextField = (TextField) pane.lookup("#nameTextField");
            TextArea messageTxtArea = (TextArea) pane.lookup("#messageTxtArea");

            ChoiceBox<String> executorCD = (ChoiceBox<String>) pane.lookup("#executorCD");
            executorCD.setValue("Ceo"); // Set default value to "Economist"

            String name = nameTextField.getText();
            String message = messageTxtArea.getText();
            String executor = addDialogController.getExecutor();

            // Execute SQL query to insert the new record into the MySQL table
            String sql = "INSERT INTO outcom (name, executor, created, message) VALUES (?, ?, NOW(), ?)";
            try {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, name);
                prepare.setString(2, executor);
                prepare.setString(3, message);
                prepare.executeUpdate();
                System.out.println("Data inserted successfully into outcom table.");
                showData();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String sql2 = "INSERT INTO incom (name, author, created, message) VALUES (?, ?, NOW(), ?)";
            try {
                Connection ceoConnect = Connecting.Connect("ceo");
                if (ceoConnect != null) {
                    prepare = ceoConnect.prepareStatement(sql2);
                    prepare.setString(1, name);
                    prepare.setString(2, "Economist");
                    prepare.setString(3, message);
                    int rowsAffected = prepare.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Insertion into incom table was successful.");
                    } else {
                        System.out.println("No rows were inserted into the incom table.");
                    }

                    if (!ceoConnect.getAutoCommit()) {
                        ceoConnect.commit();
                    }
                } else {
                    System.out.println("Failed to connect to the ceo database.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }




    @FXML
    void delete(ActionEvent event) {
        outcomData selectedData = tableOutcom.getSelectionModel().getSelectedItem();
        if (selectedData != null) {
            int id = selectedData.getId(); // Retrieve the ID from the selected outcomData object

            String sql = "DELETE FROM outcom WHERE id=?";
            String sql2 = "DELETE FROM incom WHERE id=?";
            Connection connection = null;
            PreparedStatement statement1 = null;
            PreparedStatement statement2 = null;

            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this record?");
                Optional<ButtonType> buttonType = alert.showAndWait();

                if (buttonType.isPresent() && buttonType.get() == ButtonType.OK) {
                    // Initialize connection

                    connection = Connecting.Connect("economist");
                    if (connection != null) {
                        // First delete from 'outcom' table
                        statement1 = connection.prepareStatement(sql);
                        statement1.setInt(1, id);
                        statement1.executeUpdate();

                        // Second delete from 'incom' table
                        connection = Connecting.Connect("ceo");
                        statement2 = connection.prepareStatement(sql2);
                        statement2.setInt(1, id);
                        int rowsAffected = statement2.executeUpdate();

                        // Check if the delete was successful
                        if (rowsAffected > 0) {
                            System.out.println("Deletion from incom table was successful.");
                        } else {
                            System.out.println("No rows were deleted from the incom table.");
                        }

                        // Commit if not in auto-commit mode
                        if (!connection.getAutoCommit()) {
                            connection.commit();
                        }

                        showData(); // Refresh the data displayed in the table
                    } else {
                        System.out.println("Failed to connect to the database.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    if (connection != null && !connection.getAutoCommit()) {
                        connection.rollback(); // Rollback if an error occurs
                    }
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            showData();

        }
    }

    public ObservableList<outcomData> dataList() {
        ObservableList<outcomData> messageData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM outcom";

        try {
            prepare = connect.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();
            while (result.next()) {
                outcomData data = new outcomData(
                        result.getInt("id"),
                        result.getString("name"),
                        result.getString("executor"),
                        result.getTimestamp("created"),
                        result.getString("message")
                );
                messageData.add(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return messageData;
    }

    public void showData() {
        ObservableList<outcomData> showList = dataList();
        indexColumn.setCellValueFactory(new PropertyValueFactory<>("id")); // Changed from "index" to "id"
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        executorColumn.setCellValueFactory(new PropertyValueFactory<>("executor"));
        createdColumn.setCellValueFactory(new PropertyValueFactory<>("created"));
        messegeColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
        tableOutcom.setItems(showList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connect = Connecting.Connect(DB_NAME);
        if (connect != null) {
            System.out.println("Database connection established.");
            showData();
        } else {
            System.out.println("Failed to connect to the database.");
        }
    }
}
