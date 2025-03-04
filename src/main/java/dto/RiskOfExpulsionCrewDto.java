package dto;

import domain.AttendanceHistory;
import domain.AttendanceStatus;
import domain.Crew;
import domain.RiskOfExpulsionStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record RiskOfExpulsionCrewDto(
        String crewName,
        int absenceCount,
        int lateCount,
        String expulsionStatus
) {

    public static RiskOfExpulsionCrewDto of(final AttendanceHistory attendanceHistory, final LocalDate date) {
        final Crew crew = attendanceHistory.getCrew();
        final Map<AttendanceStatus, Integer> statistics = attendanceHistory.calculateAttendanceStatusStatistics(date);
        final RiskOfExpulsionStatus riskOfExpulsionStatus = attendanceHistory.calculateRiskOfExpulsionStatus(date);
        return new RiskOfExpulsionCrewDto(crew.getName(), statistics.get(AttendanceStatus.ABSENCE),
                statistics.get(AttendanceStatus.LATE), convertRiskOfExpulsionStatusToString(riskOfExpulsionStatus));
    }

    public static List<RiskOfExpulsionCrewDto> of(final List<AttendanceHistory> attendanceHistories,
                                                  final LocalDate date) {
        return attendanceHistories.stream()
                .map(attendanceHistory -> RiskOfExpulsionCrewDto.of(attendanceHistory, date))
                .collect(Collectors.toList());
    }

    public static String convertRiskOfExpulsionStatusToString(final RiskOfExpulsionStatus riskOfExpulsionStatus) {
        if (riskOfExpulsionStatus == RiskOfExpulsionStatus.EXPULSION) {
            return "제적";
        }
        if (riskOfExpulsionStatus == RiskOfExpulsionStatus.INTERVIEW) {
            return "면담";
        }
        if (riskOfExpulsionStatus == RiskOfExpulsionStatus.WARNING) {
            return "경고";
        }
        return "이상무";
    }
}
