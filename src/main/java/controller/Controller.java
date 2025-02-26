package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import model.AttendanceCalculator;
import model.AttendanceStatus;
import util.FileInput;
import model.StudentAttendanceRecord;
import model.TodayDate;
import util.LocalDateTimePrintFormatter;
import view.InputView;
import view.OutputView;

public class Controller {
    private final TodayDate todayDate;

    public Controller(TodayDate todayDate) {
        this.todayDate = todayDate;
    }

    public void start() {

        StudentAttendanceRecord studentRecordRepository = updateStudentAttendanceRecord();

        while (true){
            String userInput = InputView.getUserWantMenu(todayDate);
            if (userInput.equals(MenuOption.QUICK.getOption())) {
                break;
            }
            if (Integer.parseInt(userInput) == MenuOption.ATTENDANCE_CHECK.getNumberOption()) {
                functionForMenuOne(todayDate, studentRecordRepository);
            }
            if (Integer.parseInt(userInput) == MenuOption.ATTENDANCE_MODIFY.getNumberOption()) {
                functionForMenuTwo(studentRecordRepository);
            }
            if (Integer.parseInt(userInput) == MenuOption.STUDENT_RECORD_CHECK.getNumberOption()) {
                functionForMenuThree(studentRecordRepository);
            }
            if (Integer.parseInt(userInput) == MenuOption.DISMISSAL_SUBJECT_CHECK.getNumberOption()) {
                functionForMenuFour(studentRecordRepository);
            }
        }
    }

    private StudentAttendanceRecord updateStudentAttendanceRecord() {
        StudentAttendanceRecord studentRecordRepository = FileInput.createStudentRepository();
        studentRecordRepository.updateEveryStudentNoInformationInFile(todayDate.getTodayDateTIme());
        return studentRecordRepository;
    }

    private static void functionForMenuFour(StudentAttendanceRecord studentRecordRepository) {
        OutputView.displayAtRiskStudent();
        for (String name : studentRecordRepository.getStudentRecord().keySet()) {
            OutputView.printDismissalSubject(AttendanceCalculator.recordAttendanceResult(
                    studentRecordRepository.getStudentRecordByName(name).getTimeRecords()),name);
        }
    }

    private static void functionForMenuThree(StudentAttendanceRecord studentRecordRepository) {
        String name = InputView.getStudentForAttendanceCheckUntilExist(studentRecordRepository);
        OutputView.printAttendanceRecord(studentRecordRepository.getStudentRecord().get(name).getTimeRecords());
        HashMap<String, Integer> attendanceRecord = AttendanceCalculator.recordAttendanceResult(
                studentRecordRepository.getStudentRecordByName(name).getTimeRecords());
        OutputView.printResult(attendanceRecord);
    }

    private static void functionForMenuTwo(StudentAttendanceRecord studentRecordRepository) {
        String studentName = InputView.getStudentNameForModifyUntilValidate(studentRecordRepository);
        LocalDateTime modifyLocalDateTime = InputView.getLocalDateTimeToModify();
        if (isHolidayForMenuTwo(modifyLocalDateTime)) {
            return;
        }
        String recordBeforeModify = LocalDateTimePrintFormatter.createAttendanceResultMessage(
                studentRecordRepository.getStudentRecord().get(studentName).findSameDay(modifyLocalDateTime));

        studentRecordRepository.modifyRecord(studentName, modifyLocalDateTime);

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

    private static void functionForMenuOne(TodayDate todayDate, StudentAttendanceRecord studentRecordRepository) {
        if (isHoliday(todayDate)) {
            return;
        }

        String name = InputView.getStudentForAttendanceCheckUntilExist(studentRecordRepository);
        if (isAlreadyAttendance(studentRecordRepository, name, todayDate)) {
            return;
        }

        LocalDateTime localDateTime = InputView.getLocalDateTimeUntilValidate(todayDate);
        studentRecordRepository.addRecord(name, localDateTime);

        AttendanceStatus todayResult = AttendanceCalculator.calculateAttendance(localDateTime,LocalTime.from(localDateTime));
        OutputView.printTodayAttendanceResult(localDateTime,todayResult);
    }

    private static boolean isAlreadyAttendance(StudentAttendanceRecord studentRecordRepository, String name,
                                               TodayDate todayDate) {
        try {
            studentRecordRepository.isAlreadyAttendanceDate(name, todayDate);
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
