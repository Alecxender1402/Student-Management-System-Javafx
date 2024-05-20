package application;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.scene.Parent;
import javafx.scene.paint.ImagePattern;

public class StudentController implements Initializable  {

    @FXML
    private Circle circle_image;

    @FXML
    private Button logout_btn;

    @FXML
    private TableColumn<DataStudentHandle, String> studentInfo_col_YE;

    @FXML
    private TableColumn<DataStudentHandle, String> studentInfo_col_gender;

    @FXML
    private TableColumn<DataStudentHandle, String> studentInfo_col_name;

    @FXML
    private TableColumn<DataStudentHandle, String> studentInfo_col_teacherID;

    @FXML
    private Button studentInformation_btn;

    @FXML
    private Label student_id;

    @FXML
    private TableView<DataStudentHandle> table_view;

    @FXML
    private Label teacher_date;

    @FXML
    private Label teacher_gender;

    @FXML
    private Label teacher_id;
    
    @FXML
    private Button student_Result;

    @FXML
    private Label teacher_name;
    
    AlertMessage alert = new AlertMessage();
    

    public ObservableList<DataStudentHandle> teacherSetData() {
        ObservableList<DataStudentHandle> listData = FXCollections.observableArrayList();
        String filePath = "teachers.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(":");
                if (data.length >= 3) { // Check if at least teacherID, gender, and yearOfExperience exist
                    String teacherId = data[0];
                    String name = data[1]; // Initialize name to an empty string
                    String gender = data[2];
                    String yearOfExperience = data[3];
                   
                    DataStudentHandle dsh = new DataStudentHandle(teacherId, name, gender, yearOfExperience);
                    listData.add(dsh);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listData;
    }

    private ObservableList<DataStudentHandle> teacherListData;

    public void teacherDisplayData() {
        teacherListData = teacherSetData();

        studentInfo_col_teacherID.setCellValueFactory(new PropertyValueFactory<>("teacherID"));
        studentInfo_col_name.setCellValueFactory(new PropertyValueFactory<>("name")); // Ensure this matches the property in DataStudentHandle
        studentInfo_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        studentInfo_col_YE.setCellValueFactory(new PropertyValueFactory<>("yearOfExperience"));

        table_view.setItems(teacherListData);
    }
    

    public void teacherSelectData() {
        DataStudentHandle dsh = table_view.getSelectionModel().getSelectedItem();
        int num = table_view.getSelectionModel().getSelectedIndex();
        
        if ((num - 1) < -1) {
            return;
        }
        
        String filePath = "teachers.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Assuming each line contains data separated by commas
                String[] data = line.split(":");
               
                if (data.length >= 4 && data[0].equals(dsh.getTeacherID())) {
                     
                    teacher_id.setText(data[0]);
                    teacher_name.setText(data[1]);
                    teacher_gender.setText(data[2]);
                    teacher_date.setText(data[3]);
                    break; // Exit loop once the matching teacher is found
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
    public void studentIDDisplay() {
    	
        
    	  String fxmlPath = "StudentDetail.fxml";
          String portalTitle = "Student Details";

          try {
              StackPane root = FXMLLoader.load(getClass().getResource(fxmlPath));
              Stage stage = new Stage();
              stage.setTitle("University Management System | " + portalTitle);
              stage.setScene(new Scene(root));
              stage.show();
          } catch (IOException e) {
              e.printStackTrace();
          }

    }
    
    public void showresult() {
    	
    	String fxmlPath = "ShowResult.fxml";
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
    public void initialize(URL location, ResourceBundle resources) {
        
        teacherDisplayData();
                
    }
    

}