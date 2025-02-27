package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Attendances {
    private final List<Attendance> records = new ArrayList<>();

    public Attendance addAttendance(LocalDateTime dateTime) {
        Attendance attendance = new Attendance(dateTime);
        if (records.contains(attendance)) {
            throw new UnsupportedOperationException("오늘 이미 출석하셨습니다. 출석 수정을 이용해주세요");
        }
        records.add(attendance);

        return attendance;
    }

    public void updateAttendance(LocalDateTime dateTime, int day) {
        if (dateTime.getDayOfMonth() > day) {
            throw new IllegalArgumentException("미래는 수정할 수 없습니다.");
        }
        Attendance attendance = new Attendance(dateTime);
        records.remove(attendance);
        records.add(attendance);
    }

    public CrewStatus calculateCrewStatus(int weekDaysCount) {
        int lateCount = 0;
        int presentCount = 0;
        for (Attendance attendance : records) {
            AttendanceStatus attendanceStatus = attendance.calculateAttendanceStatus();
            if (attendanceStatus == AttendanceStatus.LATE) {
                lateCount++;
                continue;
            }
            if (attendanceStatus == AttendanceStatus.PRESENT) {
                presentCount++;
            }
        }

        int absentCount = weekDaysCount - lateCount - presentCount;
        return CrewStatus.calculateCrewStatus(absentCount + lateCount / 3);
    }

    public List<Attendance> getRecords() {
        return records;
    }
}
