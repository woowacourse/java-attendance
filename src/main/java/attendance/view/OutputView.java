package attendance.view;

import static attendance.view.message.OutputMessage.ATTENDANCE_RESULT;
import static attendance.view.message.OutputMessage.CREW_ABSENCE_COUNT;
import static attendance.view.message.OutputMessage.CREW_ATTENDANCE;
import static attendance.view.message.OutputMessage.CREW_ATTENDANCE_TITLE;
import static attendance.view.message.OutputMessage.CREW_ATTEND_COUNT;
import static attendance.view.message.OutputMessage.CREW_LATE_COUNT;
import static attendance.view.message.OutputMessage.UPDATE_ATTENDANCE;
import static attendance.view.message.OutputMessage.WARNING_CREWS_ATTENDANCE_COUNT;
import static attendance.view.message.OutputMessage.WARNING_CREWS_TITLE;
import static attendance.view.message.OutputMessage.WARNING_TO_CREW;

import attendance.domain.Attendance;
import attendance.domain.AttendanceStatus;
import attendance.domain.Crew;
import attendance.domain.Warning;
import attendance.dto.AttendanceResultResponse;
import attendance.dto.UpdateAfterAttendanceResponse;
import attendance.dto.UpdateBeforeAttendanceResponse;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class OutputView {

    public void printAttendanceResult(AttendanceResultResponse response) {
        LocalDateTime dateTime = response.dateTime();

        System.out.printf(ATTENDANCE_RESULT, dateTime.getMonthValue(),
                dateTime.getDayOfMonth(),
                dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                dateTime.getHour(),
                dateTime.getMinute(),
                response.status());
    }

    public void printUpdateAttendance(UpdateBeforeAttendanceResponse beforeResponse, UpdateAfterAttendanceResponse afterResponse) {
        LocalDateTime beforeDateTime = beforeResponse.dateTime();
        LocalDateTime afterDateTime = afterResponse.dateTime();
        System.out.printf(UPDATE_ATTENDANCE,
                beforeDateTime.getMonthValue(),
                beforeDateTime.getDayOfMonth(),
                beforeDateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                beforeDateTime.getHour(),
                beforeDateTime.getMinute(),
                beforeResponse.status(),
                afterDateTime.getHour(),
                afterDateTime.getMinute(),
                afterResponse.status()
        );
    }

    public void printAttendanceByCrew(Crew crew) {
        System.out.printf(CREW_ATTENDANCE_TITLE, crew.getNickname());
        for (Attendance attendance : crew.getAttendances()) {
            LocalDateTime dateTime = attendance.getDateTime();
            AttendanceStatus status = attendance.getStatus();
            if (status == AttendanceStatus.ABSENCE) {
                System.out.printf(CREW_ATTENDANCE, dateTime.getMonthValue(),
                        dateTime.getDayOfMonth(),
                        dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA),
                        status.getMessage());
                continue;
            }
            printAttendanceResult(AttendanceResultResponse.from(attendance));
        }
        System.out.printf(CREW_ATTEND_COUNT, crew.countAttend());
        System.out.printf(CREW_LATE_COUNT, crew.countLate());
        System.out.printf(CREW_ABSENCE_COUNT, crew.countAbsence());
    }

    public void printWarning(Warning warning) {
        System.out.printf(WARNING_TO_CREW, warning.getMessage());
    }

    public void printWarningCrews(List<Crew> crews) {
        System.out.println(WARNING_CREWS_TITLE);
        for (Crew crew : crews) {
            System.out.printf(WARNING_CREWS_ATTENDANCE_COUNT,
                    crew.getNickname(),
                    crew.countAbsence(),
                    crew.countLate(),
                    crew.checkWarning().getMessage()
            );
        }
    }

    public void printExceptionMessage(Exception e) {
        System.out.println(e.getMessage());
    }
}
