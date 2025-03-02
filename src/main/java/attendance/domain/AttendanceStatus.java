package attendance.domain;

import java.time.LocalTime;

import attendance.utill.EnumTextConverter;

public enum AttendanceStatus implements Displaier {
    ATTENDANCE(0),
    LATE(5),
    ABSENCE(30),
    TRUANCY(0);

    private final int value;

    AttendanceStatus(int value) {
        this.value = value;
    }

    private int getValue() {
        return value;
    }

    public static AttendanceStatus judgeStatus(LocalTime time, LocalTime schedule) {
        if (time.isAfter(schedule.plusMinutes(ABSENCE.getValue()))) {
            return AttendanceStatus.ABSENCE;
        }
        if (time.isAfter(schedule.plusMinutes(LATE.getValue()))) {
            return AttendanceStatus.LATE;
        }
        return AttendanceStatus.ATTENDANCE;
    }

    @Override
    public String convertMessage() {
        return EnumTextConverter.convertState(this);
    }
}
