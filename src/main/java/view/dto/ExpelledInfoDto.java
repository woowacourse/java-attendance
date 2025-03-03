package view.dto;

import domain.AttendanceStatus;
import domain.Attendances;
import domain.CrewStatus;
import java.time.LocalDate;
import java.util.Map;

public record ExpelledInfoDto(
        String nickname,
        int absentCount,
        int lateCount,
        CrewStatus crewStatus
) implements Comparable<ExpelledInfoDto> {
    public static ExpelledInfoDto from(String nickname, Attendances attendances, LocalDate today) {
        Map<AttendanceStatus, Integer> attendanceStatus = attendances.calculateAllAttendanceStatus(today);
        return new ExpelledInfoDto(nickname, attendanceStatus.get(AttendanceStatus.ABSENT),
                attendanceStatus.get(AttendanceStatus.LATE), attendances.calculateCrewStatus(today));
    }

    public void makeInfoLog() {
        System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n", nickname, absentCount, lateCount, crewStatus.getKorean());
    }

    @Override
    public int compareTo(ExpelledInfoDto info) {
        if (crewStatus.getPriority() == info.crewStatus.getPriority()) {
            if (absentCount + lateCount == info.absentCount + info.lateCount) {
                if (absentCount == info.absentCount) {
                    return nickname.compareTo(info.nickname);
                }
                return info.absentCount - absentCount;
            }
            return (info.lateCount + info.absentCount) - (lateCount + absentCount);
        }
        return crewStatus.getPriority() - info.crewStatus.getPriority();
    }
}
