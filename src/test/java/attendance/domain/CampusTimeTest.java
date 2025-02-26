package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

class CampusTimeTest {

    @ParameterizedTest
    @CsvSource({
            "07:59",
            "23:01"
    })
    @DisplayName("캠퍼스 운영 시간이 아닌 경우 예외가 발생한다")
    void 캠퍼스_운영_시간이_아닌_경우_예외가_발생한다(LocalTime attendanceTime) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> CampusTime.validateOperateTime(attendanceTime))
                .withMessage("[ERROR] 캠퍼스 운영 시간은 08:00 ~ 23:00 입니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "08:00",
            "15:30",
            "23:00"
    })
    @DisplayName("캠퍼스 운영 시간이 아닌 경우 예외가 발생한다")
    void 캠퍼스_운영_시간인_경우_예외가_발생하지_않는다(LocalTime attendanceTime) {
        assertThatNoException()
                .isThrownBy(() -> CampusTime.validateOperateTime(attendanceTime));
    }
}
