package controller;

import domain.AttendanceBook;
import domain.AttendanceStatus;
import domain.Coach;
import domain.Crew;
import domain.DailyRecord;
import domain.Feature;
import domain.Penalty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import util.loader.FileLoader;
import util.parser.DateTimeParser;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    private static final LocalDate localDate = DateTimeParser.parseStringToDate("2024-12-13");

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
            attendanceBook.initializeCrewRecords(
                FileLoader.loadCSV("src/main/resources/attendances.csv"));
            executeFeature();
        });
    }

    protected void attendanceCheck() {
        String name = inputView.readAttendedName();
        LocalTime time = DateTimeParser.parseStringToTime(inputView.readAttendedTime());

        DailyRecord record = coach.attendCrew(name, LocalDateTime.of(localDate, time));
        outputView.printDateTimeRecord(localDate, record);
    }

    protected void attendanceEdit() {
        String name = inputView.readEditedName();
        LocalDate date = DateTimeParser.parseIntegerToDate(localDate.getYear(),
            localDate.getMonthValue(), Integer.parseInt(inputView.readEditedDay()));
        LocalTime time = DateTimeParser.parseStringToTime(inputView.readEditedTime());

        DailyRecord oldRecord = attendanceBook.findCrewByName(name).findRecordByDate(date);
        DailyRecord newRecord = coach.editCrew(name, LocalDateTime.of(date, time));
        outputView.printEditedResult(date, oldRecord, newRecord);
    }

    protected void crewRecordsCheck() {
        String name = inputView.readAttendedName();
        LocalDate startDate = localDate.withDayOfMonth(1);

        Crew crew = attendanceBook.findCrewByName(name);
        Map<LocalDate, DailyRecord> records = crew.findRecordsOfDate(startDate, localDate);
        Map<AttendanceStatus, Integer> statisticsResult = AttendanceStatus.countStatus(records);
        Penalty penalty = Penalty.of(statisticsResult.get(AttendanceStatus.LATE),
            statisticsResult.get(AttendanceStatus.ABSENT));

        outputView.printCrewRecords(name, records);
        outputView.printStatistics(statisticsResult);
        outputView.printPenalty(penalty);
    }

    protected void expelledWarningCheck() {
        LocalDate startDate = localDate.withDayOfMonth(1);
        Map<String, Crew> warningCrews = attendanceBook.findWarningCrew(startDate, localDate);

        outputView.printWarningStartMessage();
        for (String name : warningCrews.keySet()) {
            Crew crew = warningCrews.get(name);
            Map<LocalDate, DailyRecord> records = crew.findRecordsOfDate(startDate, localDate);
            Map<AttendanceStatus, Integer> statistic = AttendanceStatus.countStatus(records);
            int lateCount = statistic.get(AttendanceStatus.LATE);
            int absentCount = statistic.get(AttendanceStatus.ABSENT);

            Penalty penalty = Penalty.of(lateCount, absentCount);
            outputView.printWarningCrew(name, absentCount, lateCount, penalty);
        }
    }

    protected Runnable selectFeature(String featureNumber) {
        Map<Feature, Runnable> features = Map.of(
            Feature.ATTENDANCE_CHECK, this::attendanceCheck,
            Feature.ATTENDANCE_EDIT, this::attendanceEdit,
            Feature.CREW_RECORDS_CHECK, this::crewRecordsCheck,
            Feature.EXPELLED_WARNING_CHECK, this::expelledWarningCheck
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