package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import attendance.domain.AttendanceManager;
import attendance.repository.AttendanceFileRepository;
import attendance.service.AttendanceManagerService;

public class AttendanceHistoryTest {

    private static Stream<Arguments> attendanceTest() {
        return Stream.of(
            Arguments.arguments(
                "/testAttendanceHistory.csv",
                "빙티",
                "이번 달 빙티의 출석 기록입니다.\n"
                    + "\n"
                    + "12월 02일 월요일 13:00 (출석)\n"
                    + "12월 03일 화요일 10:07 (지각)\n"
                    + "12월 04일 수요일 10:02 (출석)\n"
                    + "12월 05일 목요일 10:06 (지각)\n"
                    + "12월 06일 금요일 10:01 (출석)\n"
                    + "12월 09일 월요일 --:-- (결석)\n"
                    + "12월 10일 화요일 10:03 (출석)\n"
                    + "12월 11일 수요일 --:-- (결석)\n"
                    + "12월 12일 목요일 --:-- (결석)\n"
                    + "12월 13일 금요일 10:02 (출석)\n"
            ),
            Arguments.arguments(
                "/testAttendanceHistory.csv",
                "빙티",
                "출석: 5회\n"
                    + "지각: 2회\n"
                    + "결석: 14회\n"
                    + "\n"
            ),
            Arguments.arguments(
                "/testAttendanceHistory.csv",
                "빙티",
                "제적 대상자입니다."
            )

        );
    }

    @ParameterizedTest
    @MethodSource("attendanceTest")
    @DisplayName("출석 데이터 테스트")
    void testAttendances(String src, String name, String formattedResult) {
        AttendanceManagerService attendanceManagerService = new AttendanceManagerService(new AttendanceManager(),
            new AttendanceFileRepository(src));
        assertThat(
            attendanceManagerService.crewAttendanceHistory(name)
        ).contains(formattedResult);
        ;
    }
}
