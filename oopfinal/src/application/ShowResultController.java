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

public class ShowResultController implements Initializable {

    @FXML
    private Label coag;

    @FXML
    private Label coamark;

    @FXML
    private Label dmg;

    @FXML
    private Label dmmark;

    @FXML
    private Label oopg;

    @FXML
    private Label osg;

    @FXML
    private Label oopmark;

    @FXML
    private Label osmark;

    @FXML
    private Label pemg;

    @FXML
    private Label pemmark;

    @FXML
    private Label spi;

    @FXML
    private Label stuid;

    private String name = "";
    private int oop_marks;
    private int os_marks;
    private int dm_marks;
    private int pem_marks;
    private int coa_marks;

    private final ExecutorService executor = Executors.newFixedThreadPool(3);

    private String getLoggedInUsername() {
        
        return SessionManager.getLoggedInUsername();
    }

    private void loadDataFromFile() {
        executor.submit(() -> {
            String filePath = "data.txt";
            String loggedInUsername = getLoggedInUsername();

            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(":");
                    if (data.length == 5 && data[1].equals(loggedInUsername)) {
                        Platform.runLater(() -> {
                            stuid.setText(data[0]);
                            name = data[0];
                        });
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void loadMarks() {
        executor.submit(() -> {
            String filePath = "result.txt";
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(":");
                    if (data.length == 6 && data[0].equals(name)) {
                        Platform.runLater(() -> {
                            oopmark.setText(data[1]);
                            oop_marks = Integer.parseInt(data[1]);
                            osmark.setText(data[2]);
                            os_marks = Integer.parseInt(data[2]);
                            coamark.setText(data[3]);
                            coa_marks = Integer.parseInt(data[3]);
                            pemmark.setText(data[4]);
                            pem_marks = Integer.parseInt(data[4]);
                            dmmark.setText(data[5]);
                            dm_marks = Integer.parseInt(data[5]);
                        });
                        break;
                    }
                }

                if (name.isEmpty()) {
                    Platform.runLater(() -> {
                        oopmark.setText("");
                        osmark.setText("");
                        coamark.setText("");
                        pemmark.setText("");
                        dmmark.setText("");
                        oop_marks = 0;
                        os_marks = 0;
                        coa_marks = 0;
                        pem_marks = 0;
                        dm_marks = 0;
                    });
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private double calculateCGPA() {
        double percentage = ((oop_marks + os_marks + dm_marks + pem_marks + coa_marks) / 500.0) * 100;
        double gradePoints = percentage / 10.0 + 0.5;
        if (gradePoints >= 10) {
            return 10;
        }
        return Math.round(gradePoints * 100.0) / 100.0;
    }

    private void loadSPI() {
        executor.submit(() -> {
            if (oop_marks == 0 && os_marks == 0 && coa_marks == 0 && pem_marks == 0 && dm_marks == 0) {
                Platform.runLater(() -> spi.setText(""));
            } else {
                double cgpa = calculateCGPA();
                Platform.runLater(() -> spi.setText(String.valueOf(cgpa)));
            }
        });
    }

    private void loadGrades() {
        executor.submit(() -> {
            if (oop_marks == 0 && os_marks == 0 && coa_marks == 0 && pem_marks == 0 && dm_marks == 0) {
                Platform.runLater(() -> {
                    oopg.setText("");
                    osg.setText("");
                    coag.setText("");
                    pemg.setText("");
                    dmg.setText("");
                });
            } else {
                Platform.runLater(() -> {
                    oopg.setText(getGrade(oop_marks));
                    osg.setText(getGrade(os_marks));
                    coag.setText(getGrade(coa_marks));
                    pemg.setText(getGrade(pem_marks));
                    dmg.setText(getGrade(dm_marks));
                });
            }
        });
    }

    private String getGrade(int marks) {
        if (marks > 80 && marks <= 100) return "AA";
        if (marks > 70 && marks <= 80) return "AB";
        if (marks > 60 && marks <= 70) return "BB";
        if (marks > 50 && marks <= 60) return "BC";
        if (marks > 40 && marks <= 50) return "CC";
        if (marks > 33 && marks <= 40) return "DD";
        return "FF";
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadDataFromFile();
        executor.submit(() -> {
            // Ensure the name is loaded first
            try {
                Thread.sleep(500); // Delay to ensure student data is loaded
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            loadMarks();
            try {
                Thread.sleep(500); // Delay to ensure marks are loaded
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            loadSPI();
            try {
                Thread.sleep(500); // Delay to ensure SPI is calculated
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            loadGrades();
        });
    }

    // Make sure to properly shutdown the executor when the application is closed
    public void shutdown() {
        executor.shutdown();
    }
}
