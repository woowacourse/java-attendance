package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import attendance.domain.AttendanceManager;
import attendance.domain.AttendanceStatus;
import attendance.exception.AttendanceException;
import attendance.repository.AttendanceFileRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class AttendanceFileRepositoryTest {

    private static Stream<Arguments> attendanceTest() {
        return Stream.of(
                Arguments.arguments(
                    "test.csv",
                    "투다",
                    LocalDate.of(2024,12,13),
                    AttendanceStatus.ATTENDANCE
                ),
                Arguments.arguments(
                    "test.csv",
                    "체체",
                    LocalDate.of(2024,12,13),
                    AttendanceStatus.LATE
                ),
                Arguments.arguments(
                    "test.csv",
                    "꾹이",
                    LocalDate.of(2024,12,13),
                    AttendanceStatus.ABSENCE
                ),
                Arguments.arguments(
                    "test.csv",
                    "꾹이",
                    LocalDate.of(2024,12,16),
                    AttendanceStatus.LATE
                ),
                Arguments.arguments(
                    "test.csv",
                    "꾹이",
                    LocalDate.of(2024,12,17),
                    AttendanceStatus.ABSENCE
                )
        );
    }

    @Test
    @DisplayName("출석 데이터 불러오기 테스트")
    void testLoadAttendance(){
        AttendanceFileRepository attendanceFileRepository = new AttendanceFileRepository("test.csv");
        attendanceFileRepository.saveFromAttendanceFile();
        AttendanceManager attendanceManager = attendanceFileRepository.getAttendanceManager();
        assertThat(
                attendanceManager.getAttendanceResult("투다",LocalDate.of(2024,12,13)).attendanceStatus()
        ).isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @ParameterizedTest
    @MethodSource("attendanceTest")
    @DisplayName("출석 데이터 테스트")
    void testAttendances(String src,String name,LocalDate localDate,AttendanceStatus attendanceStatus){
        AttendanceFileRepository attendanceFileRepository = new AttendanceFileRepository(src);
        attendanceFileRepository.saveFromAttendanceFile();
        AttendanceManager attendanceManager = attendanceFileRepository.getAttendanceManager();
        assertThat(
                attendanceManager.getAttendanceResult(name,localDate).attendanceStatus()
        ).isEqualTo(attendanceStatus);
    }

    @Test
    @DisplayName("주말 테스트")
    void testWeekend(){
        AttendanceFileRepository attendanceFileRepository = new AttendanceFileRepository("testWeekend.csv");
        assertThatThrownBy(() -> attendanceFileRepository.saveFromAttendanceFile())
                .isInstanceOf(AttendanceException.class)
                .hasMessageContaining("12월 14일 토요일은 등교일이 아닙니다.");
    }

    @Test
    @DisplayName("등교 시간 테스트")
    void testSchoolStartTime(){
        AttendanceFileRepository attendanceFileRepository = new AttendanceFileRepository("testSchoolTime.csv");
        assertThatThrownBy(() -> attendanceFileRepository.saveFromAttendanceFile())
                .isInstanceOf(AttendanceException.class)
                .hasMessageContaining("등교시간에만 출석 가능합니다.");
    }

    @Test
    @DisplayName("유효하지 않은 등교 시간 테스트")
    void testInvalidDate(){
        AttendanceFileRepository attendanceFileRepository = new AttendanceFileRepository("testInvalidDate.csv");
        assertThatThrownBy(() -> attendanceFileRepository.saveFromAttendanceFile())
                .isInstanceOf(AttendanceException.class)
                .hasMessageContaining("유효하지 않은 날짜 양식입니다.");
    }
}
