package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("캠퍼스 운영 시간 테스트")
class CampusTimeTest {

    @ParameterizedTest
    @CsvSource({
            "08:00",
            "15:30",
            "23:00"
    })
    @DisplayName("캠퍼스_운영_시간인_경우_예외가_발생하지_않는다")
    void shouldNotThrowExceptionWhenWithinCampusOperatingHours(LocalTime attendanceTime) {
        assertThatNoException()
                .isThrownBy(() -> CampusTime.validateOperateTime(attendanceTime));
    }

    @ParameterizedTest
    @CsvSource({
            "07:59",
            "23:01"
    })
    @DisplayName("캠퍼스 운영 시간이 아닌 경우 예외가 발생한다")
    void shouldThrowExceptionWhenNotWithinCampusOperatingHours(LocalTime attendanceTime) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> CampusTime.validateOperateTime(attendanceTime))
                .withMessage("[ERROR] 캠퍼스 운영 시간은 08:00 ~ 23:00 입니다.");
    }
}
