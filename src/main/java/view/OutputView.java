package view;

import domain.AttendanceStatus;
import domain.ExpelStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import util.Convertor;

public class OutputView {

    public void printMenuHeader(LocalDate nowDate) {
        System.out.print(String.format("오늘은 %d월 %d일 %s요일입니다. 기능을 선택해 주세요.", nowDate.getMonthValue(), nowDate.getDayOfMonth(),
                Convertor.convertDayOfWeekToKorean(nowDate.getDayOfWeek())));
    }

    public void printCheckAttendanceMessage(LocalDateTime attendanceDateTime, AttendanceStatus attendanceStatus) {

        System.out.print(writeAttendanceDateMessage(attendanceDateTime)
                        + writeAttendanceTimeMessage(attendanceDateTime, attendanceStatus));
    }

    public void printEditAttendanceMessage(LocalDateTime oldAttendanceDateTime, AttendanceStatus oldAttendanceStatus, LocalDateTime newAttendanceDateTime, AttendanceStatus newAttendanceStatus) {
        StringBuilder sb = new StringBuilder();
        sb.append(writeAttendanceDateMessage(oldAttendanceDateTime))
                .append(writeAttendanceTimeMessage(oldAttendanceDateTime, oldAttendanceStatus))
                .append(" ->")
                .append(writeAttendanceTimeMessage(newAttendanceDateTime, newAttendanceStatus))
                .append(" 수정 완료!");
        System.out.println(sb);
    }

    public void printCrewAttendanceHeader(String nickName) {
        System.out.println(String.format("이번 달 %s의 출석 기록입니다.", nickName));
    }

    public void printCrewStatuses(Map<AttendanceStatus, Integer> attendStatuses) {
        StringBuilder sb = new StringBuilder();
        sb.append(System.lineSeparator()).append(System.lineSeparator())
                .append(writeCrewStatus(AttendanceStatus.ATTEND, attendStatuses.get(AttendanceStatus.ATTEND)))
                .append(writeCrewStatus(AttendanceStatus.LATE, attendStatuses.get(AttendanceStatus.LATE)))
                .append(writeCrewStatus(AttendanceStatus.ABSENT, attendStatuses.get(AttendanceStatus.ABSENT) + attendStatuses.get(AttendanceStatus.UNATTEND)))
                .append(System.lineSeparator())
                .append(writeExpelStatus(attendStatuses))
                .append(System.lineSeparator());
        System.out.println(sb);
    }

    private String writeCrewStatus(AttendanceStatus attendanceStatus, int attendanceStatusCount) {
        return String.format("%s: %d회%n", attendanceStatus.getStatus(), attendanceStatusCount);
    }

    private String writeExpelStatus(Map<AttendanceStatus, Integer> attendStatuses) {
        return String.format("%s입니다.", ExpelStatus.determineExpelStatus(attendStatuses).getExpelStatus());
    }

    private String writeAttendanceDateMessage(LocalDateTime attendanceDateTime) {
        return String.format("%n%d월 %02d일 %s요일",
                attendanceDateTime.getMonthValue(),
                attendanceDateTime.getDayOfMonth(),
                Convertor.convertDayOfWeekToKorean(attendanceDateTime.getDayOfWeek()));
    }

    private String writeAttendanceTimeMessage(LocalDateTime attendanceDateTime, AttendanceStatus attendanceStatus) {
        if (attendanceStatus.equals(AttendanceStatus.UNATTEND)) {
            return String.format(" --:-- (%s)",
                    attendanceStatus.getStatus());
        }
        return String.format(" %02d:%02d (%s)",
                attendanceDateTime.getHour(),
                attendanceDateTime.getMinute(),
                attendanceStatus.getStatus());
    }
}
