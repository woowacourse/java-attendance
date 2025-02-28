package attendance.domain;

import java.util.List;

public class AttendanceRecord implements Comparable<AttendanceRecord> {

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

    public Attendances getRecord() {
        return record;
    }

    public AttendanceStatus getStatus() {
        return status;
    }

    @Override
    public int compareTo(final AttendanceRecord o) {
        int statusCompare = status.compareTo(o.status);

        if (statusCompare == 0) {
            return nickname.compareTo(o.nickname);
        }

        return statusCompare;
    }
}
