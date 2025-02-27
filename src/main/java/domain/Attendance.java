package domain;

import java.time.LocalDate;

public class Attendance {

    private final Crew crew;
    private final AttendanceTime attendanceTime;

    public Attendance(Crew crew, AttendanceTime attendanceTime) {
        this.crew = crew;
        this.attendanceTime = attendanceTime;
    }

    public boolean isSameCrewAndTime(Attendance otherAttendance) {
        return this.crew.equals(otherAttendance.crew) && this.attendanceTime.isSameDay(otherAttendance.attendanceTime);
    }

    public boolean compareByCrewAndTime(Crew otherCrew, LocalDate day) {
        return crew.equals(otherCrew) && attendanceTime.isIn(day);
    }
}
