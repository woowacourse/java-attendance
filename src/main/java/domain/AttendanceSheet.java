package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class AttendanceSheet {

    public static final int ATTENDANCE_YEAR = 2024;
    public static final int ATTENDANCE_MONTH = 12;

    List<Attendance> attendances;

    public AttendanceSheet(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public void add(String nickname, LocalDate date, LocalTime time) {
        validateIsAlreadyAttendance(nickname, date);
        this.attendances.add(new Attendance(nickname, date, time));
    }

    public void validateIsAlreadyAttendance(String nickname, LocalDate date) {
        if (isAttendanceExist(nickname, date)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석하셨습니다. 수정 기능을 이용하세요");
        }
    }

    private boolean isAttendanceExist(String nickname, LocalDate date) {
        return attendances.stream()
                .anyMatch(attendance -> attendance.isAttendanceExist(nickname, date));
    }

    public void update(String nickname, int dayOfMonth, LocalTime updateTime) {
        LocalDate updateDate = LocalDate.of(ATTENDANCE_YEAR, ATTENDANCE_MONTH, dayOfMonth);

        attendances.stream()
                .filter(attendance -> isAttendanceExist(nickname, updateDate))
                .findFirst()
                .ifPresentOrElse(attendance -> attendance.update(updateTime),
                        () -> {
                            throw new IllegalArgumentException("[ERROR] 출석 기록이 없습니다. 출석 확인 기능을 이용하세요");
                        });
    }
}
