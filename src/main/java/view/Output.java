package view;

import domain.AttendanceStatus;
import domain.RiskStatus;
import dto.AttendanceResultDto;
import dto.RiskCrewDto;
import util.Dates;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static util.Parser.localDateToDateMessage;

public class Output {
    private static final Map<RiskStatus, String> RISK_STATUS_MESSAGE = Map.of(
            RiskStatus.EXPULSION, "제적",
            RiskStatus.COUNSELING, "면담",
            RiskStatus.WARNING, "경고"
    );
    private static final Map<AttendanceStatus, String> ATTENDANCE_STATUS_MESSAGE = Map.of(
            AttendanceStatus.ABSENCE, "결석",
            AttendanceStatus.TARDY, "지각",
            AttendanceStatus.ATTEND, "출석"
    );
    public String getAttendanceMessage(AttendanceResultDto attendanceResultDto) {
        if (attendanceResultDto.attendanceStatus().equals(AttendanceStatus.ABSENCE)) {
            return getAbsenceMessage(attendanceResultDto);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = attendanceResultDto.localTime().format(formatter);
        return String.format("%s %s (%s)",
                localDateToDateMessage(attendanceResultDto.localDate()),
                formattedTime,
                getAttendanceStatusMessage(attendanceResultDto.attendanceStatus()));
    }

    private String getAbsenceMessage(AttendanceResultDto attendanceResultDto) {
        return String.format("%s --:-- (%s)", localDateToDateMessage(attendanceResultDto.localDate()),
                getAttendanceStatusMessage(attendanceResultDto.attendanceStatus()));
    }


    public void printEditAttendanceResult(AttendanceResultDto beforeAttendanceResult, AttendanceResultDto afterAttendanceResult) {
        System.out.println(getAttendanceMessage(beforeAttendanceResult) +
                " -> " +
                getAttendanceMessage(afterAttendanceResult)
        );
    }

    public void printAttendResult(AttendanceResultDto attendanceResultDto) {
        System.out.println(getAttendanceMessage(attendanceResultDto));
    }

    public void printAttendanceRecord(String name, LocalDate today, Map<LocalDate, LocalTime> attendanceBook, Map<LocalDate, AttendanceStatus> attendanceStatuses) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n", name);
        today.withDayOfMonth(1).datesUntil(today.plusDays(1))
                .filter(Dates::isNotHoliday)
                .forEach(date -> System.out.println(getAttendanceMessage(new AttendanceResultDto(
                        date,
                        attendanceBook.getOrDefault(date, Dates.DEFAULT_TIME),
                        attendanceStatuses.getOrDefault(date, AttendanceStatus.ABSENCE)
                ))));
    }

    public void printRiskCrews(List<RiskCrewDto> riskCrews) {
        riskCrews.stream().sorted(
                        Comparator.comparingInt((RiskCrewDto riskCrew) -> riskCrew.riskStatus().getRiskValue()).reversed()
                                .thenComparingInt(riskCrew -> RiskStatus.calculateRiskValue(riskCrew.absenceCount(), riskCrew.tardyCount())).reversed()
                                .thenComparing(RiskCrewDto::name)
                )
                .forEach(riskCrew -> {
                    System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n",
                            riskCrew.name(),
                            riskCrew.absenceCount(),
                            riskCrew.tardyCount(),
                            getRiskStatusMessage(riskCrew.riskStatus()));
                });
    }

    private String getRiskStatusMessage(RiskStatus riskStatus) {
        return RISK_STATUS_MESSAGE.getOrDefault(riskStatus, "없음");
    }

    private String getAttendanceStatusMessage(AttendanceStatus attendanceStatus) {
        return ATTENDANCE_STATUS_MESSAGE.getOrDefault(attendanceStatus, "출석");
    }

    public void printError(String message) {
        System.out.println("[ERROR]" + message);
    }
}
