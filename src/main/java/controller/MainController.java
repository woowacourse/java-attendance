package controller;

import domain.AbsenceHistory;
import domain.Attendance;
import domain.AttendanceState;
import domain.Command;
import domain.Crew;
import domain.DateProvider;
import domain.FeatureType;
import dto.AttendanceResultDto;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import util.FileManager;
import view.InputView;
import view.OutputView;

public class MainController {

    private static final String FILE_PATH = "src/main/resources/attendances.csv";

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
        LocalTime attendanceTime = inputView.inputGoTime();

        LocalDateTime attendanceLocalDateTime = provider.createLocalDateTimeBy(attendanceTime);

        AttendanceState attendanceState = AttendanceState.findStateBy(attendanceLocalDateTime);

        Crew crew = attendance.getCrewByName(nickname);
        attendance.save(crew, attendanceLocalDateTime);

        outputView.printTodayAttendance(provider.getToday(), getDayOfWeek(), attendanceTime, attendanceState);
    }

    private String getDayOfWeek() {
        return provider.getDayOfWeek().getDescription();
    }

    private void attendanceUpdate() {
        String nickname = inputView.inputUpdateNickName();
        int date = inputView.inputUpdateDate();
        LocalTime updateTime = inputView.inputUpdateTime();

        LocalDateTime updateLocalDateTime = provider.createLocalDateTimeBy(updateTime, date);

        Crew crew = attendance.getCrewByName(nickname);
        LocalDateTime beforeDateTime = attendance.update(crew, updateLocalDateTime);

        outputView.printUpdateAttendance(beforeDateTime, updateLocalDateTime);
    }

    private void attendanceRecord() {
        String nickname = inputView.inputNickName();
        Crew crew = attendance.getCrewByName(nickname);

        List<AttendanceResultDto> attendanceResultDtos = attendance.readRecord(crew, provider.getToday());
        outputView.printRecordAttendance(attendanceResultDtos);

        AbsenceHistory absenceHistory = AbsenceHistory.calculate(attendanceResultDtos);

        outputView.printAbsenceHistory(absenceHistory);
    }

    private void readAbsence() {
        Map<Crew, AbsenceHistory> result = attendance.getAbsence(provider.getToday());
        outputView.printAbsenceResult(result);
    }
}
