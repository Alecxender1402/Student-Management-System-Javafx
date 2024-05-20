package application;

public class DataStudentHandle {
    private String teacherID;
    private String name; // Add the name field
    private String gender;
    private String yearOfExperience;
    

    // Constructor
    public DataStudentHandle(String teacherID, String name, String gender, String yearOfExperience) {
        this.teacherID = teacherID;
        this.name = name;
        this.gender = gender;
        this.yearOfExperience = yearOfExperience;
       
    }

    // Getters and setters
    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getYearOfExperience() {
        return yearOfExperience;
    }

    public void setYearOfExperience(String yearOfExperience) {
        this.yearOfExperience = yearOfExperience;
    }

  
}
