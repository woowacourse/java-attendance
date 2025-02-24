package domain;

import static global.util.DateUtil.FIXED_REFERENCE_DATE;
import static global.util.DateUtil.assembleDateAndTime;
import static global.util.Validator.validateIsInOperationTime;

import dto.CrewAttendanceStatusResponse;
import dto.CrewResponse;
import global.util.DateUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Crew {
    private final String name;
    private final Map<LocalDate, LocalTime> attendanceBook;

    public Crew(final String name) {
        this.name = name;
        attendanceBook = new HashMap<>();
    }

    public void addAttendStatus(final LocalDateTime target) {
        validateIsInOperationTime(target.toLocalTime());
        attendanceBook.put(target.toLocalDate(), target.toLocalTime());
    }

    public boolean isNameMatch(final String name) {
        return this.name.equals(name);
    }

    public CrewAttendanceStatusResponse editAttendStatus(final LocalDateTime target) {
        validateAvailableEditAttendanceDate(target.toLocalDate());
        addAttendStatus(target);
        return createCrewAttendanceStatusResponse(target.toLocalDate());
    }

    public LocalTime getAttendanceTime(final LocalDate date) {
        return attendanceBook.getOrDefault(date, LocalTime.of(0, 0));
    }

    public void validateAvailableAttendanceDate(final LocalDate date) {
        validateIsFutureDate(date);
        if (attendanceBook.containsKey(date)) {
            throw new IllegalArgumentException("이미 출석하여 다시 출석할 수 없습니다. 수정 기능을 이용해주세요.");
        }
    }

    public void validateAvailableEditAttendanceDate(final LocalDate date) {
        validateIsFutureDate(date);
        if (!attendanceBook.containsKey(date)) {
            throw new IllegalArgumentException("출석 기록이 없어 수정할 수 없습니다.");
        }
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

    public CrewAttendanceStatusResponse createCrewAttendanceStatusResponse(LocalDate target) {
        LocalTime originalTime = getOriginalTime(target);
        return new CrewAttendanceStatusResponse(originalTime, getAttendanceStatusByDate(DateUtil.assembleDateAndTime(target, originalTime)));
    }

    public AttendanceStatus getAttendanceStatusByDate(final LocalDateTime target) {
        return AttendanceStatus.attend(target);
    }

    private LocalTime getOriginalTime(LocalDate target) {
        validateAvailableEditAttendanceDate(target);
        return attendanceBook.get(target);
    }

    private int calculateAttendanceCount() {
        return (int) attendanceBook.entrySet()
                .stream()
                .filter(e -> AttendanceStatus.attend(DateUtil.assembleDateAndTime(e.getKey(), e.getValue())) == (AttendanceStatus.ATTENDANCE)
                ).count();
    }

    private int calculateAbsenceCount2() {
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

    private int calculateAbsenceCount() {
        return (int) DateUtil.getFirstDateOfMonth().datesUntil(FIXED_REFERENCE_DATE.toLocalDate().plusDays(1))
                .filter(this::isNowAbsence)
                .count();
    }

    private int calculateTardyCount() {
        return (int) DateUtil.getFirstDateOfMonth().datesUntil(FIXED_REFERENCE_DATE.toLocalDate().plusDays(1))
                .filter(this::isNowTardy)
                .count();
    }

    private int calculateTardyCount2() {
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

//    private int determinex

    private boolean isNowAbsence(final LocalDate localDate) {
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

    private boolean isNowTardy(final LocalDate localDate) {
        if (attendanceBook.containsKey(localDate)) {
            LocalTime localTime = attendanceBook.get(localDate);
            AttendanceStatus attend = AttendanceStatus.attend(assembleDateAndTime(localDate, localTime));
            return attend == AttendanceStatus.TARDY;
        }
        return false;
    }

    private void validateIsFutureDate(LocalDate targetDate) {
        if (targetDate.isAfter(DateUtil.FIXED_REFERENCE_DATE.toLocalDate())) {
            throw new IllegalArgumentException("미래 날짜는 출석할 수 없습니다.");
        }
    }
}
