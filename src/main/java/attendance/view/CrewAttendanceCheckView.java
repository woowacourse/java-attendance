package attendance.view;

import attendance.domain.AttendanceDateTime;
import attendance.domain.Crew;
import attendance.domain.ExpulsionStatus;

import java.time.LocalDateTime;
import java.util.*;

import static attendance.domain.AttendanceStatusChecker.*;
import static attendance.view.GeneralView.*;

public class CrewAttendanceCheckView {

    public String readCrewNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return readOneLine();
    }

    public void printCrewAttendances(final Crew crew, final List<AttendanceDateTime> crewAttendanceDateTimes) {
        AttendanceStatusTextMaker attendanceStatusTextMaker = new AttendanceStatusTextMaker();
        System.out.println("이번 달 %s의 출석 기록입니다.\n".formatted(crew.getNickname()));
        for (AttendanceDateTime crewAttendanceDateTime : crewAttendanceDateTimes) {
            LocalDateTime dateTime = crewAttendanceDateTime.getLocalDateTime();
            if (dateTime.toLocalTime().equals(AttendanceDateTime.ABSENT_TIME)) {
                System.out.println(DATE_FORMATTER.format(dateTime) + " --:-- (결석)");
                continue;
            }
            AttendanceStatus attendanceStatus = checkStatus(crewAttendanceDateTime);
            System.out.println(DATE_TIME_FORMATTER.format(dateTime)
                    + " (%s)".formatted(attendanceStatusTextMaker.make(attendanceStatus)));
        }
        System.out.println();
    }

    public void printAttendanceStatuses(final Map<AttendanceStatus, Long> attendanceStatuses) {
        AttendanceStatusTextMaker attendanceStatusTextMaker = new AttendanceStatusTextMaker();
        for (AttendanceStatus attendanceStatus : AttendanceStatus.values()) {
            String attendanceStatusText = attendanceStatusTextMaker.make(attendanceStatus);
            Long statusCount = attendanceStatuses.get(attendanceStatus);
            System.out.println("%s: %d회".formatted(attendanceStatusText, statusCount));
        }
        System.out.println();
    }

    public void printExpulsionStatus(final ExpulsionStatus expulsionStatus) {
        ExpulsionStatusTextMaker expulsionStatusTextMaker = new ExpulsionStatusTextMaker();
        if (expulsionStatus.equals(ExpulsionStatus.NONE)) {
            return;
        }
        System.out.println("%s 대상자입니다.".formatted(expulsionStatusTextMaker.make(expulsionStatus)));
    }
}
