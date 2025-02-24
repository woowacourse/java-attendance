package view;

import domain.Attend;
import domain.AttendCount;
import domain.AttendStatus;
import domain.AttendanceResult;
import domain.AttendanceResults;
import domain.WarningCrew;
import domain.WarningStatus;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class OutputView {

    public void printEditResult(Attend before, Attend after, AttendStatus beforeStatus, AttendStatus afterStatus) {
        System.out.print(formatAttendAndStatus(before, beforeStatus));
        System.out.print(" -> ");
        System.out.print(formatAttendAndStatus(after, afterStatus));
        System.out.println(" 수정 완료!");
    }

    public void printAttendResult(Attend attend, AttendStatus attendStatus) {
        System.out.println(formatAttendAndStatus(attend, attendStatus));
    }

    private String formatAttendAndStatus(Attend attend, AttendStatus attendStatus) {
        String date = attend.date.format(DateTimeFormatter.ofPattern("MM월 dd일 E요일"));
        String time = "--:--";
        if (attend.time != null) {
            time = attend.time.format(DateTimeFormatter.ofPattern("HH:mm"));
        }
        String status = formatAttendStatus(attendStatus);
        return String.format("%s %s (%s)", date, time, status);
    }

    private String formatAttendStatus(AttendStatus attendStatus) {
        if (attendStatus == AttendStatus.ATTEND) {
            return "출석";
        }
        if (attendStatus == AttendStatus.LATE) {
            return "지각";
        }
        if (attendStatus == AttendStatus.ABSENCE) {
            return "결석";
        }
        return "";
    }

    public void printAttendanceResult(String name, AttendanceResults attendResult) {
        System.out.printf("이번 달 %s의 출석 기록입니다.%n%n", name);
        // 한줄씩 다 출력하는 것.
        for (AttendanceResult attendanceResult : attendResult.getAttendanceResults()) {
            Attend attend = attendanceResult.attend();
            System.out.println(formatAttendAndStatus(attend, attendanceResult.attendStatus()));
        }
        System.out.println();

        // 출석 지각 결석 출력
        AttendCount attendCount = attendResult.countAttendStatus();
        System.out.println(formatAttendCount(attendResult.countAttendStatus()));
        System.out.println();

        // 경고 메시지 출력
        WarningStatus warningStatus = attendCount.judgeWarning();
        String warningMessage = formatWarningStatus(warningStatus);
        System.out.println(warningMessage);
    }

    private String formatWarningStatus(WarningStatus warningStatus) {
        if (warningStatus == WarningStatus.CLEAR) {
            return "";
        }
        return formatWarningStatusShort(warningStatus) + " 대상자입니다.";
    }

    private String formatAttendCount(AttendCount attendCount) {
        return String.format("출석: %d회%n"
                + "지각: %d회%n"
                + "결석: %d회", attendCount.attend(), attendCount.late(), attendCount.absence());
    }

    public void printWarningCrews(List<WarningCrew> warningCrews) {
        System.out.println("제적 위험자 조회 결과");
        List<WarningCrew> sorted = warningCrews.stream()
                .sorted(Comparator.comparing(a -> ((WarningCrew) a).attendCount().calculateRank()).reversed()
                        .thenComparing(Comparator.comparing(a -> ((WarningCrew) a).name()))).toList();
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
                , formatWarningStatusShort(warningStatus));
    }

    private String formatWarningStatusShort(WarningStatus warningStatus) {
        if (warningStatus == WarningStatus.WARNING) {
            return "경고";
        }
        if (warningStatus == WarningStatus.INTERVIEW) {
            return "면담";
        }
        if (warningStatus == WarningStatus.EXPEL) {
            return "제적";
        }
        return "";
    }


}
