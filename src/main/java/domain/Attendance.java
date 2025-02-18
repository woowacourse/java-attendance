package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Attendance {
    private final List<AttendanceDate> attendanceDates = new ArrayList<>();

    public Attendance(List<LocalDateTime> localDateTimes) {
        LocalDate startDate = LocalDate.of(2024, 12, 2);
        LocalDate endDate = LocalDate.now();

        for (LocalDate cursorDate = startDate; cursorDate.isBefore(endDate); cursorDate = cursorDate.plusDays(1)) {
            if (cursorDate.getDayOfWeek().getValue() > 5 || Holiday.has(cursorDate)) {
                continue;
            }
            Optional<LocalDate> cursorlocalDate = localDateTimes.stream().map(LocalDateTime::toLocalDate)
                    .filter(cursorDate::equals).findFirst();
            if (cursorlocalDate.isPresent()) {
                LocalDateTime cursorLocalDateTime = findDateTime(cursorlocalDate.get(), localDateTimes);
                attendanceDates.add(new AttendanceDate(cursorLocalDateTime));
                continue;
            }
            attendanceDates.add(new AttendanceDate(
                    LocalDateTime.of(cursorDate.getYear(), cursorDate.getMonth(), cursorDate.getDayOfMonth(), 23,
                            59)));
        }
    }

    private LocalDateTime findDateTime(LocalDate cursorlocalDate, List<LocalDateTime> localDateTimes) {
        for (LocalDateTime localDateTime : localDateTimes) {
            if (localDateTime.getYear() == cursorlocalDate.getYear()
                    && localDateTime.getMonth() == cursorlocalDate.getMonth()
                    && localDateTime.getDayOfMonth() == cursorlocalDate.getDayOfMonth()) {
                return localDateTime;
            }
        }

        throw new IllegalArgumentException("");
    }
}
