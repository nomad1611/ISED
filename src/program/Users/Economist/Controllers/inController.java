package program.Users.Economist.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import program.Connect.Connecting;
import program.Users.Data.inData;

import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class inController implements Initializable {
    @FXML
    private TableColumn<inData, Integer> idColumn;

    @FXML
    private TableColumn<inData, String> nameColumn;

    @FXML
    private TableColumn<inData, String> authorColumn;

    @FXML
    private TableColumn<inData, Timestamp> createdColumn;

    @FXML
    private TableColumn<inData, String> messageColumn;

    @FXML
    private TableView<inData> tableIncom;

    private Connection connect;

    public ObservableList<inData> dataList() {
        ObservableList<inData> orgData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM incom";

        try {
            connect = Connecting.Connect("economist");
            if (connect != null) {
                PreparedStatement prepare = Objects.requireNonNull(connect).prepareStatement(sql);
                ResultSet result = prepare.executeQuery();
                while (result.next()) {
                    inData data = new inData(
                            result.getInt("id"),
                            result.getString("name"),
                            result.getString("author"),
                            result.getTimestamp("created"),
                            result.getString("message")
                    );
                    orgData.add(data);
                    System.out.println("Data added: " + data.getId() + ", " + data.getName());
                }
                result.close();
                prepare.close();
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orgData;
    }

    public void showData() {
        ObservableList<inData> showList = dataList();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        createdColumn.setCellValueFactory(new PropertyValueFactory<>("created"));
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
        tableIncom.setItems(showList);
        System.out.println("Data shown in TableView");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initializing controller");
        showData();
    }
}
