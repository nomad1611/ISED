package program.Users.Ceo.Controllers;




import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

    public class dialogController {

        @FXML
        private ChoiceBox<String> executorCD;

        public void initialize() {
            // Initialize the executor choice box with options
            executorCD.getItems().addAll("Economist");
            executorCD.setValue("Economist"); // Set default value to "Economist"
        }

        public String getExecutor() {
            return executorCD.getValue();
        }
        public void initExecutorChoiceBox(String defaultValue) {
            executorCD.setValue(defaultValue);
        }

        // You can add more methods and fields as needed
    }


