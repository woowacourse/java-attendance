package attendance.model.domain.attendance.vo;

import attendance.model.Calender;
import attendance.model.domain.attendance.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class AbsenceCount {

    private final int value;

    private AbsenceCount(int value) {
        validate(value);
        this.value = value;
    }

    public static AbsenceCount fromDateTimes(List<LocalDateTime> dateTimes) {
        long value = dateTimes.stream()
                .filter(AttendanceStatus::isAbsence)
                .count();

        value += Calender.getNotExistsDatesCountBeforeToday(convertToDateTimesToDates(dateTimes));

        return new AbsenceCount(Math.toIntExact(value));
    }

    private static List<LocalDate> convertToDateTimesToDates(List<LocalDateTime> dateTimes) {
        return dateTimes.stream()
                .map(LocalDateTime::toLocalDate)
                .toList();
    }

    public int getPolicyAppliedAbsenceCount(LateCount lateCount) {
        return value + lateCount.calculatePolicyAppliedAbsenceCount();
    }

    public int getValue() {
        return value;
    }

    private void validate(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("결석 횟수는 음수가 될 수 없습니다.");
        }
    }
}
