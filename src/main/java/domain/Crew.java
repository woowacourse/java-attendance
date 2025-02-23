package domain;

import dto.CrewResponse;
import global.util.DateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static global.util.DateUtil.FIXED_REFERENCE_DATE;
import static global.util.DateUtil.assembleDateAndTime;
import static global.util.Validator.validateIsFutureDate;
import static global.util.Validator.validateIsInOperationTime;

public class Crew {
    private final String name;
    private final Map<LocalDate, LocalTime> attendanceBook;

    public Crew(final String name) {
        this.name = name;
        attendanceBook = new HashMap<>();
    }

    public void addAttendStatus(final LocalDateTime target) {
        validateIsInOperationTime(target.toLocalTime());
        LocalDate date = target.toLocalDate();
        LocalTime time = target.toLocalTime();
        attendanceBook.put(date, time);
    }

    public boolean isNameMatch(final String name) {
        return this.name.equals(name);
    }

    public AttendanceStatus editAttendStatus(final LocalDateTime target) {
        validateIsInOperationTime(target.toLocalTime());
        LocalDate date = target.toLocalDate();
        if (!attendanceBook.containsKey(date)) {
            throw new IllegalArgumentException("출석 기록이 없어 수정할 수 없습니다.");
        }
        LocalTime time = target.toLocalTime();
        attendanceBook.put(date, time);

        return AttendanceStatus.attend(target);
    }

    public LocalTime getAttendanceTime(final LocalDate date) {
        return attendanceBook.getOrDefault(date, LocalTime.of(0, 0));
    }

    public void validateAvailableAttendanceDate(LocalDate date) {
        validateIsFutureDate(date);
        if (attendanceBook.containsKey(date)) {
            throw new IllegalArgumentException("이미 출석하여 다시 출석할 수 없습니다. 수정 기능을 이용해주세요.");
        }
    }

    private int calculateAttendanceCount() {
        return (int) attendanceBook.entrySet()
                .stream()
                .filter(e -> AttendanceStatus.attend(DateUtil.assembleDateAndTime(e.getKey(), e.getValue())) == (AttendanceStatus.ATTENDANCE)
                ).count();
    }

    private int calculateAbsenceCount() {
        LocalDate localDate = DateUtil.getFirstDateOfMonth();
        int absenceCount = 0;
        while (!localDate.isAfter(FIXED_REFERENCE_DATE.toLocalDate())) {
            if (isNowAbsence(localDate)) {
                absenceCount++;
            }
            localDate = localDate.plusDays(1);
        }
        return absenceCount;
    }

    private int calculateTardyCount() {
        LocalDate localDate = DateUtil.getFirstDateOfMonth();
        int tardyCount = 0;
        for (int day = 0; day < FIXED_REFERENCE_DATE.getDayOfMonth(); day++) {
            if (isNowTardy(localDate)) {
                tardyCount++;
            }
            localDate = localDate.plusDays(1);
        }
        return tardyCount;
    }

    private boolean isNowAbsence(LocalDate localDate) {
        if (!attendanceBook.containsKey(localDate) && DateUtil.isWeekday(localDate)) {
            return true;
        }
        if (!attendanceBook.containsKey(localDate)) {
            return false;
        }
        LocalTime localTime = attendanceBook.get(localDate);
        AttendanceStatus attend = AttendanceStatus.attend(assembleDateAndTime(localDate, localTime));
        return attend == AttendanceStatus.ABSENCE;
    }

    private boolean isNowTardy(LocalDate localDate) {
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

    public CrewResponse createCrewResponse() {
        int tardyCount = calculateTardyCount();
        int absenceCount = calculateAbsenceCount();
        return new CrewResponse(name, attendanceBook, calculateAttendanceCount(), absenceCount, tardyCount, calculateRiskStatus());
    }

    public CrewResponse createCrewRiskStatusResponse() {
        int tardyCount = calculateTardyCount();
        int absenceCount = calculateAbsenceCount();
        return new CrewResponse(name, calculateAttendanceCount(), absenceCount, tardyCount, calculateRiskStatus());
    }

    public AttendanceStatus getAttendanceStatusByDate(LocalDateTime target) {
        return AttendanceStatus.attend(target);
    }
}
