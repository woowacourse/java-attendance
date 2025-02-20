package attendance.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class AttendanceDetail {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일 EEEE은 등교일이 아닙니다.");

    private LocalDateTime localDateTime;
    private Attendance attendance;

    public AttendanceDetail(LocalDateTime localDateTime) {
        validateHoliday(localDateTime);
        this.localDateTime = localDateTime;
        this.attendance = Attendance.from(localDateTime);
    }

    private void validateHoliday(LocalDateTime localDateTime) {
        if (CustomLocalDateTime.isHoliday(localDateTime.toLocalDate())) {
            throw new IllegalArgumentException(localDateTime.format(formatter));
        }
    }

    public void modify(LocalTime localTime) {
        localDateTime = LocalDateTime.of(
                localDateTime.toLocalDate(),
                localTime
        );
        this.attendance = Attendance.from(localDateTime);
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public LocalDate getAttendanceDate() {
        return localDateTime.toLocalDate();
    }

    public LocalDateTime getAttendanceDateTime() {
        return localDateTime;
    }

    public boolean isSameAs(Attendance attendance) {
        return attendance.equals(this.attendance);
    }

}
