package controller;

import static domain.Feature.ATTENDANCE_CHECK;
import static domain.Feature.ATTENDANCE_EDIT;
import static domain.Feature.CREW_RECORDS_CHECK;
import static domain.Feature.EXPELLED_WARNING_CHECK;
import static util.loader.FileLoader.loadCSV;
import static util.parser.DateTimeParser.parseIntegerToDate;
import static util.parser.DateTimeParser.parseStringToDate;
import static util.parser.DateTimeParser.parseStringToTime;

import domain.AttendanceBook;
import domain.AttendanceStatus;
import domain.Coach;
import domain.Crew;
import domain.DailyRecord;
import domain.Feature;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private static final LocalDate localDate = parseStringToDate("2024-12-13");

    private final InputView inputView;
    private final OutputView outputView;
    private final AttendanceBook attendanceBook;
    private final Coach coach;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.attendanceBook = new AttendanceBook();
        this.coach = new Coach(attendanceBook);
    }

    public void start() {
        handleException(() -> {
            attendanceBook.initializeCrewRecords(loadCSV("src/main/resources/attendances.csv"));
            executeFeature();
        });
    }

    protected void attendanceCheck() {
        String name = inputView.readAttendedName();
        LocalTime time = parseStringToTime(inputView.readAttendedTime());

        DailyRecord record = coach.attendCrew(name, LocalDateTime.of(localDate, time));
        outputView.printDateTimeRecord(localDate, record);
    }

    protected void attendanceEdit() {
        String name = inputView.readEditedName();
        LocalDate date = parseIntegerToDate(localDate.getYear(), localDate.getMonthValue(),
            Integer.parseInt(inputView.readEditedDay()));
        LocalTime time = parseStringToTime(inputView.readEditedTime());

        DailyRecord oldRecord = attendanceBook.findCrewByName(name).findRecordByDate(date);
        DailyRecord newRecord = coach.editCrew(name, LocalDateTime.of(date, time));
        outputView.printEditedResult(date, oldRecord, newRecord);
    }

    protected void crewRecordsCheck() {
        String name = inputView.readAttendedName();
        LocalDate startDate = localDate.withDayOfMonth(1);

        Crew crew = attendanceBook.findCrewByName(name);
        Map<LocalDate, DailyRecord> records = crew.findRecordsOfYearAndMonth(startDate, localDate);
        Map<AttendanceStatus, Integer> statisticsResult = AttendanceStatus.countStatus(records);
        outputView.printCrewRecords(name, records);
        outputView.printStatistics(statisticsResult);
    }

    protected void expelledWarningCheck() {
        // TODO: 제적 위험자 확인 구현
        System.out.println("제적 위험자 확인 기능");
    }

    protected Runnable selectFeature(String featureNumber) {
        Map<Feature, Runnable> features = Map.of(
            ATTENDANCE_CHECK, this::attendanceCheck,
            ATTENDANCE_EDIT, this::attendanceEdit,
            CREW_RECORDS_CHECK, this::crewRecordsCheck,
            EXPELLED_WARNING_CHECK, this::expelledWarningCheck
        );

        Feature.validateProvided(featureNumber);
        return features.get(Feature.of(featureNumber));
    }

    private void executeFeature() {
        String featureNumber = inputView.readFeature(localDate);
        while (!Feature.isExit(featureNumber)) {
            Runnable action = selectFeature(featureNumber);
            action.run();
            featureNumber = inputView.readFeature(localDate);
        }
    }

    private void handleException(Runnable action) {
        try {
            action.run();
        } catch (IllegalArgumentException e) {
            outputView.printErrorMessage(e);
            start();
        }
    }
}