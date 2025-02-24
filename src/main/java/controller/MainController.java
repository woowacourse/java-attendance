package controller;

import domain.AbsenceHistory;
import domain.Attendance;
import domain.AttendanceState;
import domain.Calender;
import domain.Command;
import domain.Crew;
import domain.DateProvider;
import domain.FeatureType;
import dto.AbsenceResultDto;
import dto.AttendanceResultDto;
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

    private final InputView inputView;
    private final OutputView outputView;
    private final DateProvider provider;
    private final Attendance attendance = FileManager.readFile(FILE_PATH);

    private final Map<FeatureType, Command> features = Map.of(
            FeatureType.ATTENDANCE_CHECK, this::attendanceCheck,
            FeatureType.ATTENDANCE_UPDATE, this::attendanceUpdate,
            FeatureType.ATTENDANCE_RECORD, this::attendanceRecord,
            FeatureType.READ_ABSENCE, this::readAbsence
    );

    public MainController(final InputView inputView, final OutputView outputView, final DateProvider provider) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.provider = provider;
    }

    public void run() {
        String feature;
        do {
            feature = inputView.inputFeature(provider.getTodayMonth(), provider.getToday(), getDayOfWeek());
            executeFeature(feature);
        } while (isExit(feature));
    }

    private void executeFeature(String feature) {
        FeatureType featureType = FeatureType.findBy(feature);
        features.getOrDefault(featureType, outputView::printExit).execute();
    }

    private boolean isExit(final String feature) {
        return !FeatureType.isExitType(feature);
    }

    private void attendanceCheck() {
        String nickname = inputView.inputNickName();
        Crew crew = attendance.getCrewByName(nickname);
        String schoolStartTime = inputView.inputSchoolStartTime();

        LocalTime dateTime = parseToLocalTime(schoolStartTime);

        AttendanceState attendanceState = AttendanceState.findStateBy(dateTime, provider.getToday());

        attendance.save(crew, schoolStartTime, provider.getToday());

        outputView.printTodayAttendance(provider.getToday(), getDayOfWeek(), schoolStartTime, attendanceState);
    }

    private String getDayOfWeek() {
        return Calender.findBy(provider.getToday()).getDescription();
    }

    private void attendanceUpdate() {
        String nickname = inputView.inputUpdateNickName();
        int date = inputView.inputUpdateDate();
        String time = inputView.inputUpdateTime();

        Crew crew = attendance.getCrewByName(nickname);

        LocalDateTime beforeDateTime = attendance.update(crew, time, date);

        LocalTime afterTime = parseToLocalTime(time);
        LocalDateTime afterLocalDateTime = LocalDateTime.of(beforeDateTime.getYear(), beforeDateTime.getMonth(),
                beforeDateTime.getDayOfMonth(), afterTime.getHour(), afterTime.getMinute());

        outputView.printUpdateAttendance(beforeDateTime, afterLocalDateTime);
    }

    private LocalTime parseToLocalTime(final String schoolStartTime) {
        return LocalTime.parse(schoolStartTime, DATE_TIME_FORMATTER);
    }

    private void attendanceRecord() {
        String nickname = inputView.inputNickName();
        Crew crew = attendance.getCrewByName(nickname);

        List<AttendanceResultDto> attendanceResultDtos = attendance.readRecord(crew, provider.getToday());
        outputView.printRecordAttendance(attendanceResultDtos);

        AbsenceHistory absenceHistory = new AbsenceHistory(attendanceResultDtos);

        AbsenceResultDto absenceResultDto = absenceHistory.calculate();

        outputView.printAbsenceHistory(absenceResultDto);
    }

    private void readAbsence() {
        Map<Crew, AbsenceResultDto> result = attendance.getAbsence(provider.getToday());
        outputView.printAbsenceResult(result);
    }
}
