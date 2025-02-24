package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static domain.AttendanceStatus.LATE;
import static domain.AttendanceStatus.PRESENCE;

public class CheckInTimes {
    private List<CheckInTime> checkInTimes;

    private CheckInTimes(List<CheckInTime> checkInTimes) {
        this.checkInTimes = new ArrayList<>(checkInTimes);
    }

    public static CheckInTimes of(List<CheckInTime> checkInTimes) {
        return new CheckInTimes(checkInTimes);
    }

    public void add(CheckInTime checkInTime) {
        validateAddable(checkInTime);
        checkInTimes.add(checkInTime);
    }

    public LocalDateTime modify(CheckInTime checkInTime) {
        validateModifiable(checkInTime);

        CheckInTime target = checkInTimes.stream()
                .filter(time -> time.isSameDate(checkInTime))
                .findAny().orElseThrow(() -> new IllegalArgumentException("[ERROR] 수정 가능한 출석 내역이 없습니다."));

        LocalDateTime before = target.toLocalDateTime();
        target.modify(checkInTime);

        return before;
    }

    public List<LocalDateTime> getAttendanceLog(LocalDateTime localDateTime) {
        return checkInTimes.stream()
                .filter(time -> time.isBeforeDate(localDateTime))
                .map(CheckInTime::toLocalDateTime)
                .toList();
    }

    private void validateAddable(CheckInTime checkInTime) {

        checkInTimes.stream()
                .filter(time -> time.isSameDate(checkInTime))
                .findAny()
                .ifPresent(time -> {
                    throw new IllegalArgumentException("[ERROR] 이미 체크인을 완료했습니다.");
                });
    }

    private void validateModifiable(CheckInTime checkInTime) {
        LocalDateTime now = LocalDateTime.now();
        if (checkInTime.isNotModifiable(now)) {
            throw new IllegalArgumentException("[ERROR] 출석 가능한 시간이 아닙니다.");
        }
    }

    public int countPresence() {
        return countAttendenceStatus(PRESENCE);
    }

    public int countLate() {
        return countAttendenceStatus(LATE);
    }

    public int countAbsence() {
        return countWorkDay() - (countAttendenceStatus(PRESENCE) + countAttendenceStatus(LATE));
    }

    private int countAttendenceStatus(AttendanceStatus status) {
        return Math.toIntExact(
                checkInTimes.stream()
                        .filter(time -> time.getAttendanceStatus() == status)
                        .count()
        );
    }

    private int countWorkDay() {
        LocalDate today = LocalDate.now();
        int workDayCount = 0;
        for (int i = 1; i < today.getDayOfMonth(); i++) {
            if (LocalDate.of(2024, 12, i).getDayOfWeek() == DayOfWeek.SATURDAY
                    || LocalDate.of(2024, 12, i).getDayOfWeek() == DayOfWeek.SATURDAY
                    || i == 25) {
                continue;
            }
            workDayCount++;
        }
        return workDayCount;
    }
}
