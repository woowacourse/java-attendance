package controller;

import domain.attendance.Attendance;
import domain.attendance.Attendances;
import domain.checkin.CheckInTime;
import util.AttendanceParser;
import view.input.InputView;
import view.output.OutputView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AttendanceController {

    private final InputView inputView;
    private final OutputView outputView;
    private Attendances attendances;

    public AttendanceController(InputView inputView,
                                OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        String filePath = "src/main/resources/attendances.csv";
        attendances = AttendanceParser.registerAttendances(
                filePath,
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        );
        readFeature();
    }

    private void readFeature() {
        while (true) {
            String featureNumber = inputView.readFeatureNumber();
            if (featureNumber.equals("Q")) {
                break ;
            }
            try {
                selectFeature(featureNumber);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void selectFeature(String featureNumber) {
        if (featureNumber.equals("1")) {
            checkIn();
            return;
        }
        if (featureNumber.equals("2")) {
            modifyCheckInTime();
            return;
        }
        if (featureNumber.equals("3")) {
            readCheckInTime();
            return;
        }
        if (featureNumber.equals("4")) {
            readDangerCrews();
            return;
        }
        throw new IllegalArgumentException("[ERROR] 1, 2, 3, 4, Q 만 입력해주세요.");
    }

    private void checkIn() {
        String name = inputView.readNickName();
        Attendance attendance = attendances.findAttendanceByName(name)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 출석부에 해당하는 이름이 없습니다."));

        LocalDateTime checkInTime = getCheckInTime();
        attendance.checkIn(checkInTime);

        outputView.printTodayCheckInTime(CheckInTime.of(checkInTime));
    }

    private LocalDateTime getCheckInTime() {
        String timeString = inputView.readTimeForCheckIn();
        LocalTime parsedTime = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));

        return LocalDateTime.of(LocalDate.now(), parsedTime);
    }


    private void modifyCheckInTime() {
        String name = inputView.readNickNameForModify();
        Attendance attendance = attendances.findAttendanceByName(name)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 출석부에 해당하는 이름이 없습니다."));

        LocalDateTime newCheckInTime = getNewCheckInTime();
        LocalDateTime previousCheckInTime = attendance.modify(newCheckInTime);

        outputView.printModifyCheckInTime(CheckInTime.of(previousCheckInTime), CheckInTime.of(newCheckInTime));
    }

    private LocalDateTime getNewCheckInTime() {
        int day = Integer.parseInt(inputView.readDateForModify());
        LocalDate date = LocalDate.of(2024, 12, day);
        String timeString = inputView.readTimeForModify();
        LocalTime time = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));

        return LocalDateTime.of(date, time);
    }


    private void readCheckInTime() {
        String name = inputView.readNickName();
        Attendance attendance = attendances.findAttendanceByName(name)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 출석부에 해당하는 이름이 없습니다."));
        outputView.printAttendanceLog(attendance);
    }

    private void readDangerCrews() {
        List<Attendance> dangerCrew = attendances.findDangerCrew();
        List<Attendance> sorted = dangerCrew.stream().sorted().toList();
        outputView.printDangerCrews(sorted);
    }
}
