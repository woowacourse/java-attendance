package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.AttendanceCalculator;
import model.AttendanceDateTime;
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
        students.updateMissingAttendanceRecords(todayDate);

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
        Map<String, List<AttendanceDateTime>> studentRecordRepository = FileInput.readFileAndCreateStudentRepository();
        List<Student> students = new ArrayList<>();
        for (String name : studentRecordRepository.keySet()) {
            StudentAttendanceHistory studentAttendanceHistory = new StudentAttendanceHistory(studentRecordRepository.get(name));
            Student student = new Student(name, studentAttendanceHistory);
            students.add(student);
        }
        return new Students(students);
    }

    private static void functionForDismissalSubjectCheck(Students students) {
        OutputView.displayAtRiskStudent();
        for (Student student : students.getStudents()) {
            OutputView.printDismissalSubject(AttendanceCalculator.recordAttendanceResult(
                    student.getStudentAttendanceHistory().getAttendanceHistory()), student.getName());
        }
    }

    private static void functionForStudentRecordCheck(Students students) {
        String name = InputView.getStudentForAttendanceCheckUntilExist(students);
        students.findStudentByName(name).sortStudentAttendanceHistory();

        OutputView.printAttendanceRecord(students.findStudentByName(name).getStudentAttendanceHistory()
                .getAttendanceHistory());
        HashMap<AttendanceStatus, Integer> attendanceRecord = AttendanceCalculator.recordAttendanceResult(
                students.findStudentByName(name).getStudentAttendanceHistory().getAttendanceHistory());
        OutputView.printResult(attendanceRecord);
    }

    private static void functionForAttendanceModify(Students students) {
        String studentName = InputView.getStudentNameForModifyUntilValidate(students);
        AttendanceDateTime modifyLocalDateTime = InputView.getAttendanceDateTimeToModify();
        if (isHolidayFofAttendanceModify(modifyLocalDateTime)) {
            return;
        }
        String recordBeforeModify = LocalDateTimePrintFormatter.createAttendanceResultMessage(
                students.findStudentByName(studentName).findSameDay(modifyLocalDateTime));

        students.findStudentByName(studentName).modifyRecord(modifyLocalDateTime);

        String recordAfterModifyState = AttendanceCalculator.calculateAttendance(modifyLocalDateTime, modifyLocalDateTime.toLocalTime()).getState();
        String recordAfterModify = LocalDateTimePrintFormatter.createModifyCompleteMessage(modifyLocalDateTime,recordAfterModifyState);

        OutputView.printSecondMenu(recordBeforeModify, recordAfterModify);
    }

    private static boolean isHolidayFofAttendanceModify(AttendanceDateTime modifyLocalDateTime) {
        try {
            if(modifyLocalDateTime.isChristmas() || modifyLocalDateTime.isWeekend()) {
                throw new IllegalArgumentException("[ERROR] 주말 및 공휴일에는 출석을 수정할 수 없습니다.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return true;
        }
        return false;
    }

    private static void functionForAttendanceCheck(TodayDate todayDate, Students students) {
        if (isHoliday(todayDate)) {
            return;
        }

        String name = InputView.getStudentForAttendanceCheckUntilExist(students);
        if (isAlreadyAttendance(students, name, todayDate)) {
            return;
        }

        AttendanceDateTime attendanceDateTime = InputView.getAttendanceDateTimeUntilValidate(todayDate);
        students.findStudentByName(name).addTime(attendanceDateTime);

        AttendanceStatus todayResult = AttendanceCalculator.calculateAttendance(attendanceDateTime, attendanceDateTime.toLocalTime());
        OutputView.printTodayAttendanceResult(attendanceDateTime, todayResult);
    }

    private static boolean isAlreadyAttendance(Students students, String name,
                                               TodayDate todayDate) {
        try {
            students.findStudentByName(name).validateAlreadyAttendanceDate(todayDate);
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
                        todayDate.toAttendanceDateTime()));}
        }catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return true;
        }
        return false;
    }

}
