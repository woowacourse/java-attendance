package attendance.domain;

import java.time.LocalTime;

import attendance.interfaces.Converter;
import attendance.interfaces.Displayer;

public enum AttendanceStatus implements Displayer {
    ATTENDANCE(0),
    LATE(5),
    ABSENCE(30),
    TRUANCY(30);

    private static final String NOT_REGISTERED_CONVERTER = "컨버터가 등록되지 않았습니다.";

    private final int value;
    private static Converter<AttendanceStatus> converter;

    AttendanceStatus(int value) {
        this.value = value;
    }

    private int getValue() {
        return value;
    }

    public static void setConverter(Converter<AttendanceStatus> statusConverter) {
        converter = statusConverter;
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
        if (converter == null) {
            throw new IllegalStateException(NOT_REGISTERED_CONVERTER);
        }
        return converter.convert(this);
    }
}
