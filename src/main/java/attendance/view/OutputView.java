package attendance.view;

import attendance.dto.AttendanceCheckDto;
import attendance.dto.AttendanceExpelDto;
import attendance.dto.AttendanceExpelRecord;
import attendance.dto.AttendanceLookupDto;
import attendance.dto.AttendanceLookupRecord;
import attendance.dto.AttendanceModifyDto;

public class OutputView {
    private static final String ATTENDANCE_STRING = "%d월 %02d일 %s요일 %s (%s)";
    private static final String MODIFIED_ATTENDANCE_STRING = " -> %s (%s) 수정 완료!";
    private static final String CREW_ATTENDANCE_HISTORY_STRING = "이번 달 %s의 출석 기록입니다.";
    private static final String CREW_STATISTICS_STRING = """
            출석: %d회
            지각: %d회
            결석: %d회
            """;
    private static final String CREW_STATUS_STRING = "%s 대상자입니다.";
    private static final String EXPEL_CREWS_HEAD_STRING = "제적 위험자 조회 결과";
    private static final String EXPEL_CREW_BODY_STRING = "- %s: 결석 %d회, 지각 %d회 (%s)";

    private static final String ABSENT_TIME_VALUE = "00:00";
    private static final String ABSENT_TIME_FORMAT = "--:--";

    public void printTodayAttendance(final AttendanceCheckDto attendanceInfo) {
        System.out.println(
                ATTENDANCE_STRING.formatted(
                        attendanceInfo.month(),
                        attendanceInfo.day(),
                        attendanceInfo.dayOfWeek(),
                        attendanceInfo.attendanceTime(),
                        attendanceInfo.attendanceType()
                )
        );
        printNewLine();
    }

    public void printModifiedAttendance(final AttendanceModifyDto attendanceModifiedInfo) {
        System.out.print(
                ATTENDANCE_STRING.formatted(
                        attendanceModifiedInfo.month(),
                        attendanceModifiedInfo.day(),
                        attendanceModifiedInfo.dayOfWeek(),
                        attendanceModifiedInfo.originalTime(),
                        attendanceModifiedInfo.originalType()
                )
        );
        System.out.println(formatModifiedNotice(attendanceModifiedInfo));
        printNewLine();
    }

    private String formatModifiedNotice(final AttendanceModifyDto attendanceModifiedInfo) {
        return MODIFIED_ATTENDANCE_STRING.formatted(
                attendanceModifiedInfo.newAttendanceTime(),
                attendanceModifiedInfo.newAttendanceType()
        );
    }

    public void printCrewAttendanceHistory(final AttendanceLookupDto attendanceLookupDto) {
        System.out.println(
                CREW_ATTENDANCE_HISTORY_STRING.formatted(attendanceLookupDto.crewName())
        );
        for (AttendanceLookupRecord attendanceLookupRecord : attendanceLookupDto.attendanceLookupRecords()) {
            System.out.println(
                    ATTENDANCE_STRING.formatted(
                            attendanceLookupRecord.month(),
                            attendanceLookupRecord.day(),
                            attendanceLookupRecord.dayOfWeek(),
                            formatAbsentTimeString(attendanceLookupRecord.attendanceTime()),
                            attendanceLookupRecord.attendanceType()
                    )
            );
        }
        printNewLine();
    }

    private String formatAbsentTimeString(String absentTime) {
        if (absentTime.equals(ABSENT_TIME_VALUE)) {
            absentTime = ABSENT_TIME_FORMAT;
        }
        return absentTime;
    }

    public void printCrewStatisticStatus(final AttendanceLookupDto attendanceLookupDto) {
        System.out.println(
                CREW_STATISTICS_STRING.formatted(
                        attendanceLookupDto.safeCount(),
                        attendanceLookupDto.lateCount(),
                        attendanceLookupDto.absentCount()
                )
        );
        System.out.println(
                CREW_STATUS_STRING.formatted(attendanceLookupDto.crewPenaltyStatus())
        );
        printNewLine();
    }

    public void printExpelExpectedCrews(final AttendanceExpelDto attendanceExpelDto) {
        System.out.println(EXPEL_CREWS_HEAD_STRING);
        for (AttendanceExpelRecord attendanceExpelRecord : attendanceExpelDto.attendanceExpelRecords()) {
            System.out.println(
                    EXPEL_CREW_BODY_STRING.formatted(
                            attendanceExpelRecord.crewName(),
                            attendanceExpelRecord.absentCount(),
                            attendanceExpelRecord.lateCount(),
                            attendanceExpelRecord.expectedPenalty()
                    )
            );
        }
        printNewLine();
    }

    public void printNewLine() {
        System.out.println();
    }
}
