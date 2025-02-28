package domain;

public class Crew {

    private final Nickname nickname;
    private final AttendanceRecords attendanceRecords;
    private final DisciplinaryStatus disciplinaryStatus;

    public Crew(final Nickname nickname,
                final AttendanceRecords attendanceRecords,
                final DisciplinaryStatus disciplinaryStatus) {
        this.nickname = nickname;
        this.attendanceRecords = attendanceRecords;
        this.disciplinaryStatus = disciplinaryStatus;
    }

    public DisciplinaryStatus getDisciplinaryStatus() {
        return disciplinaryStatus;
    }

    public AttendanceRecords getAttendanceRecords() {
        return attendanceRecords;
    }

    public Nickname getNickname() {
        return nickname;
    }
}
