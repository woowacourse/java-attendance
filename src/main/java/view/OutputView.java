package view;

import domain.AttendanceDto;
import domain.AttendanceStatus;
import domain.CrewDto;
import domain.Punishment;
import domain.Week;
import java.time.LocalDateTime;
import java.util.List;

public final class OutputView {

    private static final int ABSENCE_HOUR = 0;

    private OutputView() {
    }

    public static void printAttendance(AttendanceDto attendanceSummary) {
        final LocalDateTime localDateTime = attendanceSummary.attendanceDateTime();
        final AttendanceStatus attendanceStatus = attendanceSummary.attendanceStatus();
        final String format = String.format("%s (%s)", localDateTime.format(Week.KOREAN_DATE_TIME_FORMAT),
                attendanceStatus.getDisplayName());

        printMessageWithLineSeparator(format);
    }

    public static void printUpdateAttendance(final AttendanceDto oldAttendanceSummary,
                                             final AttendanceDto newAttendanceSummary) {
        final LocalDateTime oldDateTime = oldAttendanceSummary.attendanceDateTime();
        final String oldStatus = getStatusDisplayName(oldAttendanceSummary);
        final LocalDateTime newDateTime = newAttendanceSummary.attendanceDateTime();
        final String newStatus = getStatusDisplayName(newAttendanceSummary);

        final String oldFormat = String.format("%s (%s)", oldDateTime.format(Week.KOREAN_DATE_TIME_FORMAT), oldStatus);
        final String newFormat = String.format("%s (%s)", newDateTime.toLocalDate(), newStatus);
        final String finalFormat = String.format("%s -> %s 수정 완료!", oldFormat, newFormat);

        printMessageWithLineSeparator(finalFormat);
    }

    private static String getStatusDisplayName(final AttendanceDto oldAttendanceSummary) {
        final AttendanceStatus attendanceStatus = oldAttendanceSummary.attendanceStatus();
        return attendanceStatus.getDisplayName();
    }

    public static void printCrewAttendances(final CrewDto crewDto,
                                            List<AttendanceDto> attendanceSummaries) {
        final String nickname = crewDto.nickname();
        final Punishment punishment = crewDto.punishment();
        final String titleFormat = "이번 달 %s의 출석 기록입니다.";

        printMessageWithLineSeparator(String.format(titleFormat, nickname));
        printAttendances(attendanceSummaries);
        printCountAboutAttendance(crewDto);
        printMessageWithLineSeparator(String.format("%s 대상자입니다.", punishment.getPunishmentName()));
    }

    private static void printAttendances(List<AttendanceDto> attendanceSummaries) {
        for (AttendanceDto attendanceSummary : attendanceSummaries) {
            final LocalDateTime attendanceDateTime = attendanceSummary.attendanceDateTime();
            String formattedDateTime = adjustFormat(attendanceDateTime);

            printMessage(formattedDateTime);
        }
    }

    private static String adjustFormat(LocalDateTime localDateTime) {
        if (localDateTime.getHour() == ABSENCE_HOUR) {
            return localDateTime.format(Week.ABSENCE_FORMAT);
        }
        return localDateTime.format(Week.KOREAN_DATE_TIME_FORMAT);
    }

    private static void printCountAboutAttendance(final CrewDto crewDto) {
        final String countFormat = "%s: %d회";

        printMessage(
                String.format(
                        countFormat,
                        AttendanceStatus.ATTENDANCE.getDisplayName(),
                        crewDto.attendanceCount()
                )
        );
        printMessage(
                String.format(countFormat, AttendanceStatus.TARDINESS.getDisplayName(),
                        crewDto.tardinessCount()));
        printMessage(
                String.format(countFormat, AttendanceStatus.ABSENCE.getDisplayName(), crewDto.absenceCount()));
    }

    public static void printAllExpulsion(final List<CrewDto> crewDtos) {
        printMessageWithLineSeparator("제적 위험자 조회");

        for (CrewDto crewDto : crewDtos) {
            final String nickname = crewDto.nickname();
            final int absenceCount = crewDto.absenceCount();
            final int tardinessCount = crewDto.tardinessCount();
            final Punishment punishment = crewDto.punishment();
            final String punishmentDisplayName = punishment.getPunishmentName();

            if (punishment.equals(Punishment.NONE)) {
                continue;
            }
            final String outputFormat = "- %s: 결석 %d회, 지각 %d회 (%s)";
            printMessage(String.format(outputFormat, nickname, absenceCount, tardinessCount, punishmentDisplayName));
        }
    }

    private static void printMessage(String message) {
        System.out.println(message);
    }

    private static void printMessageWithLineSeparator(String message) {
        System.out.println(separateLine() + message + separateLine());
    }

    private static String separateLine() {
        return System.lineSeparator();
    }
}
