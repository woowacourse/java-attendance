package attendance.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AttendanceResult implements Comparable<AttendanceResult> {
    private final Crew crew;
    private final Map<AttendanceType, Integer> attendanceTypes;
    private final List<Attendance> attendances;

    public AttendanceResult(Crew crew, Map<AttendanceType, Integer> attendanceTypes, List<Attendance> attendances) {
        this.crew = crew;
        this.attendanceTypes = attendanceTypes;
        this.attendances = attendances;
    }

    public static AttendanceResult create(Crew crew, List<Attendance> attendances) {
        Map<AttendanceType, Integer> attendanceTypes = new HashMap<>();
        for (Attendance attendance : attendances) {
            AttendanceType attendanceType = attendance.getAttendanceType();
            attendanceTypes.put(attendanceType, attendanceTypes.getOrDefault(attendanceType, 0) + 1);
        }
        return new AttendanceResult(crew, attendanceTypes, attendances);
    }

    public AttendanceWarningLevel getAttendanceWarningLevel() {
        return AttendanceWarningLevel.judge(
                attendanceTypes.getOrDefault(AttendanceType.LATE, 0),
                attendanceTypes.getOrDefault(AttendanceType.ABSENCE, 0));
    }

    public Map<AttendanceType, Integer> getAttendanceTypes() {
        return Map.copyOf(attendanceTypes);
    }

    public List<Attendance> getAttendances() {
        return List.copyOf(attendances);
    }

    public Crew getCrew() {
        return crew;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        AttendanceResult that = (AttendanceResult) object;
        return Objects.equals(attendanceTypes, that.attendanceTypes) && Objects.equals(attendances,
                that.attendances);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(attendanceTypes);
        result = 31 * result + Objects.hashCode(attendances);
        return result;
    }

    @Override
    public int compareTo(AttendanceResult o) {
        if (this.getAttendanceWarningLevel().getImportance() == o.getAttendanceWarningLevel().getImportance()) {
            if (this.totalAbsenceCount() == o.totalAbsenceCount()) {
                return this.crew.getNickname().compareTo(o.crew.getNickname());
            }
            return o.totalAbsenceCount() - this.totalAbsenceCount();
        }
        return o.getAttendanceWarningLevel().getImportance() - this.getAttendanceWarningLevel().getImportance();
    }

    private int totalAbsenceCount() {
        return AttendanceWarningLevel.calculateLateToAbsent(
                attendanceTypes.getOrDefault(AttendanceType.LATE, 0)) +
                attendanceTypes.getOrDefault(AttendanceType.ABSENCE, 0);
    }
}
