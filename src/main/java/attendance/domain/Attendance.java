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

            return checkStatusWithCondition(13, 5, 30);
        }

        return checkStatusWithCondition(10, 5, 30);
    }

    private String checkStatusWithCondition(int hour,
                                            int lateMinute, int absentMinute) {

        if (!attendanceTime.isAfter(
                LocalDateTime.of(attendanceTime.getYear(), attendanceTime.getMonth(), attendanceTime.getDay(),
                        hour,
                        lateMinute))) {
            return "출석";
        }

        if (!attendanceTime.isAfter(
                LocalDateTime.of(attendanceTime.getYear(), attendanceTime.getMonth(), attendanceTime.getDay(),
                        hour,
                        absentMinute))) {
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


    public boolean isSameByNameAndLocalDate(String name, int year, int month, int day) {
        return crewName.equals(name) && day == attendanceTime.getDay() && year == attendanceTime.getYear()
                && month == attendanceTime.getMonth();
    }

    public void modifyAttendanceTime(Time modifyTime) {
        this.attendanceTime = modifyTime;
    }
}
