package domain;

import domain.util.DateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static domain.util.DateUtil.TODAY;
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
        addAttendStatus(target);
    }

    public LocalTime getAttendanceTime(final LocalDate date) {
        return attendanceBook.getOrDefault(date, LocalTime.of(0, 0));
    }

    public int calculateAbsenceCount() {
        LocalDate localDate = DateUtil.getFirstDateOfMonth();
        int absenceCount = 0;
        for (int day = 0; day < TODAY.getDayOfMonth(); day++) {
            if(isNowAbsence(localDate)) {
                absenceCount ++;
            }
            localDate = localDate.plusDays(1);
        }
        return absenceCount;
    }

    public int calculateTardyCount() {
        LocalDate localDate = DateUtil.getFirstDateOfMonth();
        int tardyCount = 0;
        for (int day = 0; day < TODAY.getDayOfMonth(); day++) {
            if(isNowTardy(localDate)) {
                tardyCount ++;
            }
            localDate = localDate.plusDays(1);
        }
        return tardyCount;
    }

    public boolean isNowAbsence(LocalDate localDate) {
        if(!attendanceBook.containsKey(localDate) && !DateUtil.isWeekend(localDate)) {
            return true;
        }
        if(!attendanceBook.containsKey(localDate)) {
            return false;
        }
        LocalTime localTime = attendanceBook.get(localDate);
        AttendanceStatus attend = AttendanceStatus.attend(assembleDateAndTime(localDate, localTime));
        return attend == AttendanceStatus.ABSENCE;
    }

    public boolean isNowTardy(LocalDate localDate) {
        if (attendanceBook.containsKey(localDate)) {
            LocalTime localTime = attendanceBook.get(localDate);
            AttendanceStatus attend = AttendanceStatus.attend(assembleDateAndTime(localDate, localTime));
            return attend == AttendanceStatus.TARDY;
        }
        return false;
    }

    public RiskStatus calculateRiskStatus() {
        return RiskStatus.getRiskStatus(calculateAbsenceCount(), calculateTardyCount());
    }
}
