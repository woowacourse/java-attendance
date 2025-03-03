package attendance.domain;

import java.time.LocalTime;

import attendance.interfaces.EnumDisplayConverter;
import attendance.interfaces.EnumToTextConverter;

public enum AttendanceStatus implements EnumDisplayConverter {
    ATTENDANCE(0),
    LATE(5),
    ABSENCE(30),
    TRUANCY(30);

    private static final String NOT_REGISTERED_CONVERTER = "컨버터가 등록되지 않았습니다.";

    private final int value;
    private static EnumToTextConverter<AttendanceStatus> enumToTextConverter;

    AttendanceStatus(int value) {
        this.value = value;
    }

    private int getValue() {
        return value;
    }

    public static void setConverter(EnumToTextConverter<AttendanceStatus> statusEnumToTextConverter) {
        enumToTextConverter = statusEnumToTextConverter;
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
    public String convert() {
        if (enumToTextConverter == null) {
            throw new IllegalStateException(NOT_REGISTERED_CONVERTER);
        }
        return enumToTextConverter.convert(this);
    }
}
