package view;

import domain.attendance.AttendanceStatus;
import dto.AttendanceLogDto;
import dto.AttendanceResultDto;
import dto.CrewDto;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import util.Convertor;

public class OutputView {

    public static void printErrorMessage(String message) {
        System.out.println("\n" + message + "\n");
    }

    public static void printAttendanceRegisterLog(AttendanceLogDto attendanceLogDto) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(System.lineSeparator())
                .append(formatAttendanceLog(attendanceLogDto))
                .append(System.lineSeparator());
        System.out.println(stringBuilder);
    }

    public static void printAttendanceEditLog(AttendanceLogDto oldAttendanceLogDto,
                                              AttendanceLogDto newAttendanceLogDto) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(System.lineSeparator())
                .append(formatAttendanceLog(oldAttendanceLogDto))
                .append(" -> ")
                .append(formatAttendanceLogTime(newAttendanceLogDto.attendanceTime()))
                .append(" 수정 완료!")
                .append(System.lineSeparator());
        System.out.println(stringBuilder);
    }

    public static void printCrewAttendance(String crewName, List<AttendanceLogDto> attendanceLogDtos,
                                           AttendanceResultDto attendanceResultDto, LocalDate runDate) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(formatCrewAttendanceHeader(crewName))
                .append(System.lineSeparator())
                .append(formatCrewAttendanceLogsStatus(attendanceLogDtos, runDate))
                .append(System.lineSeparator())
                .append(formatCrewAttendanceResult(attendanceResultDto))
                .append(System.lineSeparator())
                .append(formatCrewStatus(attendanceResultDto));
        System.out.println(stringBuilder);
    }

    public static void printExpulsionRiskCrews(List<Map.Entry<CrewDto, AttendanceResultDto>> expulsionRiskCrewsDtos) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<CrewDto, AttendanceResultDto> entry : expulsionRiskCrewsDtos) {
            stringBuilder.append(formatExpulsionRiskCrew(entry.getKey(), entry.getValue()))
                    .append(System.lineSeparator());
        }
        System.out.println(stringBuilder);
    }

    private static String formatCrewAttendanceHeader(String crewName) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(System.lineSeparator())
                .append(String.format("이번 달 %s의 출석 기록입니다.", crewName));
        return stringBuilder.toString();
    }

    private static String formatCrewAttendanceLogsStatus(List<AttendanceLogDto> attendanceLogDtos, LocalDate runDate) {
        StringBuilder stringBuilder = new StringBuilder();
        List<LocalDate> logDates = generateLogDates(runDate);
        for (LocalDate logDate : logDates) {
            stringBuilder.append(formatCrewAttendanceLogStatus(attendanceLogDtos, logDate))
                    .append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    private static List<LocalDate> generateLogDates(LocalDate endDate) {
        List<LocalDate> logDates = new ArrayList<>();
        LocalDate startDate = LocalDate.of(2024, 12, 1);
        for (LocalDate logDate = startDate; logDate.isBefore(endDate); logDate = logDate.plusDays(1)) {
            addLogDate(logDates, logDate);
        }
        return logDates;
    }

    private static void addLogDate(List<LocalDate> logDates, LocalDate logDate) {
        if (!isWeekend(logDate.getDayOfWeek()) && !isHoliday(logDate)) {
            logDates.add(logDate);
        }
    }

    private static boolean isWeekend(DayOfWeek dayOfWeek) {
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return true;
        }
        return false;
    }

    private static boolean isHoliday(LocalDate date) {
        if (date.equals(LocalDate.of(2024, 12, 25))) {
            return true;
        }
        return false;
    }

    private static String formatCrewAttendanceLogStatus(List<AttendanceLogDto> attendanceLogDtos, LocalDate logDate) {
        return attendanceLogDtos.stream()
                .filter(logDto -> logDto.attendanceDate().equals(logDate))
                .findFirst()
                .map(OutputView::formatAttendanceLog)
                .orElse(formatUnattendAttendanceLog(logDate));
    }

    private static String formatAttendanceLog(AttendanceLogDto attendanceLogDto) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(formatAttendanceLogDate(attendanceLogDto.attendanceDate()))
                .append(formatAttendanceLogTime(attendanceLogDto.attendanceTime()))
                .append(formatAttendanceLogStatus(attendanceLogDto.attendanceStatus()));
        return stringBuilder.toString();
    }

    private static String formatUnattendAttendanceLog(LocalDate logDate) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(formatAttendanceLogDate(logDate))
                .append(" --:--")
                .append(formatAttendanceLogStatus(AttendanceStatus.ABSENT));
        return stringBuilder.toString();
    }

    private static String formatAttendanceLogDate(LocalDate attendanceLogDate) {
        return String.format("%02d월 %02d일 %s요일",
                attendanceLogDate.getMonthValue(),
                attendanceLogDate.getDayOfMonth(),
                Convertor.convertDayOfWeekToKorean(attendanceLogDate.getDayOfWeek()));
    }

    private static String formatAttendanceLogTime(LocalTime attendanceLogTime) {
        return String.format(" %02d:%02d",
                attendanceLogTime.getHour(),
                attendanceLogTime.getMinute());
    }

    private static String formatAttendanceLogStatus(AttendanceStatus attendanceStatus) {
        return String.format(" (%s)", attendanceStatus.getDescription());
    }

    private static String formatCrewAttendanceResult(AttendanceResultDto attendanceResultDto) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(
                        formatCrewAttendanceResultStatus(AttendanceStatus.ATTEND, attendanceResultDto.attendCount()))
                .append(formatCrewAttendanceResultStatus(AttendanceStatus.LATE, attendanceResultDto.lateCount()))
                .append(formatCrewAttendanceResultStatus(AttendanceStatus.ABSENT, attendanceResultDto.absentCount()));
        return stringBuilder.toString();
    }

    private static String formatCrewAttendanceResultStatus(AttendanceStatus attendanceStatus, int count) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%s: %s회", attendanceStatus.getDescription(), count))
                .append(System.lineSeparator());
        return stringBuilder.toString();
    }

    private static String formatCrewStatus(AttendanceResultDto attendanceResultDto) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%s 대상자입니다.", attendanceResultDto.crewStatus()))
                .append(System.lineSeparator());
        return stringBuilder.toString();
    }

    private static String formatExpulsionRiskCrew(CrewDto crewDto, AttendanceResultDto attendanceResultDto) {
        return String.format("- %s: 결석 %d회, 지각 %d회 (%s)", crewDto.name(), attendanceResultDto.lateCount(),
                attendanceResultDto.absentCount(), attendanceResultDto.crewStatus());
    }
}
