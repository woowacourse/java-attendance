package attendance.initialize;

import attendance.domain.Attendance;
import attendance.domain.AttendanceBook;
import attendance.domain.Crews;
import attendance.io.file.AttendanceInfo;
import attendance.io.file.AttendanceInfoLinesReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AttendanceBookInitializer {

    private final AttendanceInfoLinesReader attendanceInfoLinesReader;

    public AttendanceBookInitializer(AttendanceInfoLinesReader attendanceInfoLinesReader) {
        this.attendanceInfoLinesReader = attendanceInfoLinesReader;
    }

    public AttendanceBook initialize() {
        List<AttendanceInfo> attendanceInfos = attendanceInfoLinesReader.readAttendanceInfos();

        Set<String> nicknames = new HashSet<>();
        List<Attendance> attendances = new ArrayList<>();
        for (AttendanceInfo attendanceInfo : attendanceInfos) {
            nicknames.add(attendanceInfo.nickname());
            attendances.add(new Attendance(attendanceInfo.nickname(), attendanceInfo.attendanceDateTime()));
        }
        return new AttendanceBook(new Crews(nicknames), attendances);
    }
}
