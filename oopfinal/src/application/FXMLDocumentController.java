package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class FXMLDocumentController implements Initializable {
	
	@FXML
    private PasswordField admin_cPassword;

    @FXML
    private AnchorPane admin_form;

    @FXML
    private PasswordField admin_password;

    @FXML
    private Hyperlink admin_signIn;

    @FXML
    private Button admin_signupBtn;

    @FXML
    private TextField admin_username;

    @FXML
    private Button login_btn;

    @FXML
    private AnchorPane login_form;

    @FXML
    private PasswordField login_password;

    @FXML
    private ComboBox<String> login_role;

    @FXML
    private TextField login_username;

    @FXML
    private PasswordField student_cPassword;

    @FXML
    private TextField student_email;

    @FXML
    private AnchorPane student_form;

    @FXML
    private PasswordField student_password;

    @FXML
    private Hyperlink student_signIn;

    @FXML
    private Button student_signupBtn;

    @FXML
    private TextField student_username;

    @FXML
    private PasswordField teacher_cPassword;

    @FXML
    private TextField teacher_email;

    @FXML
    private AnchorPane teacher_form;

    @FXML
    private PasswordField teacher_password;

    @FXML
    private Hyperlink teacher_signIn;
    
    @FXML
    private TextField student_name;

    @FXML
    private Button teacher_signupBtn;

    @FXML
    private TextField teacher_username;
    
    private static final String STUDENT_ID_FILE = "last_student_id.txt";
    private static final String TEACHER_ID_FILE = "last_teacher_id.txt";
    
    
   
    public void loginAccount(ActionEvent event) throws IOException {
        Alert alert;

        if (login_username.getText().isEmpty() || login_password.getText().isEmpty() || login_role.getValue() == null) {
            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all fields and select a role!");
            alert.showAndWait();
        } else {
            String username = login_username.getText();
            String password = login_password.getText();
            String selectedRole = login_role.getValue();

            if (authenticateUser(username, password, selectedRole)) {
            	SessionManager.setLoggedInUsername(username);
                openMainForm(selectedRole);
                login_btn.getScene().getWindow().hide();
            } else {
                // Deny access
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid username and password");
                alert.showAndWait();
            }
        }
    }
    
    private HashMap<String, String> teacherCredentials = new HashMap<>();
    private HashMap<String, String> studentCredentials = new HashMap<>();
 
    public FXMLDocumentController() {
    	teacherCredentials.put("teacher1", "password1");
        teacherCredentials.put("teacher2", "password2");
        teacherCredentials.put("teacher3", "password3");
        teacherCredentials.put("teacher4", "password4");
        
        try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line into username and password using a delimiter (e.g., comma)
                String[] parts = line.split(":");
                if (parts.length == 5) {
                    String username = parts[1].trim();
                    String password = parts[3].trim();
                    // Add student credentials to the HashMap
                    studentCredentials.put(username, password);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean authenticateUser(String username, String password, String role) {
        // Perform actual authentication logic here, such as querying a database
        // For demonstration purposes, I'll use simple hard-coded credentials
        switch (role) {
            case "Admin":
                return username.equals("admin") && password.equals("admin");
            case "Student":
                // Add authentication logic for students
            	return studentCredentials.containsKey(username) && studentCredentials.get(username).equals(password);
            case "Teacher":
                
            	return teacherCredentials.containsKey(username) && teacherCredentials.get(username).equals(password);
        }
        return false;
    }

    private void openMainForm(String role) {
        String fxmlPath = "";
        String portalTitle = "";

        switch (role) {
            case "Admin":
                fxmlPath = "AdminMainForm.fxml";
                portalTitle = "Admin Portal";
                break;
            case "Student":
                fxmlPath = "StudentMainForm.fxml";
                portalTitle = "Student Portal";
                break;
            case "Teacher":
                fxmlPath = "TeacherMainForm.fxml";
                portalTitle = "Teacher Portal";
                break;
        }

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

 
    
public void registerAdmin() {
    	
        Alert alert;

        if (admin_username.getText().isEmpty() || admin_password.getText().isEmpty()
                || admin_cPassword.getText().isEmpty()) {

            alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields!");
            alert.showAndWait();

        } else {
            String username = admin_username.getText();
            String password = admin_password.getText();
            String confirmPassword = admin_cPassword.getText();

            if (!password.equals(confirmPassword)) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Passwords do not match.");
                alert.showAndWait();
            } else if (password.length() < 8) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Invalid password. Password should be at least 8 characters long.");
                alert.showAndWait();
            } else {
                if (registerAdminToFile(username, password)) {
                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Success Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Admin registered successfully!");
                    alert.showAndWait();

                    login_form.setVisible(true);
                    admin_form.setVisible(false);
                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to register admin. Please try again later.");
                    alert.showAndWait();
                }
            }
        }
    }
    private boolean registerAdminToFile(String username, String password) {
        try (FileWriter writer = new FileWriter("data.txt", true);
             BufferedWriter bw = new BufferedWriter(writer);
             PrintWriter out = new PrintWriter(bw))
              {

            out.println(username+":"+password);

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error writing to data.txt: " + e.getMessage());
            return false;
        } 
         
        
    }
    
    public void registerStudent() {
    	
    	Alert alert;

         if (student_name.getText().isEmpty() || student_email.getText().isEmpty() || student_username.getText().isEmpty()
                 || student_password.getText().isEmpty() || student_cPassword.getText().isEmpty()) {
             alert = new Alert(AlertType.ERROR);
             alert.setTitle("Error Message");
             alert.setHeaderText(null);
             alert.setContentText("Please fill all blank fields");
             alert.showAndWait();
         } else {
        	 
        	
        	 
             int studentID = generateStudentID();

             if (studentID == -1) {
                 alert = new Alert(AlertType.ERROR);
                 alert.setTitle("Error Message");
                 alert.setHeaderText(null);
                 alert.setContentText("Failed to generate student ID");
                 alert.showAndWait();
                 return;
             }
             String name = student_name.getText();
             String email = student_email.getText();
             String username = student_username.getText();
             String password = student_password.getText();
             String confirmPassword = student_cPassword.getText();

             if (!password.equals(confirmPassword)) {
                 alert = new Alert(AlertType.ERROR);
                 alert.setTitle("Error Message");
                 alert.setHeaderText(null);
                 alert.setContentText("Passwords do not match.");
                 alert.showAndWait();
             } else if (password.length() < 8) {
                 alert = new Alert(AlertType.ERROR);
                 alert.setTitle("Error Message");
                 alert.setHeaderText(null);
                 alert.setContentText("Invalid password. Password should be at least 8 characters long.");
                 alert.showAndWait();
             } else {
                 if (registerStudentToFile(studentID, email, username, password,name)) {
                     alert = new Alert(AlertType.INFORMATION);
                     alert.setTitle("Success Message");
                     alert.setHeaderText(null);
                     alert.setContentText("Student registered successfully!");
                     alert.showAndWait();

                     login_form.setVisible(true);
                     student_form.setVisible(false);
                 } else {
                     alert = new Alert(AlertType.ERROR);
                     alert.setTitle("Error Message");
                     alert.setHeaderText(null);
                     alert.setContentText("Failed to register student. Please try again later.");
                     alert.showAndWait();
                 }
             }
         }
     }

     private int generateStudentID() {
         int lastStudentID = getLastStudentID();

         int newStudentID = lastStudentID + 1;

         if (!updateLastStudentID(newStudentID)) {
             return -1; 
         }

         return newStudentID;
     }

     private int getLastStudentID() {
         try (BufferedReader reader = new BufferedReader(new FileReader(STUDENT_ID_FILE))) {
             String line = reader.readLine();
             if (line != null && !line.isEmpty()) {
                 return Integer.parseInt(line);
             }
         } catch (IOException | NumberFormatException e) {
             e.printStackTrace();
         }
        
         return 0;
     }

     private boolean updateLastStudentID(int newStudentID) {
         try (BufferedWriter writer = new BufferedWriter(new FileWriter(STUDENT_ID_FILE))) {
             writer.write(Integer.toString(newStudentID));
             return true;
         } catch (IOException e) {
             e.printStackTrace();
             return false;
         }
     }

     private boolean registerStudentToFile(int studentID, String email,String name, String username, String password) {
         try (FileWriter writer = new FileWriter("data.txt", true);
              BufferedWriter bw = new BufferedWriter(writer);
              PrintWriter out = new PrintWriter(bw)) {
        	 
             out.println(studentID+":"+name+":"+email+":"+username+":"+password);

             return true;

         } catch (IOException e) {
             e.printStackTrace();
             return false;
         }
}
    
  public void registerTeacher() {
	  
	  Alert alert;

      if (teacher_email.getText().isEmpty() || teacher_username.getText().isEmpty()
              || teacher_password.getText().isEmpty() || teacher_cPassword.getText().isEmpty()) {
    	  
          alert = new Alert(AlertType.ERROR);
          alert.setTitle("Error Message");
          alert.setHeaderText(null);
          alert.setContentText("Please fill all blank fields");
          alert.showAndWait();
      } else {
          int teacherID = generateTeacherID();

          if (teacherID == -1) {
              alert = new Alert(AlertType.ERROR);
              alert.setTitle("Error Message");
              alert.setHeaderText(null);
              alert.setContentText("Failed to generate student ID");
              alert.showAndWait();
              return;
          }

          String email = teacher_email.getText();
          String username = teacher_username.getText();
          String password = teacher_password.getText();
          String confirmPassword = teacher_cPassword.getText();

          if (!password.equals(confirmPassword)) {
              alert = new Alert(AlertType.ERROR);
              alert.setTitle("Error Message");
              alert.setHeaderText(null);
              alert.setContentText("Passwords do not match.");
              alert.showAndWait();
          } else if (password.length() < 8) {
              alert = new Alert(AlertType.ERROR);
              alert.setTitle("Error Message");
              alert.setHeaderText(null);
              alert.setContentText("Invalid password. Password should be at least 8 characters long.");
              alert.showAndWait();
          } else {
              if (registerTeacherToFile(teacherID, email, username, password)) {
                  alert = new Alert(AlertType.INFORMATION);
                  alert.setTitle("Success Message");
                  alert.setHeaderText(null);
                  alert.setContentText("Student registered successfully!");
                  alert.showAndWait();

                  login_form.setVisible(true);
                  student_form.setVisible(false);
              } else {
                  alert = new Alert(AlertType.ERROR);
                  alert.setTitle("Error Message");
                  alert.setHeaderText(null);
                  alert.setContentText("Failed to register student. Please try again later.");
                  alert.showAndWait();
              }
          }
      }
  }

  private int generateTeacherID() {
      int lastTeacherID = getLastTeacherID();

      int newTeacherID = lastTeacherID + 1;

      // Update the last student ID file with the new ID
      if (!updateLastTeacherID(newTeacherID)) {
          return -1; // Return -1 if failed to update the last student ID file
      }

      return newTeacherID;
  }

  private int getLastTeacherID() {
      try (BufferedReader reader = new BufferedReader(new FileReader(TEACHER_ID_FILE))) {
          String line = reader.readLine();
          if (line != null && !line.isEmpty()) {
              return Integer.parseInt(line);
          }
      } catch (IOException | NumberFormatException e) {
          e.printStackTrace();
      }
      // Return 0 if file is empty or cannot be read
      return 0;
  }

  private boolean updateLastTeacherID(int newTeacherID) {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEACHER_ID_FILE))) {
          writer.write(Integer.toString(newTeacherID));
          return true;
      } catch (IOException e) {
          e.printStackTrace();
          return false;
      }
  }

  private boolean registerTeacherToFile(int teacherID, String email, String username, String password) {
      try (FileWriter writer = new FileWriter("teachers.txt", true);
           BufferedWriter bw = new BufferedWriter(writer);
           PrintWriter out = new PrintWriter(bw)) {

          // Write student registration data to the file
          out.println(username +":"+ password);

          return true;

      } catch (IOException e) {
          e.printStackTrace();
          return false;
      }
   }

    public void roleList() {

        List<String> listR = new ArrayList<String>();

        for (String data : ListData.role) {
            listR.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listR);
        login_role.setItems(listData);

    }
    
    public void signInForm() {
        login_form.setVisible(true);
        admin_form.setVisible(false);
        student_form.setVisible(false);
        teacher_form.setVisible(false);
    }
    
    public void switchForm() {
    	
        login_form.setVisible(false);
        admin_form.setVisible(false);
        student_form.setVisible(true);
        teacher_form.setVisible(false);

    }

    public void initialize(URL url, ResourceBundle rb) {
    	
    	roleList();
    }
}
