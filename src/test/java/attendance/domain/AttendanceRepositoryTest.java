package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("출석 정보를 관리하는 저장소")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AttendanceRepositoryTest {
    private AttendanceRepository attendanceRepository;

    @BeforeEach
    void setUp() {
        List<Attendance> attendances = List.of(
                new Attendance("빙티"),
                new Attendance("이든"),
                new Attendance("쿠키"),
                new Attendance("빙봉")
        );
        attendanceRepository = new AttendanceRepository(attendances);
    }

    @Test
    void 크루의_출석_정보를_저장한다() {
        String name = "빙봉";
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 1);

        assertThatCode(() -> attendanceRepository.add(name, localDateTime)).doesNotThrowAnyException();
    }

    @Test
    void 출석_저장_시_존재하지_않는_이름이면_예외가_발생한다() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 23, 13, 1);

        String otherName = "루키";

        assertThatThrownBy(() -> attendanceRepository.add(otherName, attendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 닉네임입니다.");
    }

    @Test
    void 크루의_출석_정보를_수정한다() {
        String name = "빙봉";
        LocalDateTime prevAttendanceTime = LocalDateTime.of(2024, 12, 23, 13, 35);
        attendanceRepository.add(name, prevAttendanceTime);

        LocalDateTime newAttendanceTime = LocalDateTime.of(2024, 12, 23, 13, 1);

        assertThatCode(() -> attendanceRepository.update(name, newAttendanceTime))
                .doesNotThrowAnyException();
    }

    @Test
    void 출석_정보를_수정하면_이전_출석_시간을_반환한다() {
        String name = "빙봉";
        LocalDateTime prevAttendanceTime = LocalDateTime.of(2024, 12, 23, 15, 35);
        attendanceRepository.add(name, prevAttendanceTime);

        LocalDateTime newAttendanceTime = LocalDateTime.of(2024, 12, 23, 13, 1);
        HourMinute prevHourMinute = attendanceRepository.update(name, newAttendanceTime);

        assertThat(prevHourMinute.hour()).isEqualTo(15);
        assertThat(prevHourMinute.minute()).isEqualTo(35);
    }

    @Test
    void 출석_수정_시_존재하지_않는_이름이면_예외가_발생한다() {
        String name = "루키";
        LocalDateTime newAttendanceTime = LocalDateTime.of(2024, 12, 22, 13, 1);

        assertThatThrownBy(() -> attendanceRepository.update(name, newAttendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 닉네임입니다.");

    }

    @Test
    void 수정_시_해당_날짜에_출석_기록이_없으면_예외가_발생한다() {
        String name = "빙봉";
        LocalDateTime prevAttendanceTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        attendanceRepository.add(name, prevAttendanceTime);

        LocalDateTime newLocalDateTime = LocalDateTime.of(2024, 12, 22, 13, 1);

        assertThatThrownBy(() -> attendanceRepository.update(name, newLocalDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 날짜에 출석 기록이 없습니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "'빙티', COUNSELING",
            "'이든', COUNSELING",
            "'쿠키', WARNING",
            "'빙봉', WARNING"
    })
    void 제적_위험_상태를_반환한다(String name, WarningLevel expected) {
        int today = 13;
        AttendanceRepository attendanceRepository = AttendanceRepositoryTestFixture.createAttendanceRepository();
        WarningLevel warningLevel = attendanceRepository.queryWarningLevelByName(name, 13);

        assertThat(warningLevel).isEqualTo(expected);
    }
}
