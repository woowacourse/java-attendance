package attendance.view;

import attendance.domain.AttendanceDateTime;
import attendance.domain.Crew;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import static attendance.domain.AttendanceStatusChecker.*;

public class CrewAttendanceCheckView {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm", Locale.KOREA);
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 E요일", Locale.KOREA);

    public String readCrewNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return readOneLine();
    }

    public void printCrewAttendances(Crew crew, List<AttendanceDateTime> crewAttendanceDateTimes) {
        System.out.println("이번 달 %s의 출석 기록입니다.".formatted(crew));
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
    }

    private String readOneLine() {
        final Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
