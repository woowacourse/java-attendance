package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import model.AttendanceCalculator;
import model.AttendanceStatus;
import util.FileInput;
import model.StudentRecordRepository;
import model.TodayDate;
import util.LocalDateTimePrintFormatter;
import view.InputView;
import view.OutputView;

public class Controller {
    private final TodayDate todayDate = new TodayDate(LocalDate.of(2024,12,12));
    private final StudentRecordRepository studentRecordRepository = FileInput.createStudentRepository();
    private final int ATTENDANCE_CHECK = 1;
    private final int ATTENDANCE_MODIFY = 2;
    private final int STUDENT_RECORD_CHECK = 3;
    private final int DISMISSAL_SUBJECT_CHECK = 4;
    private final String QUICK = "Q";

    public void start() {
        studentRecordRepository.updateEveryStudentNoInformationInFile(todayDate.getTodayDateTIme());
        while (true){
            String userInput = InputView.getUserWantMenu(todayDate);
            if (userInput.equals(QUICK)) {
                break;
            }
            if (Integer.parseInt(userInput) == ATTENDANCE_CHECK) {
                if (functionForMenuOne(todayDate, studentRecordRepository)) {
                    continue;
                }
            }
            if (Integer.parseInt(userInput) == ATTENDANCE_MODIFY) {
                if (functionForMenuTwo(studentRecordRepository)) {
                    continue;
                }

            }
            if (Integer.parseInt(userInput) == STUDENT_RECORD_CHECK) {
                functionForMenuThree(studentRecordRepository);
            }
            if (Integer.parseInt(userInput) == DISMISSAL_SUBJECT_CHECK) {
                functionForMenuFour(studentRecordRepository);
            }
        }
    }

    private static void functionForMenuFour(StudentRecordRepository studentRecordRepository) {
        OutputView.displayAtRiskStudent();
        for (String name : studentRecordRepository.getStudentRecord().keySet()) {
            OutputView.printDismissalSubject(AttendanceCalculator.recordAttendanceResult(
                    studentRecordRepository.getStudentRecordByName(name).getTimeRecords()),name);
        }
    }

    private static void functionForMenuThree(StudentRecordRepository studentRecordRepository) {
        String name = InputView.getStudentForAttendanceCheckUntilExist(studentRecordRepository);
        OutputView.printAttendanceRecord(studentRecordRepository.getStudentRecord().get(name).getTimeRecords());
        HashMap<String, Integer> attendanceRecord = AttendanceCalculator.recordAttendanceResult(
                studentRecordRepository.getStudentRecordByName(name).getTimeRecords());
        OutputView.printResult(attendanceRecord);
    }

    private static boolean functionForMenuTwo(StudentRecordRepository studentRecordRepository) {
        String studentName = InputView.getStudentNameForModifyUntilValidate(studentRecordRepository);
        LocalDateTime modifyLocalDateTime = InputView.getLocalDateTimeToModify();
        if (isHolidayForMenuTwo(modifyLocalDateTime)) {
            return true;
        }
        String recordBeforeModify = LocalDateTimePrintFormatter.LocalDateTimeToLocalTime(
                studentRecordRepository.getStudentRecord().get(studentName).compareDayIsSame(modifyLocalDateTime));

        studentRecordRepository.modifyRecord(studentName, modifyLocalDateTime);

        int day = modifyLocalDateTime.getDayOfWeek().getValue();

        String recordAfterModifyState = AttendanceCalculator.calculateAttendance(day,LocalTime.from(modifyLocalDateTime)).getState();
        String recordAfterModify = modifyLocalDateTime.format(DateTimeFormatter.ofPattern("HH:mm" + " (" + recordAfterModifyState + ") 수정 완료!"));

        OutputView.printSecondMenu(recordBeforeModify, recordAfterModify);
        return false;
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

    private static boolean functionForMenuOne(TodayDate todayDate, StudentRecordRepository studentRecordRepository) {
        if (isHoliday(todayDate)) {
            return true;
        }

        String name = InputView.getStudentForAttendanceCheckUntilExist(studentRecordRepository);
        if (isAlreadyAttendance(studentRecordRepository, name, todayDate)) {
            return true;
        }

        LocalDateTime localDateTime = InputView.getLocalDateTimeUntilValidate(todayDate);
        studentRecordRepository.addRecord(name, localDateTime);

        int day = localDateTime.getDayOfWeek().getValue();
        AttendanceStatus todayResult = AttendanceCalculator.calculateAttendance(day,LocalTime.from(localDateTime));
        OutputView.printTodayAttendanceResult(localDateTime,todayResult);
        return false;
    }

    private static boolean isAlreadyAttendance(StudentRecordRepository studentRecordRepository, String name,
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
            InputView.checkAttendanceAvailable(todayDate);
        }catch (IllegalArgumentException e) {
            return true;
        }
        return false;
    }

}
