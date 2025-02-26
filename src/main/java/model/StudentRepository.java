package model;

import java.util.ArrayList;
import java.util.List;

public class StudentRepository {

    List<Student> studentRepository;

    public StudentRepository(List<Student> studentRepository) {
        this.studentRepository = studentRepository;
    }

    public boolean isExistStudent(String name) {
        for (Student student : studentRepository) {
            if (!student.getName().equals(name)) {
                continue;
            }
            return true;
        }
        return false;
    }

    public Student findStudentByName(String name) {
        for (Student student : studentRepository) {
            if (student.getName().equals(name)) {
                return student;
            }
        }
        return null;
    }

    public void updateEveryStudentNoInformationInFile(TodayDate todayDate) {
        for (Student student : studentRepository) {
            student.updateNoInformationInFile(todayDate.getTodayDateTIme());
        }
    }

    public List<Student> getStudentRepository() {
        return studentRepository;
    }
}
