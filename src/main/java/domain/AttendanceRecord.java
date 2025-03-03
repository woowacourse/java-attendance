package domain;

import static domain.DangerousStatus.LATE_TO_ABSENCE_RATE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AttendanceRecord {

    private final AttendancePolicy attendancePolicy;
    private final DateProvider dateProvider;
    private final List<LocalDateTime> attendanceTimes;

    public AttendanceRecord(DateProvider dateProvider) {
        this.dateProvider = dateProvider;
        this.attendanceTimes = new ArrayList<>();
        this.attendancePolicy = new AttendancePolicy();
    }

    public LocalDateTime attend(LocalTime todayTime) {
        LocalDate today = dateProvider.getDate();

        validateAlreadyAttend(today);
        attendancePolicy.validateIsWeekDays(today);
        attendancePolicy.validateCampusOpen(todayTime);

        LocalDateTime attendanceTime = LocalDateTime.of(today, todayTime);
        attendanceTimes.add(attendanceTime);
        return attendanceTime;
    }

    public void add(LocalDateTime attendanceTime) {
        attendanceTimes.add(attendanceTime);
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
        return attendancePolicy.getAttendanceStatus(attendanceTime);
    }

    public void modifyAttendanceTime(int modifyDay, LocalTime modifyTime) {
        LocalDateTime modifyDateTime = LocalDateTime.of(dateProvider.getDate(), modifyTime);
        attendanceTimes.removeIf(time -> time.getDayOfMonth() == modifyDay);
        attendanceTimes.add(modifyDateTime);
    }

    public int totalAbsenceCount() {
        int absence = countStatus(AttendanceStatus.ABSENCE);
        int late = countStatus(AttendanceStatus.LATE);

        return late / LATE_TO_ABSENCE_RATE + absence;
    }

    public int countStatus(AttendanceStatus type) {
        List<LocalDate> openDays = getOpenDays();

        int statusCount = 0;
        for (LocalDate day : openDays) {
            statusCount += addCount(day, type);
        }
        return statusCount;
    }

    public List<LocalDate> getOpenDays() {
        LocalDate startDate = dateProvider.getDate().withDayOfMonth(1);
        LocalDate endDate = dateProvider.getDate().plusDays(1);

        List<LocalDate> openDays = new ArrayList<>();
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            if (attendancePolicy.ignoreWeekendAndHoliday(date)) {
                openDays.add(date);
            }
        }
        return openDays;
    }

    private int addCount(LocalDate date, AttendanceStatus type) {
        AttendanceStatus status = determineStatus(date);
        if (status == type) {
            return 1;
        }
        return 0;
    }

    private AttendanceStatus determineStatus(LocalDate date) {
        if (!existsAttendanceTime(date)) {
            return AttendanceStatus.ABSENCE;
        }
        return getAttendanceStatus(date.getDayOfMonth());
    }

    private boolean existsAttendanceTime(LocalDate date) {
        return attendanceTimes.stream()
                .anyMatch(time -> time.getDayOfMonth() == date.getDayOfMonth());
    }

    public boolean isAbsent(LocalDate currentDay) {
        return determineStatus(currentDay) == AttendanceStatus.ABSENCE;
    }
}
