package attendance.domain.checker;

import static attendance.fixture.CampusTimeFixture.CAMPUS_END_TIME;
import static attendance.fixture.CampusTimeFixture.CAMPUS_START_TIME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.exception.AttendanceException;
import attendance.exception.ExceptionMessage;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CampusTimeTest {

    @DisplayName("캠퍼스 운영시간인지 체크할 수 있다")
    @ParameterizedTest
    @MethodSource()
    void 캠퍼스_운영시간인지_체크할_수_있다(LocalTime time, boolean inInCampusTime) {
        assertThat(CampusTime.checkInCampusTime(time))
                .isEqualTo(inInCampusTime);
    }

    static Stream<Arguments> 캠퍼스_운영시간인지_체크할_수_있다() {
        return Stream.of(
                Arguments.of(CAMPUS_START_TIME.minusSeconds(1), false),
                Arguments.of(CAMPUS_START_TIME, true),
                Arguments.of(CAMPUS_START_TIME.plusSeconds(1), true),
                Arguments.of(CAMPUS_END_TIME.minusSeconds(1), true),
                Arguments.of(CAMPUS_END_TIME, true),
                Arguments.of(CAMPUS_END_TIME.plusSeconds(1), false)
        );
    }

    @DisplayName("컴퍼스 운영시간이 아닌 경우를 검증할 수 있다")
    @ParameterizedTest
    @MethodSource()
    void 컴퍼스_운영시간이_아닌_경우를_검증할_수_있다(LocalTime time) {
        assertThatThrownBy(() -> CampusTime.validateCampusTime(time))
                .isInstanceOf(AttendanceException.class)
                .hasMessage(ExceptionMessage.OUT_OF_CAMPUS_TIME.getMessage());
    }

    static Stream<Arguments> 컴퍼스_운영시간이_아닌_경우를_검증할_수_있다() {
        return Stream.of(
                Arguments.of(CAMPUS_START_TIME.minusSeconds(1), false),
                Arguments.of(CAMPUS_END_TIME.plusSeconds(1), false)
        );
    }
}