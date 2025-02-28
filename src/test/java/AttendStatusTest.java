import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.AttendStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AttendStatusTest {

    @ParameterizedTest
    @CsvSource(value = {
            "2024-12-02,13:05,ATTEND", "2024-12-03,10:05,ATTEND",
            "2024-12-02,13:30,LATE", "2024-12-03,10:30,LATE",
            "2024-12-02,13:31,ABSENCE", "2024-12-03,10:31,ABSENCE",
            "2024-12-02,,ABSENCE", "2024-12-03,,ABSENCE"
    })
    @DisplayName("요일에 따른 출석 상태 판정 기능")
    void checkAttendStatusUsingDateAndTime(LocalDate date, LocalTime time, AttendStatus expected) {
        //when
        AttendStatus actual = AttendStatus.checkAttendStatus(date, time);

        //then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"2024-12-01,10:00", "2024-12-25,10:00"})
    @DisplayName("운영일 외 일자를 입력하면 예외 처리")
    void throwExceptionWhenDateIsNotOperation(LocalDate date, LocalTime time) {
        //when & then
        assertThatThrownBy(() -> AttendStatus.checkAttendStatus(date, time))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
