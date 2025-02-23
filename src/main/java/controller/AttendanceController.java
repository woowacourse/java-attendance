package controller;

import domain.AbsentPolicy;
import domain.AttendanceDateTime;
import domain.AttendanceSheet;
import domain.AttendanceSheets;
import domain.AttendanceSheetsFactory;
import domain.AttendanceState;
import domain.Calandar;
import java.time.DayOfWeek;
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
            List<AttendanceSheet> attendanceByNickname = attendanceSheets.findAttendanceByNickname(name);
            Map<Integer, AttendanceDateTime> map = attendanceSheetListToDayOfAttendanceTimeMap(
                    attendanceByNickname);

            int lateCount = calculateLateCount(attendanceByNickname);
            int absentCount = calculateAbsentCount(map);

            AbsentPolicy absentPolicy = AbsentPolicy.calculateAbsentPolicy(absentCount, lateCount);
            if (AbsentPolicy.isRiskOfExpulsion(absentPolicy)) {
                OutputView.printRiskOfExpulsion(name, lateCount, absentCount, absentPolicy);
            }
        }
        System.out.print(System.lineSeparator());
    }

    private void printAttendance(AttendanceSheets attendanceSheets) {
        String nickname = inputView.inputNickname();

        OutputView.printAttendanceSheetIntro(nickname);

        List<AttendanceSheet> attendancesByNickname = attendanceSheets.findAttendanceByNickname(nickname);
        Map<Integer, AttendanceDateTime> dayToAttendanceDateTime = attendanceSheetListToDayOfAttendanceTimeMap(
                attendancesByNickname);

        OutputView.printAttendanceSheets(dayToAttendanceDateTime, today.getDayOfMonth());

        int attendCount = calculateAttendCount(attendancesByNickname);
        int lateCount = calculateLateCount(attendancesByNickname);
        int absentCount = calculateAbsentCount(dayToAttendanceDateTime);
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


    private int calculateAttendCount(List<AttendanceSheet> attendanceByNickname) {
        int attendCount = 0;

        for (AttendanceSheet attendanceSheet : attendanceByNickname) {
            AttendanceDateTime attendanceDateTime = attendanceSheet.getAttendanceDateTime();

            attendCount += countStateByAttendanceDateTime(attendanceDateTime, AttendanceState.ATTEND);
        }

        return attendCount;
    }

    private int calculateLateCount(List<AttendanceSheet> attendanceByNickname) {
        int lateCount = 0;

        for (AttendanceSheet attendanceSheet : attendanceByNickname) {
            AttendanceDateTime attendanceDateTime = attendanceSheet.getAttendanceDateTime();

            lateCount += countStateByAttendanceDateTime(attendanceDateTime, AttendanceState.LATE);
        }

        return lateCount;
    }

    private int calculateAbsentCount(Map<Integer, AttendanceDateTime> attendanceDateTimes) {
        int absentCount = 0;

        for (int day = Calandar.DECEMBER.startDay; day < today.getDayOfMonth(); day++) {
            absentCount += countAbsentByDay(attendanceDateTimes, day);
        }

        return absentCount;
    }

    private int countAbsentByDay(Map<Integer, AttendanceDateTime> attendanceDateTimes, int day) {
        AttendanceDateTime datetime = attendanceDateTimes.getOrDefault(day, null);

        if (datetime == null) {
            return calculateCountByDayOfWeek(day);
        }

        return countStateByAttendanceDateTime(datetime, AttendanceState.ABSENT);
    }

    private int countStateByAttendanceDateTime(AttendanceDateTime datetime, AttendanceState state) {
        if (datetime.check() == state) {
            return 1;
        }

        return 0;
    }

    private int calculateCountByDayOfWeek(int day) {
        LocalDate localDate = LocalDate.of(2024, 12, day);
        DayOfWeek week = localDate.getDayOfWeek();

        if (week == DayOfWeek.SATURDAY || week == DayOfWeek.SUNDAY || localDate.isEqual(
                LocalDate.of(2024, 12, 25))) {
            return 0;
        }

        return 1;
    }
}
