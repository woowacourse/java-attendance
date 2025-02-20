package controller;

import domain.Attendance;
import domain.Attendances;
import domain.CheckInTime;
import util.AttendanceParser;
import view.InputView;
import view.OutputView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        String filePath = "src/main/resources/attendances.csv";
        Attendances attendances = AttendanceParser.registerAttendances(
                filePath,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        );
        readFeature(attendances);
    }

    private void readFeature(Attendances attendances) {
        String featureNumber = "";
        while (!featureNumber.equals("Q")) {
            featureNumber = inputView.readFeatureNumber();
            try {
                selectFeature(attendances, featureNumber);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void selectFeature(Attendances attendances, String featureNumber) {
        if (featureNumber.equals("1")) {
            checkIn(attendances);
            return;
        }
        if (featureNumber.equals("2")) {
            modifyCheckInTime(attendances);
            return;
        }
        if (featureNumber.equals("3")) {
            readCheckInTime(attendances);
            return;
        }
        if (featureNumber.equals("4")) {
            readDangerCrews(attendances);
            return;
        }
        throw new IllegalArgumentException("[ERROR] 1, 2, 3, 4, Q 만 입력해주세요.");
    }

    private void checkIn(Attendances attendances) {
        String name = inputView.readNickName();
        Attendance attendanceByName = attendances.findAttendanceByName(name);

        LocalDateTime checkInTime = getCheckInTime();
        attendanceByName.checkIn(checkInTime);

        outputView.printTodayCheckInTime(CheckInTime.of(checkInTime));
    }

    private LocalDateTime getCheckInTime() {
        String timeString = inputView.readTimeForCheckIn();
        LocalTime parsedTime = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));

        return LocalDateTime.of(LocalDate.now(), parsedTime);
    }


    private void modifyCheckInTime(Attendances attendances) {
        String name = inputView.readNickNameForModify();
        Attendance attendanceByName = attendances.findAttendanceByName(name);

        LocalDateTime newCheckInTime = getNewCheckInTime();
        LocalDateTime previousCheckInTime = attendanceByName.modify(newCheckInTime);

        outputView.printModifyCheckInTime(CheckInTime.of(previousCheckInTime), CheckInTime.of(newCheckInTime));
    }

    private LocalDateTime getNewCheckInTime() {
        int day = Integer.parseInt(inputView.readDateForModify());
        LocalDate date = LocalDate.of(2024, 12, day);
        String timeString = inputView.readTimeForModify();
        LocalTime time = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));

        return LocalDateTime.of(date, time);
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
}
