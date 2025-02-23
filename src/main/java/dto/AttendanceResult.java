package dto;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import model.Attendance;
import model.AttendanceType;
import model.Attendances;
import model.PunishmentType;

public class AttendanceResult {

    private final Attendances attendances;
    private final Map<AttendanceType, Integer> counts;
    private final PunishmentType punishmentType;

    private AttendanceResult(Attendances allAttendances, Map<AttendanceType, Integer> counts, PunishmentType type) {
        this.attendances = allAttendances;
        this.counts = counts;
        this.punishmentType = type;
    }

    public static AttendanceResult of(Attendances attendances) {
        Map<AttendanceType, Integer> counts = calculateAttendanceTypeCount(attendances);
        PunishmentType type = PunishmentType.calculateType(counts);
        return new AttendanceResult(attendances, counts, type);
    }

    public static Map<AttendanceType, Integer> calculateAttendanceTypeCount(Attendances attendances) {
        Map<AttendanceType, Integer> attendanceTypesCount = new HashMap<>();
        for (AttendanceType type : AttendanceType.values()) {
            attendanceTypesCount.put(type, 0);
        }

        attendances.getAttendances()
                .forEach(attendance -> {
                    AttendanceType type = attendance.isCome() ? attendance.getAttendanceType() : AttendanceType.ABSENCE;
                    attendanceTypesCount.put(type, attendanceTypesCount.get(type) + 1);
                });

        return attendanceTypesCount;
    }

    public Attendances getAttendances() {
        return attendances;
    }

    public Map<AttendanceType, Integer> getCounts() {
        return counts;
    }

    public PunishmentType getPunishmentType() {
        return punishmentType;
    }
}
