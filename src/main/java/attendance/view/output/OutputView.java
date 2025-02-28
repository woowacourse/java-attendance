package attendance.view.output;

import attendance.controller.dto.AttendanceResponse;
import attendance.controller.dto.CrewAttendanceResponse;
import attendance.controller.dto.WarningCrewResponse;
import attendance.view.KoreaDayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class OutputView {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private static final Comparator<WarningCrewResponse> warningCrewResponseComparator = new Comparator<WarningCrewResponse>() {
        @Override
        public int compare(WarningCrewResponse o1, WarningCrewResponse o2) {
            if (o2.getPolicyAppliedAbsenceCount() == o1.getPolicyAppliedAbsenceCount()) {
                if (o2.getPolicyAppliedLateCount() == o1.getPolicyAppliedLateCount()) {
                    return o1.getCrewNickName().compareTo(o2.getCrewNickName());
                }
                return o2.getPolicyAppliedLateCount() - o1.getPolicyAppliedLateCount();
            }
            return o2.getPolicyAppliedAbsenceCount() - o1.getPolicyAppliedAbsenceCount();
        }
    };

    public void printCrewAttendance(final CrewAttendanceResponse crewAttendanceResponse) {
        System.out.printf("이번 달 %s의 출석 기록입니다.\n", crewAttendanceResponse.crewNickName());
        crewAttendanceResponse.attendanceResponses()
                .forEach(this::printAttendanceResponse);
        System.out.println();
        crewAttendanceResponse.attendanceStatusStatistics().forEach((status, count) -> {
            System.out.printf("%s: %d회\n", status, count);
        });
        System.out.println();
        System.out.println(crewAttendanceResponse.crewStatus() + " 대상자입니다.");
    }

    public void printWarningCrewResponses(final List<WarningCrewResponse> warningCrewResponses) {
        System.out.println("제적 위험자 조회 결과");
        warningCrewResponses.stream()
                .sorted(warningCrewResponseComparator)
                .forEach(this::printWarningCrewResponse);
    }

    private void printAttendanceResponse(final AttendanceResponse attendanceResponse) {
        System.out.printf("%s %s (%s)\n",
                formatDateWithDayOfWeek(attendanceResponse.getDate()),
                attendanceResponse.getTime().map(time -> time.format(dateTimeFormatter)).orElse("--:--"),
                attendanceResponse.getAttendanceStatus()
        );
    }

    private String formatDateWithDayOfWeek(final LocalDate date) {
        return String.format("%02d월 %02d일 %s", date.getMonthValue(), date.getDayOfMonth(),
                KoreaDayOfWeek.fromDayOfWeek(date.getDayOfWeek()).getName());
    }

    private void printWarningCrewResponse(final WarningCrewResponse warningCrewResponse) {
        System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)\n",
                warningCrewResponse.getCrewNickName(),
                warningCrewResponse.getAbsenceCount(),
                warningCrewResponse.getLateCount(),
                warningCrewResponse.getCrewStatus()
        );
    }
}
