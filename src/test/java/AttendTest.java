import static org.assertj.core.api.Assertions.assertThat;

import domain.AttendStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendTest {

    @ParameterizedTest
    @CsvSource(value = {"2024-12-02,2,true", "2024-12-02,3,false"})
    @DisplayName("출석일이 주어진 값과 같은지 판정하는 기능")
    void checkAttendStatus(LocalDate date, int day, boolean expected) {
        //given
        Attend attend = new Attend(date);

        //when
        boolean actual = attend.equalsDay(day);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "2024-12-02,13:05,ATTEND", "2024-12-03,10:05,ATTEND",
            "2024-12-02,13:30,LATE", "2024-12-03,10:30,LATE",
            "2024-12-02,13:31,ABSENCE", "2024-12-03,10:31,ABSENCE",
            "2024-12-02,,ABSENCE", "2024-12-03,,ABSENCE"
    })
    @DisplayName("평일 교육일 기반 출석 상태 판정 기능")
    void checkAttendStatus(LocalDate date, LocalTime time, AttendStatus expected) {
        //given
        Attend attend = new Attend(date, time);

        //when
        AttendStatus actual = attend.checkStatus();

        //then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> provideAttendsAndExpect() {
        return Stream.of(
                Arguments.of(
                        new Attend(LocalDate.of(2024, 12, 2), LocalTime.of(10, 0)),
                        new Attend(LocalDate.of(2024, 12, 2), LocalTime.of(11, 0)),
                        true
                ),
                Arguments.of(
                        new Attend(LocalDate.of(2024, 12, 2), LocalTime.of(10, 0)),
                        new Attend(LocalDate.of(2024, 12, 2)),
                        true
                ),
                Arguments.of(
                        new Attend(LocalDate.of(2024, 12, 2), LocalTime.of(10, 0)),
                        new Attend(LocalDate.of(2024, 12, 3), LocalTime.of(10, 0)),
                        false
                )
        );
    }

    @ParameterizedTest
    @MethodSource("provideAttendsAndExpect")
    @DisplayName("날짜를 기반으로 Attend가 같은지 판정하는 기능")
    void equalsUsingOnlyDateNotUseTime(Attend attend, Attend anotherAttend, boolean expected) {
        //when
        boolean actual = attend.equalsDate(anotherAttend);
        //then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"2024-12-01", "2024-12-25"})
    @DisplayName("운영일이 아닌 날짜의 attend를 생성 시도할 때 예외 처리")
    void throwExceptionWhenDateIsNotOperationDate(LocalDate date) {
        //when & then
        Assertions.assertThatThrownBy(() -> new Attend(date))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
