package attendance.view;

import static attendance.domain.AttendanceStatus.ABSENT;

import attendance.domain.Attendance;
import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceRecord;
import attendance.domain.AttendanceStatus;
import attendance.domain.CrewAttendance;
import attendance.domain.WarningCrewDto;
import attendance.domain.WarningLevel;
import attendance.util.DateFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ResultView {
    private static final String CREW_ATTENDANCE_HEADER = "\n이번 달 %s의 출석 기록입니다.\n\n";
    private static final String ABSENT_TIME_FORMAT = " --:-- ";
    private static final String ATTENDANCE_STATUS_FORMAT = "(%s)";
    private static final String ATTENDANCE_STATUS_COUNTS_FORMAT = "\n%s: %d회";
    private static final String WARNING_LEVEL_NOTICE_FORMAT = "\n\n%s 대상자입니다.\n";
    private static final String WARNING_CREWS_HEADER = "\n제적 위험자 조회 결과";
    private static final String WARNING_CREW_FORMAT = "\n- %s: 결석 %d회, 지각 %d회 " + ATTENDANCE_STATUS_FORMAT;
    private static final String MODIFIED_RESULT_FORMAT = "%s -> %s " + ATTENDANCE_STATUS_FORMAT + " 수정 완료!\n";

    public void printAttendance(final Attendance attendance) {
        System.out.println(System.lineSeparator() + format(attendance));
    }

    public void printModifiedResult(final Attendance prevAttendance, final Attendance newAttendance) {
        final String result = String.format(MODIFIED_RESULT_FORMAT,
                format(prevAttendance),
                newAttendance.record().time().toString(),
                newAttendance.status().getDisplayName());
        System.out.print(System.lineSeparator() + result);
    }

    public void printCrewAttendanceHeader(final String nickname) {
        System.out.printf(CREW_ATTENDANCE_HEADER, nickname);
    }

    public void printCrewAttendances(final CrewAttendance crewAttendance, final LocalDateTime today) {
        Map<LocalDate, AttendanceStatus> attendanceStatuses = crewAttendance.getAttendanceStatusesBefore(today);
        attendanceStatuses.keySet().stream()
                .sorted(Comparator.naturalOrder())
                .forEach(localDate ->
                        System.out.println(formatAttendance(localDate, attendanceStatuses, crewAttendance)));
    }

    private String formatAttendance(final LocalDate localDate,
                                    final Map<LocalDate, AttendanceStatus> attendanceStatuses,
                                    final CrewAttendance crewAttendance) {
        if (attendanceStatuses.get(localDate) == ABSENT) {
            return formatAbsentOn(localDate);
        }
        return format(crewAttendance.getAttendanceOn(localDate));
    }

    private String format(final Attendance attendance) {
        AttendanceRecord attendanceRecord = attendance.record();
        return DateFormatter.formatDate(attendanceRecord.date()) + " "
               + attendanceRecord.time().toString() + " "
               + String.format(ATTENDANCE_STATUS_FORMAT, attendance.status().getDisplayName());
    }

    private String formatAbsentOn(final LocalDate localDate) {
        return DateFormatter.formatDate(localDate)
               + ABSENT_TIME_FORMAT
               + String.format(ATTENDANCE_STATUS_FORMAT, ABSENT.getDisplayName());
    }

    public void printAttendanceStatusCounts(final CrewAttendance crewAttendance, final LocalDateTime today) {
        final Map<AttendanceStatus, Integer> attendanceStatusCounts =
                crewAttendance.countAttendanceStatusesBefore(today);

        for (AttendanceStatus attendanceStatus : AttendanceStatus.values()) {
            System.out.printf(ATTENDANCE_STATUS_COUNTS_FORMAT,
                    attendanceStatus.getDisplayName(),
                    attendanceStatusCounts.get(attendanceStatus));
        }
    }

    public void printWarningLevel(final AttendanceBook attendanceBook, final String nickname,
                                  final LocalDateTime today) {
        System.out.printf(WARNING_LEVEL_NOTICE_FORMAT,
                attendanceBook.getWarningLevelOf(nickname, today).getDisplayName());
    }

    private static void printCrewsOf(final List<WarningCrewDto> warningCrewDtos) {
        warningCrewDtos.sort(Comparator
                .comparing((WarningCrewDto::convertedAbsentCount), Comparator.reverseOrder())
                .thenComparing(WarningCrewDto::crewNickname)
        );
        for (WarningCrewDto warningCrewDto : warningCrewDtos) {
            System.out.print(formatWarningCrew(warningCrewDto));
        }
    }

    private static String formatWarningCrew(final WarningCrewDto warningCrewDto) {
        return String.format(WARNING_CREW_FORMAT,
                warningCrewDto.crewNickname(),
                warningCrewDto.absentCount(),
                warningCrewDto.lateCount(),
                warningCrewDto.warningLevel().getDisplayName());
    }

    public void printWarningCrews(final List<WarningCrewDto> warningCrewDtos) {
        System.out.print(WARNING_CREWS_HEADER);
        List<WarningLevel> warningLevels = Arrays.stream(WarningLevel.values())
                .filter(level -> level != WarningLevel.NONE)
                .toList();
        for (WarningLevel warningLevel : warningLevels) {
            List<WarningCrewDto> warningLevelCrewDtos = new ArrayList<>();
            warningCrewDtos.stream()
                    .filter(dto -> dto.warningLevel().equals(warningLevel))
                    .forEach(warningLevelCrewDtos::add);
            printCrewsOf(warningLevelCrewDtos);
        }
        System.out.println();
    }
}
