package view;

import domain.Attendance;
import domain.AttendanceRecord;
import domain.Day;

import java.time.LocalDate;
import java.time.LocalTime;

public class OutputView {
    public void displayAttendanceRecord(AttendanceRecord attendanceRecord) {
        LocalDate date = attendanceRecord.getDate();
        Day day = Day.getDay(date);
        LocalTime time = attendanceRecord.getTime();
        Attendance attendance = attendanceRecord.getAttendance();
        System.out.printf("%d월 %02d일 %s %s (%s)",
                date.getMonthValue(),
                date.getDayOfMonth(),
                day.getName(),
                time,
                attendance.getName());
    }

    public void displayUpdatedRecord(AttendanceRecord oldRecord, AttendanceRecord newRecord) {
        LocalTime newTime = newRecord.getTime();
        Attendance newAttendance = newRecord.getAttendance();
        displayAttendanceRecord(oldRecord);
        System.out.printf(" -> %s (%s) 수정 완료!%n", newTime, newAttendance.getName());
    }
}
