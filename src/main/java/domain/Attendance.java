package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Attendance {
    private static final Integer LATE_CRITERIA_MINUTES = 5;
    private static final Integer ABSENT_CRITERIA_MINUTES = 30;
    private static final LocalTime OPERATION_START_TIME = LocalTime.of(8, 0);
    private static final LocalTime OPERATION_END_TIME = LocalTime.of(23, 0);
    private final Day day;
    private LocalTime time;
    private Boolean isLate;
    private Boolean isAbsent;

    public Attendance(Day day, LocalTime time) {
        validateTime(time);
        this.day = day;
        this.time = time;
        updateStatus();
    }

    private void validateTime(LocalTime time) {
        if (time != null && (time.isBefore(OPERATION_START_TIME) || time.isAfter(OPERATION_END_TIME))) {
            throw new IllegalStateException("[ERROR] 운영 시간이 아닙니다.");
        }
    }

    private void updateStatus() {
        LocalTime criteriaTime = day.getCriteriaTime();
        if (time == null || criteriaTime.plusMinutes(ABSENT_CRITERIA_MINUTES).isBefore(time)) {
            markAsAbsent();
            return;
        }
        isAbsent = false;
        isLate = criteriaTime.plusMinutes(LATE_CRITERIA_MINUTES).isBefore(time);
    }

    private void markAsAbsent() {
        isAbsent = true;
        isLate = false;
    }

    public Boolean isLate() {
        return isLate;
    }

    public Boolean isAbsent() {
        return isAbsent;
    }

    public LocalTime getTime() {
        return time;
    }

    public Day getDay() {
        return day;
    }

    public Boolean has(LocalDate date) {
        return this.day.getDate().equals(date);
    }

    public void modifyTimeTo(LocalTime time) {
        validateTime(time);
        this.time = time;
        updateStatus();
    }

    public Attendance toImmutable() {
        return new Attendance(this.day, this.time) {
            @Override
            public void modifyTimeTo(LocalTime time) {
                throw new UnsupportedOperationException("[ERROR] 수정할 수 없는 Attendance 객체입니다.");
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attendance that = (Attendance) o;
        return Objects.equals(day, that.day);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(day);
    }
}
