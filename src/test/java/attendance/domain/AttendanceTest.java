package attendance.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceTest {


    @DisplayName("크루 이름과 년월일이 같다면 true 다르다면 false를 반환한다")
    @ParameterizedTest
    @CsvSource(value = {
            "2025,2,27,27,10,0,10,5, true", "2025,2,27,28,10,0,10,0, false"
    })
    void 크루_이름과_년월일이_같다면_true_다르다면_false를_반환한다(int year, int month, int day, int otherDay, int hour,
                                               int minute,
                                               int otherHour, int otherMinute, boolean result) {

        // given
        Attendance attendance1 = new Attendance("체체", new Time(LocalDateTime.of(year, month, day, hour, minute)));
        Attendance attendance2 = new Attendance("체체",
                new Time(LocalDateTime.of(year, month, otherDay, otherHour, otherMinute)));

        // when
        boolean isEqual = attendance1.isSameLocalDate(attendance2);

        // then
        assertThat(isEqual).isEqualTo(result);

    }
}
