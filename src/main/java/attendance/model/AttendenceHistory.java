package attendance.model;

import java.util.ArrayList;
import java.util.List;

public class AttendenceHistory {
    private final List<AttendenceDetail> attendenceHisoty = new ArrayList<>();

    public void addAttendenceDetail(AttendenceDetail attendenceDetail) {
        attendenceHisoty.add(attendenceDetail);
    }

    public List<AttendenceDetail> getAttendenceHisoty() {
        return attendenceHisoty;
    }

    public long getAttendenceCount() {
        return attendenceHisoty.stream()
                .filter(attendenceDetail -> attendenceDetail.getAttendence().equals(Attendence.출석))
                .count();

    }

    public long getLateCount() {
        return getTotalLateCount() % 3;

    }

    private long getTotalLateCount() {
        return attendenceHisoty.stream()
                .filter(attendenceDetail -> attendenceDetail.getAttendence().equals(Attendence.지각))
                .count();
    }

    public long getAbsenceCount() {
        return getTotalLateCount() / 3 + attendenceHisoty.stream()
                .filter(attendenceDetail -> attendenceDetail.getAttendence().equals(Attendence.결석))
                .count();

    }
}
