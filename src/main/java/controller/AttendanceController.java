package controller;

import domain.AttendanceRecord;
import domain.Crew;
import domain.CrewAttendanceRecords;
import domain.CsvParsingGenerator;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.function.Supplier;
import view.InputView;
import view.OutputView;

public class AttendanceController {
    private static final int START_DATE_INDEX = 0;
    private static final String QUIT_MENU = "[Qq]";
    private static final LocalDate SYSTEM_START = LocalDate.of(2024, 12, 1);
    private static final LocalDate SYSTEM_END = LocalDate.of(2024, 12, 31);

    private final Map<String, Runnable> menu = Map.of(
            "1", this::checkIn,
            "2", this::updateAttendance,
            "3", this::checkAttendanceRecords,
            "4", this::checkDisciplinaryStatus);
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final LocalDate systemDate;
    private final CrewAttendanceRecords crewAttendanceRecords;

    public AttendanceController(String[] args) {
        this.systemDate = parseSystemDate(args[START_DATE_INDEX]);
        this.crewAttendanceRecords = new CrewAttendanceRecords(new CsvParsingGenerator(), systemDate);
    }


    public void run() {
        String menuInput;
        do {
            menuInput = retryUntilSuccess(() -> {
                String input = inputView.readMenu(systemDate);
                menu.get(input).run();
                return input;
            });
        } while (!menuInput.matches(QUIT_MENU));
    }

    public void checkIn() {
        Crew crew = inputView.readNickname();
        LocalTime time = inputView.readCheckInTime();
        AttendanceRecord attendanceRecord = crewAttendanceRecords.checkIn(crew, time, systemDate);
        outputView.displayAttendanceRecord(attendanceRecord);
    }

    public void updateAttendance() {
        Crew crew = inputView.readUpdateNickname();
        LocalDate date = inputView.readUpdateDate();
        LocalTime time = inputView.readUpdateTime();
        AttendanceRecord newRecord = AttendanceRecord.of(date, time);
        AttendanceRecord oldRecord = crewAttendanceRecords.updateAttendanceRecord(crew, newRecord);
        outputView.displayUpdatedRecord(oldRecord, newRecord);
    }

    public void checkAttendanceRecords() {
        Crew crew = inputView.readNickname();
        outputView.displayAttendanceRecords(crew, crewAttendanceRecords);
    }

    public void checkDisciplinaryStatus() {
        outputView.displayWarnedCrews(crewAttendanceRecords.getWarnedCrews(), crewAttendanceRecords);
    }

    private String retryUntilSuccess(Supplier<String> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private LocalDate parseSystemDate(String systemDateInput) {
        try {
            LocalDate systemDate = LocalDate.parse(systemDateInput);
            validatePeriod(systemDate);
            return systemDate;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("[ERROR] 프로그램 인수를 2024-12-dd 형식으로 입력해 주세요.");
        }
    }

    private void validatePeriod(LocalDate systemDate) {
        if (systemDate.isBefore(SYSTEM_START) || systemDate.isAfter(SYSTEM_END)) {
            throw new IllegalArgumentException("[ERROR] 시스템은 12월 1일~31일 사이에만 작동합니다.");
        }
    }
}
