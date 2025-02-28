package attendance.domain;

import java.util.List;

public class AttendanceRecord {

    private final String nickname;
    private final List<Attendance> record;

    public AttendanceRecord(final String nickname, final List<Attendance> record) {
        this.nickname = nickname;
        this.record = record;
    }

    public List<Attendance> getRecord() {
        return record;
    }

    public String getNickname() {
        return nickname;
    }
}
