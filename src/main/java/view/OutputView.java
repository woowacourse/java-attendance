package view;

import domain.AttendanceRecord;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class OutputView {
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM월 dd일 E요일");

    public void printCheckInResult(AttendanceRecord attendanceRecord) {
        LocalDate date = attendanceRecord.getDate();
        LocalTime time = attendanceRecord.getTime();
        String attendanceStatus = attendanceRecord.getAttendanceStatus().getName();

        if (attendanceStatus.equals("결석")) {
            System.out.print(dateFormatter.format(date) + " --:-- (" + attendanceStatus + ")");
            return;
        }
        System.out.print(dateFormatter.format(date) + " " + time + " (" + attendanceStatus + ")");
    }
}
