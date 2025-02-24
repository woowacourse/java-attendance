package controller;

import domain.Attendance;
import domain.AttendanceState;
import domain.Crew;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import util.FileManager;
import view.InputView;
import view.OutputView;

public class MainController {

    private Attendance attendance;

    public void run() {
        prepareToday();
        String feature;
        do {
            feature = InputView.inputFeature();
            if ("1".equals(feature)) {
                attendanceCheck();
            }
            if ("2".equals(feature)) {
                attendanceUpdate();
            }
            if ("3".equals(feature)) {
                attendanceHistory();
            }
            if ("4".equals(feature)) {
                absenceHistory();
            }

        } while (!"Q".equals(feature));
    }

    private void prepareToday() {
        attendance = FileManager.readFile();
    }

    private void attendanceCheck() {
        String nickname = InputView.inputNickName();
        Crew crew = attendance.getCrewByName(nickname);
        String schoolStartTime = InputView.inputSchoolStartTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime dateTime = LocalTime.parse(schoolStartTime, formatter);

//        AttendanceState attendanceState = AttendanceState.findStateBy(dateTime, LocalDate.now());
        AttendanceState attendanceState = AttendanceState.findStateBy(dateTime, LocalDate.of(2024, 12, 16));

//        attendance.save(crew, schoolStartTime, LocalDate.now());
        attendance.save(crew, schoolStartTime, LocalDate.of(2024, 12, 16));

        OutputView.printTodayAttendance(schoolStartTime, attendanceState.getDescription());
    }

    private void attendanceUpdate() {
        String nickname = InputView.inputUpdateNickName();
        int date = Integer.parseInt(InputView.inputUpdateDate());
        String time = InputView.inputUpdateTime();

        Crew crew = attendance.getCrewByName(nickname);

        LocalTime beforeTime = attendance.update(crew, time, date);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime afterTime = LocalTime.parse(time, formatter);

        // 수정 필요...
        LocalDateTime afterLocalDateTime = LocalDateTime.of(
                2024, 12, date, afterTime.getHour(), afterTime.getMinute());

        OutputView.printUpdateAttendance(beforeTime, afterLocalDateTime);
    }

    private void attendanceHistory() {
//        String nickname = InputView.inputNickName();
//        Crew crew = attendance.getCrewByName(nickname);
//
//        AttendanceHistoryDto attendanceHistoryDto = attendance.getAttendanceHistory(crew);
//        OutputView.printRecordAttendance(attendanceHistoryDto);
//
//        AbsenceHistory absenceHistory = new AbsenceHistory(attendanceHistoryDto);
//
//        AbsenceHistoryDto absenceResultDto = absenceHistory.calculate();
//
//        OutputView.printAbsenceHistory(absenceResultDto);
    }

    private void absenceHistory() {
//        Map<Crew, AbsenceHistoryDto> result = attendance.getAbsenceHistory();
//        OutputView.printAbsenceResult(result);
    }
}
