package application;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ResultController implements Initializable {
	
    @FXML
    private ComboBox<String> std_id;

    @FXML
    private Button submit;
   
    @FXML
    private TextField coa_marks;

    @FXML
    private TextField dm_marks;

    @FXML
    private TextField oop_marks;

    @FXML
    private TextField os_marks;

    @FXML
    private TextField pem_marks;
	 
   
    
    public void submit() {
    	
    	 Alert alert;
    	
    	
        
        if(coa_marks.getText().isEmpty() || dm_marks.getText().isEmpty() || oop_marks.getText().isEmpty() || os_marks.getText().isEmpty() || pem_marks.getText().isEmpty()) {
        	
        	alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("please enter blank fields..!");
            alert.show();
        	
        } else {
        
        String student_id = std_id.getSelectionModel().getSelectedItem();
    	String coa = coa_marks.getText();
        String dm = dm_marks.getText();
        String oop = oop_marks.getText();
        String os = os_marks.getText();
        String pem = pem_marks.getText();
        
         if (!isValidMark(oop) || !isValidMark(os) || !isValidMark(coa) || !isValidMark(pem) || !isValidMark(dm)) {
        	Alert alert1 = new Alert(AlertType.ERROR);
            alert1.setTitle("Error");
            alert1.setHeaderText(null);
            alert1.setContentText("not valid marks ");
            alert1.showAndWait();
            return; 
        }
        
        else {
        	 try (FileWriter writer = new FileWriter("result.txt", true);
                     BufferedWriter bw = new BufferedWriter(writer);
                     PrintWriter out = new PrintWriter(bw)) {
               	 
                    out.println(student_id+":"+coa+":"+dm+":"+oop+":"+os+":"+pem);
                    Alert alert1 = new Alert(AlertType.INFORMATION);
                    alert1.setTitle("information");
                    alert1.setHeaderText(null);
                    alert1.setContentText("enter sucessfully..!");
                    alert1.showAndWait();
                   

                } catch (IOException e) {
                    e.printStackTrace();
                    
                }
        
   
        }
        }
    }
    
    private boolean isValidMark(String mark) {
		   try {
             int value = Integer.parseInt(mark);
             return value > 0 && value <= 100;
         } catch (NumberFormatException e) {
             return false; // Invalid mark if not a valid integer
         }
	}
	
	 
	 
	public void roleList1() {

	        List<String> listR = new ArrayList<String>();

	        for (String data : ListData.std_data) {
	            listR.add(data);
	        }

	        ObservableList listData = FXCollections.observableArrayList(listR);
	        std_id.setItems(listData);

	    }
	    
	
	
	 public void initialize(URL location, ResourceBundle resources) {
		 roleList1();
	                
	    }
	    
}