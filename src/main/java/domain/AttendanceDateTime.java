package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class AttendanceDateTime {
    private static final LocalDate START_DATE = LocalDate.of(2025, 2, 11);

    private final LocalDateTime attendanceDateTime;

    public AttendanceDateTime(LocalDateTime attendanceDateTime) {
        validateDayOff(attendanceDateTime.toLocalDate());
        validateOperatingTime(attendanceDateTime.toLocalTime());
        this.attendanceDateTime = attendanceDateTime;
    }

    public static int countValidDays(LocalDate lastDate) {
        return (int) START_DATE.datesUntil(lastDate)
                .filter(localDate -> !isDayOff(localDate))
                .count();
    }

    public LocalDate toDate() {
        return attendanceDateTime.toLocalDate();
    }

    public boolean isDateBefore(LocalDate other) {
        return attendanceDateTime.toLocalDate()
                .isBefore(other);
    }

    public boolean isStatusOf(AttendanceStatus attendanceStatus) {
        return AttendanceStatus.of(attendanceDateTime) == attendanceStatus;
    }

    public LocalDateTime getLocalDateTime() {
        return attendanceDateTime;
    }

    public AttendanceStatus getStatus() {
        return AttendanceStatus.of(attendanceDateTime);
    }

    private void validateDayOff(LocalDate attendanceDate) {
        if (isDayOff(attendanceDate)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M월 d일 E요일");
            throw new IllegalArgumentException(
                    String.format("[ERROR] %s은 등교일이 아닙니다.", formatter.format(attendanceDate)));
        }
    }

    private static boolean isDayOff(LocalDate attendanceDate) {
        return attendanceDate.getDayOfWeek() == DayOfWeek.SATURDAY
                || attendanceDate.getDayOfWeek() == DayOfWeek.SUNDAY
                || LegalHoliday.isHoliday(attendanceDate)
                || Vacation.isVacation(attendanceDate);
    }

    private void validateOperatingTime(LocalTime attendanceTime) {
        if (!CampusHour.isOperatingTime(attendanceTime)) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간에만 출석이 가능합니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AttendanceDateTime that = (AttendanceDateTime) o;
        return Objects.equals(attendanceDateTime, that.attendanceDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attendanceDateTime);
    }
}
