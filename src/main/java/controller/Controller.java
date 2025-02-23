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

    public void start() {
        TodayDate todayDate = new TodayDate(LocalDate.of(2024,12,14));
        StudentRecordRepository studentRecordRepository = FileInput.createStudentRepository();
        studentRecordRepository.updateEveryStudentNoInformationInFile(todayDate.getTodayDateTIme());

        while (true){
            String userInput = InputView.getUserWantMenu(todayDate);

            if (userInput.equals("Q")) {
                break;
            }
            if (Integer.parseInt(userInput) == 1) {
                try {
                    InputView.checkAttendanceAvailable(todayDate);
                }catch (IllegalArgumentException e) {
                    continue;
                }
                String name = InputView.getStudentForAttendanceCheckUntilExist(studentRecordRepository);
                LocalDateTime localDateTime = InputView.getLocalDateTimeUntilValidate(todayDate);

                studentRecordRepository.addRecord(name, localDateTime);

                int day = localDateTime.getDayOfWeek().getValue();
                AttendanceStatus todayResult = AttendanceCalculator.calculateAttendance(day,LocalTime.from(localDateTime));
                OutputView.printTodayAttendanceResult(localDateTime,todayResult);
            }
            if (Integer.parseInt(userInput) == 2) {
                String studentName = InputView.getStudentNameForModifyUntilValidate(studentRecordRepository);
                LocalDateTime modifyLocalDateTime = InputView.getLocalDateTimeToModify();
                String recordBeforeModify = LocalDateTimePrintFormatter.LocalDateTimeToLocalTime(studentRecordRepository.getStudentRecord().get(studentName).compareDayIsSame(modifyLocalDateTime));

                studentRecordRepository.modifyRecord(studentName, modifyLocalDateTime);

                int day = modifyLocalDateTime.getDayOfWeek().getValue();

                String recordAfterModifyState = AttendanceCalculator.calculateAttendance(day,LocalTime.from(modifyLocalDateTime)).getState();
                String recordAfterModify = modifyLocalDateTime.format(DateTimeFormatter.ofPattern("HH:mm" + " (" + recordAfterModifyState + ") 수정 완료!"));

                OutputView.printSecondMenu(recordBeforeModify, recordAfterModify);

            }

            if (Integer.parseInt(userInput) == 3) {
                String name = InputView.getStudentForAttendanceCheckUntilExist(studentRecordRepository);
                OutputView.printAttendanceRecord(studentRecordRepository.getStudentRecord().get(name).getTimeRecords());
                HashMap<String, Integer> attendanceRecord = AttendanceCalculator.recordAttendanceResult(studentRecordRepository.getStudentRecordByName(name).getTimeRecords());
                OutputView.printResult(attendanceRecord);
            }

            if (Integer.parseInt(userInput) == 4) {
                OutputView.displayAtRiskStudent();
                for (String name : studentRecordRepository.getStudentRecord().keySet()) {
                    OutputView.printDismissalSubject(AttendanceCalculator.recordAttendanceResult(studentRecordRepository.getStudentRecordByName(name).getTimeRecords()),name);
                }
            }
        }
    }

}
