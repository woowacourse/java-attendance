package attendance.domain;

import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AttendanceHistories {

    private List<AttendanceHistory> attendanceHistories;

    private AttendanceHistories(List<AttendanceHistory> attendanceHistories) {
        this.attendanceHistories = attendanceHistories;
    }

    public static AttendanceHistories fromRegister(Register register) {
        List<AttendanceHistory> attendanceHistories = new ArrayList<>();
        Set<Crew> crewSet = register.getCrews().getCrews();
        for (Crew crew : crewSet) {
            String crewName = crew.getCrewName();
            LocalDate now = LocalDate.now();
            DateInfos dateInfos = register.findDateInfos(crewName);
            attendanceHistories.add(AttendanceHistory.fromDateInfos(crewName, now, dateInfos));
        }
        return new AttendanceHistories(attendanceHistories);
    }

    public AttendanceHistory findAttendanceHistoryByCrewName(String crewName) {
        return attendanceHistories.stream()
                .filter(history -> history.findByCrewName(crewName))
                .findFirst()
                .orElseThrow(() -> CustomException.from(ErrorMessage.NICKNAME_NOT_PRESENCE));
    }

    public List<AttendanceHistory> findWarningAttendanceHistory() {
        return attendanceHistories.stream()
                .filter(AttendanceHistory::isWarningCrew)
                .collect(Collectors.toList());
    }

}
