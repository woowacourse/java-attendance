package controller;

import domain.*;
import view.InputView;
import view.OutputView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.function.Supplier;

public class AttendanceController {
    private final static int START_DATE_INDEX = 0;
    private static final String QUIT_MENU = "Q";

    private final Map<String, Runnable> menu = Map.of(
            "1", this::checkIn,
            "2", this::updateAttendance,
            "3", this::checkAttendanceRecords,
            "4", this::checkDisciplinaryStatus,
            "Q", () -> {
            });
    private final InputView inputView = new InputView();
    private final OutputView outputView = new OutputView();
    private final DateGenerator currentDateGenerator;
    private final CrewAttendanceRecords crewAttendanceRecords;

    public AttendanceController(String[] args) {
        this.currentDateGenerator = () -> {
            try {
                return LocalDate.parse(args[START_DATE_INDEX]);
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("[ERROR] 프로그램 인수를 YYYY-MM-DD 형식으로 입력해 주세요.");
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IllegalArgumentException("[ERROR] 프로그램 인수를 YYYY-MM-DD 형식으로 입력해 주세요.");
            }
        };
        this.crewAttendanceRecords = new CrewAttendanceRecords(new CsvParsingGenerator(), currentDateGenerator);
    }

    public void run() {
        String menuInput;
        do {
            menuInput = retryUntilSuccess(() -> {
                String input = inputView.readMenu(currentDateGenerator.generate());
                menu.get(input).run();
                return input;
            });
        } while (!menuInput.matches(QUIT_MENU));
    }

    public void checkIn() {
        Crew crew = inputView.readNickname();
        LocalTime time = inputView.readCheckInTime();
        AttendanceRecord attendanceRecord = crewAttendanceRecords.checkIn(crew, time, currentDateGenerator);
        outputView.displayAttendanceRecord(attendanceRecord);
    }

    public void updateAttendance() {
        Crew crew = inputView.readUpdateNickname();
        LocalDate date = inputView.readUpdateDate();
        AttendanceRecord oldRecord = crewAttendanceRecords.getRecordAtDate(crew, date);
        LocalTime time = inputView.readUpdateTime();
        AttendanceRecord newRecord = AttendanceRecord.of(date, time);
        crewAttendanceRecords.updateAttendanceRecord(crew, oldRecord, newRecord);
        outputView.displayUpdatedRecord(oldRecord, newRecord);
    }

    public void checkAttendanceRecords() {
        Crew crew = inputView.readNickname();
        outputView.displayRecords(crew, crewAttendanceRecords);
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
            } catch (NullPointerException e) {
                System.out.println("[ERROR] 존재하지 않는 메뉴입니다.");
            }
        }
    }
}
