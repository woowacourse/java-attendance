package view;

import dto.AttendanceRecordResponse;
import dto.AttendanceStatusCountResponse;
import dto.CheckAttendanceResponse;
import dto.CrewWithPenaltyResponse;
import dto.ModifyAttendanceResponse;
import java.util.List;

public class OutputView {

    private static final String ATTENDANCE_STATUS_FORMAT = "(%s)";

    public void printCheckAttendanceResult(CheckAttendanceResponse response) {
        System.out.printf("%s %s " + ATTENDANCE_STATUS_FORMAT + System.lineSeparator(), response.date(),
                response.time(), response.status());
    }

    public void printModifyAttendanceResult(ModifyAttendanceResponse response) {
        System.out.printf("%s %s " + ATTENDANCE_STATUS_FORMAT + " -> %s (%s) 수정 완료!" + System.lineSeparator(),
                response.date(),
                response.originalTime(),
                response.originalStatus(), response.modifiedTime(), response.modifiedStatus());
    }

    public void printGetAttendanceRecordsResult(String name, List<AttendanceRecordResponse> attendanceRecordResponses,
                                                AttendanceStatusCountResponse attendanceStatusCountResponse,
                                                String penaltyStatus
    ) {
        System.out.printf("이번 달 %s의 출석 기록입니다.", name);
        printAttendanceRecordResponse(attendanceRecordResponses);
        printAttendanceStatusCountResponse(attendanceStatusCountResponse);
        printPenaltyStatus(penaltyStatus);
    }

    private void printAttendanceRecordResponse(List<AttendanceRecordResponse> responses) {
        responses.forEach(response ->
                System.out.printf("%s %s " + ATTENDANCE_STATUS_FORMAT + System.lineSeparator(), response.date(),
                        response.time(),
                        response.status()));
    }

    private void printAttendanceStatusCountResponse(AttendanceStatusCountResponse response) {
        System.out.printf("""
                출석: %d회
                지각: %d회
                결석: %d회
                """ + System.lineSeparator(), response.attendCount(), response.lateCount(), response.absentCount());
    }

    private void printPenaltyStatus(String penaltyStatus) {
        System.out.printf("%s 대상자입니다.", penaltyStatus);
    }

    public void printCrewWithPenaltyResponses(List<CrewWithPenaltyResponse> responses) {
        System.out.println("제적 위험자 조회 결과");
        responses.forEach(response ->
                System.out.printf("- %s: 결석 %d회, 지각 %d회 " + ATTENDANCE_STATUS_FORMAT + System.lineSeparator(),
                        response.name(), response.absentCount(), response.lateCount(), response.penalty()));
    }

    public void printErrorMessage(String message) {
        System.out.println(message);
    }
}
