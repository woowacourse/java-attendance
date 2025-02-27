package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.LocalDate;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import attendance.domain.AttendanceBook;
import attendance.domain.AttendanceDateTime;
import attendance.exception.AttendanceArgumentException;
import attendance.exception.AttendanceFileException;
import attendance.utility.CsvReader;

public class AttendanceBookTest {
    private final CsvReader csvReader = new CsvReader("/attendances.csv");
    private final AttendanceBook attendanceBook = AttendanceBook.of(csvReader.getLines(), new AttendanceDateTime());

    public AttendanceBookTest() throws AttendanceFileException {
    }

    @ParameterizedTest
    @MethodSource("getSourceForAttendanceInfo")
    @DisplayName("csv 파일로부터 출석 정보를 불러온다.")
    void test_getAttendanceInfoOfCSV(String nickname, LocalDate date) {
        assertThat(attendanceBook.findAttendance(nickname, date)).isNotEmpty();
    }

    @Test
    @DisplayName("등록되지 않은 닉네임을 입력하면, 예외가 발생된다.")
    void error_notRegisteredNickname() {
        var nickname = "고든";

        assertThatThrownBy(() -> attendanceBook.getAttendances(nickname))
            .isInstanceOf(AttendanceArgumentException.class)
            .hasMessageContaining("등록되지 않은 닉네임");
    }

    private static Stream<Arguments> getSourceForAttendanceInfo() {
        return Stream.of(
            Arguments.arguments(
                "이든",
                LocalDate.of(2024, 12, 3)
            ),
            Arguments.arguments(
                "빙티",
                LocalDate.of(2024, 12, 5)
            ),
            Arguments.arguments(
                "짱수",
                LocalDate.of(2024, 12, 3)
            )
        );
    }
}
