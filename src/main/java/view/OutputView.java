package view;

import controller.dto.AttendanceRecordsDto;
import controller.dto.PenaltyRecordsDto;
import domain.Penalty;
import domain.WorkDate;
import domain.WorkDateTime;
import domain.WorkDay;
import domain.WorkTime;
import java.util.List;
import java.util.Objects;

public class OutputView {
    
    public void printArriveResult(WorkDateTime workDateTime, String attendanceStatusName) {
        WorkDate workDate = workDateTime.getDate();
        WorkDay workDay = workDate.getWorkDay();
        WorkTime workTime = workDateTime.getTime();

        System.out.printf("%s월 %s일 %s요일 %s:%s (%s)\n", workDate.getMonth(),
                workDate.getDay(), workDay.getDayOfWeekKorean(),
                convertTime(workTime.getHour().orElse(null)), convertTime(workTime.getMinute().orElse(null)),
                attendanceStatusName);
    }

    public void printUpdateResult(WorkDateTime beforeWorkDateTime, String beforeAttendanceStatusName,
                                  WorkDateTime afterWorkDateTime, String afterAttendanceStatusName) {
        WorkDate workDate = beforeWorkDateTime.getDate();
        WorkDay workDay = workDate.getWorkDay();
        WorkTime beforeWorkTime = beforeWorkDateTime.getTime();
        WorkTime afterWorkTime = afterWorkDateTime.getTime();

        System.out.printf("%s월 %s일 %s요일 %s:%s (%s) -> %s:%s (%s) 수정 완료!\n",
                workDate.getMonth(), workDate.getDay(), workDay.getDayOfWeekKorean(),
                convertTime(beforeWorkTime.getHour().orElse(null)),
                convertTime(beforeWorkTime.getMinute().orElse(null)), beforeAttendanceStatusName,
                convertTime(afterWorkTime.getHour().orElse(null)),
                convertTime(afterWorkTime.getMinute().orElse(null)), afterAttendanceStatusName);
    }

    public void printTotalAttendanceStatus(AttendanceRecordsDto attendanceRecordsDto) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n", attendanceRecordsDto.name());
        printAttendanceRecords(attendanceRecordsDto.attendanceRecords());
        printAttendanceSummary(attendanceRecordsDto);
        printPenaltyInfo(attendanceRecordsDto.penaltyName());
    }

    private void printAttendanceRecords(List<AttendanceRecordsDto.AttendanceRecordDto> attendanceRecords) {
        attendanceRecords.forEach(record -> {
            WorkDate workDate = record.workDateTime().getDate();
            WorkDay workDay = workDate.getWorkDay();
            WorkTime workTime = record.workDateTime().getTime();

            System.out.printf("%s월 %s일 %s요일 %s:%s (%s)\n",
                    workDate.getMonth(),
                    workDate.getDay(),
                    workDay.getDayOfWeekKorean(),
                    convertTime(workTime.getHour().orElse(null)),
                    convertTime(workTime.getMinute().orElse(null)),
                    record.attendanceStatusName());
        });
        System.out.println();
    }

    private void printAttendanceSummary(AttendanceRecordsDto attendanceRecordsDto) {
        System.out.printf("출석: %d회\n", attendanceRecordsDto.attendanceCount());
        System.out.printf("지각: %d회\n", attendanceRecordsDto.perceptionCount());
        System.out.printf("결석: %d회\n", attendanceRecordsDto.absenceCount());
    }

    private void printPenaltyInfo(String penaltyName) {
        if (!Objects.equals(penaltyName, Penalty.NONE.getName())) {
            System.out.printf("%s 대상자입니다.\n", penaltyName);
        }
    }

    private String convertTime(Integer time) {
        if (time == null) {
            return "--";
        }

        String before = String.valueOf(time);
        if (before.length() < 2) {
            return "0" + before;
        }

        return before;
    }

    public void printPenaltyCrews(PenaltyRecordsDto penaltyRecordsDto) {
        System.out.println("제적 위험자 조회 결과");
        penaltyRecordsDto.penaltyRecords().forEach(penaltyCrewDto -> {
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n",
                    penaltyCrewDto.name(), penaltyCrewDto.absenceCount(),
                    penaltyCrewDto.perceptionCount(), penaltyCrewDto.penaltyName());
        });
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}
