package controller;

import java.io.IOException;
import model.StudentRepository;
import util.FileInput;

public class Controller {

    public StudentRepository readFileAndCreateStudentRepository() throws IOException {
        FileInput fileInput = new FileInput();
        StudentRepository studentRepository = new StudentRepository();
        studentRepository.createStudent(fileInput.readAttendanceFile());
        return studentRepository;
    }

    public void attendanceStart() {
    }
}
