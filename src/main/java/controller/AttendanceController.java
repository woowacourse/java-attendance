package controller;

import domain.AttendanceDateTime;
import domain.AttendanceSheet;
import domain.AttendanceSheets;
import domain.AttendanceSheetsFactory;
import domain.AttendanceStatics;
import dto.AttendanceStatusDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import util.FileReaderUtil;
import view.InputView;
import view.OutputView;

public class AttendanceController {

    public static final String ATTENDANCE_FILE_PATH = "src/main/resources/attendance.csv";

    private final LocalDate today;
    private final InputView inputView;

    public AttendanceController(LocalDate today, InputView inputView) {
        this.today = today;
        this.inputView = inputView;
    }

    public void run() {
        AttendanceSheetsFactory attendanceSheetsFactory = new AttendanceSheetsFactory(new FileReaderUtil(
                ATTENDANCE_FILE_PATH));
        AttendanceSheets attendanceSheets = attendanceSheetsFactory.create();

        while (true) {
            String select = inputView.inputMenu(today);
            System.out.print(System.lineSeparator());

            if (select.equals("1")) {
                attend(attendanceSheets);
                continue;
            }

            if (select.equals("2")) {
                updateAttendance(attendanceSheets);
                continue;
            }

            if (select.equals("3")) {
                printAttendanceSheetsByCrew(attendanceSheets);
                continue;
            }

            if (select.equals("4")) {
                printRiskOfExpulsion(attendanceSheets);
                continue;
            }

            if (select.equals("Q")) {
                return;
            }
        }
    }

    private void printRiskOfExpulsion(AttendanceSheets attendanceSheets) {
        List<String> allNames = attendanceSheets.findAllNames();

        OutputView.printRiskOfExpulsionBanner();

        for (String name : allNames) {
            AttendanceStatics attendanceStatics = attendanceSheets.calculateRiskOfExpulsionBy(name, today);
            if (attendanceStatics.isRiskOfExpulsion()) {
                OutputView.printRiskOfExpulsion(name, attendanceStatics);
            }
        }
        System.out.print(System.lineSeparator());
    }

    private void printAttendanceSheetsByCrew(AttendanceSheets attendanceSheets) {
        String nickname = inputView.inputNickname();
        List<AttendanceSheet> attendancesByNickname = attendanceSheets.findAttendanceByNickname(nickname);
        OutputView.printAttendanceSheetsByCrew(nickname, attendancesByNickname, attendanceSheets, today);
    }

    private void updateAttendance(AttendanceSheets attendanceSheets) {
        String nickname = inputView.inputUpdateNickname();
        int day = Integer.parseInt(inputView.inputUpdateDate());

        AttendanceSheet attendanceSheet = attendanceSheets.findAttendanceSheetByNicknameAndDay(nickname,
                day);
        AttendanceStatusDTO beforeStatus = attendanceSheet.getAttendanceStatus();

        String time = inputView.inputUpdateTime();
        int hour = Integer.parseInt(time.split(":")[0]);
        int minute = Integer.parseInt(time.split(":")[1]);

        attendanceSheet.updateTime(LocalTime.of(hour, minute));

        AttendanceStatusDTO afterStatus = attendanceSheet.getAttendanceStatus();
        OutputView.printUpdateInformation(beforeStatus, afterStatus);
    }

    private void attend(AttendanceSheets attendanceSheets) {
        String nickname = inputView.inputNickname();
        String time = inputView.inputTime();
        int hour = Integer.parseInt(time.split(":")[0]);
        int minute = Integer.parseInt(time.split(":")[1]);

        AttendanceDateTime attendanceDateTime = AttendanceDateTime.from(
                LocalDateTime.of(today, LocalTime.of(hour, minute)));
        attendanceSheets.add(new AttendanceSheet(nickname, attendanceDateTime));
        OutputView.printAddInformation(hour, minute, attendanceDateTime);
    }
}
