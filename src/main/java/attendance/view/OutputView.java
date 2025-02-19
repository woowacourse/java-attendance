package attendance.view;

import java.util.List;

public class OutputView {
    // string formats
    private static final String ATTENDANCE_STRING = "%s월 %s일 %s요일 %s (%s)";
    private static final String MODIFIED_ATTENDANCE_STRING = " -> %s (%s) 수정 완료!";
    private static final String CREW_ATTENDANCE_HISTORY_STRING = "이번 달 %s의 출석 기록입니다.";

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
        System.out.println();
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
        System.out.println();
    }

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
    }

    // TODO 크루별 출석 기록 확인 기능 출력:
    // 이번 달 빙티의 출석 기록입니다.
    //
    // (주말 출력 X)
    // 02월 02일 월요일 13:00 (출석)
    // 02월 03일 화요일 10:07 (지각)
    // 02월 04일 수요일 10:02 (출석)
    // 02월 05일 목요일 10:06 (지각)
    // 02월 06일 금요일 10:01 (출석)
    // 02월 09일 월요일 --:-- (결석)
    // (전날까지의 기록 출력)
    // TODO >>>>>>
    // 출석: 3회
    // 지각: 0회
    // 결석: 3회
    //
    // 면담 대상자입니다.

    // TODO 제적 위험자 확인 기능 출력:
    // 제적 위험자 조회 결과
    // - 빙티: 결석 3회, 지각 4회 (면담)
    // (해당 되지 않는 사람은 출력 X)
}
