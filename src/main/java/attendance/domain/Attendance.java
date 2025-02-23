package attendance.domain;

public class Attendance {

    private final String crewName;
    private AttendanceTime attendanceTime;

    private static final String MONDAY = "월요일";
    private static final int MONDAY_ATTEND_HOUR = 13;
    private static final int NORMAL_ATTEND_HOUR = 10;
    private static final int LATE_DEADLINE_MINUTE = 5;
    private static final int ABSENT_DEADLINE_MINUTE = 30;

    public Attendance(final String crewName, final AttendanceTime attendanceTime) {

        this.crewName = crewName;
        this.attendanceTime = attendanceTime;
    }

    public AttendanceTime getAttendanceTime() {

        return new AttendanceTime(attendanceTime.date(), attendanceTime.hour(),
                attendanceTime.minute(), attendanceTime.isAbsent());
    }

    public String getCrewName() {

        return crewName;
    }

    public String getAttendanceStatus() {

        if (attendanceTime.getDayOfWeek().equals(MONDAY)) {
            return AttendanceStatus.getAttendanceStatusWithCondition(attendanceTime, MONDAY_ATTEND_HOUR,
                    LATE_DEADLINE_MINUTE, ABSENT_DEADLINE_MINUTE).getValue();
        }

        return AttendanceStatus.getAttendanceStatusWithCondition(attendanceTime, NORMAL_ATTEND_HOUR,
                LATE_DEADLINE_MINUTE, ABSENT_DEADLINE_MINUTE).getValue();
    }

    public boolean isAlreadyAttendance(final Attendance currentAttendance) {

        if (!crewName.equals(currentAttendance.crewName)) {
            return false;
        }

        return attendanceTime.date().isEqual(currentAttendance.attendanceTime.date());
    }


    public boolean isSameByNameAndLocalDate(final String name, int year, int month, int day) {

        return crewName.equals(name) && day == attendanceTime.getDay() && year == attendanceTime.getYear()
                && month == attendanceTime.getMonth();
    }

    public void modifyAttendanceTime(final AttendanceTime modifyAttendanceTime) {

        this.attendanceTime = modifyAttendanceTime;
    }
}
