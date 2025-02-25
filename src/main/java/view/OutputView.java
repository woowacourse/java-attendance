package view;

import domain.Attend;
import domain.AttendCount;
import domain.AttendStatus;
import domain.AttendanceResult;
import domain.WarningCrew;
import domain.WarningStatus;
import dto.AttendResultDto;
import java.util.Comparator;
import java.util.List;

public class OutputView {

    public void printEditResult(Attend before, Attend after, AttendStatus beforeStatus, AttendStatus afterStatus) {
        System.out.print(formatAttendAndStatus(before, beforeStatus));
        System.out.print(" -> ");
        System.out.print(formatAttendAndStatus(after, afterStatus));
        System.out.println(" 수정 완료!");
        System.out.println();
    }

    public void printAttendResult(Attend attend, AttendStatus attendStatus) {
        System.out.println(formatAttendAndStatus(attend, attendStatus));
        System.out.println();
    }

    public void printAttendanceResult(AttendResultDto attendResultDto) {
        String name = attendResultDto.name();
        System.out.printf("이번 달 %s의 출석 기록입니다.%n%n", name);
        List<AttendanceResult> attendResult = attendResultDto.attendanceResults();
        AttendCount attendCount = attendResultDto.attendCount();
        printAttendanceStatus(attendResult);
        printAttendCount(attendCount);
        printWarningMessage(attendCount);
    }

    private void printAttendanceStatus(List<AttendanceResult> attendResult) {
        for (AttendanceResult attendanceResult : attendResult) {
            Attend attend = attendanceResult.attend();
            AttendStatus attendStatus = attendanceResult.attendStatus();
            System.out.println(formatAttendAndStatus(attend, attendStatus));
        }
        System.out.println();
    }

    private String formatAttendAndStatus(Attend attend, AttendStatus attendStatus) {
        String date = attend.formatDate(DateTimeFormat.DATE.getDateTimeFormatter());
        String time = "--:--";
        if (attend.hasTime()) {
            time = attend.formatTime(DateTimeFormat.TIME.getDateTimeFormatter());
        }
        String status = AttendMessage.formatAttendStatus(attendStatus);
        return String.format("%s %s (%s)", date, time, status);
    }

    private void printAttendCount(AttendCount attendCount) {
        System.out.println(formatAttendCount(attendCount));
        System.out.println();
    }

    private String formatAttendCount(AttendCount attendCount) {
        return String.format("출석: %d회%n"
                + "지각: %d회%n"
                + "결석: %d회", attendCount.attend(), attendCount.late(), attendCount.absence());
    }

    private static void printWarningMessage(AttendCount attendCount) {
        WarningStatus warningStatus = attendCount.judgeWarning();
        String warningMessage = WarningMessage.formatWarningStatus(warningStatus);
        System.out.println(warningMessage);
    }

    public void printWarningCrews(List<WarningCrew> warningCrews) {
        System.out.println("제적 위험자 조회 결과");
        List<WarningCrew> sorted = warningCrews.stream()
                .sorted(Comparator.comparing(warningCrew -> ((WarningCrew) warningCrew).attendCount().calculateRank())
                        .reversed())
                .toList();
        for (WarningCrew warningCrew : sorted) {
            String message = formatWarningCrew(warningCrew);
            System.out.println(message);
        }
        System.out.println();
    }

    private String formatWarningCrew(WarningCrew warningCrew) {
        WarningStatus warningStatus = warningCrew.attendCount().judgeWarning();
        return String.format("- %s: 결석 %d회, 지각 %d회 (%s)"
                , warningCrew.name()
                , warningCrew.attendCount().absence()
                , warningCrew.attendCount().late()
                , WarningMessage.formatWarningStatusShort(warningStatus));
    }
}
