package attendance.domain;

import java.util.List;

public class AttendanceRecord {

    private final String nickname;
    private final Attendances record;
    private final AttendanceStatus status;

    private AttendanceRecord(final String nickname, final Attendances record, final AttendanceStatus status) {
        this.nickname = nickname;
        this.record = record;
        this.status = status;
    }

    public static AttendanceRecord fromNicknameAndAttendances(final String nickname, final List<Attendance> attendances) {
        AttendanceStatus status = AttendanceStatus.fromAttendances(attendances);
        return new AttendanceRecord(nickname, new Attendances(attendances), status);
    }

    public String getNickname() {
        return nickname;
    }
}
