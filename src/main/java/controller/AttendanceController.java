package controller;

import domain.AbsentPolicy;
import domain.AttendanceDateTime;
import domain.AttendanceSheet;
import domain.AttendanceSheets;
import domain.AttendanceSheetsFactory;
import domain.AttendanceState;
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

    private final InputView inputView;

    public AttendanceController(InputView inputView) {
        this.inputView = inputView;
    }

    public void run() {
        LocalDate date = LocalDate.of(2024, 12, 13);

        AttendanceSheetsFactory attendanceSheetsFactory = new AttendanceSheetsFactory(new FileReaderUtil());
        AttendanceSheets attendanceSheets = attendanceSheetsFactory.create();

        while (true) {
            String select = inputView.inputMenu(date);
            System.out.print(System.lineSeparator());

            if (select.equals("1")) {
                attend(date, attendanceSheets);
                continue;
            }

            if (select.equals("2")) {
                updateAttendance(attendanceSheets);
                continue;
            }

            if (select.equals("3")) {
                printAttendance(attendanceSheets, date);
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

            int lateCount = getLateCount(attendanceByNickname);
            int absentCount = getAbsentCount(attendanceByNickname);

            AbsentPolicy absentPolicy = AbsentPolicy.calculateAbsentPolicy(absentCount, lateCount);
            if (AbsentPolicy.isRiskOfExpulsion(absentPolicy)) {
                continue;
            }

            OutputView.printRiskOfExpulsion(name, lateCount, absentCount, absentPolicy);
        }
        System.out.print(System.lineSeparator());
    }

    private void printAttendance(AttendanceSheets attendanceSheets, LocalDate date) {
        String nickname = inputView.inputNickname();

        OutputView.printAttendanceSheetIntro(nickname);

        List<AttendanceSheet> attendancesByNickname = attendanceSheets.findAttendanceByNickname(nickname);
        Map<Integer, AttendanceDateTime> dayToAttendanceDateTime = new HashMap<>();
        attendancesByNickname.forEach(attendance ->
                dayToAttendanceDateTime.put(
                        attendance.getAttendanceDateTime().getAttendanceDateTime().getDayOfMonth(),
                        attendance.getAttendanceDateTime()));

        OutputView.printAttendanceSheets(dayToAttendanceDateTime, date.getDayOfMonth());

        int attendCount = getAttendCount(attendancesByNickname);
        int lateCount = getLateCount(attendancesByNickname);
        int absentCount = getAbsentCount(attendancesByNickname);
        OutputView.printAttendanceStatistics(attendCount, lateCount, absentCount);

        OutputView.printAbsentPolicy(AbsentPolicy.calculateAbsentPolicy(absentCount, lateCount));
        System.out.print(System.lineSeparator());
    }

    private void updateAttendance(AttendanceSheets attendanceSheets) {
        String nickname = inputView.inputUpdateNickname();

        List<AttendanceSheet> attendanceByNickname = attendanceSheets.findAttendanceByNickname(nickname);

        int day = Integer.parseInt(inputView.inputUpdateDate());

        AttendanceSheet attendanceSheetByNicknameAndDay = attendanceByNickname.stream()
                .filter(attendanceSheet -> attendanceSheet.getAttendanceDateTime().getAttendanceDateTime().getDayOfMonth() == day)
                .findFirst()
                .orElseThrow();

        String time = inputView.inputUpdateTime();
        int hour = Integer.parseInt(time.split(":")[0]);
        int minute = Integer.parseInt(time.split(":")[1]);

        attendanceSheetByNicknameAndDay.getAttendanceDateTime().update(LocalTime.of(hour, minute));
    }

    private void attend(LocalDate date, AttendanceSheets attendanceSheets) {
        String nickname = inputView.inputNickname();
        String time = inputView.inputTime();
        int hour = Integer.parseInt(time.split(":")[0]);
        int minute = Integer.parseInt(time.split(":")[1]);

        LocalDateTime localDateTime = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), hour, minute);
        attendanceSheets.add(new AttendanceSheet(nickname, AttendanceDateTime.from(localDateTime)));
    }


    private int getLateCount(List<AttendanceSheet> attendanceByNickname) {
        int lateCount = 0;

        for (AttendanceSheet attendanceSheet : attendanceByNickname) {
            AttendanceState state = attendanceSheet.getAttendanceDateTime().check();

            if (state == AttendanceState.LATE) {
                lateCount++;
            }
        }

        return lateCount;
    }

    private int getAbsentCount(List<AttendanceSheet> attendanceByNickname) {
        int absentCount = 0;

        for (AttendanceSheet attendanceSheet : attendanceByNickname) {
            AttendanceState state = attendanceSheet.getAttendanceDateTime().check();

            if (state == AttendanceState.ABSENT) {
                absentCount++;
            }
        }

        return absentCount;
    }

    private int getAttendCount(List<AttendanceSheet> attendanceByNickname) {
        int attendCount = 0;

        for (AttendanceSheet attendanceSheet : attendanceByNickname) {
            AttendanceState state = attendanceSheet.getAttendanceDateTime().check();

            if (state == AttendanceState.ATTEND) {
                attendCount++;
            }
        }

        return attendCount;
    }
}
