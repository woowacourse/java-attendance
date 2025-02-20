package controller;

import domain.*;
import util.CsvParser;
import view.InputView;
import view.OutputView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;

    public AttendanceController(InputView inputView,
                                OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Attendances attendances = registerAttendances();

//        checkIn(attendances);

//        modifyCheckInTime(attendances);

//        readCheckInTime(attendances);

//        readDangerCrews(attendances);
    }

    private void checkIn(Attendances attendances) {
        String name = inputView.readNickName();
        Attendance attendanceByName = attendances.findAttendanceByName(name);
        String s = inputView.readTimeForCheckIn();
        LocalTime parsed = LocalTime.parse(s, DateTimeFormatter.ofPattern("HH:mm"));
        LocalDateTime checkInTime = LocalDateTime.of(LocalDate.now(), parsed);
        attendanceByName.checkIn(checkInTime);
        outputView.printTodayCheckInTime(CheckInTime.of(checkInTime));
    }

    private void modifyCheckInTime(Attendances attendances) {
        String name = inputView.readNickNameForModify();
        Attendance attendanceByName = attendances.findAttendanceByName(name);
        int day = Integer.parseInt(inputView.readDateForModify());
        LocalDate date = LocalDate.of(2024, 12, day);
        String s = inputView.readTimeForModify();
        LocalTime time = LocalTime.parse(s, DateTimeFormatter.ofPattern("HH:mm"));
        LocalDateTime checkInTime = LocalDateTime.of(date, time);
        LocalDateTime before = attendanceByName.modify(checkInTime);
        outputView.printModifyCheckInTime(CheckInTime.of(before), CheckInTime.of(checkInTime));
    }

    private void readCheckInTime(Attendances attendances) {
        String name = inputView.readNickName();
        Attendance attendanceByName = attendances.findAttendanceByName(name);
        outputView.printAttendanceLog(attendanceByName);
    }

    private void readDangerCrews(Attendances attendances) {
        List<Attendance> dangerCrew = attendances.findDangerCrew();
        List<Attendance> sorted = dangerCrew.stream().sorted().toList();
        outputView.printDangerCrews(sorted);
    }

    private static Attendances registerAttendances() {
        List<List<String>> rawAttendances = CsvParser.readFile("src/main/resources/attendances.csv");

        List<Attendance> attendances = new ArrayList<>();

        Attendances entity = Attendances.of(attendances);

        for (List<String> line : rawAttendances) {
            String crewName = line.get(0);
            try {
                Attendance attendanceByName = entity.findAttendanceByName(crewName);
                LocalDateTime time = getLocalDateTime(line.get(1));
                attendanceByName.checkIn(time);
            } catch (IllegalArgumentException e) {
                Crew crew = Crew.of(crewName);
                Attendance newAttendance = Attendance.of(crew, CheckInTimes.of(List.of()));
                LocalDateTime time = getLocalDateTime(line.get(1));
                newAttendance.checkIn(time);
                attendances.add(newAttendance);
            }
        }

        return entity;
    }

    private static LocalDateTime getLocalDateTime(String rawDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime time = LocalDateTime.parse(rawDateTime, dateTimeFormatter);
        return time;
    }
}
