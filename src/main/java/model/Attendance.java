package model;

import static constant.AttendanceConstant.BLANK_SEPARATOR;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import util.DateTimeGenerator;
import util.InputParser;

public class Attendance {

    private final LocalDate checkInDate;
    private LocalTime checkInTime;
    private AttendanceType attendanceType;

    private Attendance(LocalDate checkInDate, LocalTime checkInTime, AttendanceType attendanceType) {
        this.checkInDate = checkInDate;
        this.checkInTime = checkInTime;
        this.attendanceType = attendanceType;
    }

    public static Attendance of(String rawCheckInDateTime) {
        List<String> checkInDateTime = InputParser.split(rawCheckInDateTime, BLANK_SEPARATOR);
        LocalDate checkInDate = LocalDate.parse(checkInDateTime.get(0));
        LocalTime checkInTime = LocalTime.parse(checkInDateTime.get(1));
        AttendanceType attendanceType = AttendanceType.calculate(checkInDate, checkInTime);

        return new Attendance(checkInDate, checkInTime, attendanceType);
    }

    public static Attendance of(DateTimeGenerator dateTimeGenerator, String rawCheckInTime) {
        LocalDate checkInDate = dateTimeGenerator.now().toLocalDate();
        LocalTime checkInTime = LocalTime.parse(rawCheckInTime);
        AttendanceType attendanceType = AttendanceType.calculate(checkInDate, checkInTime);

        return new Attendance(checkInDate, checkInTime, attendanceType);
    }

    public static Attendance ofEmpty(LocalDate checkInDate) {
        return new Attendance(checkInDate, null, AttendanceType.ABSENCE);
    }

    public void update(LocalTime updateTime) {
        this.checkInTime = updateTime;
        this.attendanceType = AttendanceType.calculate(checkInDate, updateTime);
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalTime getCheckInTime() {
        return checkInTime;
    }

    public AttendanceType getAttendanceType() {
        return attendanceType;
    }
}
