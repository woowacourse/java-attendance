package attendance.view;

import attendance.domain.InfoCheckAttendance;
import attendance.domain.InfoLookupAttendance;
import attendance.domain.InfoLookupExpelRecord;
import attendance.domain.InfoLookupExpulsion;
import attendance.domain.InfoModifyAttendance;

public class OutputView {
    private static final String ATTENDANCE_STRING = "%s월 %s일 %s요일 %s (%s)";
    private static final String MODIFIED_ATTENDANCE_STRING = " -> %s (%s) 수정 완료!";
    private static final String CREW_ATTENDANCE_HISTORY_STRING = "이번 달 %s의 출석 기록입니다.";
    private static final String CREW_STATISTICS_STRING = """
            출석: %s회
            지각: %s회
            결석: %s회
            """;
    private static final String CREW_STATUS_STRING = "%s 대상자입니다.";
    private static final String EXPEL_CREWS_HEAD_STRING = "제적 위험자 조회 결과";
    private static final String EXPEL_CREW_BODY_STRING = "- %s: 결석 %s회, 지각 %s회 (%s)";

    private static final String ABSENT_TIME_VALUE = "00:00";
    private static final String ABSENT_TIME_FORMAT = "--:--";

    public void printTodayAttendance(final InfoCheckAttendance attendanceInfo) {
        System.out.println(
                ATTENDANCE_STRING.formatted(
                        attendanceInfo.getMonth(),
                        attendanceInfo.getDay(),
                        attendanceInfo.getDayOfWeek(),
                        attendanceInfo.getAttendanceTime(),
                        attendanceInfo.getAttendanceType()
                )
        );
        printNewLine();
    }

    public void printModifiedAttendance(final InfoModifyAttendance attendanceModifiedInfo) {
        System.out.print(
                ATTENDANCE_STRING.formatted(
                        attendanceModifiedInfo.getMonth(),
                        attendanceModifiedInfo.getDay(),
                        attendanceModifiedInfo.getDayOfWeek(),
                        attendanceModifiedInfo.getOriginalTime(),
                        attendanceModifiedInfo.getOriginalType()
                )
        );
        System.out.println(formatModifiedNotice(attendanceModifiedInfo));
        printNewLine();
    }

    private String formatModifiedNotice(final InfoModifyAttendance attendanceModifiedInfo) {
        return MODIFIED_ATTENDANCE_STRING.formatted(
                attendanceModifiedInfo.getNewTime(),
                attendanceModifiedInfo.getNewType()
        );
    }

    public void printCrewAttendanceHistory(final InfoLookupAttendance infoLookupAttendance) {
        System.out.println(
                CREW_ATTENDANCE_HISTORY_STRING.formatted(infoLookupAttendance.getCrewName())
        );
        for (InfoCheckAttendance infoLookupRecord : infoLookupAttendance.getCrewAttendanceRecords()) {
            System.out.println(
                    ATTENDANCE_STRING.formatted(
                            infoLookupRecord.getMonth(),
                            infoLookupRecord.getDay(),
                            infoLookupRecord.getDayOfWeek(),
                            formatAbsentTimeString(infoLookupRecord.getAttendanceTime()),
                            infoLookupRecord.getAttendanceType()
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

    public void printCrewStatisticStatus(final InfoLookupAttendance infoLookupAttendance) {
        System.out.println(
                CREW_STATISTICS_STRING.formatted(
                        infoLookupAttendance.getCrewStatisticSafe(),
                        infoLookupAttendance.getCrewStatisticLate(),
                        infoLookupAttendance.getCrewStatisticAbsent()
                )
        );
        System.out.println(
                CREW_STATUS_STRING.formatted(infoLookupAttendance.getCrewStatisticPenalty())
        );
        printNewLine();
    }

    public void printExpelExpectedCrews(final InfoLookupExpulsion infoLookupExpulsion) {
        System.out.println(EXPEL_CREWS_HEAD_STRING);
        for (InfoLookupExpelRecord infoLookupExpelRecord : infoLookupExpulsion.getCrewExpelRecords()) {
            System.out.println(
                    EXPEL_CREW_BODY_STRING.formatted(
                            infoLookupExpelRecord.getCrewName(),
                            infoLookupExpelRecord.getAbsentCount(),
                            infoLookupExpelRecord.getLateCount(),
                            infoLookupExpelRecord.getExpectedPenalty()
                    )
            );
        }
        printNewLine();
    }

    public void printNewLine() {
        System.out.println();
    }
}
