package attendance.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import attendance.domain.AttendanceStatus;
import attendance.domain.Crew;
import attendance.domain.Risk;

public record RiskCrewsResponse(
    List<InnerCrew> crews
) {

    public static RiskCrewsResponse of(List<Crew> crews, LocalDate date) {
        return new RiskCrewsResponse(
            crews.stream()
                .map(crew -> InnerCrew.of(crew, date))
                .toList());
    }

    public record InnerCrew(
        String name,
        Map<AttendanceStatus, Integer> statistics,
        Risk risk
    ) implements Comparable<InnerCrew> {

        public static InnerCrew of(Crew crew, LocalDate date) {
            return new InnerCrew(
                crew.getName(),
                crew.getAttendanceStatistics(date),
                crew.getRisk(date));
        }

        @Override
        public int compareTo(InnerCrew o) {
            return Integer.compare(
                AttendanceStatus.getConvertedAbsence(o.statistics),
                AttendanceStatus.getConvertedAbsence(this.statistics));
        }
    }
}
