package controller;

import domain.*;
import util.FileReader;
import view.InputView;
import view.OutputView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class AttendanceController {
    private static final String FILE_PATH = "src/main/resources/attendances.csv";
    private static final int DATA_SKIP_COUNT = 1;

    private final Map<String, Runnable> menuActions = Map.of(
            "1", this::checkIn,
            "2", this::updateRecord,
            "3", this::viewRecord,
            "4", this::viewWarnedCrews,
            "Q", () -> {
            }
    );
    private final LocalDate currentDate = LocalDate.of(2024, 12, LocalDate.now().getDayOfMonth());
    private final InputView inputView;
    private final OutputView outputView;
    private final CrewRecords crewRecords;

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.crewRecords = loadCrewRecords();
    }

    public void run() {
        String menuSelection;
        do {
            menuSelection = inputView.readMenuSelection(currentDate);
            repeatAction(menuSelection);
        } while (!menuSelection.equals("Q"));
    }

    private CrewRecords loadCrewRecords() {
        CrewRecordsGenerator crewRecordsGenerator = new CrewRecordsGenerator();
        List<String> lines = FileReader.read(FILE_PATH).stream().skip(DATA_SKIP_COUNT).toList();
        return crewRecordsGenerator.generate(currentDate, lines);
    }

    private void checkIn() {
        String nickname = inputView.readNickname();
        String time = inputView.readCheckInTime();

        Crew crew = new Crew(nickname);
        AttendanceRecord attendanceRecord = new AttendanceRecord(LocalDateTime.of(currentDate, LocalTime.parse(time)));
        crewRecords.addRecord(crew, attendanceRecord);
        outputView.printAttendanceRecord(attendanceRecord);
    }

    private void updateRecord() {
        String nickname = inputView.readUpdateNickname();
        String dateOfMonth = inputView.readUpdateDate();
        String time = inputView.readUpdateTime();

        Crew crew = new Crew(nickname);
        LocalDate date = LocalDate.of(2024, 12, Integer.parseInt(dateOfMonth));
        AttendanceRecord oldRecord = crewRecords.getRecordOnDate(crew, date);
        crewRecords.updateRecord(crew, date, LocalTime.parse(time));
        AttendanceRecord newRecord = crewRecords.getRecordOnDate(crew, date);
        outputView.printUpdateResult(oldRecord, newRecord, LocalTime.parse(time));
    }

    private void viewRecord() {
        String nickname = inputView.readNickname();

        Crew crew = new Crew(nickname);
        AttendanceRecords attendanceRecords = crewRecords.getAttendanceRecordsOf(crew);
        outputView.printCrewRecord(currentDate, attendanceRecords, crew);
        outputView.printWarningStatus(crewRecords.getWarningStatus(crew));
    }

    private void viewWarnedCrews() {
        List<WarnedCrew> warnedCrews = crewRecords.getWarnedCrews();
        outputView.printWarnedCrews(warnedCrews);
    }

    private void validateMenu(String input) {
        if (!menuActions.containsKey(input)) {
            throw new IllegalArgumentException("[ERROR] 메뉴에 없는 선택지입니다." + System.lineSeparator());
        }
    }

    private void repeatAction(String action) {
        try {
            validateMenu(action);
            menuActions.get(action).run();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
