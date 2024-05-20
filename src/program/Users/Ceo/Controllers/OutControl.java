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
import program.Users.Data.outcomData;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class OutControl implements Initializable {
    String db="ceo";
    private Connection connect= Connecting.Connect(db);
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;


    @FXML
    private Button addButton;

    @FXML
    public TableColumn<outcomData, String> executorColumn;

    @FXML
    private TableColumn<outcomData, String> createdColumn;

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<outcomData, Integer> indexColumn;

    @FXML
    private TableColumn<outcomData, String> messegeColumn;

    @FXML
    private TableColumn<outcomData, String> nameColumn;

    @FXML
    private TableView<outcomData> tableOutcom;

    @FXML
    void add(ActionEvent event) {

    }

    @FXML
    void delete(ActionEvent event) {

    }
    public ObservableList<outcomData> dataList(){
        ObservableList<outcomData> messageData = FXCollections.observableArrayList();
        String sql="SELECT * FROM outcom";

        try{
        prepare=connect.prepareStatement(sql);
        result = prepare.executeQuery();
        outcomData data;
        while(result.next()){
            data = new outcomData(result.getInt("id"), result.getString("name"), result.getString("executor"), result.getTimestamp("created"), result.getString("message") );
            messageData.add(data);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return messageData;
    }

    public void showData(){
        ObservableList<outcomData> showList=dataList();
        indexColumn.setCellValueFactory(new PropertyValueFactory<>("index"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        executorColumn.setCellValueFactory(new PropertyValueFactory<>("executor"));
        createdColumn.setCellValueFactory(new PropertyValueFactory<>("created"));
        messegeColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
        tableOutcom.setItems(showList);

    }

    public void clear(){

    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Connecting.Connect(db);
        showData();
        clear();

    }
}

