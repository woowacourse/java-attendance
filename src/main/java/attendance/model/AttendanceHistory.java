package attendance.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class AttendanceHistory {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일 EEEE은 등교일이 아닙니다.");
    private final List<AttendanceDetail> attendanceHistory = new ArrayList<>();

    public void addAttendanceDetail(AttendanceDetail attendanceDetail) {
        validateHoliday(attendanceDetail.getAttendanceDate());
        attendanceHistory.add(attendanceDetail);
    }

    public long getAttendanceCount() {
        return attendanceHistory.stream()
                .filter(attendanceDetail -> attendanceDetail.getAttendance().equals(Attendance.출석))
                .count();
    }

    public long getLateCount() {
        return attendanceHistory.stream()
                .filter(attendanceDetail -> attendanceDetail.isSameAs(Attendance.지각))
                .count();
    }

    public long getAbsenceCount() {
        return attendanceHistory.stream()
                .filter(attendanceDetail -> attendanceDetail.isSameAs(Attendance.결석))
                .count();
    }

    public boolean containsDate(LocalDate date) {
        return attendanceHistory.stream()
                .anyMatch(attendanceDetail -> attendanceDetail.getAttendanceDate().equals(date));
    }

    public AttendanceDetail findAttendanceDetail(LocalDate attendanceDate) {
        validateHoliday(attendanceDate);
        return attendanceHistory.stream()
                .filter(attendanceDetail -> attendanceDetail.getAttendanceDate().equals(attendanceDate))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("존재하지 않는 날짜입니다."));
    }

    public Stream<AttendanceDetail> stream() {
        return attendanceHistory.stream();
    }

    private void validateHoliday(LocalDate attendanceDate) {
        if (CustomLocalDateTime.isHoliday(attendanceDate)) {
            throw new IllegalArgumentException(attendanceDate.format(formatter));
        }
    }

}
