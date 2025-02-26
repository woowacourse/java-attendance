package controller;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.AttendanceCalculator;
import model.AttendanceStatus;
import model.Student;
import model.StudentRepository;
import util.FileInput;
import model.TodayDate;
import util.LocalDateTimePrintFormatter;
import view.InputView;
import view.OutputView;

public class AttendanceManagementController {

    public void start() {
        TodayDate todayDate = new TodayDate();

        StudentRepository studentRepository = updateStudentAttendanceRecord();
        studentRepository.updateEveryStudentNoInformationInFile(todayDate);

        String userInput = "";

        while (!userInput.equals(MenuOption.QUIT.getOption())){
            userInput = InputView.getUserWantMenu(todayDate);

            if (userInput.equals(MenuOption.ATTENDANCE_CHECK.getOption())) {
                functionForAttendanceCheck(todayDate, studentRepository);
            }
            if (userInput.equals(MenuOption.ATTENDANCE_MODIFY.getOption())) {
                functionForAttendanceModify(studentRepository);
            }
            if (userInput.equals(MenuOption.STUDENT_RECORD_CHECK.getOption())) {
                functionForStudentRecordCheck(studentRepository);
            }
            if (userInput.equals(MenuOption.DISMISSAL_SUBJECT_CHECK.getOption())) {
                functionForDismissalSubjectCheck(studentRepository);
            }
        }
    }

    private StudentRepository updateStudentAttendanceRecord() {
        Map<String, List<LocalDateTime>> studentRecordRepository = FileInput.createStudentRepository();
        List<Student> students = new ArrayList<>();
        for (String name : studentRecordRepository.keySet()) {
            Student student = new Student(name, studentRecordRepository.get(name));
            students.add(student);
        }
        return new StudentRepository(students);
    }

    private static void functionForDismissalSubjectCheck(StudentRepository studentRepository) {
        OutputView.displayAtRiskStudent();
        for (Student student : studentRepository.getStudentRepository()) {
            OutputView.printDismissalSubject(AttendanceCalculator.recordAttendanceResult(
                    student.getTimeRecords()), student.getName());
        }
    }

    private static void functionForStudentRecordCheck(StudentRepository studentRepository) {
        String name = InputView.getStudentForAttendanceCheckUntilExist(studentRepository);
        OutputView.printAttendanceRecord(studentRepository.findStudentByName(name).getTimeRecords());
        HashMap<AttendanceStatus, Integer> attendanceRecord = AttendanceCalculator.recordAttendanceResult(
                studentRepository.findStudentByName(name).getTimeRecords());
        OutputView.printResult(attendanceRecord);
    }

    private static void functionForAttendanceModify(StudentRepository studentRepository) {
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

    private static void functionForAttendanceCheck(TodayDate todayDate, StudentRepository studentRepository) {
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

    private static boolean isAlreadyAttendance(StudentRepository studentRepository, String name,
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
