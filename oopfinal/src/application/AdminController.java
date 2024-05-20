package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.stage.Stage;

public class AdminController implements Initializable{

    @FXML
    private Button addCourse_addBtn;

    @FXML
    private Button addCourse_btn;

    @FXML
    private Button addCourse_clearBtn;

    @FXML
    private TableColumn<CourseData, String> addCourse_col_course;

    @FXML
    private TableColumn<CourseData, String> addCourse_col_department;

    @FXML
    private TableColumn<CourseData, String> addCourse_col_price;

    @FXML
    private TableColumn<CourseData, String> addCourse_col_status;

    @FXML
    private TextField addCourse_course;

    @FXML
    private Button addCourse_deleteBtn;

    @FXML
    private TextField addCourse_department;

    @FXML
    private AnchorPane addCourse_form;

    @FXML
    private TextField addCourse_price;

    @FXML
    private ComboBox<String> addCourse_status;

    @FXML
    private TableView<CourseData> addCourse_tableView;

    @FXML
    private Button addCourse_updateBtn;

    @FXML
    private Button addStudent_addBtn;

    @FXML
    private Button addStudent_btn;

    @FXML
    private TableColumn<StudentData,String> addStudent_col_Email;

    @FXML
    private TableColumn<StudentData,String> addStudent_col_Name;

    @FXML
    private TableColumn<StudentData,String> addStudent_col_Password;

    @FXML
    private TableColumn<StudentData,String> addStudent_col_Username;

    @FXML
    private TableColumn<StudentData,String> addStudent_col_studentid;

    @FXML
    private Button addStudent_deleteBtn;

    @FXML
    private AnchorPane addStudent_form;

    @FXML
    private TableView<StudentData> addStudent_tableView;

    @FXML
    private Button addTeacher_addBtn;

    @FXML
    private Button addTeacher_btn;

    @FXML
    private Button addTeacher_clearBtn;

    @FXML
    private TableColumn<SubjectData, String> addTeacher_col_department;

    @FXML
    private TableColumn<SubjectData, String> addTeacher_col_experience;

    @FXML
    private TableColumn<SubjectData, String> addTeacher_col_gender;

    @FXML
    private TableColumn<SubjectData, String> addTeacher_col_name;

    @FXML
    private TableColumn<SubjectData, String> addTeacher_col_teacherID;

    @FXML
    private Button addTeacher_deleteBtn;

    @FXML
    private ComboBox<String> addTeacher_department;

    @FXML
    private TextField addTeacher_experience;

    @FXML
    private AnchorPane addTeacher_form;

    @FXML
    private TextField addTeacher_fullName;

    @FXML
    private ComboBox<String> addTeacher_gender;

    @FXML
    private TableView<SubjectData> addTeacher_tableView;

    @FXML
    private TextField addTeacher_teacherID;

    @FXML
    private Button addTeacher_updateBtn;

    @FXML
    private Label dashboard_TI;

    @FXML
    private Label dashboard_TS;

    @FXML
    private Label dashboard_TT;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Label greet_username;
    
    private AlertMessage alert = new AlertMessage();
    
