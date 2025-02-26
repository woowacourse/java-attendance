package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.AttendanceCalculator;
import model.AttendanceStatus;
import model.Student;
import model.StudentAttendanceHistory;
import model.Students;
import util.FileInput;
import model.TodayDate;
import util.LocalDateTimePrintFormatter;
import view.InputView;
import view.OutputView;

public class AttendanceManagementController {

    private final TodayDateGenerator todayDateGenerator;

    public AttendanceManagementController(final TodayDateGenerator todayDateGenerator) {
        this.todayDateGenerator = todayDateGenerator;
    }
    public void start() {

        final TodayDate todayDate = new TodayDate(todayDateGenerator.generate());

        Students students = updateStudentAttendanceRecord();
        students.updateEveryStudentNoInformationInFile(todayDate);

        String userInput = "";

        while (!userInput.equals(MenuOption.QUIT.getOption())){
            userInput = InputView.getUserWantMenu(todayDate);

            if (userInput.equals(MenuOption.ATTENDANCE_CHECK.getOption())) {
                functionForAttendanceCheck(todayDate, students);
            }
            if (userInput.equals(MenuOption.ATTENDANCE_MODIFY.getOption())) {
                functionForAttendanceModify(students);
            }
            if (userInput.equals(MenuOption.STUDENT_RECORD_CHECK.getOption())) {
                functionForStudentRecordCheck(students);
            }
            if (userInput.equals(MenuOption.DISMISSAL_SUBJECT_CHECK.getOption())) {
                functionForDismissalSubjectCheck(students);
            }
        }
    }

    public Students updateStudentAttendanceRecord() {
        Map<String, List<LocalDateTime>> studentRecordRepository = FileInput.createStudentRepository();
        List<Student> students = new ArrayList<>();
        for (String name : studentRecordRepository.keySet()) {
            StudentAttendanceHistory studentAttendanceHistory = new StudentAttendanceHistory(studentRecordRepository.get(name));
            Student student = new Student(name, studentAttendanceHistory);
            students.add(student);
        }
        return new Students(students);
    }

    private static void functionForDismissalSubjectCheck(Students studentRepository) {
        OutputView.displayAtRiskStudent();
        for (Student student : studentRepository.getStudentRepository()) {
            OutputView.printDismissalSubject(AttendanceCalculator.recordAttendanceResult(
                    student.getStudentAttendanceHistory().getAttendanceHistory()), student.getName());
        }
    }

    private static void functionForStudentRecordCheck(Students studentRepository) {
        String name = InputView.getStudentForAttendanceCheckUntilExist(studentRepository);
        OutputView.printAttendanceRecord(studentRepository.findStudentByName(name).getStudentAttendanceHistory()
                .getAttendanceHistory());
        HashMap<AttendanceStatus, Integer> attendanceRecord = AttendanceCalculator.recordAttendanceResult(
                studentRepository.findStudentByName(name).getStudentAttendanceHistory().getAttendanceHistory());
        OutputView.printResult(attendanceRecord);
    }

    private static void functionForAttendanceModify(Students studentRepository) {
        String studentName = InputView.getStudentNameForModifyUntilValidate(studentRepository);
        LocalDateTime modifyLocalDateTime = InputView.getLocalDateTimeToModify();
        if (isHolidayForMenuTwo(modifyLocalDateTime)) {
            return;
        }
        String recordBeforeModify = LocalDateTimePrintFormatter.createAttendanceResultMessage(
                studentRepository.findStudentByName(studentName).findSameDay(modifyLocalDateTime));

        studentRepository.findStudentByName(studentName).modifyRecord(modifyLocalDateTime);

        String recordAfterModifyState = AttendanceCalculator.calculateAttendance(modifyLocalDateTime,LocalTime.from(modifyLocalDateTime)).getState();
        String recordAfterModify = LocalDateTimePrintFormatter.creatModifyCompleteMessage(modifyLocalDateTime,recordAfterModifyState);

        OutputView.printSecondMenu(recordBeforeModify, recordAfterModify);
    }

    private static boolean isHolidayForMenuTwo(LocalDateTime modifyLocalDateTime) {
        try {
            if(AttendanceCalculator.checkHoliday(modifyLocalDateTime)) {
                throw new IllegalArgumentException("[ERROR] 주말 및 공휴일에는 출석을 수정할 수 없습니다.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return true;
        }
        return false;
    }

    private static void functionForAttendanceCheck(TodayDate todayDate, Students studentRepository) {
        if (isHoliday(todayDate)) {
            return;
        }

        String name = InputView.getStudentForAttendanceCheckUntilExist(studentRepository);
        if (isAlreadyAttendance(studentRepository, name, todayDate)) {
            return;
        }

        LocalDateTime localDateTime = InputView.getLocalDateTimeUntilValidate(todayDate);
        studentRepository.findStudentByName(name).addTime(localDateTime);

        AttendanceStatus todayResult = AttendanceCalculator.calculateAttendance(localDateTime,LocalTime.from(localDateTime));
        OutputView.printTodayAttendanceResult(localDateTime,todayResult);
    }

    private static boolean isAlreadyAttendance(Students studentRepository, String name,
                                               TodayDate todayDate) {
        try {
            studentRepository.findStudentByName(name).isAlreadyAttendanceDate(todayDate);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return true;
        }
        return false;
    }

    private static boolean isHoliday(TodayDate todayDate) {
        try {
            if (todayDate.isHoliday()) {
                throw new IllegalArgumentException(LocalDateTimePrintFormatter.createNonSchoolDayMessage(
                        todayDate.getTodayDate()));}
        }catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return true;
        }
        return false;
    }

}
