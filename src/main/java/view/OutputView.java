package view;

import domain.AttendanceStatus;
import domain.AttendanceTime;
import domain.ExpelStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import util.Convertor;

public class OutputView {

    public void printErrorMessage(String message) {
        System.out.println(message);
    }

    public void printMenuHeader(LocalDate nowDate) {
        System.out.printf("%n오늘은 %d월 %d일 %s요일입니다. 기능을 선택해 주세요.", nowDate.getMonthValue(), nowDate.getDayOfMonth(),
                Convertor.convertDayOfWeekToKorean(nowDate.getDayOfWeek()));
    }

    public void printCheckAttendanceMessage(LocalDateTime attendanceDateTime, AttendanceStatus attendanceStatus) {
        System.out.print(writeAttendanceDateMessage(attendanceDateTime) + writeAttendanceTimeMessage(attendanceDateTime,
                attendanceStatus));
    }

    public void printEditAttendanceMessage(AttendanceTime oldAttendanceTime, AttendanceTime newAttendanceTime) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(writeAttendanceDateMessage(oldAttendanceTime.getAttendanceDateTime()))
                .append(writeAttendanceTimeMessage(oldAttendanceTime.getAttendanceDateTime(),
                        oldAttendanceTime.getAttendanceStatus()))
                .append(" ->")
                .append(writeAttendanceTimeMessage(newAttendanceTime.getAttendanceDateTime(),
                        newAttendanceTime.getAttendanceStatus()))
                .append(" 수정 완료!");
        System.out.println(stringBuilder);
    }

    public void printCrewAttendanceHeader(String nickName) {
        System.out.printf("이번 달 %s의 출석 기록입니다.%n", nickName);
    }

    public void printCrewStatuses(Map<AttendanceStatus, Integer> attendStatuses) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(System.lineSeparator()).append(System.lineSeparator())
                .append(writeCrewStatus(AttendanceStatus.ATTEND, attendStatuses.get(AttendanceStatus.ATTEND)))
                .append(writeCrewStatus(AttendanceStatus.LATE, attendStatuses.get(AttendanceStatus.LATE)))
                .append(writeCrewStatus(AttendanceStatus.ABSENT,
                        attendStatuses.get(AttendanceStatus.ABSENT) + attendStatuses.get(AttendanceStatus.UNATTEND)))
                .append(System.lineSeparator())
                .append(writeExpelStatus(attendStatuses))
                .append(System.lineSeparator());
        System.out.println(stringBuilder);
    }

    public void printExpelledCrewHeader() {
        System.out.println("\n제적 위험자 조회 결과");
    }

    public void printExpelledCrew(String crewName, Map<AttendanceStatus, Integer> attendStatuses) {
        System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)%n", crewName,
                attendStatuses.get(AttendanceStatus.ABSENT) + attendStatuses.get(AttendanceStatus.UNATTEND),
                attendStatuses.get(AttendanceStatus.LATE),
                ExpelStatus.determineExpelStatus(attendStatuses).getExpelStatus());
    }

    private String writeCrewStatus(AttendanceStatus attendanceStatus, int attendanceStatusCount) {
        return String.format("%s: %d회%n", attendanceStatus.getStatus(), attendanceStatusCount);
    }

    private String writeExpelStatus(Map<AttendanceStatus, Integer> attendStatuses) {
        return String.format("%s 대상자입니다.", ExpelStatus.determineExpelStatus(attendStatuses).getExpelStatus());
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
