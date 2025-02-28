package controller;

import domain.AttendanceRecord;
import domain.Crew;
import domain.CrewRecords;
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

    public AttendanceController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        String menuSelection = inputView.readMenuSelection();
        validateMenu(menuSelection);

        if (menuSelection.equals("1")) {
            checkIn();
        }
        if (menuSelection.equals("2")) {
            editRecord();
        }
    }

    private void checkIn() {
        String nickname = inputView.readCheckInNickname();
        String time = inputView.readCheckInTime();

        Crew crew = new Crew(nickname);
        CrewRecords crewRecords = new CrewRecords();
        crewRecords.validateCrew(crew);
        AttendanceRecord attendanceRecord = new AttendanceRecord(LocalDateTime.of(currentDate, LocalTime.parse(time)));
        crewRecords.addRecord(crew, attendanceRecord);
        outputView.printCheckInResult(attendanceRecord);
    }

    private void editRecord() {
        String nickname = inputView.readEditNickname();

        Crew crew = new Crew(nickname);
        CrewRecords crewRecords = new CrewRecords();
        crewRecords.validateCrew(crew);
    }

    private void validateMenu(String input) {
        if (!MENU_CHOICES.matcher(input).matches()) {
            throw new IllegalArgumentException("[ERROR] 메뉴에 없는 선택지입니다.");
        }
    }
}
