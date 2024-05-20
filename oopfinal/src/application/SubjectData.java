package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SubjectData {

    private final StringProperty TecherId;
    private final StringProperty TeacherName;
    private final StringProperty TeacherGender;
    private final StringProperty TeacherYearExp;

    public SubjectData(String TecherId, String TeacherName, String TeacherGender,
             String TeacherYearExp) {
    	this.TecherId = new SimpleStringProperty(TecherId);
        this.TeacherName = new SimpleStringProperty(TeacherName);
        this.TeacherGender = new SimpleStringProperty(TeacherGender);
        this.TeacherYearExp = new SimpleStringProperty(TeacherYearExp);
    }

 

    public String getTeacherId() {
        return TecherId.get();
    }

    public String getTeacherName() {
        return TeacherName.get();
    }

    public String getTeacherGender() {
        return TeacherGender.get();
    }

    public String getTeacherYearExp() {
        return TeacherYearExp.get();
    }
    
    public StringProperty teacherIdProperty() {
        return TecherId;
    }

    public StringProperty teachernameProperty() {
        return TeacherName;
    }

    public StringProperty teacherGenderProperty() {
        return TeacherGender;
    }

    public StringProperty teacherYearExpProperty() {
        return TeacherYearExp;
    }

}

