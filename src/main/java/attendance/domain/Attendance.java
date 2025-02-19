package attendance.domain;

import java.time.LocalDateTime;

public class Attendance {
    private final String crewName;
    private Time attendanceTime;

    public Attendance(String crewName, Time attendanceTime) {
        this.crewName = crewName;
        this.attendanceTime = attendanceTime;
    }

    public Time getAttendanceTime() {
        return attendanceTime;
    }

    public String getCrewName() {
        return crewName;
    }

    public String getAttendanceStatus() {
        if (attendanceTime.getDayOfWeek().equals("월요일")) { // 월요일

            return checkStatusWithCondition(13, 0, 6);
        }

        return checkStatusWithCondition(10, 0, 6);
    }

    private String checkStatusWithCondition(int hour, int attendanceMinute,
                                            int lateMinute) {

        if (!attendanceTime.isAfter(
                LocalDateTime.of(attendanceTime.getYear(), attendanceTime.getMonth(), attendanceTime.getDay(),
                        hour,
                        attendanceMinute))) {
            return "출석";
        }

        if (!attendanceTime.isAfter(
                LocalDateTime.of(attendanceTime.getYear(), attendanceTime.getMonth(), attendanceTime.getDay(),
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

        return attendanceTime.getDate().isEqual(currentAttendance.attendanceTime.getDate());
    }


    public boolean isSameByNameAndDay(String name, int day) {
        return crewName.equals(name) && day == attendanceTime.getDay();
    }

    public void modifyAttendanceTime(Time modifyTime) {
        this.attendanceTime = modifyTime;
    }
}
