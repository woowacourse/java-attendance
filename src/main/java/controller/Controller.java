package controller;

import constant.DateFormatInformation;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Student;
import model.StudentRepository;
import util.FileInput;
import util.LocalDateTimePrintFormatter;
import view.InputView;
import view.OutputView;

public class Controller {
    private static final LocalDateTime TODAY = LocalDateTime.of(2024, 12, 13, 10, 0);
    private static final int CHECK_IN = 1;
    private static final int MODIFY_ATTENDANCE = 2;
    private static final int CHECK_BY_CREW = 3;
    private static final int CHECK_DROPOUT_RISK = 4;


    private StudentRepository readFileAndCreateStudentRepository() throws IOException {
        FileInput fileInput = new FileInput();
        List<Student> students = fileInput.createStudents();
        return new StudentRepository(students);
    }

    public StudentRepository createStudentRepository() {
        try {
            return readFileAndCreateStudentRepository();
        } catch (IOException e) {
            return createStudentRepository();
        }
    }

    public void attendanceStart() {
        LocalDate todayDate = LocalDate.from(TODAY);
        InputView.printTodayAndSelectFunction(todayDate);
        StudentRepository studentRepository = createStudentRepository();

        for (Student student : studentRepository.getStudents()) {
            student.getAttendanceRecords().updateStateNotExistInFile(todayDate);
        }

        while (true) {
            String selectFunction = InputView.getUserInputString();
            if (selectFunction.equals("Q")) {
                break;
            }
            if (Integer.parseInt(selectFunction) == CHECK_IN) {
                functionForMenuOne(studentRepository, todayDate);
            }
            if (Integer.parseInt(selectFunction) == MODIFY_ATTENDANCE) {
                functionForMenuTwo(studentRepository);
            }
            if (Integer.parseInt(selectFunction) == CHECK_BY_CREW) {
                functionForMenuThree(studentRepository);
            }
            if (Integer.parseInt(selectFunction) == CHECK_DROPOUT_RISK) {
                functionForMenuFour(studentRepository);
            }
        }

    }

    private static void functionForMenuFour(StudentRepository studentRepository) {
        OutputView.printEveryStudentPunishmentLabel(studentRepository);
    }

    private void functionForMenuThree(StudentRepository studentRepository) {
        String studentName = getStudentForAttendanceCheckUntilExist(studentRepository);
        OutputView.printAttendanceRecord(studentRepository
                .findStudentByName(studentName).getAttendanceRecords().getRecord());
        studentRepository.findStudentByName(studentName).calculateAbsent();
        OutputView.printStudentState(studentRepository.findStudentByName(studentName));
        OutputView.printStudentPunishmentLabel(studentRepository.findStudentByName(studentName));
    }

    private void functionForMenuTwo(StudentRepository studentRepository) {
        String studentName = getStudentNameForModifyUntilValidate(studentRepository);
        Student student = studentRepository.findStudentByName(studentName);
        LocalDateTime modifyLocalDateTime = getLocalDateTimeToModify();
        LocalDate modifyLocalDate = LocalDate.from(modifyLocalDateTime);
        String recordBeforeModify = LocalDateTimePrintFormatter
                .LocalDateTimeToLocalTime(modifyLocalDate,
                        student.getAttendanceRecords().getRecord().get(modifyLocalDate)) +
                student.findStateByLocalDateTime(modifyLocalDateTime);

        student.modifyAttendanceRecord(modifyLocalDateTime);
        String recordAfterModify = student.findStateByLocalDateTime(modifyLocalDateTime);

        String localDateTimeFormat = modifyLocalDateTime.format(DateTimeFormatter.ofPattern(
                DateFormatInformation.LOCAL_TIME_FORMATTER + " (" + recordAfterModify + ") 수정 완료!"));

        OutputView.printSecondMenu(recordBeforeModify, localDateTimeFormat);
    }

    private LocalDateTime getLocalDateTimeToModify() {
        int modifyDate = InputView.inputDateForModify();
        InputView.printTimeForModify();
        LocalDate localDate = LocalDate.of(2024, 12, modifyDate);
        return getTimeUntilValidate(localDate);
    }

    private void functionForMenuOne(StudentRepository studentRepository, LocalDate todayDate) {
        String studentName = getStudentForAttendanceCheckUntilExist(studentRepository);
        LocalDateTime localDateTime = getLocalDateTimeUntilValidate(todayDate);
        Student student = studentRepository.findStudentByName(studentName);
        student.attendanceRegister(localDateTime);
        OutputView.printTodayAttendanceResult(student, localDateTime);
    }

    private String getStudentNameForModifyUntilValidate(StudentRepository studentRepository) {
        try {
            InputView.printStudentNameForModify();
            return getStudentNameUntilExist(studentRepository);
        } catch (IllegalArgumentException e) {
            return getStudentNameForModifyUntilValidate(studentRepository);
        }
    }

    private LocalDateTime getLocalDateTimeUntilValidate(LocalDate todayDate) {
        try {
            InputView.printStartTime();
            return getTimeUntilValidate(todayDate);
        } catch (IllegalArgumentException e) {
            return getLocalDateTimeUntilValidate(todayDate);
        }
    }

    private String getStudentForAttendanceCheckUntilExist(StudentRepository studentRepository) {
        InputView.printInputNicName();
        try {
            return getStudentNameUntilExist(studentRepository);
        } catch (IllegalArgumentException e) {
            return getStudentForAttendanceCheckUntilExist(studentRepository);
        }
    }

    private String getStudentNameUntilExist(StudentRepository studentRepository) {
        String userName = InputView.userInput();
        try {
            studentRepository.notExistStudent(userName);
            return userName;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException();
        }
    }

    private LocalDateTime getTimeUntilValidate(LocalDate localDate) {
        try {
            LocalDateTime localDateTimeToAttendanceCheck = InputView.makeLocalDateToLocalDateTime(localDate);
            InputView.isNotOpeningHour(localDateTimeToAttendanceCheck);
            return localDateTimeToAttendanceCheck;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw new IllegalArgumentException();
        }
    }
}
