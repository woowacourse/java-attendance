package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AttendanceStatistics {

    private final List<AttendanceCounts> attendanceStatistics;

    private AttendanceStatistics(List<AttendanceCounts> attendanceStatistics) {
        this.attendanceStatistics = attendanceStatistics;
    }

    public static AttendanceStatistics from(List<AttendanceCounts> attendanceStatistics) {
        return new AttendanceStatistics(attendanceStatistics);
    }

    public AttendanceStatistics orderByExpulsionRiskLevelAndNickname() {
        List<AttendanceCounts> sortedList = new ArrayList<>(attendanceStatistics);
        sortedList.sort(Comparator.comparingInt(AttendanceCounts::getExpulsionRiskLevel).reversed()
                .thenComparing(attendanceCounts -> attendanceCounts.getNickname().value()));

        return AttendanceStatistics.from(sortedList);
    }

    public List<AttendanceCounts> getAttendanceStatistics() {
        return Collections.unmodifiableList(attendanceStatistics);
    }
}
