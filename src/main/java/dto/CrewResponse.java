package dto;

import domain.RiskStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public record CrewResponse(
        String name,
        Map<LocalDate, LocalTime> attendanceBook,
        int attendanceCount,
        int absenceCount,
        int tardyCount,
        RiskStatus riskStatus
) {
    public CrewResponse(String name, int attendanceCount, int absenceCount, int tardyCount, RiskStatus riskStatus) {
        this(name, Map.of(), attendanceCount, absenceCount, tardyCount, riskStatus);
    }
}
