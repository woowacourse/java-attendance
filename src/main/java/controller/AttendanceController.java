package controller;

import domain.AttendanceSheet;
import domain.AttendanceSheets;
import domain.AttendanceState;
import util.FileReaderUtil;

import java.io.IOException;
import java.util.List;

public class AttendanceController {

    public void run() throws IOException {
        AttendanceSheetsFactory attendanceSheetsFactory = new AttendanceSheetsFactory(new FileReaderUtil());
        AttendanceSheets attendanceSheets = attendanceSheetsFactory.create();

        List<AttendanceSheet> foundAttendanceSheet = attendanceSheets.findAttendanceByNickname("쿠키");
        foundAttendanceSheet.stream()
                .forEach(attendanceSheet -> System.out.println(attendanceSheet.getNickname() + ", " + attendanceSheet.getAttendanceDateTime()));

        List<String> allNames = attendanceSheets.findAllNames();
        System.out.println(allNames);

        for (String name : allNames) {
            List<AttendanceSheet> attendanceByNickname = attendanceSheets.findAttendanceByNickname(name);

            int lateCount = 0;
            int absentCount = 0;

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
