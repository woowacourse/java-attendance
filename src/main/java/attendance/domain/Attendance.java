package attendance.domain;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class Attendance {
    private final String crewName;
    private LocalDateTime attendanceTime;

    public Attendance(String crewName, LocalDateTime attendanceTime) {
        validatePossibleAttendance(attendanceTime);
        this.crewName = crewName;
        this.attendanceTime = attendanceTime;
    }

    public LocalDateTime getAttendanceTime() {
        return attendanceTime;
    }

    public String getCrewName() {
        return crewName;
    }

    private void validatePossibleAttendance(LocalDateTime attendanceTime) {
        String day = attendanceTime.getDayOfWeek().name();

        if (day.equals("SATURDAY") || day.equals("SUNDAY")) {
            String message = String.format("[ERROR] %d월 %d일 %s은 등교일이 아닙니다.",
                    attendanceTime.getMonthValue(), attendanceTime.getDayOfMonth(),
                    attendanceTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN));
            throw new IllegalArgumentException(message);
        }

        int hour = attendanceTime.getHour();
        if (hour < 8 || (hour == 23 && attendanceTime.getMinute() > 0)) {
            throw new IllegalArgumentException("[ERROR] 출석 가능한 시간이 아닙니다.");
        }
    }


    public String getAttendanceStatus() {
        if (attendanceTime.getDayOfWeek().name().equals("MONDAY")) { // 월요일

            return checkStatusWithCondition(13, 0, 6);
        }

        return checkStatusWithCondition(10, 0, 6);
    }

    private String checkStatusWithCondition(int hour, int attendanceMinute,
                                            int lateMinute) {

        if (!attendanceTime.isAfter(
                LocalDateTime.of(attendanceTime.getYear(), attendanceTime.getMonth(), attendanceTime.getDayOfMonth(),
                        hour,
                        attendanceMinute))) {
            return "출석";
        }

        if (!attendanceTime.isAfter(
                LocalDateTime.of(attendanceTime.getYear(), attendanceTime.getMonth(), attendanceTime.getDayOfMonth(),
                        hour,
                        lateMinute))) {
            return "지각";
        }

        return "결석";
    }

    public boolean isAlreadyAttendance(Attendance currentAttendance) {

        if (!crewName.equals(currentAttendance.crewName)) {
            return false;
        }

        return attendanceTime.toLocalDate().isEqual(currentAttendance.attendanceTime.toLocalDate());
    }


    public boolean isSameByNameAndDay(String name, int day) {
        return crewName.equals(name) && day == attendanceTime.getDayOfMonth();
    }

    public void modifyAttendanceTime(LocalDateTime modifyTime) {
        this.attendanceTime = modifyTime;
    }
}
