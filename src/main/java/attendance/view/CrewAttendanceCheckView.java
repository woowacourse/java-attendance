package attendance.view;

import attendance.domain.AttendanceDateTime;
import attendance.domain.Crew;
import attendance.domain.ExpulsionStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static attendance.domain.AttendanceStatusChecker.*;

public class CrewAttendanceCheckView {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm", Locale.KOREA);
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 E요일", Locale.KOREA);

    public String readCrewNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return readOneLine();
    }

    public void printCrewAttendances(Crew crew, List<AttendanceDateTime> crewAttendanceDateTimes) {
        System.out.println("이번 달 %s의 출석 기록입니다.".formatted(crew.getNickname()));
        System.out.println();
        for (AttendanceDateTime crewAttendanceDateTime : crewAttendanceDateTimes) {
            LocalDateTime dateTime = crewAttendanceDateTime.getLocalDateTime();
            if (dateTime.toLocalTime().equals(AttendanceDateTime.ABSENT_TIME)) {
                System.out.println(DATE_FORMATTER.format(dateTime) + " --:-- (결석)");
                continue;
            }
            AttendanceStatusTextMaker attendanceStatusTextMaker = new AttendanceStatusTextMaker();
            AttendanceStatus attendanceStatus = checkStatus(crewAttendanceDateTime);
            System.out.println(DATE_TIME_FORMATTER.format(dateTime)
                    + " (%s)".formatted(attendanceStatusTextMaker.make(attendanceStatus)));
        }
        System.out.println();
    }

    public void printAttendanceStatuses(Map<AttendanceStatus, Long> attendanceStatuses) {
        AttendanceStatusTextMaker attendanceStatusTextMaker = new AttendanceStatusTextMaker();
        Arrays.stream(AttendanceStatus.values())
                .forEach(attendanceStatus -> {
                    String attendanceStatusText = attendanceStatusTextMaker.make(attendanceStatus);
                    Long statusCount = attendanceStatuses.get(attendanceStatus);
                    if (statusCount == null) {
                        statusCount = 0L;
                    }
                    System.out.println("%s: %d회".formatted(attendanceStatusText, statusCount));
                });
        System.out.println();
    }

    public void printExpulsionStatus(ExpulsionStatus expulsionStatus) {
        ExpulsionStatusTextMaker expulsionStatusTextMaker = new ExpulsionStatusTextMaker();
        if (expulsionStatus.equals(ExpulsionStatus.NONE)) {
            return;
        }
        System.out.println("%s 대상자입니다.".formatted(expulsionStatusTextMaker.make(expulsionStatus)));
    }

    private String readOneLine() {
        final Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
