package attendance.domain;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public class AttendanceCounter {

    private final Map<AttendanceState, Integer> counter;

    public AttendanceCounter(final Map<AttendanceState, Integer> counts) {
        this.counter = initialize();
        for (Entry<AttendanceState, Integer> entry : counts.entrySet()) {
            counter.merge(entry.getKey(), entry.getValue(), Integer::sum);
        }
    }

    public void increase(final AttendanceState attendanceState) {
        counter.merge(attendanceState, 1, Integer::sum);
    }

    private Map<AttendanceState, Integer> initialize() {
        Map<AttendanceState, Integer> counter = new EnumMap<>(AttendanceState.class);
        for (AttendanceState attendanceState : AttendanceState.values()) {
            counter.put(attendanceState, 0);
        }
        return counter;
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof final AttendanceCounter counter1)) {
            return false;
        }
        return Objects.equals(counter, counter1.counter);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(counter);
    }

    public int getCount(final AttendanceState attendanceState) {
        return counter.get(attendanceState);
    }
}
