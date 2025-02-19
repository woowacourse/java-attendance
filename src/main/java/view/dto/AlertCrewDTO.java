package view.dto;

import domain.AttendanceCount;
import domain.Crew;

public record AlertCrewDTO(String nickName, int absent, int late, String AlertLevel) implements
        Comparable<AlertCrewDTO> {
    public static AlertCrewDTO from(Crew crew) {
        AttendanceCount attendanceCount = crew.getAttendanceCount();
        return new AlertCrewDTO(crew.getNickname(), attendanceCount.getAbsent(), attendanceCount.getLate(),
                attendanceCount.calculateAttendanceAlertLevel().getName());
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