    private static int countEntries() {
    	
    	 String filePath = "data.txt";
    	 int count = 0;
         try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
             while (br.readLine() != null) {
                 count++;
             }
         } catch (IOException e) {
             e.printStackTrace();
         }
         return count-1;
    	 
    }
    
    private static int countEntries1() {
    	
   	 String filePath = "teachers.txt";
   	 int count1 = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while (br.readLine() != null) {
                count1++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count1;
   	 
   }
    public void getstdcnt(){
    	int count = countEntries();
    	dashboard_TS.setText(String.valueOf(count));
    }
    
    public void teachercnt(){
    	int count_teacher = countEntries1();
    	dashboard_TT.setText(String.valueOf(count_teacher));
    }
    
    public void dashboard(){
    	dashboard_form.setVisible(true);
        addStudent_form.setVisible(false);
        addTeacher_form.setVisible(false); 
        getstdcnt();
    	teachercnt();
    }
    
    public void student_transfer(){
    	
        dashboard_form.setVisible(false);
        addStudent_form.setVisible(true);
        addTeacher_form.setVisible(false);
       
        

    }
  public void teachertransfer() {
    	
        dashboard_form.setVisible(false);
        addStudent_form.setVisible(false);
        addTeacher_form.setVisible(true);

    }
  
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

	    addStudent_col_studentid.setCellValueFactory(new PropertyValueFactory<>("studentId"));
	    addStudent_col_Username.setCellValueFactory(new PropertyValueFactory<>("username"));
	    addStudent_col_Email.setCellValueFactory(new PropertyValueFactory<>("email"));
	    addStudent_col_Password.setCellValueFactory(new PropertyValueFactory<>("password"));
	    addStudent_col_Name.setCellValueFactory(new PropertyValueFactory<>("name"));

	    addStudent_tableView.setItems(studentListData);
	}
	
	  public ObservableList<SubjectData> teacherSetData() {
		    ObservableList<SubjectData> listData = FXCollections.observableArrayList();
		    String filePath1 = "teachers.txt";

		    try (BufferedReader br = new BufferedReader(new FileReader(filePath1))) {
		        String line1;
		        while ((line1 = br.readLine()) != null) {
		            String[] data1 = line1.split(":");
		            if (data1.length == 4) { // Check if all fields exist
		                String teacherId = data1[0];
		                String name = data1[1]; 
		                String gender= data1[2];
		                String YearOfExp = data1[3];
		                
		                SubjectData teacher = new SubjectData(teacherId, name, gender, YearOfExp);
		                listData.add(teacher);
		            }
		        }
		    } catch (IOException e) {
		        e.printStackTrace();
		    }

		    return listData;
		}

		public void TeacherDisplayData() {
		    ObservableList<SubjectData> teacherListData = teacherSetData();

		    addTeacher_col_teacherID.setCellValueFactory(new PropertyValueFactory<>("teacherId"));
		    addTeacher_col_name.setCellValueFactory(new PropertyValueFactory<>("TeacherName"));
		    addTeacher_col_gender.setCellValueFactory(new PropertyValueFactory<>("TeacherGender"));
		    addTeacher_col_experience.setCellValueFactory(new PropertyValueFactory<>("TeacherYearExp"));
		    addTeacher_tableView.setItems(teacherListData);
		}

	 public void addStudentAddBtn() {

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
	        StudentData selectedStudent = addStudent_tableView.getSelectionModel().getSelectedItem();
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
	            ObservableList<StudentData> studentList = addStudent_tableView.getItems();
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
	    
	    public void genderlist(){

	    	List<String> listR = new ArrayList<>(ListData.gender);
	        ObservableList<String> listData = FXCollections.observableArrayList(listR);
	        addTeacher_gender.setItems(listData);
	   }
	    
	    public void addTeacher_addbtn(){
	    	if(addTeacher_teacherID.getText().isEmpty() || addTeacher_experience.getText().isEmpty() || addTeacher_fullName.getText().isEmpty() || addTeacher_gender.getSelectionModel().getSelectedItem().isEmpty()) {
	    		alert.errorMessage("please enter blank Field.!");
	    	}
	    	else {
	    		Alert alert;
		    	
		    	String TecherId = addTeacher_teacherID.getText();
		        String TeacherName = addTeacher_fullName.getText();
		        String TeacherGender = addTeacher_gender.getValue();
		        String TeacherYearExp = addTeacher_experience.getText();
		      
		    	
		        SubjectData newStudent = new SubjectData(TecherId, TeacherName, TeacherGender, TeacherYearExp);
		        
		        writeToFile1(newStudent);
		        
		        alert = new Alert(AlertType.INFORMATION);
	            alert.setTitle("Information Message");
	            alert.setHeaderText(null);
	            alert.setContentText("Data Added Sucessfully.!");
	            alert.showAndWait();
	    	}
	    }
	    
	    private void writeToFile1(SubjectData newStudent) {
	        String filePath = "teachers.txt";
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
	            writer.write(newStudent.getTeacherId() + ":" + newStudent.getTeacherName() + ":" + newStudent.getTeacherGender() + ":" + newStudent.getTeacherYearExp());
	            writer.newLine(); // Add a new line for the next entry
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public void clearbtn(){
	    	addTeacher_teacherID.setText("");
	    	addTeacher_fullName.setText("");
	    	addTeacher_gender.setValue("");
	    	addTeacher_experience.setText("");
	    	
	    }
	    
		 public void addTeacherDeleteBtn() {
		        SubjectData selectedTeacher = addTeacher_tableView.getSelectionModel().getSelectedItem();
		        if (selectedTeacher == null) {
		            // If no item is selected, show an error message
		            alert.errorMessage("Please select an item first");
		            return;
		        }

		        if (alert.confirmMessage("Are you sure you want to delete Student ID: " + selectedTeacher.getTeacherId() + "?")) {
		            // Read all lines from the file into a list
		            List<String> lines = readAllLinesFromFile1("teachers.txt");

		            // Identify the line to be deleted
		            String lineToRemove = selectedTeacher.getTeacherId() + ":" + selectedTeacher.getTeacherName() + ":" +
		            		selectedTeacher.getTeacherGender() + ":" + selectedTeacher.getTeacherYearExp();

		            // Remove the identified line from the list
		            lines.remove(lineToRemove);

		            // Write the updated list of lines back to the file
		            writeLinesToFile1(lines, "teachers.txt");

		            // Remove the selected student from the TableView
		            ObservableList<SubjectData> teacherList = addTeacher_tableView.getItems();
		            teacherList.remove(selectedTeacher);
		        }
		    }

		    // This method reads all lines from a file into a list
		    private List<String> readAllLinesFromFile1 (String filePath) {
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
		    private void writeLinesToFile1 (List<String> lines, String filePath) {
		        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
		            for (String line : lines) {
		                writer.write(line);
		                writer.newLine();
		            }
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		    }

    
    public void addstudent(){
    	student_transfer();
    	StudentDisplayData();	
    }
    
    public void addteacher(){
    	teachertransfer();
    	TeacherDisplayData();
    	
    }
    public void initialize(URL url, ResourceBundle rb) {
    	getstdcnt();
    	teachercnt();
    	genderlist();
    }
}