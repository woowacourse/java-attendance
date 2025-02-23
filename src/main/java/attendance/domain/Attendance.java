package attendance.domain;

public class Attendance {

    private final String crewName;
    private AttendanceTime attendanceTime;

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

        if (attendanceTime.getDayOfWeek().equals("월요일")) {
            return AttendanceStatus.getAttendanceStatusWithCondition(attendanceTime, 13, 5, 30).getValue();
        }

        return AttendanceStatus.getAttendanceStatusWithCondition(attendanceTime, 10, 5, 30).getValue();
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
