package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

public enum AttendanceType {

    SUCCESS(0),
    BE_LATE(5),
    ABSENCE(30),
    ;

    private final int standardMinute;

    AttendanceType(int standardMinute) {
        this.standardMinute = standardMinute;
    }

    public static AttendanceType find(LocalDate localDate, LocalTime localTime) {
        if (AttendanceTime.isLate(localDate, localTime, BE_LATE.standardMinute, ABSENCE.standardMinute)) {
            return BE_LATE;
        }
        if (AttendanceTime.isAbsence(localDate, localTime, ABSENCE.standardMinute)) {
            return ABSENCE;
        }
        return SUCCESS;
    }

    public static EnumMap<AttendanceType, Integer> calculateTotal(List<Attendance> attendances) {
        EnumMap<AttendanceType, Integer> attendanceTotal = new EnumMap<>(AttendanceType.class);

        Arrays.stream(AttendanceType.values())
                .forEach(attendanceType -> attendanceTotal.put(attendanceType, 0));

        attendances.forEach(attendance -> {
            AttendanceType attendanceType = attendance.getAttendanceType();
            attendanceTotal.put(attendanceType, attendanceTotal.get(attendanceType) + 1);
        });

        return attendanceTotal;
    }
}
