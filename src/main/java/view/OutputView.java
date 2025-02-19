package view;

import domain.Attendance;
import domain.Day;

import java.time.LocalDate;
import java.time.LocalTime;

public class OutputView {
    public void displayAttendanceRecord(LocalDate date, Day day, LocalTime time, Attendance attendance) {
        System.out.printf("%n%d월 %02d일 %s %s (%s)%n",
                date.getMonthValue(),
                date.getDayOfMonth(),
                day.getName(),
                time,
                attendance.getName());
    }
}
