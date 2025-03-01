package controller;

import domain.AttendanceRecord;
import domain.Crew;
import domain.CrewRecords;
import domain.CrewRecordsGenerator;
import util.FileReader;
import view.InputView;
import view.OutputView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.regex.Pattern;

public class AttendanceController {
    private final Pattern MENU_CHOICES = Pattern.compile("[1234Q]");
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
        String menuSelection = inputView.readMenuSelection();
        validateMenu(menuSelection);

        if (menuSelection.equals("1")) {
            checkIn();
        }
        if (menuSelection.equals("2")) {
            updateRecord();
        }
        if (menuSelection.equals("3")) {
            viewRecord();
        }
    }

    private CrewRecords loadCrewRecords() {
        CrewRecordsGenerator crewRecordsGenerator = new CrewRecordsGenerator();
        return crewRecordsGenerator.generate(FileReader.read("src/main/resources/attendances.csv").stream().skip(1).toList());
    }

    private void checkIn() {
        String nickname = inputView.readNickname();
        String time = inputView.readCheckInTime();

        Crew crew = new Crew(nickname);
        crewRecords.validateCrew(crew);
        AttendanceRecord attendanceRecord = new AttendanceRecord(LocalDateTime.of(currentDate, LocalTime.parse(time)));
        crewRecords.addRecord(crew, attendanceRecord);
        outputView.printCheckInResult(attendanceRecord);
    }

    private void updateRecord() {
        String nickname = inputView.readUpdateNickname();
        String dateOfMonth = inputView.readUpdateDate();
        String time = inputView.readUpdateTime();

        Crew crew = new Crew(nickname);
        crewRecords.validateCrew(crew);
        LocalDate date = LocalDate.of(2024, 12, Integer.parseInt(dateOfMonth));
        AttendanceRecord oldRecord = crewRecords.getRecordOnDate(crew, date);
        crewRecords.updateRecord(crew, date, LocalTime.parse(time));
        AttendanceRecord newRecord = crewRecords.getRecordOnDate(crew, date);
        outputView.printUpdateResult(oldRecord, newRecord, LocalTime.parse(time));
    }

    private void viewRecord() {
        String nickname = inputView.readNickname();
    }

    private void validateMenu(String input) {
        if (!MENU_CHOICES.matcher(input).matches()) {
            throw new IllegalArgumentException("[ERROR] 메뉴에 없는 선택지입니다.");
        }
    }
}
