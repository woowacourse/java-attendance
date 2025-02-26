import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceCheck {
    static final LocalTime ATTENDANCE_CRITERIA_TIME = LocalTime.of(10, 0);
    static final List<LocalDate> HOLIDAY = List.of(LocalDate.of(2025, 1, 1));
    static final LocalTime OPERATION_START_TIME = LocalTime.of(8, 0);
    static final LocalTime OPERATION_CLOSING_TIME = LocalTime.of(23, 0);
    Map<String, Map<LocalDate, LocalTime>> attendanceRecord = new HashMap<>();

    public void attend(LocalDate date, String nickname, LocalTime attendanceTime) {
        if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY || HOLIDAY.contains(
                date)) {
            throw new IllegalStateException("[ERROR] 오늘은 등교일이 아닙니다.");
        }

        if (attendanceTime.isBefore(OPERATION_START_TIME) || attendanceTime.isAfter(OPERATION_CLOSING_TIME)) {
            throw new IllegalStateException("[ERROR] 현재 운영 시간이 아닙니다.");
        }

        if (!attendanceRecord.containsKey(nickname) || !attendanceRecord.get(nickname).containsKey(date)) {
            Map<LocalDate, LocalTime> attendance = Map.of(date, attendanceTime);
            attendanceRecord.put(nickname, attendance);
            return;
        }

        throw new IllegalStateException("[ERROR] 이미 출석이 완료되었습니다. 수정 기능을 이용하세요.");
    }

    public LocalTime getAttendanceTime(LocalDate date, String nickname) {
        return attendanceRecord.get(nickname).get(date);
    }

    public String getAttendanceStatus(LocalDate date, String nickname) {
        if (DayOfWeek.MONDAY.equals(date.getDayOfWeek())) {
            if (LocalTime.of(13, 0).plusMinutes(30).isBefore(attendanceRecord.get(nickname).get(date))) {
                return "결석";
            }

            if (LocalTime.of(13, 0).plusMinutes(5).isBefore(attendanceRecord.get(nickname).get(date))) {
                return "지각";
            }
            return "출석";
        }

        if (ATTENDANCE_CRITERIA_TIME.plusMinutes(30).isBefore(attendanceRecord.get(nickname).get(date))) {
            return "결석";
        }

        if (ATTENDANCE_CRITERIA_TIME.plusMinutes(5).isBefore(attendanceRecord.get(nickname).get(date))) {
            return "지각";
        }

        return "출석";
    }

}
