package attendance.view;

import attendance.domain.AttendancePenalty;
import attendance.domain.AttendanceStatus;
import static attendance.domain.AttendanceStatus.ABSENCE;
import attendance.dto.AttendanceResultResponse;
import attendance.dto.AttendancesResponse;
import attendance.dto.PenaltyCrewsResponse;
import attendance.dto.PenaltyCrewsResponse.PenaltyCrewResponse;
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

    public void printUpdateResult(final AttendanceResultResponse beforeResponse,
                                  final AttendanceResultResponse afterResponse) {
        DateTimeFormatter afterFormatter = DateTimeFormatter.ofPattern("HH:mm");

        printAttendResult(beforeResponse);
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

    public void printAttendancesByCrew(final AttendancesResponse response) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n", response.nickname());
        response.attendances()
                .forEach(this::printAttendResult);
        System.out.printf("%s: %s회\n", AttendanceStatus.ATTEND.getMessage(), response.attendCount());
        System.out.printf("%s: %s회\n", AttendanceStatus.LATE.getMessage(), response.lateCount());
        System.out.printf("%s: %s회\n", AttendanceStatus.ABSENCE.getMessage(), response.absenceCount());
    }

    public void printPenalty(final AttendancePenalty warning) {
        System.out.printf("%s 대상자입니다.\n", warning.getMessage());
    }

    public void printPenaltyCrews(final PenaltyCrewsResponse response) {
        List<PenaltyCrewResponse> penaltyCrewResponses = response.penaltyCrewResponses()
                .stream()
                .sorted(Comparator.comparing(PenaltyCrewResponse::absenceCount)
                        .thenComparing(PenaltyCrewResponse::lateCount)
                        .thenComparing(PenaltyCrewResponse::nickname))
                .toList();
        for (PenaltyCrewResponse penaltyCrewResponse : penaltyCrewResponses) {
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n",
                    penaltyCrewResponse.nickname(),
                    penaltyCrewResponse.absenceCount(),
                    penaltyCrewResponse.lateCount(),
                    penaltyCrewResponse.risk().getMessage()
            );
        }
    }

    public void printErrorMessage(final String massage) {
        System.out.println(massage);
    }
}
