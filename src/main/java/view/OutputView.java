package view;

import domain.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class OutputView {
    public void displayAttendanceRecord(AttendanceRecord attendanceRecord) {
        LocalDate date = attendanceRecord.getDate();
        Day day = Day.getDay(date);
        LocalTime time = attendanceRecord.getTime();
        Attendance attendance = attendanceRecord.getAttendance();
        System.out.printf("%d월 %02d일 %s %s (%s)",
                date.getMonthValue(),
                date.getDayOfMonth(),
                day.getName(),
                getDisplayTime(time, attendance),
                attendance.getName());
    }

    public void displayUpdatedRecord(AttendanceRecord oldRecord, AttendanceRecord newRecord) {
        LocalTime newTime = newRecord.getTime();
        Attendance newAttendance = newRecord.getAttendance();
        displayAttendanceRecord(oldRecord);
        System.out.printf(" -> %s (%s) 수정 완료!%n", newTime, newAttendance.getName());
    }

    public void displayAttendanceRecords(Crew crew, CrewAttendanceRecords crewAttendanceRecords) {
        System.out.printf("%n이번 달 %s의 출석 기록입니다.%n%n", crew.name());
        displaySortedRecords(crew, crewAttendanceRecords);
        displayAttendanceCount(crew, crewAttendanceRecords);
        displayDisciplinaryStatus(crew, crewAttendanceRecords);
    }

    public void displayWarnedCrews(List<Crew> warnedCrews, CrewAttendanceRecords crewAttendanceRecords) {
        System.out.println("\n제적 위험자 조회 결과");
        for (Crew warnedCrew : warnedCrews) {
            int absentCount = crewAttendanceRecords.getAbsentCount(warnedCrew);
            int tardyCount = crewAttendanceRecords.getTardyCount(warnedCrew);
            System.out.printf("- %s: %s %d회, %s %d회 (%s)%n", warnedCrew.name(), Attendance.ABSENT.getName(), absentCount,
                    Attendance.TARDY.getName(), tardyCount, DisciplinaryStatus.getStatus(absentCount, tardyCount).getName());
        }
    }

    private void displaySortedRecords(Crew crew, CrewAttendanceRecords crewAttendanceRecords) {
        List<AttendanceRecord> sortedRecords = crewAttendanceRecords.getSortedRecords(crew);
        sortedRecords.forEach((record) -> {
            displayAttendanceRecord(record);
            System.out.println();
        });
    }

    private void displayAttendanceCount(Crew crew, CrewAttendanceRecords crewAttendanceRecords) {
        System.out.println();
        Arrays.stream(Attendance.values())
                .forEach(attendance -> System.out.printf("%s: %d회%n",
                        attendance.getName(),
                        crewAttendanceRecords.getAttendanceCount(crew, attendance)));
        System.out.println();
    }

    private void displayDisciplinaryStatus(Crew crew, CrewAttendanceRecords crewAttendanceRecords) {
        int tardyCount = crewAttendanceRecords.getAttendanceCount(crew, Attendance.TARDY);
        int absentCount = crewAttendanceRecords.getAttendanceCount(crew, Attendance.ABSENT);
        DisciplinaryStatus status = DisciplinaryStatus.getStatus(absentCount, tardyCount);
        if (status == DisciplinaryStatus.NONE) {
            return;
        }
        System.out.printf("%s 대상자입니다.%n", status.getName());
    }

    private String getDisplayTime(LocalTime time, Attendance attendance) {
        if (attendance == Attendance.ABSENT) {
            return "--:--";
        }
        return time.toString();
    }
}
