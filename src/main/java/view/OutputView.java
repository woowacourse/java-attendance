package view;

import domain.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class OutputView {
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM월 dd일 E요일");

    public void printAttendanceRecord(AttendanceRecord attendanceRecord) {
        AttendanceStatus attendanceStatus = attendanceRecord.getAttendanceStatus();
        LocalDate date = attendanceRecord.getDate();
        LocalTime time = attendanceRecord.getTime();
        String displayTime = getDisplayTime(attendanceStatus, time);

        System.out.print(System.lineSeparator() + dateFormatter.format(date) + " " + displayTime + " (" +
                attendanceStatus.getName() + ")");
    }

    public void printUpdateResult(AttendanceRecord oldRecord, AttendanceRecord newRecord, LocalTime newTime) {
        LocalDate date = oldRecord.getDate();
        LocalTime oldTime = oldRecord.getTime();
        AttendanceStatus oldAttendanceStatus = oldRecord.getAttendanceStatus();
        AttendanceStatus newAttendanceStatus = newRecord.getAttendanceStatus();

        System.out.println(System.lineSeparator() + dateFormatter.format(date) + " " +
                getDisplayTime(oldAttendanceStatus, oldTime) + " (" + oldAttendanceStatus.getName() + ") -> " +
                getDisplayTime(newAttendanceStatus, newTime) + " (" + newAttendanceStatus.getName() + ") 수정 완료!");
    }

    public void printCrewRecord(List<AttendanceRecord> attendanceRecords, Crew crew) {
        System.out.printf("%n이번 달 %s의 출석 기록입니다.%n", crew.name());
        attendanceRecords.forEach(this::printAttendanceRecord);
    }

    public void printAttendanceStatusSummary(Map<AttendanceStatus, Integer> summary) {
        System.out.println(System.lineSeparator());
        for (AttendanceStatus status : AttendanceStatus.values()) {
            System.out.printf("%s: %d회%n", status.getName(), summary.get(status));
        }
    }

    public void printWarningStatus(WarningStatus warningStatus) {
        if (warningStatus == WarningStatus.NONE) {
            return;
        }
        System.out.printf("%s 대상자입니다.", warningStatus.getName());
    }

    public void printWarnedCrews(List<WarnedCrew> warnedCrews) {
        System.out.println("제적 위험자 조회 결과");
        for (WarnedCrew warnedCrew : warnedCrews) {
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)%n", warnedCrew.name(), warnedCrew.tardyCount(),
                    warnedCrew.absentCount(), warnedCrew.warningStatus());
        }
    }

    private String getDisplayTime(AttendanceStatus attendanceStatus, LocalTime time) {
        if (attendanceStatus == AttendanceStatus.ABSENT) {
            return "--:--";
        }
        return time.toString();
    }
}
