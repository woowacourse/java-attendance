package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import attendance.common.exception.AttendanceFileException;
import attendance.domain.Attendance;
import attendance.domain.AttendanceManager;

public class AttendanceManagerTest {
    private AttendanceManager manager;
    private static final String TEST_FILE = "/attendances.csv";

    @Nested
    @DisplayName("잘못된 File에 대한 예외 케이스 테스트")
    class FileExceptionCases {
        @Test
        @DisplayName("잘못된 파일 주소가 있을 경우, 예외를 발생한다.")
        void error_wrongFileURL() {
            assertThatThrownBy(() -> new AttendanceManager("/invalid"))
                .isInstanceOf(AttendanceFileException.class)
                .hasMessageContaining("존재하지 않은 파일입니다.");
        }

        @Test
        @DisplayName("잘못된 파일 주소가 있을 경우, 예외를 발생한다.")
        void error_invalidFile() {
            assertThatThrownBy(() -> new AttendanceManager(""))
                .isInstanceOf(AttendanceFileException.class)
                .hasMessageContaining("유효하지 않은 파일입니다.");
        }
    }

    @Nested
    @DisplayName("기능에 대한 테스트")
    class TestForAttendanceManaging {

        @BeforeEach
        void setUp() throws AttendanceFileException {
            manager = new AttendanceManager(TEST_FILE);
        }

        @ParameterizedTest
        @MethodSource("getSourceForAttendanceInfo")
        @DisplayName("csv 파일로부터 출석 정보를 불러온다.")
        void test_getAttendanceInfoFromCSV(String nickname, Attendance attendance) {
            assertThat(manager.findAttendance(nickname, attendance)).isEqualTo(attendance);
        }

        @Test
        @DisplayName("닉네임과 출석 정보을 입력하면, 출석 정보를 저장한다.")
        void test_attendance() {
            var nickname = "이든";
            var time = LocalDateTime.of(2024, 12, 13, 10, 1);

            var attendance = new Attendance(time);

            manager.addAttendance(nickname, attendance);

            Assertions.assertThat(manager.findAttendance(nickname, attendance)).isEqualTo(attendance);
        }

        @Test
        @DisplayName("등록되지 않은 닉네임을 입력하면, 예외가 발생된다.")
        void error_notRegisteredNickname() {

        }

        @Test
        @DisplayName("다시 출석할 경우, 예외가 발생한다.")
        void error_retireAttendance() {

        }

        private static Stream<Arguments> getSourceForAttendanceInfo() {
            return Stream.of(
                Arguments.arguments(
                    "이든",
                    new Attendance(LocalDateTime.of(2024, 12, 3, 10, 6))
                ),
                Arguments.arguments(
                    "빙티",
                    new Attendance(LocalDateTime.of(2024, 12, 5, 10, 6))
                ),
                Arguments.arguments(
                    "짱수",
                    new Attendance(LocalDateTime.of(2024, 12, 3, 10, 0))
                )
            );
        }
    }

}
