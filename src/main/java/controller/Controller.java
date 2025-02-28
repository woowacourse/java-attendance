package controller;

import domain.AttendanceBook;
import domain.FileWithAttendanceData;
import domain.Option;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import view.InputView;

public class Controller {

    private static final int ATTENDANCE_YEAR = 2024;
    private static final int ATTENDANCE_MONTH = 12;
    private static final int ATTENDANCE_DAY_OF_MONTH = 16;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private final InputView inputView;
    private final AttendanceBook attendanceBook;

    public Controller(InputView inputView, AttendanceBook attendanceBook) {
        this.inputView = inputView;
        this.attendanceBook = attendanceBook;
    }

    public void loadFile() {
        FileWithAttendanceData fileWithAttendanceData = new FileWithAttendanceData(attendanceBook);
        fileWithAttendanceData.loadFile("src/main/resources/attendances.csv");
    }

    public void runSystem() {
        LocalDate nowDate = LocalDate.of(ATTENDANCE_YEAR, ATTENDANCE_MONTH, ATTENDANCE_DAY_OF_MONTH);
        loadFile();

        try {
            selectOption(nowDate);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void selectOption(LocalDate nowDate) {
        while (true) {
            Option option = Option.from(inputView.readOptionNumber(nowDate));

            if (option == Option.ATTEND) {
                attend(nowDate);
            }
            if (option == Option.EDIT) {
                edit();
            }
            if (option == Option.CHECK_RECORDS) {
                checkRecords();
            }
            if (option == Option.CHECK_EXPULSION_RISK_CREW) {
                checkExpulsionRiskCrew();
            }
            if (option == Option.QUIT) {
                break;
            }
        }
    }

    private void attend(LocalDate nowDate) {
        try {
            String name = inputView.readName();
            String attendTime = inputView.readAttendTime();
            LocalTime time = LocalTime.parse(attendTime, TIME_FORMATTER);
            attendanceBook.attendCrew(name, nowDate, time);
        } catch (DateTimeParseException e) {
            System.out.println("[ERROR] 시간 형식이 일치하지 않습니다.");
        }
    }

    private void edit() {

    }

    private void checkRecords() {

    }

    private void checkExpulsionRiskCrew() {
    }
}
