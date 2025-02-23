package controller;

import controller.feature.Feature;
import domain.attendance.Attendance;
import domain.attendance.Attendances;
import domain.checkin.CheckInTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import util.AttendanceParser;
import view.input.InputView;
import view.output.OutputView;

public class AttendanceController {

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    public static final DateTimeFormatter CSV_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final String CSV_PATH = "src/main/resources/attendances.csv";

    private final InputView inputView;
    private final OutputView outputView;
    private final Map<Feature, Runnable> features;
    private Attendances attendances;

    public AttendanceController(InputView inputView,
                                OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.features = Map.of(
                Feature.CHECK_IN, this::checkIn,
                Feature.MODIFY_CHECK_IN, this::modifyCheckInTime,
                Feature.READ_CHECK_IN, this::readCheckInTime,
                Feature.READ_DANGER_CREWS, this::readDangerCrews
        );
    }

    public void run() {
        attendances = AttendanceParser.registerAttendances(
                CSV_PATH,
                CSV_DATE_FORMATTER
        );
        selectFeature();
    }

    private void selectFeature() {
        while (runFeature()) {
            // continue running
        }
    }

    private boolean runFeature() {
        Feature feature = getFeature();
        if (feature == null) {
            return true;
        }
        if (feature == Feature.QUIT) {
            return false;
        }
        try {
            features.get(feature).run();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    private Feature getFeature() {
        try {
            String featureNumber = inputView.readFeatureNumber();
            return Feature.from(featureNumber);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void checkIn() {
        String name = inputView.readNickName();
        Attendance attendance = attendances.findAttendanceByName(name)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 출석부에 해당하는 이름이 없습니다."));

        LocalDateTime checkInTime = getCheckInTime();
        attendance.checkIn(checkInTime);

        outputView.printTodayCheckInTime(CheckInTime.of(checkInTime));
    }

    private LocalDateTime getCheckInTime() {
        String timeString = inputView.readTimeForCheckIn();
        LocalTime parsedTime = LocalTime.parse(timeString, TIME_FORMATTER);

        return LocalDateTime.of(LocalDate.now(), parsedTime);
    }


    private void modifyCheckInTime() {
        String name = inputView.readNickNameForModify();
        Attendance attendance = attendances.findAttendanceByName(name)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 출석부에 해당하는 이름이 없습니다."));

        LocalDateTime newCheckInTime = getNewCheckInTime();
        LocalDateTime previousCheckInTime = attendance.modify(newCheckInTime);

        outputView.printModifyCheckInTime(CheckInTime.of(previousCheckInTime), CheckInTime.of(newCheckInTime));
    }

    private LocalDateTime getNewCheckInTime() {
        int day = Integer.parseInt(inputView.readDateForModify());
        LocalDate date = LocalDate.of(2024, 12, day);
        String timeString = inputView.readTimeForModify();
        LocalTime time = LocalTime.parse(timeString, TIME_FORMATTER);

        return LocalDateTime.of(date, time);
    }


    private void readCheckInTime() {
        String name = inputView.readNickName();
        Attendance attendance = attendances.findAttendanceByName(name)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 출석부에 해당하는 이름이 없습니다."));
        outputView.printAttendanceLog(attendance);
    }

    private void readDangerCrews() {
        List<Attendance> dangerCrew = attendances.findDangerCrew();
        List<Attendance> sorted = dangerCrew.stream()
                .sorted()
                .toList();
        outputView.printDangerCrews(sorted);
    }
}
