package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class TeacherController implements Initializable{

	@FXML
    private Button addStudent_btn;

    @FXML
    private TextField addStudent_stdid;

    @FXML
    private TextField addStudent_stdname;

    @FXML
    private TextField addStudent_stdnemail;

    @FXML
    private TextField addStudent_stdpassword;

    @FXML
    private TextField addStudent_stdusername;

    @FXML
    private Button addStudents_addBtn;

    @FXML
    private Button addStudents_clearBtn;

    @FXML
    private TableColumn<StudentData, String> addStudents_col_email;

    @FXML
    private TableColumn<StudentData, String> addStudents_col_name;

    @FXML
    private TableColumn<StudentData, String> addStudents_col_password;

    @FXML
    private TableColumn<StudentData, String> addStudents_col_studentID;

    @FXML
    private TableColumn<StudentData, String> addStudents_col_username;

    @FXML
    private AnchorPane addStudents_form;

    @FXML
    private Button addStudents_removeBtn;

    @FXML
    private TableView<StudentData> addStudents_tableView;

    @FXML
    private Label current_form;

    @FXML
    private Label greet_name;

    @FXML
    private Button logout_btn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button teacher_result_btn;

    
    private AlertMessage alert = new AlertMessage();
    
    public ObservableList<StudentData> studentSetData() {
	    ObservableList<StudentData> listData1 = FXCollections.observableArrayList();
	    String filePath = "data.txt";

	    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] data = line.split(":");
	            if (data.length == 5) { // Check if all fields exist
	                String studentId = data[0];
	                String username = data[1]; 
	                String email = data[2];
	                String password = data[3];
	                String name = data[4];
	                
	                StudentData student = new StudentData(studentId, username, email, password, name);
	                listData1.add(student);
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return listData1;
	}

	public void StudentDisplayData() {
	    ObservableList<StudentData> studentListData = studentSetData();

	    addStudents_col_studentID.setCellValueFactory(new PropertyValueFactory<>("studentId"));
	    addStudents_col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
	    addStudents_col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
	    addStudents_col_password.setCellValueFactory(new PropertyValueFactory<>("password"));
	    addStudents_col_name.setCellValueFactory(new PropertyValueFactory<>("name"));

	    addStudents_tableView.setItems(studentListData);
	}
	
	public void dddteacher_addbtn(){
		try {
        	AnchorPane root = FXMLLoader.load(getClass().getResource("AddStudent.fxml"));

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Add Student");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	public void addStudentDeleteBtn() {
        StudentData selectedStudent = addStudents_tableView.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            // If no item is selected, show an error message
            alert.errorMessage("Please select an item first");
            return;
        }

        if (alert.confirmMessage("Are you sure you want to delete Student ID: " + selectedStudent.getStudentId() + "?")) {
            // Read all lines from the file into a list
            List<String> lines = readAllLinesFromFile("data.txt");

            // Identify the line to be deleted
            String lineToRemove = selectedStudent.getStudentId() + ":" + selectedStudent.getUsername() + ":" +
                    selectedStudent.getEmail() + ":" + selectedStudent.getPassword() + ":" + selectedStudent.getName();

            // Remove the identified line from the list
            lines.remove(lineToRemove);

            // Write the updated list of lines back to the file
            writeLinesToFile(lines, "data.txt");

            // Remove the selected student from the TableView
            ObservableList<StudentData> studentList = addStudents_tableView.getItems();
            studentList.remove(selectedStudent);
        }
    }

    // This method reads all lines from a file into a list
    private List<String> readAllLinesFromFile(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    // This method writes a list of lines to a file
    private void writeLinesToFile(List<String> lines, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void writeToFile(StudentData student) {
        String filePath = "data.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(student.getStudentId() + ":" + student.getUsername() + ":" + student.getEmail() + ":" + student.getPassword() + ":" + student.getName());
            writer.newLine(); // Add a new line for the next entry
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
   

 public synchronized void addresult() {
	
	String fxmlPath = "Result.fxml";
    String portalTitle = "Result Details";

    try {
        AnchorPane root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Stage stage = new Stage();
        stage.setTitle("University Management System | " + portalTitle);
        stage.setScene(new Scene(root));
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

public void logoutBtn() {
    
    try {
        if (alert.confirmMessage("Are you sure you want to logout?")) {
        	StackPane root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            
            stage.setScene(scene);
            stage.show();
            
            logout_btn.getScene().getWindow().hide();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    
}



public void switchForm(ActionEvent event) {

    if (event.getSource() == addStudent_btn) {
        addStudents_form.setVisible(true); 

        current_form.setText("Add Students form");
    }
}

public void initialize(URL location, ResourceBundle resources) {
	StudentDisplayData();
	
        
    }

}