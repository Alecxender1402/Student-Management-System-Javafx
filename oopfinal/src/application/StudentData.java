package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StudentData {
    private final StringProperty studentId;
    private final StringProperty username;
    private final StringProperty email;
    private final StringProperty password;
    private final StringProperty name;

    public StudentData(String studentId, String username, String email, String password, String name) {
        this.studentId = new SimpleStringProperty(studentId);
        this.username = new SimpleStringProperty(username);
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);
        this.name = new SimpleStringProperty(name);
    }

    // Getter methods
    public String getStudentId() {
        return studentId.get();
    }

    public String getUsername() {
        return username.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getPassword() {
        return password.get();
    }

    public String getName() {
        return name.get();
    }

    // Property methods
    public StringProperty studentIdProperty() {
        return studentId;
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty emailProperty() {
        return email;
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public StringProperty nameProperty() {
        return name;
    }
}