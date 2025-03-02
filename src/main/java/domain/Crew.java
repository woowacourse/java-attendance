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

    public void attend(final AttendanceDateTime attendanceDateTime) {
        if (!attendanceRecords.hasAttendanceDateTime(attendanceDateTime)) {
            throw new IllegalArgumentException("이미 출석을 했습니다. 다시 출석을 할 수 없으며 수정은 원할 시 수정기능을 사용해주세요.");
        }

        final AttendanceRecord attendanceRecord = new AttendanceRecord(attendanceDateTime);

        attendanceRecords.add(attendanceRecord);
    }

    public boolean isSameAs(final Nickname nickname) {
        return this.nickname.equals(nickname);
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
