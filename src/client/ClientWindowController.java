package client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClientWindowController  implements Initializable {


    public TextField txtId;
    @FXML
    private Button AddButton;

    @FXML
    private Button DeleteButton;

    @FXML
    private TableColumn<clientData, Integer> IDColumn;

    @FXML
    private Button UpdateButton;

    @FXML
    private TableColumn<clientData, String> positionColumn;

    @FXML
    private TableColumn<clientData,String> nameColumn;

    @FXML
    private TableView<clientData> table;

    @FXML
    private TextField txtPosition;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSalary;

    @FXML
    private TableColumn<clientData, Integer> salaryColumn;


    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;


    @FXML
    void Add(ActionEvent event) {

        Connect();
        String sql = "insert into HR_Department values(?,?,?,?)";
        int  id;
        String ID=txtId.getText();
        id= Integer.parseInt(ID);

        try {
            if ( txtId.getText().isEmpty() | txtName.getText().isEmpty() | txtPosition.getText().isEmpty() | txtSalary.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the fields");
                alert.showAndWait();
            } else {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, txtId.getText());
                prepare.setString(2, txtName.getText());
                prepare.setString(3, txtPosition.getText());
                prepare.setString(4, txtSalary.getText());
                prepare.executeUpdate();
                showData();
                clear();
            }
        }catch (Exception e){}

    }



    @FXML
    void Delete (ActionEvent event){
        int myIndex = table.getSelectionModel().getSelectedIndex();
        int id= Integer.parseInt(String.valueOf(table.getItems().get(myIndex).getId()));
        String sql = "delete from HR_Department where id = ?";
        connect = Connect();
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this bank?");
            Optional<ButtonType> buttonType= alert.showAndWait();
            if ( buttonType.isPresent() && buttonType.get() == ButtonType.OK){
                prepare = connect.prepareStatement(sql);
                prepare.setInt(1, id);
                prepare.executeUpdate();
                showData();
                clear();
            }else{
                System.out.println("Failed to establish database connection.");
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }






    public ObservableList<clientData> dataList(){
        connect= Connect();
        ObservableList<clientData> datalist = FXCollections.observableArrayList();
        String sql="SELECT * From HR_Department";
        try {
            prepare=connect.prepareStatement(sql);
            result = prepare.executeQuery();
            clientData data;
            while (result.next()){
                data = new clientData(result.getInt("ID"), result.getString("Name"), result.getString("Position"), result.getInt("Salary"));
                datalist.add(data);
            }

        }catch (Exception e){}

        return datalist;
    }


    public void showData(){
        ObservableList<clientData> showList = dataList();
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory( new PropertyValueFactory<>("Name"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("Position"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("Salary"));
        table.setItems(showList);
    }


    public void clear(){
        txtId.setText("");
        txtName.setText("");
        txtPosition.setText("");
        txtSalary.setText("");
    }



    public Connection Connect () {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost:3307/ISED", "root", "max123456!");
            System.out.println("Connected to database");
            return connect;
        } catch (Exception ex) {
            System.out.println(ex);
            return  null;


        }

    }

    @Override
    public void initialize (URL url, ResourceBundle rb){
        Connect();
        showData();
        clear();

    }
}