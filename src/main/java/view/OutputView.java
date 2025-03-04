package view;

import domain.Attend;
import domain.AttendCount;
import domain.AttendStatus;
import domain.Nickname;
import domain.WarningCrew;
import domain.WarningStatus;
import java.time.LocalTime;
import java.util.List;

public class OutputView {
    public void printAttendResult(final Attend attend, final AttendStatus attendStatus) {
        String dateFormat = DateTimeFormat.DATE.formatDate(attend.getDate());
        String timeFormat = formatAttendTime(attend);
        String attendStatusFormat = AttendStatusFormat.findStatusFormat(attendStatus);
        System.out.printf("%s %s (%s)%n%n", dateFormat, timeFormat, attendStatusFormat);
    }

    public void printEditResult(final Attend before, final AttendStatus beforeStatus,
                                final Attend after, final AttendStatus afterStatus) {
        String date = DateTimeFormat.DATE.formatDate(before.getDate());
        String beforeTime = formatAttendTime(before);
        String afterTime = formatAttendTime(after);
        String beforeStatusFormat = AttendStatusFormat.findStatusFormat(beforeStatus);
        String afterStatusFormat = AttendStatusFormat.findStatusFormat(afterStatus);
        System.out.printf("%s %s (%s) -> %s (%s) 수정 완료!%n%n",
                date, beforeTime, beforeStatusFormat, afterTime, afterStatusFormat);
    }

    private String formatAttendTime(Attend attend) {
        String result = "--:--";
        if (attend.checkTimeNull()) {
            LocalTime time = attend.getTime();
            result = DateTimeFormat.TIME.formatTime(time);
        }
        return result;
    }

    public void printSearchedAttend(final Nickname name, final List<Attend> attends,
                                    final List<AttendStatus> attendStatuses, final AttendCount attendCount,
                                    final WarningStatus warningStatus) {
        System.out.printf("이번 달 %s의 출석 기록입니다.%n%n", name.nickname());
        for (int i = 0; i < attends.size(); i++) {
            String date = DateTimeFormat.DATE.formatDate(attends.get(i).getDate());
            String time = formatAttendTime(attends.get(i));
            String attendStatus = AttendStatusFormat.findStatusFormat(attendStatuses.get(i));
            System.out.printf("%s %s (%s)%n", date, time, attendStatus);
        }
        System.out.println();
        printAttendCount(attendCount);
        System.out.println();
        printWarningStatus(warningStatus);
    }

    private void printAttendCount(final AttendCount attendCount) {
        System.out.printf("출석: %d%n", attendCount.attendCount());
        System.out.printf("지각: %d%n", attendCount.lateCount());
        System.out.printf("결석: %d%n", attendCount.absenceCount());
    }

    private void printWarningStatus(final WarningStatus warningStatus) {
        if (warningStatus != WarningStatus.PASS) {
            System.out.printf("%s 대상자 입니다.%n%n", WarningStatusFormatter.findStatusText(warningStatus));
        }
    }

    public void printWarningCrew(final List<WarningCrew> warningCrews) {
        System.out.println("제적 위험자 조회 결과");
        for (WarningCrew warningCrew : warningCrews) {
            AttendCount attendCount = warningCrew.attendCount();
            String warningStatus = WarningStatusFormatter.findStatusText(warningCrew.warningStatus());
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)%n",
                    warningCrew.name().nickname(), attendCount.absenceCount(), attendCount.lateCount(), warningStatus);
        }
        System.out.println();
    }
}
