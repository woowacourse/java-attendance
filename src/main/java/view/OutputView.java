package view;

import domain.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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
                getDisplayTime(time, attendance),
                attendance.getName());
    }

    private String getDisplayTime(LocalTime time, Attendance attendance) {
        if (attendance == Attendance.ABSENT) {
            return "--:--";
        }
        return time.toString();
    }

    public void displayUpdatedRecord(AttendanceRecord oldRecord, AttendanceRecord newRecord) {
        LocalTime newTime = newRecord.getTime();
        Attendance newAttendance = newRecord.getAttendance();
        displayAttendanceRecord(oldRecord);
        System.out.printf(" -> %s (%s) 수정 완료!%n", newTime, newAttendance.getName());
    }

    public void displayAttendanceRecords(Crew crew, CrewAttendanceRecords crewAttendanceRecords) {
        System.out.printf("%n이번 달 %s의 출석 기록입니다.%n%n", crew.getName());
        displaySortedRecords(crew, crewAttendanceRecords);
    }

    private void displaySortedRecords(Crew crew, CrewAttendanceRecords crewAttendanceRecords) {
        List<AttendanceRecord> sortedRecords = crewAttendanceRecords.getSortedRecords(crew);
        sortedRecords.forEach((record) -> {
            displayAttendanceRecord(record);
            System.out.println();
        });
    }
}
