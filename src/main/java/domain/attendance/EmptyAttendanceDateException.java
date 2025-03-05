package domain.attendance;

import controller.DateTimeConverter;
import java.time.LocalDate;

public class EmptyAttendanceDateException extends IllegalArgumentException {
    private final LocalDate date;

    public EmptyAttendanceDateException(LocalDate date) {
        this.date = date;
    }

    @Override
    public String getMessage() {
        return DateTimeConverter.convertLocalDateToString(this.date) + "은 출석 기록이 없습니다.";
    }
}
