package application;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

public class ListData{
	
public static String admin_username;
    
    public static String teacher_username;
    
    public static String student_username;
    
    public static String[] role = {"Admin", "Teacher", "Student"};
    
    public static String[] std_data = {"1", "2", "3","4","5","6","7","8","9","10"};

    public static String[] year = {"1st year", "2nd Year", "3rd Year", "4th Year"};

    public static String[] course = {"BSCS", "BSIT", "BS"};

    public static String[] section = {"A", "B", "C", "D", "E"};

    public static String[] paymentStatus = {"Paid", "Pending"};

    public static String[] status = {"Active", "Inactive", "Approval"};

    public static String[] semester = {"1st Semester", "2nd Semester"};

    public static List<String> gender = Arrays.asList("Male", "Female", "Other");

    public static String[] yearExperience = {"1 Year", "2 years", "3 Years", "4 Years or more"};
    
    public static String[] department = {"Department of BSIT", "Department of BSCS"};
    
    public static String[] statusA = {"Available", "Unavailable"};

    public static String path;

    public static String temp_studentNumber;
    public static String temp_studentName;
    public static Date temp_studentBirthDate;
    public static String temp_studentYear;
    public static String temp_studentCourse;
    public static String temp_studentSection;
    public static String temp_studentSubject;
    public static String temp_studentGender;
    public static String temp_studentSemester;
    public static Double temp_studentPay;
    public static String temp_studentPaymentStatus;
    public static String temp_studentStatus;

}