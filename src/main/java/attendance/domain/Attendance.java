package attendance.domain;

public class Attendance {

    private final static String MONDAY = "월요일";
    private final static int MONDAY_EDUCATION_HOUR = 13;
    private final static int EDUCATION_HOUR = 10;
    private final static int LATE_MINUTE = 5;
    private final static int ABSENT_MINUTE = 30;

    private final String crewName;
    private Time attendanceTime;

    public Attendance(final String crewName, final Time attendanceTime) {
        this.crewName = crewName;
        this.attendanceTime = attendanceTime;
    }

    public String getAttendanceStatus() {
        if (attendanceTime.getDayOfWeek().equals(MONDAY)) {

            return AttendanceStatus.checkStatusWithCondition(attendanceTime, MONDAY_EDUCATION_HOUR, LATE_MINUTE,
                    ABSENT_MINUTE);
        }

        return AttendanceStatus.checkStatusWithCondition(attendanceTime, EDUCATION_HOUR, LATE_MINUTE, ABSENT_MINUTE);
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

    public void modifyAttendanceTime(final Time modifyTime) {
        this.attendanceTime = modifyTime;
    }

    public Time getAttendanceTime() {
        return attendanceTime;
    }

    public String getCrewName() {
        return crewName;
    }
}
