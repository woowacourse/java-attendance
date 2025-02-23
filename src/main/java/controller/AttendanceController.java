package controller;

import domain.AbsentPolicy;
import domain.AttendanceDateTime;
import domain.AttendanceSheet;
import domain.AttendanceSheets;
import domain.AttendanceSheetsFactory;
import util.FileReaderUtil;
import view.InputView;
import view.OutputView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                printAttendance(attendanceSheets);
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
            int lateCount = attendanceSheets.calculateLateCountBy(name);
            int absentCount = attendanceSheets.calculateAbsentCount(name, today);

            AbsentPolicy absentPolicy = AbsentPolicy.calculateAbsentPolicy(absentCount, lateCount);
            if (AbsentPolicy.isRiskOfExpulsion(absentPolicy)) {
                OutputView.printRiskOfExpulsion(name, lateCount, absentCount, absentPolicy);
            }
        }
        System.out.print(System.lineSeparator());
    }

    private void printAttendance(AttendanceSheets attendanceSheets) {
        String nickname = inputView.inputNickname();

        List<AttendanceSheet> attendancesByNickname = attendanceSheets.findAttendanceByNickname(nickname);
        Map<Integer, AttendanceDateTime> dayToAttendanceDateTime = attendanceSheetListToDayOfAttendanceTimeMap(
                attendancesByNickname);

        int attendCount = attendanceSheets.calculateAttendCountBy(nickname);
        int lateCount = attendanceSheets.calculateLateCountBy(nickname);
        int absentCount = attendanceSheets.calculateAbsentCount(nickname, today);

        OutputView.printAttendanceSheetIntro(nickname);
        OutputView.printAttendanceSheets(dayToAttendanceDateTime, today.getDayOfMonth());
        OutputView.printAttendanceStatistics(attendCount, lateCount, absentCount);

        OutputView.printAbsentPolicy(AbsentPolicy.calculateAbsentPolicy(absentCount, lateCount));
        System.out.print(System.lineSeparator());
    }

    private Map<Integer, AttendanceDateTime> attendanceSheetListToDayOfAttendanceTimeMap(
            List<AttendanceSheet> attendancesByNickname) {
        Map<Integer, AttendanceDateTime> dayToAttendanceDateTime = new HashMap<>();
        attendancesByNickname.forEach(attendance ->
                dayToAttendanceDateTime.put(
                        attendance.getAttendanceDateTime().getAttendanceDateTime().getDayOfMonth(),
                        attendance.getAttendanceDateTime()));
        return dayToAttendanceDateTime;
    }

    private void updateAttendance(AttendanceSheets attendanceSheets) {
        String nickname = inputView.inputUpdateNickname();

        List<AttendanceSheet> attendanceByNickname = attendanceSheets.findAttendanceByNickname(nickname);

        int day = Integer.parseInt(inputView.inputUpdateDate());

        AttendanceSheet attendanceSheetByNicknameAndDay = attendanceByNickname.stream()
                .filter(attendanceSheet ->
                        attendanceSheet.getAttendanceDateTime().getAttendanceDateTime().getDayOfMonth() == day)
                .findFirst()
                .orElseThrow();

        String time = inputView.inputUpdateTime();
        int hour = Integer.parseInt(time.split(":")[0]);
        int minute = Integer.parseInt(time.split(":")[1]);

        attendanceSheetByNicknameAndDay.getAttendanceDateTime().update(LocalTime.of(hour, minute));
    }

    private void attend(AttendanceSheets attendanceSheets) {
        String nickname = inputView.inputNickname();
        String time = inputView.inputTime();
        int hour = Integer.parseInt(time.split(":")[0]);
        int minute = Integer.parseInt(time.split(":")[1]);

        LocalDateTime localDateTime = LocalDateTime.of(today.getYear(), today.getMonth(), today.getDayOfMonth(), hour,
                minute);
        attendanceSheets.add(new AttendanceSheet(nickname, AttendanceDateTime.from(localDateTime)));
    }
}
