package domain.attendance;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EmptyAttendanceDate extends AttendanceDate {
    public EmptyAttendanceDate(LocalDate date, AttendanceStatus status) {
        super(date, status);
    }

    @Override
    public LocalDateTime getDateTime() {
        throw new IllegalArgumentException("출입 기록이 없는 날에는 출석 시간을 조회할 수 없습니다");
    }
}
