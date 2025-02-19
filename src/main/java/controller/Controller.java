package controller;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import model.Student;
import model.StudentRepository;
import model.TodayDate;
import util.FileInput;
import util.LocalDateTimePrintFormatter;
import view.InputView;
import view.OutputView;

public class Controller {

    public StudentRepository readFileAndCreateStudentRepository() throws IOException {
        FileInput fileInput = new FileInput();
        StudentRepository studentRepository = new StudentRepository();
        studentRepository.createStudent(fileInput.readAttendanceFile());
        return studentRepository;
    }

    public StudentRepository createStudentRepository(){
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
            InputView.printInputNicName();
            String studentName = getStudentNameUntilExist(studentRepository);
            InputView.printStartTime();
            LocalDateTime localDateTime = getTimeUntilValidate(todayDate.getTodayDate());
            Student student = studentRepository.findStudentByName(studentName);
            student.updateState(localDateTime);
            OutputView.printTodayAttendanceResult(student, localDateTime);
        }

        if (Integer.parseInt(selectFunction) == 2) {
            InputView.printStudentNameForModify();
            String studentName = getStudentNameUntilExist(studentRepository);
            Student student = studentRepository.findStudentByName(studentName);

            int modifyDate = InputView.inputDateForModify();

            InputView.printTimeForModify();
            LocalDate localDate = LocalDate.of(2024, 12, modifyDate);

            LocalDateTime modifyLocalDateTime = getTimeUntilValidate(localDate);

            String recordBeforeModify = LocalDateTimePrintFormatter.LocalDateTimeToLocalTime(student.findLocalDateTime(modifyLocalDateTime)) + student.findStateByLocalDateTime(modifyLocalDateTime);

            student.updateState(modifyLocalDateTime);
            String recordAfterModify = student.findStateByLocalDateTime(modifyLocalDateTime);
            String localDateTimeFormat3 = modifyLocalDateTime.format(DateTimeFormatter.ofPattern("HH:mm" + " (" + recordAfterModify + ") 수정 완료!"));

            OutputView.printSecondMenu(recordBeforeModify, localDateTimeFormat3);
        }
        if (Integer.parseInt(selectFunction) == 3) {
            InputView.printInputNicName();
            String studentName = getStudentNameUntilExist(studentRepository);
            OutputView.printAttendanceRecord(studentRepository.findStudentByName(studentName).getRecord());
            studentRepository.findStudentByName(studentName).calculateAbsent();
            OutputView.printStudentState(studentRepository.findStudentByName(studentName));
            OutputView.printStudentPunishmentLabel(studentRepository.findStudentByName(studentName));
        }
        if (Integer.parseInt(selectFunction) == 4) {
            OutputView.printEveryStudentPunishmentLabel(studentRepository);
        }
    }

    private String getStudentNameUntilExist(StudentRepository studentRepository) {
        String userName = InputView.userInput();
        try{
            studentRepository.notExistStudent(userName);
            return userName;
        }catch (IllegalArgumentException e){
            return getStudentNameUntilExist(studentRepository);
        }
    }

    private LocalDateTime getTimeUntilValidate(LocalDate localDate) {
        try{
            return InputView.makeLocalDateToLocalDateTime(localDate);
        }catch (DateTimeException e){
            return getTimeUntilValidate(localDate);
        }
    }
}
