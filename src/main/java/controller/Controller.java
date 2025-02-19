package controller;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import model.Student;
import model.StudentRepository;
import model.TodayDate;
import util.FileInput;
import view.InputView;
import view.OutputView;

public class Controller {

    public StudentRepository readFileAndCreateStudentRepository() throws IOException {
        FileInput fileInput = new FileInput();
        StudentRepository studentRepository = new StudentRepository();
        studentRepository.createStudent(fileInput.readAttendanceFile());
        return studentRepository;
    }

    private StudentRepository createStudentRepository(){
        try{
            return readFileAndCreateStudentRepository();
        } catch (IOException e){
            return createStudentRepository();
        }
    }

    public void attendanceStart() {
        TodayDate todayDate = new TodayDate(LocalDate.of(2024, 12, 13));
        InputView.printTodayAndSelectFunction(todayDate.getTodayDate());
        String selectFunction = InputView.getUserInputString();
        StudentRepository studentRepository = createStudentRepository();

        if (selectFunction.equals("Q")){
            return;
        }
        if (Integer.parseInt(selectFunction) == 1) {
            String studentName = getStudentNameUntilExist(studentRepository);
            LocalDateTime localDateTime = getStartTimeUntilValidate(todayDate);
            System.out.println(localDateTime);
            Student student = studentRepository.findStudentByName(studentName);
            student.updateState(localDateTime);
            OutputView.printTodayAttendanceResult(student, localDateTime);
            System.out.println(student.getRecord());
        }

        if (Integer.parseInt(selectFunction) == 2) {

        }
        if (Integer.parseInt(selectFunction) == 3) {

        }
        if (Integer.parseInt(selectFunction) == 4) {

        }
    }

    private String getStudentNameUntilExist(StudentRepository studentRepository) {
        String userName = InputView.printInputNicName();
        try{
            studentRepository.notExistStudent(userName);
            return userName;
        }catch (IllegalArgumentException e){
            return getStudentNameUntilExist(studentRepository);
        }
    }

    private LocalDateTime getStartTimeUntilValidate(TodayDate todayDate) {
        String startTime = InputView.printStartTime();
        try{
            return todayDate.makeLocalDateToLocalDateTime(startTime);
        }catch (DateTimeException e){
            return getStartTimeUntilValidate(todayDate);
        }
    }




    private void attendanceFunctionOne(){

    }
}
