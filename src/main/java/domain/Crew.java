package domain;

import domain.util.DateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static domain.util.DateUtil.assembleDateAndTime;

public class Crew {
    private final String name;
    private final Map<LocalDate, LocalTime> attendanceBook;

    public Crew(final String name) {
        this.name = name;
        attendanceBook = new HashMap<>();
    }

    public void addAttendStatus(final LocalDateTime target) {
        LocalDate date = target.toLocalDate();
        LocalTime time = target.toLocalTime();
        attendanceBook.put(date, time);
    }

    public boolean isNameMatch(final String name) {
        return this.name.equals(name);
    }

    public void editAttendStatus(final LocalDateTime target) {
        LocalDate date = target.toLocalDate();
        if (!attendanceBook.containsKey(date)) {
            throw new IllegalArgumentException();
        }
        attendanceBook.put(date, target.toLocalTime());
    }

    public LocalTime getAttendanceTime(final LocalDate date) {
        return attendanceBook.getOrDefault(date, LocalTime.of(0, 0));
    }

    public int calculateAbsence(int now) {
        LocalDate localDate = LocalDate.of(2024, 12, 1);
        int absenceCount = 0, tardyCount = 0;
        for (int day = 0; day < now; day++) {
            if (!attendanceBook.containsKey(localDate) && !DateUtil.isWeekend(localDate)) {
                absenceCount++;
            }
            if (attendanceBook.containsKey(localDate)) {
                LocalTime localTime = attendanceBook.get(localDate);
                AttendanceStatus attend = AttendanceStatus.attend(assembleDateAndTime(localDate, localTime));
                if (attend == AttendanceStatus.ABSENCE) absenceCount++;
                if (attend == AttendanceStatus.TARDY) tardyCount++;
            }
            localDate = localDate.plusDays(1);
        }
        return absenceCount + tardyCount / 3;
    }
}
