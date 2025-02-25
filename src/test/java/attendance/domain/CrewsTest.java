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

@DisplayName("크루원들을 관리하는 저장소")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CrewsTest {
    private Crews crews;

    @BeforeEach
    void setUp() {
        List<Crew> crews = List.of(
                new Crew("빙티"),
                new Crew("이든"),
                new Crew("쿠키"),
                new Crew("빙봉")
        );
        this.crews = new Crews(crews);
    }

    @Test
    void 크루의_출석_정보를_저장한다() {
        String name = "빙봉";
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 23, 13, 1);

        assertThatCode(() -> crews.attend(name, localDateTime)).doesNotThrowAnyException();
    }

    @Test
    void 출석_저장_시_존재하지_않는_이름이면_예외가_발생한다() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 23, 13, 1);

        String otherName = "루키";

        assertThatThrownBy(() -> crews.attend(otherName, attendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 닉네임입니다.");
    }

    @Test
    void 출석_저장_시_출석_기록이_존재하면_예외가_발생한다() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 23, 13, 1);
        String crewName = "빙봉";
        crews.attend(crewName, attendanceTime);
        LocalDateTime newAttendanceTime = LocalDateTime.of(2024, 12, 23, 13, 3);

        assertThatThrownBy(() -> crews.attend(crewName, newAttendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석 기록이 존재합니다. 출석 수정 기능을 이용하세요.");
    }

    @Test
    void 출석_정보를_수정하면_이전_출석_시간을_반환한다() {
        String name = "빙봉";
        LocalDateTime prevAttendanceTime = LocalDateTime.of(2024, 12, 23, 15, 35);
        crews.attend(name, prevAttendanceTime);

        LocalDateTime newAttendanceTime = LocalDateTime.of(2024, 12, 23, 13, 1);
        Attendance prevAttendance = crews.update(name, newAttendanceTime);

        assertThat(prevAttendance.getHour()).isEqualTo(15);
        assertThat(prevAttendance.getMinute()).isEqualTo(35);
    }

    @Test
    void 출석_수정_시_존재하지_않는_이름이면_예외가_발생한다() {
        String name = "루키";
        LocalDateTime newAttendanceTime = LocalDateTime.of(2024, 12, 22, 13, 1);

        assertThatThrownBy(() -> crews.update(name, newAttendanceTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("유효하지 않은 닉네임입니다.");
    }

    @Test
    void 수정_시_해당_날짜에_출석_기록이_없으면_예외가_발생한다() {
        String name = "빙봉";
        LocalDateTime prevAttendanceTime = LocalDateTime.of(2024, 12, 23, 13, 3);
        crews.attend(name, prevAttendanceTime);

        LocalDateTime newLocalDateTime = LocalDateTime.of(2024, 12, 22, 13, 1);

        assertThatThrownBy(() -> crews.update(name, newLocalDateTime))
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
        Crews crews = CrewsTestFixture.createAttendanceRepository();
        WarningLevel warningLevel = crews.queryWarningLevelByName(name, 13);

        assertThat(warningLevel).isEqualTo(expected);
    }
}
