package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class StudentDetailController implements Initializable {

    @FXML
    private Label Student_email;

    @FXML
    private Label Student_id;

    @FXML
    private Label Student_name;

    @FXML
    private Label Student_username;

    private final ExecutorService executor = Executors.newFixedThreadPool(2);

    private String getLoggedInUsername() {
        // Implement a method to retrieve the currently logged-in username
        // For example:
        return SessionManager.getLoggedInUsername();
    }

    private void loadDataFromFile() {
        executor.submit(() -> {
            String filePath = "data.txt";
            String loggedInUsername = getLoggedInUsername(); // Get the currently logged-in username

            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                boolean userFound = false;

                while ((line = br.readLine()) != null) {
                    String[] data = line.split(":");

                    if (data.length == 5 && data[1].equals(loggedInUsername)) {
                        Platform.runLater(() -> {
                            Student_id.setText(data[0]);
                            Student_username.setText(data[1]);
                            Student_email.setText(data[2]);
                            // Assuming data[3] is not used
                            Student_name.setText(data[4]);
                        });
                        userFound = true;
                        break;
                    }
                }

                if (!userFound) {
                    Platform.runLater(this::clearFields);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void clearFields() {
        // Clear all label fields
        Student_id.setText("");
        Student_username.setText("");
        Student_email.setText("");
        Student_name.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDataFromFile();
    }

    // Make sure to properly shutdown the executor when the application is closed
    public void shutdown() {
        executor.shutdown();
    }
}
