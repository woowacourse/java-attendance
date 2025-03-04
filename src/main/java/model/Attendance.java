package model;

import util.HolidayManager;

import java.time.LocalDateTime;

public class Attendance implements Comparable<Attendance>{

    private final AttendanceDateTime attendanceDateTime;
    private final AttendanceStatus attendanceStatus;

    private Attendance(final AttendanceDateTime attendanceDateTime, final AttendanceStatus attendanceStatus) {
        this.attendanceDateTime = attendanceDateTime;
        this.attendanceStatus = attendanceStatus;
    }

    public static Attendance of(final AttendanceDateTime attendanceDateTime) {
        final AttendanceStatus attendanceStatus = AttendanceStatus.findByAttendanceDateTime(attendanceDateTime);
        return new Attendance(attendanceDateTime, attendanceStatus);
    }

    public static void validatePossibleDate(final AttendanceDateTime attendanceDateTime) {
        validateHoliday(attendanceDateTime);
        validateWeekend(attendanceDateTime);
    }

    private static void validateHoliday(final AttendanceDateTime attendanceDateTime) {
        if (HolidayManager.isHoliday(attendanceDateTime)) {
            throw new IllegalArgumentException("공휴일에는 출석할 수 없습니다.");
        }
    }

    private static void validateWeekend(final AttendanceDateTime attendanceDateTime) {
        BusinessHours.find(attendanceDateTime);
    }

    public void validateSameTime(final AttendanceTime attendanceTime) {
        if (attendanceDateTime.getAttendanceTime().equals(attendanceTime)) {
            throw new IllegalArgumentException("수정 전 시간과 동일한 시간으로 변경할 수 없습니다");
        }
    }

    public AttendanceDateTime getAttendanceDateTime() {
        return attendanceDateTime;
    }

    public AttendanceStatus getAttendanceStatus() {
        return attendanceStatus;
    }

    @Override
    public int compareTo(final Attendance attendance) {
        final LocalDateTime srcDateTIme = this.attendanceDateTime.getDateTime();
        final LocalDateTime descDateTIme = attendance.attendanceDateTime.getDateTime();
        if (srcDateTIme.getMonth().getValue() == descDateTIme.getMonth().getValue()) {
            return srcDateTIme.getDayOfMonth() - descDateTIme.getDayOfMonth();
        }
        return srcDateTIme.getMonth().getValue() - descDateTIme.getMonth().getValue();
    }
}
