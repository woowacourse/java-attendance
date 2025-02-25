package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AttendanceSheet {
    List<Attendance> attendances;

    public AttendanceSheet(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public void add(String nickname, LocalDate date, LocalTime time) {
        validateIsAlreadyAttendance(nickname, date);
        this.attendances.add(new Attendance(nickname, date, time));
    }

    public void validateIsAlreadyAttendance(String nickname, LocalDate date) {
        if(isAttendanceExist(nickname, date)){
            throw new IllegalArgumentException("[ERROR] 이미 출석하셨습니다. 수정 기능을 이용하세요");
        }
    }

    private boolean isAttendanceExist(String nickname, LocalDate date) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.isAttendanceExist(nickname, date));
    }

    public void update(String nickname, int dayOfMonth, LocalTime updateTime) {
        LocalDate updateDate = LocalDate.of(2024, 12, dayOfMonth);
        validateNotFoundAttendance(nickname, updateDate);

        attendances.stream()
                .filter(attendance -> isAttendanceExist(nickname, updateDate))
                .findFirst()
                .ifPresent(attendance -> attendance.update(updateTime));
    }

    public void validateNotFoundAttendance(String nickname, LocalDate date) {
        if(isAttendanceNotExist(nickname, date)){
            throw new IllegalArgumentException("[ERROR] 출석 기록이 없습니다. 출석 확인 기능을 이용하세요");
        }
    }

    private boolean isAttendanceNotExist(String nickname, LocalDate date) {
        return attendances.stream()
                .noneMatch(attendance -> attendance.isAttendanceExist(nickname, date));
    }
}
