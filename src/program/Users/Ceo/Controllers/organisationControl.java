package program.Users.Ceo.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import program.Connect.Connecting;
import program.Users.Data.organisationData;
import program.Users.Data.outcomData;

import java.net.URL;
import java.sql.*;
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

    }

    @FXML
    void delete(ActionEvent event) {

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

