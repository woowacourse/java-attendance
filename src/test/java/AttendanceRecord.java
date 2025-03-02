import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AttendanceRecord {

    private final DateProvider dateProvider;
    private List<LocalDateTime> attendanceTimes;

    public AttendanceRecord(DateProvider dateProvider) {
        this.dateProvider = dateProvider;
        this.attendanceTimes = new ArrayList<>();
    }

    public LocalDateTime attend(String time) {
        LocalDateTime attendanceTime = LocalDateTime.of(
                dateProvider.getDate(),
                LocalTime.of(
                        Integer.parseInt(time.split(":")[0]),
                        Integer.parseInt(time.split(":")[1])
                ));
        attendanceTimes.add(attendanceTime);
        return attendanceTime;
    }

    public LocalDateTime findAttendanceTimeByDay(int dayOfMonth) {
        return attendanceTimes.stream()
                .filter(time -> time.getDayOfMonth() == dayOfMonth)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 해당 날짜의 출석 시간이 없습니다."));
    }

    public AttendanceStatus getAttendanceStatus(int dayOfMonth) {
        LocalDateTime attendanceTime = findAttendanceTimeByDay(dayOfMonth);
        if (attendanceTime.toLocalTime().isBefore(LocalTime.of(10, 6))) {
            return AttendanceStatus.ATTEND;
        }
        if (attendanceTime.toLocalTime().isBefore(LocalTime.of(10, 31))) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ABSENCE;
    }
}
