package controller;

import domain.AbsenceHistory;
import domain.Attendance;
import domain.AttendanceState;
import domain.Calender;
import domain.Command;
import domain.Crew;
import domain.FeatureType;
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

    private static final String FILE_PATH = "src/main/resources/attendances.csv";
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final int MONTH = 12;

    private LocalDate today;
    private int todayMonth;
    private int todayDay;
    private String todayDayOfWeek;
    private Attendance attendance;

    private final Map<FeatureType, Command> features = Map.of(
            FeatureType.ATTENDANCE_CHECK, this::attendanceCheck,
            FeatureType.ATTENDANCE_UPDATE, this::attendanceUpdate,
            FeatureType.ATTENDANCE_RECORD, this::attendanceRecord,
            FeatureType.READ_ABSENCE, this::readAbsence
    );

    public void run() {
        prepareToday();
        String feature;
        do {
            feature = InputView.inputFeature(todayMonth, todayDay, todayDayOfWeek);
            executeFeature(feature);
        } while (isExit(feature));
    }

    private void prepareToday() {
        attendance = FileManager.readFile(FILE_PATH);
        today = LocalDate.now();
        todayMonth = MONTH;
        todayDay = today.getDayOfMonth();
        todayDayOfWeek = Calender.findBy(todayDay).getDescription();
    }

    private void executeFeature(String feature) {
        FeatureType featureType = FeatureType.findBy(feature);
        features.getOrDefault(featureType, OutputView::printExit).execute();
    }

    private boolean isExit(final String feature) {
        return !FeatureType.isExitType(feature);
    }

    private void attendanceCheck() {
        String nickname = InputView.inputNickName();
        Crew crew = attendance.getCrewByName(nickname);
        String schoolStartTime = InputView.inputSchoolStartTime();

        LocalTime dateTime = parseToLocalTime(schoolStartTime);

        AttendanceState attendanceState = AttendanceState.findStateBy(dateTime, todayDay);

        attendance.save(crew, schoolStartTime, todayDay);

        OutputView.printTodayAttendance(todayDay, todayDayOfWeek, schoolStartTime, attendanceState);
    }

    private void attendanceUpdate() {
        String nickname = InputView.inputUpdateNickName();
        int date = InputView.inputUpdateDate();
        String time = InputView.inputUpdateTime();

        Crew crew = attendance.getCrewByName(nickname);

        LocalDateTime beforeDateTime = attendance.update(crew, time, date);

        LocalTime afterTime = parseToLocalTime(time);
        LocalDateTime afterLocalDateTime = LocalDateTime.of(beforeDateTime.getYear(), beforeDateTime.getMonth(),
                beforeDateTime.getDayOfMonth(), afterTime.getHour(), afterTime.getMinute());

        OutputView.printUpdateAttendance(beforeDateTime, afterLocalDateTime);
    }

    private LocalTime parseToLocalTime(final String schoolStartTime) {
        return LocalTime.parse(schoolStartTime, DATE_TIME_FORMATTER);
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
        Map<Crew, AbsenceResultDto> result = attendance.getAbsence(todayDay);
        OutputView.printAbsenceResult(result);
    }
}
