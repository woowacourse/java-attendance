package controller;

import domain.AbsentPolicy;
import domain.AttendanceDateTime;
import domain.AttendanceSheet;
import domain.AttendanceSheets;
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

    public void run() throws IOException {
    private final InputView inputView;

    public AttendanceController(InputView inputView) {
        this.inputView = inputView;
    }

    public void run() {
        LocalDate date = LocalDate.of(2024, 12, 13);

        AttendanceSheetsFactory attendanceSheetsFactory = new AttendanceSheetsFactory(new FileReaderUtil());
        AttendanceSheets attendanceSheets = attendanceSheetsFactory.create();

        while (true) {
            String select = inputView.inputMenu(LocalDate.now());

            if (select.equals("1")) {
                attend(date, attendanceSheets);
                continue;
            }

        List<String> allNames = attendanceSheets.findAllNames();
        System.out.println(allNames);

        for (String name : allNames) {
            List<AttendanceSheet> attendanceByNickname = attendanceSheets.findAttendanceByNickname(name);

            int lateCount = 0;
            int absentCount = 0;
            if (select.equals("Q")) {
                return;
            }
        }
    }

    private void attend(LocalDate date, AttendanceSheets attendanceSheets) {
        String nickname = inputView.inputNickname();
        String time = inputView.inputTime();
        int hour = Integer.parseInt(time.split(":")[0]);
        int minute = Integer.parseInt(time.split(":")[1]);

        LocalDateTime localDateTime = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), hour, minute);
        attendanceSheets.add(new AttendanceSheet(nickname, AttendanceDateTime.from(localDateTime)));
    }

            for (AttendanceSheet attendanceSheet : attendanceByNickname) {
                AttendanceState state = attendanceSheet.getAttendanceDateTime().check();

                if (state == AttendanceState.LATE) {
                    lateCount++;
                }

                if (state == AttendanceState.ABSENT) {
                    absentCount++;
                }
            }

            System.out.println("name = " + name);
            System.out.println("absentCount = " + absentCount);
            System.out.println("lateCount = " + lateCount);

            absentCount += lateCount / 3;
            lateCount %= 3;

//            경고 대상자: 결석 2회 이상
//            면담 대상자: 결석 3회 이상
//            제적 대상자: 결석 5회 초과

            if (absentCount > 5) {
                System.out.println("제적");
            } else if (absentCount >= 3) {
                System.out.println("면담");
            } else if (absentCount == 2) {
                System.out.println("경고");
            }
            System.out.println();


        }
    }
}
