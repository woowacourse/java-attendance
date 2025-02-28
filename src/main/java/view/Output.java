package view;

import domain.AttendanceStatus;
import domain.RiskStatus;
import dto.AttendanceResultDto;
import dto.RiskCrewDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Output {
    public static LocalTime DEFAULT_TIME = LocalTime.of(0, 0);

    public String getAttendanceMessage(AttendanceResultDto attendanceResultDto) {
        if (attendanceResultDto.attendanceStatus().equals(AttendanceStatus.ABSENCE)) {
            return getAbsenceMessage(attendanceResultDto);
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일 EEEE HH:mm", Locale.KOREAN);
        LocalDateTime dateTime = LocalDateTime.of(attendanceResultDto.localDate(), attendanceResultDto.localTime());
        String formattedDate = dateTime.format(formatter);
        return String.format("%s (%s)", formattedDate,
                getAttendanceStatusMessage(attendanceResultDto.attendanceStatus()));
    }

    private String getAbsenceMessage(AttendanceResultDto attendanceResultDto) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일 EEEE", Locale.KOREAN);
        String formattedDate = attendanceResultDto.localDate().format(formatter);
        return String.format("%s --:-- (%s)", formattedDate,
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
        for (LocalDate date = today.withDayOfMonth(1); !date.isAfter(today); date = date.plusDays(1)) {
            if (!attendanceStatuses.containsKey(date) || attendanceStatuses.get(date).equals(AttendanceStatus.ABSENCE)) {
                System.out.println(getAbsenceMessage(new AttendanceResultDto(
                        date,
                        DEFAULT_TIME,
                        AttendanceStatus.ABSENCE
                )));
            } else {
                System.out.println(getAttendanceMessage(new AttendanceResultDto(
                        date,
                        attendanceBook.get(date),
                        attendanceStatuses.get(date)
                )));
            }
        }
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

    public String getRiskStatusMessage(RiskStatus riskStatus) {
        if(riskStatus.equals(RiskStatus.EXPULSION)) {
            return "제적";
        }
        if(riskStatus.equals(RiskStatus.COUNSELING)) {
            return "면담";
        }
        if(riskStatus.equals(RiskStatus.WARNING)) {
            return "경고";
        }
        return "없음";
    }

    public String getAttendanceStatusMessage(AttendanceStatus attendanceStatus) {
        if(attendanceStatus.equals(AttendanceStatus.ABSENCE)) {
            return "결석";
        }
        if(attendanceStatus.equals(AttendanceStatus.TARDY)) {
            return "지각";
        }
        return "출석";
    }
}
