package attendance.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AttendanceHistories {

    private final List<AttendanceHistory> attendanceHistories;

    private AttendanceHistories(List<AttendanceHistory> attendanceHistories) {
        this.attendanceHistories = attendanceHistories;
    }

    public static AttendanceHistories fromRegister(LocalDate now, Register register) {
        List<AttendanceHistory> attendanceHistories = new ArrayList<>();
        Set<Crew> crewSet = register.getCrews().getCrews();
        for (Crew crew : crewSet) {
            String crewName = crew.getCrewName();
            DateInfos dateInfos = register.findDateInfos(crewName);
            attendanceHistories.add(AttendanceHistory.fromDateInfos(crewName, now, dateInfos));
        }
        return new AttendanceHistories(attendanceHistories);
    }

    public List<AttendanceHistory> findWarningAttendanceHistory() {
        return attendanceHistories.stream()
                .filter(AttendanceHistory::isWarningCrew)
                .collect(Collectors.toList());
    }

}
