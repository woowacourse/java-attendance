package controller;

import domain.AbsenceHistory;
import domain.Attendance;
import domain.AttendanceState;
import domain.Calender;
import domain.Crew;
import dto.AbsenceResultDto;
import dto.AttendanceResultDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import util.FileManager;
import view.InputView;
import view.OutputView;

public class MainController {

    private LocalDate today;
    private int todayMonth;
    private int todayDay;
    private String todayDayOfWeek;
    private Attendance attendance;

    public void run() {
        prepareToday();
        String feature;
        do {
            feature = InputView.inputFeature(todayMonth, todayDay, todayDayOfWeek);
            if (feature.equals("1")) {
                attendanceCheck();
            }
            if (feature.equals("2")) {
                attendanceUpdate();
            }
            if (feature.equals("3")) {
                attendanceRecord();
            }
            if (feature.equals("4")) {
                readAbsence();
            }

        } while (!feature.equals("Q"));
    }

    private void prepareToday() {
        attendance = FileManager.readFile();
        today = LocalDate.now();
        todayMonth = 12;
        todayDay = today.getDayOfMonth();
        todayDayOfWeek = Calender.findBy(todayDay);
    }

    private void attendanceCheck() {
        String nickname = InputView.inputNickName();
        Crew crew = attendance.getCrewByName(nickname);
        String schoolStartTime = InputView.inputSchoolStartTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime dateTime = LocalTime.parse(schoolStartTime, formatter);

        String attendanceState = AttendanceState.findStateBy(dateTime, todayDay);

        attendance.save(crew, schoolStartTime, todayDay);

        OutputView.printTodayAttendance(todayDay, todayDayOfWeek, schoolStartTime, attendanceState);
    }

    private void attendanceUpdate() {
        String nickname = InputView.inputUpdateNickName();
        int date = Integer.parseInt(InputView.inputUpdateDate());
        String time = InputView.inputUpdateTime();

        Crew crew = attendance.getCrewByName(nickname);

        LocalDateTime beforeDateTime = attendance.update(crew, time, date);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime afterTime = LocalTime.parse(time, formatter);

        LocalDateTime afterLocalDateTime = LocalDateTime.of(beforeDateTime.getYear(), beforeDateTime.getMonth(),
                beforeDateTime.getDayOfMonth(), afterTime.getHour(), afterTime.getMinute());

        OutputView.printUpdateAttendance(beforeDateTime, afterLocalDateTime);
    }

    private void attendanceRecord() {
        String nickname = InputView.inputNickName();
        Crew crew = attendance.getCrewByName(nickname);

        List<AttendanceResultDto> attendanceResultDtos = attendance.readRecord(crew, todayDay);
        OutputView.printRecordAttendance(attendanceResultDtos);

        AbsenceHistory absenceHistory = new AbsenceHistory(attendanceResultDtos);

        AbsenceResultDto absenceResultDto = absenceHistory.calculate();

        OutputView.printAbsenceHistory(absenceResultDto);
    }

    private void readAbsence() {
        Map<Crew, AbsenceResultDto> result = attendance.getAbsence(14);
        OutputView.printAbsenceResult(result);
    }
}
