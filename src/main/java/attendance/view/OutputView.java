package attendance.view;

import java.util.List;

public class OutputView {
    // string formats
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

    // print methods
    // TODO 출석 확인 기능 출력:
    // 02월 05일 화요일 09:59 (출석)
    public void printTodayAttendance(final List<String> attendanceInfo) {
        System.out.println(
                ATTENDANCE_STRING.formatted(
                        attendanceInfo.get(0),
                        attendanceInfo.get(1),
                        attendanceInfo.get(2),
                        attendanceInfo.get(3),
                        attendanceInfo.get(4)
                )
        );
        printNewLine();
    }

    // TODO 출석 수정 기능 출력:
    // 02월 03일 화요일 10:07 (지각) -> 09:58 (출석)
    public void printModifiedAttendance(String originalTime, String originalType, List<String> newAttendanceInfo) {
        System.out.print(
                ATTENDANCE_STRING.formatted(
                        newAttendanceInfo.get(0),
                        newAttendanceInfo.get(1),
                        newAttendanceInfo.get(2),
                        originalTime,
                        originalType
                )
        );
        System.out.println(
                MODIFIED_ATTENDANCE_STRING.formatted(
                        newAttendanceInfo.get(3),
                        newAttendanceInfo.get(4)
                )
        );
        printNewLine();
    }

    // TODO 크루별 출석 기록 확인 기능 출력:
    // 2월 13일 목요일 10:07 (지각)
    // 2월 14일 금요일 --:-- (결석)
    //
    // 출석: 3회
    // 지각: 0회
    // 결석: 3회
    //
    // 면담 대상자입니다.
    public void printCrewAttendanceHistory(String crewName, List<List<String>> crewAttendanceHistory) {
        System.out.println(
                CREW_ATTENDANCE_HISTORY_STRING.formatted(crewName)
        );

        for (List<String> crewStatistic : crewAttendanceHistory) {
            String absentTime = crewStatistic.get(3);
            if (absentTime.equals("00:00")) {
                absentTime = "--:--";
            }

            System.out.println(
                    ATTENDANCE_STRING.formatted(
                            crewStatistic.get(0),
                            crewStatistic.get(1),
                            crewStatistic.get(2),
                            absentTime,
                            crewStatistic.get(4)
                    )
            );
        }
        System.out.println();
    }

    public void printCrewStatisticStatus(List<String> crewStatisticStatus) {
        System.out.println(
                CREW_STATISTICS_STRING.formatted(
                        crewStatisticStatus.get(0),
                        crewStatisticStatus.get(1),
                        crewStatisticStatus.get(2)
                )
        );

        System.out.println(
                CREW_STATUS_STRING.formatted(crewStatisticStatus.get(3))
        );
        System.out.println();
    }

    // TODO 제적 위험자 확인 기능 출력:
    // 제적 위험자 조회 결과
    // - 빙티: 결석 3회, 지각 4회 (면담)
    // (해당 되지 않는 사람은 출력 X)
    public void printExpelCrewHead() {
        System.out.println(EXPEL_CREWS_HEAD_STRING);
    }

    public void printExpelCrew(List<String> crewExpelExpectedInfo) {
        System.out.println(
                EXPEL_CREW_BODY_STRING.formatted(
                        crewExpelExpectedInfo.get(0),
                        crewExpelExpectedInfo.get(1),
                        crewExpelExpectedInfo.get(2),
                        crewExpelExpectedInfo.get(3)
                )
        );
    }

    public void printNewLine() {
        System.out.println();
    }
}
