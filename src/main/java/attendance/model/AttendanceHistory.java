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
        LocalDate localDate = attendanceDetail.getLocalDateTime().toLocalDate();

        if (WoowaDayOfWeek.isHoliday(localDate)) {
            throw new IllegalArgumentException(localDate.format(formatter));
        }
        
        attendanceHistory.add(attendanceDetail);
    }

    public List<AttendanceDetail> getAttendanceHistory() {
        return attendanceHistory;
    }

    public long getAttendanceCount() {
        return attendanceHistory.stream()
                .filter(attendanceDetail -> attendanceDetail.getAttandence().equals(Attendance.출석))
                .count();

    }

    public long getLateCount() {
        return getTotalLateCount() % 3;

    }

    public long getTotalLateCount() {
        return attendanceHistory.stream()
                .filter(attendanceDetail -> attendanceDetail.getAttandence().equals(Attendance.지각))
                .count();
    }

    public long getAbsenceCount() {
        return getTotalLateCount() / 3 + attendanceHistory.stream()
                .filter(attendanceDetail -> attendanceDetail.getAttandence().equals(Attendance.결석))
                .count();
    }

    public long getTotalAbsenceCount() {
        return attendanceHistory.stream()
                .filter(attendanceDetail -> attendanceDetail.getAttandence().equals(Attendance.결석))
                .count();
    }

    public Stream<AttendanceDetail> stream() {
        return attendanceHistory.stream();
    }

    public boolean containsNowDate(LocalDate nowDate) {
        return attendanceHistory.stream()
                .anyMatch(attendanceDetail -> attendanceDetail.getLocalDateTime().toLocalDate().equals(nowDate));
    }

    public AttendanceDetail getAttendanceDetail(LocalDate localDate) {

        return attendanceHistory.stream()
                .filter(attendanceDetail -> attendanceDetail.getLocalDateTime().toLocalDate().equals(localDate))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("존재하지 않는 날짜입니다."));
    }
}
