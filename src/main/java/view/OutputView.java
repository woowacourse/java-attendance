package view;

import domain.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TreeSet;

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

    public void printCrewRecord(LocalDate currentDate, CrewRecords crewRecords, Crew crew) {
        AttendanceRecords attendanceRecords = crewRecords.getAttendanceRecordsOf(crew);
        System.out.printf("%n이번 달 %s의 출석 기록입니다.%n", crew.name());
        TreeSet<AttendanceRecord> records = attendanceRecords.getRecords();
        records.stream()
                .filter(record -> record.getDate().isBefore(currentDate))
                .forEach(this::printAttendanceRecord);
        printAttendanceStatus(attendanceRecords);
        printWarningStatus(crewRecords, crew);
    }

    public void printWarnedCrews(CrewRecords crewRecords) {
        System.out.println("제적 위험자 조회 결과");
        List<Crew> warnedCrews = crewRecords.getWarnedCrews();
        for (Crew warnedCrew : warnedCrews) {
            int tardyCount = crewRecords.getTardyCount(warnedCrew);
            int absentCount = crewRecords.getAbsentCount(warnedCrew);
            String warningStatus = crewRecords.getWarningStatus(warnedCrew).getName();
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)%n", warnedCrew.name(), absentCount, tardyCount, warningStatus);
        }
    }

    private void printAttendanceStatus(AttendanceRecords attendanceRecords) {
        System.out.println(System.lineSeparator());
        for (AttendanceStatus status : AttendanceStatus.values()) {
            int count = attendanceRecords.getAttendanceCount(status);
            System.out.printf("%s: %d회%n", status.getName(), count);
        }
    }

    private void printWarningStatus(CrewRecords crewRecords, Crew crew) {
        WarningStatus warningStatus = crewRecords.getWarningStatus(crew);
        if (warningStatus == WarningStatus.NONE) {
            return;
        }
        System.out.println(System.lineSeparator());
        System.out.printf("%s 대상자입니다.", warningStatus.getName());
    }

    private String getDisplayTime(AttendanceStatus attendanceStatus, LocalTime time) {
        if (attendanceStatus == AttendanceStatus.ABSENT) {
            return "--:--";
        }
        return time.toString();
    }
}
