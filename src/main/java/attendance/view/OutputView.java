package attendance.view;

import attendance.dto.AttendanceCheckDto;
import attendance.dto.AttendanceExpelDto;
import attendance.dto.AttendanceExpelRecord;
import attendance.dto.AttendanceLookupDto;
import attendance.dto.AttendanceLookupRecord;
import attendance.dto.AttendanceModifyDto;

public class OutputView {

    private static final String CREW_ATTENDANCE_FORMAT = "%d월 %02d일 %s요일 %s (%s)";
    private static final String CREW_MODIFIED_ATTENDANCE_FORMAT = "%d월 %02d일 %s요일 %s (%s) -> %s (%s) 수정 완료!";
    private static final String CREW_ATTENDANCE_TITLE = "이번 달 %s의 출석 기록입니다.";
    private static final String ABSENT_TIME_VALUE = "00:00";
    private static final String ABSENT_TIME_FORMAT = "--:--";
    private static final String CREW_STATISTIC_FORMAT = """
            출석: %d회
            지각: %d회
            결석: %d회
            """;
    private static final String CREW_STATISTIC_PENALTY_FORMAT = "%s 대상자입니다.";
    private static final String CREW_EXPULSION_FORMAT_HEAD = "제적 위험자 조회 결과";
    private static final String CREW_NO_PENALTY_STATUS = "비";
    private static final String CREW_EXPULSION_FORMAT_BODY = "- %s: 결석 %d회, 지각 %d회 (%s)";

    public void printAttendanceCheckResult(final AttendanceCheckDto attendanceCheckDto) {
        System.out.println();
        System.out.println(
                CREW_ATTENDANCE_FORMAT.formatted(
                        attendanceCheckDto.month(),
                        attendanceCheckDto.day(),
                        attendanceCheckDto.dayOfWeek(),
                        attendanceCheckDto.attendanceTime(),
                        attendanceCheckDto.attendanceType()
                )
        );
        System.out.println();
    }

    public void printAttendanceModifyResult(final AttendanceModifyDto attendanceModifyDto) {
        System.out.println();
        System.out.println(
                CREW_MODIFIED_ATTENDANCE_FORMAT.formatted(
                        attendanceModifyDto.month(),
                        attendanceModifyDto.day(),
                        attendanceModifyDto.dayOfWeek(),
                        attendanceModifyDto.attendanceTime(),
                        attendanceModifyDto.attendanceType(),
                        attendanceModifyDto.newAttendanceTime(),
                        attendanceModifyDto.newAttendanceType()
                )
        );
        System.out.println();
    }

    public void printAttendanceLookupResult(final AttendanceLookupDto attendanceLookupDto) {
        printCrewAttendanceLookupTitle(attendanceLookupDto);
        for (AttendanceLookupRecord attendanceLookupRecord : attendanceLookupDto.attendanceLookupRecords()) {
            System.out.println(
                    CREW_ATTENDANCE_FORMAT.formatted(
                            attendanceLookupRecord.month(),
                            attendanceLookupRecord.day(),
                            attendanceLookupRecord.dayOfWeek(),
                            formatCrewAttendanceTime(attendanceLookupRecord.attendanceTime()),
                            attendanceLookupRecord.attendanceType()
                    )
            );
        }
        printCrewAttendanceLookupStatistic(attendanceLookupDto);
    }

    private void printCrewAttendanceLookupTitle(final AttendanceLookupDto attendanceLookupDto) {
        System.out.println();
        System.out.println(CREW_ATTENDANCE_TITLE.formatted(attendanceLookupDto.crewName()));
        System.out.println();
    }

    private void printCrewAttendanceLookupStatistic(final AttendanceLookupDto attendanceLookupDto) {
        System.out.println();
        System.out.println(CREW_STATISTIC_FORMAT.formatted(
                attendanceLookupDto.crewSafeCount(),
                attendanceLookupDto.crewLateCount(),
                attendanceLookupDto.crewAbsentCount()
        ));
        System.out.println(CREW_STATISTIC_PENALTY_FORMAT.formatted(
                attendanceLookupDto.crewPenalty()
        ));
        System.out.println();
    }
    
    private String formatCrewAttendanceTime(final String attendanceTime) {
        if (attendanceTime.equals(ABSENT_TIME_VALUE)) {
            return ABSENT_TIME_FORMAT;
        }
        return attendanceTime;
    }

    public void printAttendanceExpelResult(final AttendanceExpelDto attendanceExpelDto) {
        System.out.println();
        System.out.println(CREW_EXPULSION_FORMAT_HEAD);
        for (AttendanceExpelRecord attendanceExpelRecord : attendanceExpelDto.attendanceExpelRecords()) {
            printCrewPenalty(attendanceExpelRecord);
        }
        System.out.println();
    }

    private void printCrewPenalty(final AttendanceExpelRecord attendanceExpelRecord) {
        if (hasNoPenalty(attendanceExpelRecord.crewPenalty())) {
            return;
        }
        System.out.println(
                CREW_EXPULSION_FORMAT_BODY.formatted(
                        attendanceExpelRecord.crewName(),
                        attendanceExpelRecord.crewAbsentCount(),
                        attendanceExpelRecord.crewLateCount(),
                        attendanceExpelRecord.crewPenalty()
                )
        );
    }

    private boolean hasNoPenalty(final String crewPenalty) {
        return crewPenalty.equals(CREW_NO_PENALTY_STATUS);
    }
}
