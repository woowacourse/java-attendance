package components;

import domain.AttendanceHistory;
import domain.AttendanceRecord;
import domain.AttendanceStatus;
import domain.Crew;
import domain.RiskOfExpulsionStatus;
import dto.AttendanceRecordDto;
import dto.RiskOfExpulsionCrewDto;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DtoConverter {

    public List<AttendanceRecordDto> convertToAttendanceRecordDtos(final List<AttendanceRecord> attendanceRecords) {
        return attendanceRecords.stream()
                .map(this::convertToAttendanceRecordDto)
                .collect(Collectors.toList());
    }

    private AttendanceRecordDto convertToAttendanceRecordDto(final AttendanceRecord attendanceRecord) {
        return new AttendanceRecordDto(
                attendanceRecord.getDateTime(),
                attendanceRecord.isEmpty(),
                convertAttendanceStatusToString(attendanceRecord.calculateAttendanceStatus())
        );
    }

    private String convertAttendanceStatusToString(final AttendanceStatus attendanceStatus) {
        if (attendanceStatus == AttendanceStatus.ABSENCE) {
            return "결석";
        }
        if (attendanceStatus == AttendanceStatus.LATE) {
            return "지각";
        }
        return "출석";
    }

    public Map<String, Integer> convertToStringStatistics(final Map<AttendanceStatus, Integer> statistics) {
        return statistics.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        entry -> convertAttendanceStatusToString(entry.getKey()),
                        entry -> entry.getValue())
                );
    }

    public String convertRiskOfExpulsionStatusToString(final RiskOfExpulsionStatus riskOfExpulsionStatus) {
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

    public List<RiskOfExpulsionCrewDto> convertToRiskOfExpulsionCrewDtos(
            final List<AttendanceHistory> attendanceHistories, final LocalDate date
    ) {
        return attendanceHistories.stream()
                .map(attendanceHistory -> convertToRiskOfExpulsionCrewDto(attendanceHistory, date))
                .collect(Collectors.toList());
    }

    private RiskOfExpulsionCrewDto convertToRiskOfExpulsionCrewDto(final AttendanceHistory attendanceHistory,
                                                                  final LocalDate date) {
        final Crew crew = attendanceHistory.getCrew();
        final Map<AttendanceStatus, Integer> statistics = attendanceHistory.calculateAttendanceStatusStatistics(date);
        final RiskOfExpulsionStatus riskOfExpulsionStatus = attendanceHistory.calculateRiskOfExpulsionStatus(date);
        return new RiskOfExpulsionCrewDto(crew.getName(), statistics.get(AttendanceStatus.ABSENCE),
                statistics.get(AttendanceStatus.LATE), convertRiskOfExpulsionStatusToString(riskOfExpulsionStatus));
    }
}
