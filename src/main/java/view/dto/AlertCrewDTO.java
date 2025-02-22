package view.dto;

import domain.AttendanceStatus;
import domain.AttendanceStatusCount;
import domain.Crew;
import java.util.Map;

public record AlertCrewDTO(String nickName, int absent, int late, String AlertLevel) implements
        Comparable<AlertCrewDTO> {
    public static AlertCrewDTO from(Crew crew) {
        AttendanceStatusCount attendanceStatusCount = crew.getAttendanceCount();
        Map<AttendanceStatus, Integer> statuses = attendanceStatusCount.getStatuses();
        return new AlertCrewDTO(crew.getNickname(), statuses.get(AttendanceStatus.ABSENT),
                statuses.get(AttendanceStatus.LATE),
                attendanceStatusCount.calculateAttendanceAlertLevel().getName());
    }

    @Override
    public int compareTo(AlertCrewDTO crewDTO) {
        int thisAlertTotal = this.late + this.absent;
        int otherAlertTotal = crewDTO.late + crewDTO.absent;

        if (thisAlertTotal == otherAlertTotal) {
            if (this.absent == crewDTO.absent) {
                return this.nickName.compareTo(crewDTO.nickName);
            }
            return crewDTO.absent - this.absent;
        }
        return otherAlertTotal - thisAlertTotal;
    }
}
