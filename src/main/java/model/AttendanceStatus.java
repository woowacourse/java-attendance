package model;

import java.time.LocalDateTime;
import java.time.LocalTime;

public enum AttendanceStatus {

    ABSENT("결석"),
    ATTENDANCE("출석"),
    LATE("지각");

    private final String state;

    AttendanceStatus(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public AttendanceStatus getAttendanceStatusForTime (LocalDateTime updateLocalDateTime){
        int day = updateLocalDateTime.getDayOfWeek().getValue();
        return AttendanceRuleByDay.calculateAttendance(day, LocalTime.from(updateLocalDateTime));
    }
    //AttendanceStatus 시간에 따라서 가져오는 메서드
}
