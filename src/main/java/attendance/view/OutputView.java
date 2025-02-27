package attendance.view;

import static attendance.domain.AttendanceStatus.ABSENCE;

import attendance.domain.AttendanceStatus;
import attendance.domain.Warning;
import attendance.dto.AttendanceResultResponse;
import attendance.dto.AttendancesResponse;
import attendance.dto.WarningCrewsResponse;
import attendance.dto.WarningCrewsResponse.WarningCrew;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class OutputView {
    public void printAttendResult(final AttendanceResultResponse response) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일 EEEE HH:mm");
        LocalDateTime dateTime = response.dateTime();
        AttendanceStatus status = response.status();
        if (ABSENCE.equals(status)) {
            printAbsenceResult(response);
            System.out.println();
            return;
        }
        System.out.printf("%s (%s)\n", dateTime.format(formatter), status.getMessage());
    }

    public void printUpdateResult(AttendanceResultResponse beforeResponse,
                                  AttendanceResultResponse afterResponse) {
        DateTimeFormatter afterFormatter = DateTimeFormatter.ofPattern("HH:mm");

        printAbsenceResult(beforeResponse);
        System.out.printf(" -> %s (%s)수정 완료!\n",
                afterResponse.dateTime().format(afterFormatter),
                afterResponse.status().getMessage()
        );
    }

    public void printAbsenceResult(final AttendanceResultResponse response) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일 EEEE --:--");
        LocalDateTime dateTime = response.dateTime();
        AttendanceStatus status = response.status();
        System.out.printf("%s (%s)", dateTime.format(formatter), status.getMessage());
    }

    public void printAttendancesByCrew(AttendancesResponse response) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n", response.nickname());
        response.attendances()
                .forEach(this::printAttendResult);
        System.out.printf("%s: %s회\n", AttendanceStatus.ATTEND.getMessage(), response.attendCount());
        System.out.printf("%s: %s회\n", AttendanceStatus.LATE.getMessage(), response.lateCount());
        System.out.printf("%s: %s회\n", AttendanceStatus.ABSENCE.getMessage(), response.absenceCount());
    }

    public void printWarning(Warning warning) {
        System.out.printf("%s 대상자입니다.\n", warning.getMessage());
    }

    public void printWarningCrews(WarningCrewsResponse response) {
        List<WarningCrew> warningCrews = response.warningCrews()
                .stream()
                .sorted(Comparator.comparing(WarningCrew::absenceCount)
                        .thenComparing(WarningCrew::lateCount)
                        .thenComparing(WarningCrew::nickname))
                .toList();
        for (WarningCrew warningCrew : warningCrews) {
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n",
                    warningCrew.nickname(),
                    warningCrew.absenceCount(),
                    warningCrew.lateCount(),
                    warningCrew.warning().getMessage()
            );
        }
    }

    public void printErrorMessage(String massage) {
        System.out.println(massage);
    }
}
