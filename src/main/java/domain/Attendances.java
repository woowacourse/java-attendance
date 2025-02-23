package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import util.Day;

public class Attendances {
    public static final int LATE_TO_ABSENT_THRESHOLD = 3;
    public static final int ABSENT_HOUR = 23;
    public static final int ABSENT_MINUTE = 59;

    public final List<Attendance> attendanceLog = new ArrayList<>();

    public Attendances() {

    }

    public void addAttendance(Attendance attendance) {
        attendanceLog.add(attendance);
    }

    public AttendanceAlertLevel calculateAttendanceAlertLevel() {
        int absentTotal = countAbsent() + (countLate() / LATE_TO_ABSENT_THRESHOLD);
        if (absentTotal >= AttendanceAlertLevel.DISMISSED.absenceLimit) {
            return AttendanceAlertLevel.DISMISSED;
        }
        if (absentTotal >= AttendanceAlertLevel.COUNSEL_REQUIRED.absenceLimit) {
            return AttendanceAlertLevel.COUNSEL_REQUIRED;
        }
        if (absentTotal >= AttendanceAlertLevel.CAUTION.absenceLimit) {
            return AttendanceAlertLevel.CAUTION;
        }
        return AttendanceAlertLevel.NORMAL;
    }

    public int countPresent() {
        return Math.toIntExact(attendanceLog.stream()
                .filter(attendance -> attendance.calculateAttendanceStatus().equals(AttendanceStatus.PRESENT))
                .count());
    }

    public int countLate() {
        return Math.toIntExact(attendanceLog.stream()
                .filter(attendance -> attendance.calculateAttendanceStatus().equals(AttendanceStatus.LATE))
                .count());
    }

    public int countAbsent() {
        return Math.toIntExact(attendanceLog.stream()
                .filter(attendance -> attendance.calculateAttendanceStatus().equals(AttendanceStatus.ABSENT))
                .count());
    }


    // 특정 날짜가 존재하는지 확인 없으면 null
    public Optional<Attendance> getSpecificAttendance(int testDay) {
        return attendanceLog.stream()
                .filter(attendance -> attendance.isSameDay(testDay))
                .findAny();
    }

    public void addAbsent(LocalDateTime today) {
        int dayOfMonth = today.getDayOfMonth();
        List<Integer> attendanceDays = attendanceLog.stream().map(Attendance::getDay).toList();
        List<Integer> weekDays = new ArrayList<>();
        for (int day = 1; day < dayOfMonth; day++) {
            if (Day.isHoliday(day, today)) {
                continue;
            }
            weekDays.add(day);
        }
        weekDays.removeAll(attendanceDays);

        for (int day : weekDays) {
            attendanceLog.add(new Attendance(LocalDateTime.of(today.getYear(), today.getMonth(), day, ABSENT_HOUR,
                    ABSENT_MINUTE)));
        }
    }

    public Attendance changeAttendance(int date, Time time) {
        Attendance targetAttendance = getSpecificAttendance(date).get();
        targetAttendance.updateAttendance(time); // 원하는 시간으로 바꿈

        return targetAttendance; // 바꾼 시간 리턴
    }

    public List<Attendance> getAttendanceLog() {
        return attendanceLog;
    }
}
