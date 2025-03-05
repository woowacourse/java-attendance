package view;

import controller.Menu;
import domain.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OutputView {
    public void printDateAndMenus() {
        String formattedDate = CustomDate.now().format(
                DateTimeFormatter.ofPattern("MM월 dd일 E요일").withLocale(Locale.forLanguageTag("ko"))
        );
        System.out.printf("오늘은 %s입니다. 기능을 선택해 주세요.%n", formattedDate);

        Arrays.stream(Menu.values()).forEach(menu -> {
            System.out.printf("%s. %s%n", menu.getInputValue(), menu.getExpression());
        });
    }

    public void printAttendanceResult(LocalDate date, LocalTime time, AttendanceStatus status) {
        String formattedDate = date.format(
                DateTimeFormatter.ofPattern("MM월 dd일 E요일").withLocale(Locale.forLanguageTag("ko"))
        );
        String formattedTime = time.format(
                DateTimeFormatter.ofPattern("HH:mm").withLocale(Locale.forLanguageTag("ko"))
        );

        String formattedStatus = "(" + getAttendanceStatusText(status) + ")";
        System.out.println(formattedDate + " " + formattedTime + " " + formattedStatus);
    }

    public void printExceptionMessage(String message) {
        System.out.println(message);
    }

    public void printModifyResult(
            LocalDate date,
            LocalTime beforeTime,
            AttendanceStatus beforeStatus,
            LocalTime afterTime,
            AttendanceStatus afterStatus
    ) {
        String formattedDate = getFormattedDate(date);

        String formattedBeforeTime = "--:--";
        if (beforeTime != null) {
            formattedBeforeTime = getFormattedTime(beforeTime);
        }

        String formattedAfterTime = "--:--";
        if (afterTime != null) {
            formattedAfterTime = getFormattedTime(afterTime);
        }

        System.out.printf("%s %s (%s) -> %s %s (%s) 수정 완료!\n",
                formattedDate,
                formattedBeforeTime,
                getAttendanceStatusText(beforeStatus),
                formattedDate,
                formattedAfterTime,
                getAttendanceStatusText(afterStatus)
        );
    }

    public void printAttendances(String crew, List<Attendance> attendances) {
        System.out.println("이번 달 " + crew + "의 출석 기록입니다.");
        List<String> formatted = attendances.stream()
                .sorted(Comparator.comparing(Attendance::getDate))
                .map(this::getFormattedAttendance)
                .toList();
        for (String attendanceText : formatted) {
            System.out.println(attendanceText);
        }
    }

    public void printAttendanceCounts(final int attendance, final int late, final int absence) {
        System.out.println("출석: " + attendance + "회");
        System.out.println("지각: " + late + "회");
        System.out.println("결석: " + absence + "회");
    }

    public void printRiskStatus(ExpulsionRiskStatus status) {
        if (status == ExpulsionRiskStatus.NORMAL) {
            return;
        }
        System.out.println(getExpulsionRiskText(status) + " 대상자입니다.");
    }

    public void printRiskCrewStatistics(RiskCrewStatistics statistics) {
        System.out.println("제적 위험자 조회 결과");
        List<String> expelledCrews = statistics.getCrewNamesByStatus(ExpulsionRiskStatus.EXPELLED);
        printRiskCrews(expelledCrews, statistics, ExpulsionRiskStatus.EXPELLED);
        List<String> interviewCrews = statistics.getCrewNamesByStatus(ExpulsionRiskStatus.INTERVIEW);
        printRiskCrews(interviewCrews, statistics, ExpulsionRiskStatus.INTERVIEW);
        List<String> warningCrews = statistics.getCrewNamesByStatus(ExpulsionRiskStatus.WARNING);
        printRiskCrews(warningCrews, statistics, ExpulsionRiskStatus.WARNING);
    }

    private void printRiskCrews(List<String> crews, RiskCrewStatistics statistics, ExpulsionRiskStatus status) {
        List<String> sortedCrews = crews.stream()
                .sorted(Comparator.comparing(statistics::getTotalAbsenceCount).reversed())
                .sorted()
                .toList();
        for (String crew : sortedCrews) {
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n",
                    crew,
                    statistics.getAbsenceCount(crew),
                    statistics.getLateCount(crew),
                    getExpulsionRiskText(status)
            );
        }
    }

    private String getFormattedAttendance(Attendance attendance) {
        String date = getFormattedDate(attendance.getDate());
        AttendanceStatus status = attendance.getStatus();
        if (attendance.isTimeRecorded()) {
            return date + " " + getFormattedTime(attendance.getTime()) + " " + getAttendanceStatusText(status);
        }
        return date + " --:-- " + getAttendanceStatusText(status);
    }

    private String getFormattedDate(LocalDate date) {
        return date.format(
                DateTimeFormatter.ofPattern("MM월 dd일 E요일").withLocale(Locale.forLanguageTag("ko"))
        );
    }

    private String getFormattedTime(LocalTime time) {
        return time.format(
                DateTimeFormatter.ofPattern("HH:mm").withLocale(Locale.forLanguageTag("ko"))
        );
    }

    private String getAttendanceStatusText(AttendanceStatus status) {
        return switch (status) {
            case ATTENDANCE -> "출석";
            case LATE -> "지각";
            case ABSENCE -> "결석";
        };
    }

    private String getExpulsionRiskText(ExpulsionRiskStatus status) {
        return switch (status) {
            case EXPELLED -> "제적";
            case INTERVIEW -> "면담";
            case WARNING -> "경고";
            default -> "";
        };
    }
}
