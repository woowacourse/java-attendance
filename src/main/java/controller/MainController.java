package controller;

import domain.Attendance;
import domain.AttendanceState;
import domain.Crew;
import domain.HistoryCalculator;
import dto.AbsenceRecordDto;
import dto.AttendanceHistoryDto;
import dto.AttendanceRecord;
import dto.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import util.DateTimeUtil;
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
            switch (feature) {
                case "1":
                    attendanceCheck();
                    break;
                case "2":
                    attendanceUpdate();
                    break;
                case "3":
                    attendanceHistory();
                    break;
                case "4":
                    absenceHistory();
                    break;
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

        AttendanceState attendanceState = AttendanceState.findStateBy(dateTime,
                LocalDate.of(2024, 12, DateTimeUtil.getTodayDate()));

        attendance.save(crew, schoolStartTime, LocalDate.of(2024, 12, DateTimeUtil.getTodayDate()));

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

        LocalDateTime afterLocalDateTime = LocalDateTime.of(
                2024, 12, date, afterTime.getHour(), afterTime.getMinute());

        OutputView.printUpdateAttendance(beforeTime, afterLocalDateTime);
    }

    private void attendanceHistory() {
        String nickname = InputView.inputNickName();
        Crew crew = attendance.getCrewByName(nickname);

        List<AttendanceRecord> attendanceRecords = attendance.getRecordByCrew(crew);

        AttendanceStatus attendanceStatus = HistoryCalculator.calculateAttendanceRecordBy(attendanceRecords);

        AttendanceHistoryDto attendanceHistoryDto = new AttendanceHistoryDto(crew, attendanceRecords, attendanceStatus);

        OutputView.printRecordAttendance(attendanceHistoryDto);
    }

    private void absenceHistory() {
        List<AbsenceRecordDto> absenceRecordDtos = HistoryCalculator.calculateAbsenceRecordBy(attendance);
        OutputView.printAbsenceResult(absenceRecordDtos);
    }
}
