package controller;

import domain.AbsenceHistory;
import domain.Attendance;
import domain.AttendanceState;
import domain.Crew;
import dto.AbsenceResultDto;
import dto.AttendanceResultDto;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import util.FileManager;
import util.TodayDateTimeUtil;
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
                attendanceRecord();
            }
            if ("4".equals(feature)) {
                readAbsence();
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

        String attendanceState = AttendanceState.findStateBy(dateTime, TodayDateTimeUtil.nowDate());

        attendance.save(crew, schoolStartTime);

        OutputView.printTodayAttendance(schoolStartTime, attendanceState);
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

        List<AttendanceResultDto> attendanceResultDtos = attendance.readRecord(crew);
        OutputView.printRecordAttendance(attendanceResultDtos);

        AbsenceHistory absenceHistory = new AbsenceHistory(attendanceResultDtos);

        AbsenceResultDto absenceResultDto = absenceHistory.calculate();

        OutputView.printAbsenceHistory(absenceResultDto);
    }

    private void readAbsence() {
        Map<Crew, AbsenceResultDto> result = attendance.getAbsence();
        OutputView.printAbsenceResult(result);
    }
}
