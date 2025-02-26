package domain;

import java.util.Collections;
import java.util.Map;

public class AttendanceStatistic {
    private final Map<AttendanceStatus, Integer> value;

    public AttendanceStatistic(Map<AttendanceStatus, Integer> value) {
        this.value = value;
    }

    public Map<AttendanceStatus, Integer> getValue() {
        return Collections.unmodifiableMap(value);
    }

    public int getTotalAbsenceCount() {
        final int absenceCount = getAbsenceCount();
        return absenceCount + getLateCount() / 3;
    }

    public int getAbsenceCount() {
        return value.getOrDefault(AttendanceStatus.ABSENCE, 0);
    }

    public int getLateCount() {
        return value.getOrDefault(AttendanceStatus.LATE, 0);
    }

    public CrewStatus getCrewStatus() {
        return CrewStatus.from(getTotalAbsenceCount());
    }
}
