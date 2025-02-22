package view;

import domain.Attend;
import domain.AttendCount;
import domain.AttendStatus;
import domain.AttendanceResult;
import domain.AttendanceResults;
import domain.WarningCrew;
import domain.WarningStatus;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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

    private String formatAttendAndStatus(Attend attend, AttendStatus attendStatus) {
        String date = attend.formatDate(DateTimeFormatter.ofPattern("MM월 dd일 E요일"));
        String time = "--:--";
        if (attend.hasTime()) {
            time = attend.formatTime(DateTimeFormatter.ofPattern("HH:mm"));
        }
        String status = formatAttendStatus(attendStatus);
        return String.format("%s %s (%s)", date, time, status);
    }

    public void printAttendanceResult(String name, AttendanceResults attendResult) {
        System.out.printf("이번 달 %s의 출석 기록입니다.%n%n", name);
        // 출석 현황 출력
        for (AttendanceResult attendanceResult : attendResult.getAttendanceResults()) {
            Attend attend = attendanceResult.attend();
            System.out.println(formatAttendAndStatus(attend, attendanceResult.attendStatus()));
        }
        System.out.println();

        // 출석 지각 결석 횟수 출력
        AttendCount attendCount = attendResult.countAttendStatus();
        System.out.println(formatAttendCount(attendResult.countAttendStatus()));
        System.out.println();

        // 경고 메시지 출력
        WarningStatus warningStatus = attendCount.judgeWarning();
        String warningMessage = formatWarningStatus(warningStatus);
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

    private String formatAttendStatus(AttendStatus attendStatus) {
        return Arrays.stream(AttendMessage.values())
                .filter(attendMessage -> attendMessage.match(attendStatus))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("출석 상태 판정 실패"))
                .getMessage();
    }

    private String formatAttendCount(AttendCount attendCount) {
        return String.format("출석: %d회%n"
                + "지각: %d회%n"
                + "결석: %d회", attendCount.attend(), attendCount.late(), attendCount.absence());
    }

    private String formatWarningStatus(WarningStatus warningStatus) {
        return Arrays.stream(WarningMessage.values())
                .filter(warningMessage -> warningMessage.match(warningStatus))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("경고 대상자 판정에 실패함"))
                .getLongMessage();
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
        return Arrays.stream(WarningMessage.values())
                .filter(warningMessage -> warningMessage.match(warningStatus))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("경고 판정에 실패함"))
                .getMessage();
    }
}
