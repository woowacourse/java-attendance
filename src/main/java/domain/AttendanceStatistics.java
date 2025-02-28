package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AttendanceStatistics{

    private final List<AttendanceCounts> nicknameToAttendanceCounts;

    private AttendanceStatistics(List<AttendanceCounts> nicknameToAttendanceCounts) {
        this.nicknameToAttendanceCounts = nicknameToAttendanceCounts;
    }

    public static AttendanceStatistics from(List<AttendanceCounts> attendanceStatistics) {
        return new AttendanceStatistics(attendanceStatistics);
    }

    public AttendanceStatistics orderByExpulsionRiskLevelAndNickname() {
        List<AttendanceCounts> sortedList = new ArrayList<>(nicknameToAttendanceCounts);
        sortedList.sort(Comparator.comparingInt(AttendanceCounts::getExpulsionRiskLevel).reversed()
                .thenComparing(attendanceCounts -> attendanceCounts.getNickname().value()));

        return AttendanceStatistics.from(sortedList);
    }

    public List<AttendanceCounts> getNicknameToAttendanceCounts() {
        return Collections.unmodifiableList(nicknameToAttendanceCounts);
    }
}