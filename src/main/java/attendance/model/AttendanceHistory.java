package attendance.model;

import static attendance.error.ErrorMessage.ERROR_ATTENDANCE_DETAIL_NOT_FOUND;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AttendanceHistory {
    private final List<AttendanceDetail> attendanceHistory = new ArrayList<>();

    public void addAttendanceDetail(AttendanceDetail attendanceDetail) {
        attendanceHistory.add(attendanceDetail);
    }

    public long getAttendanceCount() {
        return attendanceHistory.stream()
                .filter(attendanceDetail -> attendanceDetail.isSameAs(Attendance.PRESENT))
                .count();
    }

    public long getLateCount() {
        return attendanceHistory.stream()
                .filter(attendanceDetail -> attendanceDetail.isSameAs(Attendance.LATE))
                .count();
    }

    public long getAbsenceCount() {
        return attendanceHistory.stream()
                .filter(attendanceDetail -> attendanceDetail.isSameAs(Attendance.ABSENT))
                .count();
    }

    public boolean containsDate(LocalDate date) {
        return attendanceHistory.stream()
                .anyMatch(attendanceDetail -> attendanceDetail.getAttendanceDate().getLocalDate().equals(date));
    }

    public AttendanceDetail findAttendanceDetail(LocalDate attendanceDate) {
        return attendanceHistory.stream()
                .filter(attendanceDetail -> attendanceDetail.getAttendanceDate().getLocalDate().equals(attendanceDate))
                .findFirst().orElseThrow(() -> new IllegalArgumentException(ERROR_ATTENDANCE_DETAIL_NOT_FOUND));
    }

    public Stream<AttendanceDetail> stream() {
        return attendanceHistory.stream();
    }

}
