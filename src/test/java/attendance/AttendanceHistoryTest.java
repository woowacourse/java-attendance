package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import attendance.domain.AttendanceManager;
import attendance.repository.AttendanceFileRepository;
import attendance.service.AttendanceManagerService;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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
                                + "\n"
                                + "출석: 3회\n"
                                + "지각: 0회\n"
                                + "결석: 3회\n"
                                + "\n"
                                + "면담 대상자입니다."
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
    }
//
//    @Test
//    @DisplayName("주말 테스트")
//    void testWeekend(){
//        assertThatThrownBy(() -> new AttendanceManagerService(new AttendanceManager(),new AttendanceFileRepository("testWeekend.csv")))
//                .isInstanceOf(AttendanceArgumentException.class)
//                .hasMessageContaining("12월 14일 토요일은 등교일이 아닙니다.");
//    }
//
//    @Test
//    @DisplayName("등교 시간 테스트")
//    void testSchoolStartTime(){
//        assertThatThrownBy(() -> new AttendanceManagerService(new AttendanceManager(),new AttendanceFileRepository("testSchoolTime.csv")))
//                .isInstanceOf(AttendanceArgumentException.class)
//                .hasMessageContaining("등교시간에만 출석 가능합니다.");
//    }
//
//    @Test
//    @DisplayName("유효하지 않은 날짜 테스트")
//    void testInvalidDate(){
//        assertThatThrownBy(() -> new AttendanceManagerService(new AttendanceManager(),new AttendanceFileRepository("testInvalidDate.csv")))
//                .isInstanceOf(AttendanceArgumentException.class)
//                .hasMessageContaining("유효하지 않은 날짜 양식입니다.");
//    }
//
//    @Test
//    @DisplayName("출석 중복 테스트")
//    void duplicateAttendanceTest() {
//        assertThatThrownBy(() -> new AttendanceManagerService(new AttendanceManager(),new AttendanceFileRepository("testDuplicateNickname.csv")))
//                .isInstanceOf(AttendanceArgumentException.class)
//                .hasMessageContaining("이미 출석되었습니다.");
//    }
//
//    @Test
//    @DisplayName("닉네임이 유효하지 않으면 예외가 발생한다")
//    void testNicknameInvalid() {
//        assertThatThrownBy(() -> new AttendanceManagerService(new AttendanceManager(),new AttendanceFileRepository("testInvalidNickname.csv")))
//                .isInstanceOf(AttendanceArgumentException.class)
//                .hasMessageContaining("닉네임은 공백일 수 없습니다.");
//    }
}
