package attendance.view.output;

import attendance.dto.AttendanceLogResponse;
import attendance.dto.CrewAttendanceLogResponse;
import attendance.dto.RequiresManagementCrewResponse;
import attendance.dto.UpdateAttendanceResponse;
import attendance.view.input.KoreaDayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class ConsoleOutputView implements OutputView {


    @Override
    public void printAttendanceLog(AttendanceLogResponse response) {
        String message = String.format("%s %s %s (%s)",
                formatDate(response.getDate()),
                KoreaDayOfWeek.from(response.getDate().getDayOfWeek()).getName(),
                formatTime(response.getTime()),
                response.getAttendanceStatusResponse().getAttendanceStatus());
        System.out.println(message);
    }

    @Override
    public void printUpdateAttendanceResponse(UpdateAttendanceResponse updateAttendanceResponse) {
        String message = String.format("%s %s %s (%s) -> %s (%s) 수정 완료!",
                formatDate(updateAttendanceResponse.getBefore().toLocalDate()),
                KoreaDayOfWeek.from(updateAttendanceResponse.getBefore().getDayOfWeek()).getName(),
                formatTime(updateAttendanceResponse.getBefore().toLocalTime()),
                updateAttendanceResponse.getBeforeStatus(),
                formatTime(updateAttendanceResponse.getAfter().toLocalTime()),
                updateAttendanceResponse.getAfterStatus());
        System.out.println(message);
    }

    @Override
    public void printCrewAttendanceLogResponse(CrewAttendanceLogResponse crewAttendanceLogResponse) {
        System.out.printf("이번 달 %s의 출석 기록입니다.%n%n", crewAttendanceLogResponse.getCrewName());

        crewAttendanceLogResponse.getTimeLogs().forEach(this::printAttendanceLog);

        Map<String, Integer> attendanceStatusStatistics = crewAttendanceLogResponse.getAttendanceStatusStatistics();
        System.out.println();
        attendanceStatusStatistics.forEach((status, count) -> System.out.printf("%s: %d회%n", status, count));

        System.out.printf("%n%s 대상자입니다.%n", crewAttendanceLogResponse.getManagementStatus());
    }

    @Override
    public void printRequiresManagementCrewResponse(
            List<RequiresManagementCrewResponse> requiresManagementCrewResponses) {
        System.out.println("제적 위험자 조회 결과");
        requiresManagementCrewResponses.forEach(response -> {
            System.out.printf("- %s: 결석 %d회, 지각 %d회 (%s)%n",
                    response.getCrewName(),
                    response.getAbsenceCount(),
                    response.getLateCount(),
                    response.getManagementStatus());
        });
    }

    private String formatTime(LocalTime time) {
        if (time == null) {
            return "--:--";
        }
        return String.format("%02d:%02d", time.getHour(), time.getMinute());
    }

    private String formatDate(LocalDate date) {
        return String.format("%02d월 %02d일", date.getMonthValue(), date.getDayOfMonth());
    }
}
