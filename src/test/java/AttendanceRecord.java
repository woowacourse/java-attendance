import java.time.LocalDate;
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
        AttendancePolicy attendancePolicy = new AttendancePolicy();

        LocalDate today = dateProvider.getDate();
        LocalTime todayTime = LocalTime.of(
                Integer.parseInt(time.split(":")[0]),
                Integer.parseInt(time.split(":")[1]));

        validateAlreadyAttend(today);
        attendancePolicy.validateIsWeekDays(today);
        attendancePolicy.validateCampusOpen(todayTime);

        LocalDateTime attendanceTime = LocalDateTime.of(today, todayTime);
        attendanceTimes.add(attendanceTime);
        return attendanceTime;
    }

    private void validateAlreadyAttend(LocalDate today) {
        if (attendanceTimes.stream()
                .anyMatch(attendanceTime -> attendanceTime.getDayOfMonth() == today.getDayOfMonth())) {
            throw new IllegalArgumentException("[ERROR] 해당 날짜에는 이미 출석했습니다. 수정 기능을 이용해주세요.");
        }
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
