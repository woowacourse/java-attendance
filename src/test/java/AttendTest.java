import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.Attend;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendTest {

    static Stream<Arguments> provideAttendForIsSame() {
        return Stream.of(
                Arguments.of(Attend.fromDay(1), Attend.fromDay(1), true),
                Arguments.of(Attend.fromDay(1), Attend.fromDay(2), false)
        );
    }

    static Stream<Arguments> provideAttendForIsDayOff() {
        return Stream.of(
                Arguments.of(Attend.fromDay(1), true),
                Arguments.of(Attend.fromDay(2), false)
        );
    }

    @Test
    @DisplayName("day 를 기반으로 한 Attend 생성 테스트")
    void testAttendCreateUsingDate() {
        //given
        int targetDay = 3;

        //when
        Attend attend = Attend.fromDay(targetDay);

        //then
        assertThat(attend.getDay()).isEqualTo(3);
    }

    @Test
    @DisplayName("time 을 기반으로 한 Attend 생성 테스트")
    void testAttendCreateUsingTime() {
        //given
        LocalTime targetTime = LocalTime.of(9, 59, 0);

        //when
        Attend attend = Attend.fromTime(targetTime);

        //then
        assertThat(attend.isEqual(targetTime)).isTrue();
    }

    @Test
    @DisplayName("day, time 을 기반으로 한 Attend 생성 테스트")
    void testAttendCreateUsingDayAndTime() {
        //given
        LocalDate day = LocalDate.of(2024, 12, 3);
        LocalTime time = LocalTime.of(9, 59);

        //when
        var result = Attend.of(day, time);

        //then
        assertAll(
                () -> assertThat(result.getDay()).isEqualTo(3),
                () -> assertThat(result.isEqual(time)).isTrue()
        );
    }

    @ParameterizedTest
    @MethodSource("provideAttendForIsSame")
    @DisplayName("같은 날짜인지 확인 - Attend 이용")
    void compareAttendIsSameUsingDay(Attend attend, Attend compareAttend, boolean actual) {

        //when
        boolean result = attend.isDayEqual(compareAttend);

        //then
        Assertions.assertThat(result).isEqualTo(actual);
    }

    @ParameterizedTest
    @CsvSource(value = {"1,1,true", "2,3,false"})
    @DisplayName("같은 날짜인지 확인 - day 이용")
    void compareAttendIsSameUsingDay(int day, int compareDay, boolean actual) {
        //given
        Attend attend = Attend.fromDay(day);
        Attend compare = Attend.fromDay(day);

        //when
        boolean result = attend.isDayEqual(compareDay);

        //then
        Assertions.assertThat(result).isEqualTo(actual);
    }

    @ParameterizedTest
    @MethodSource("provideAttendForIsDayOff")
    @DisplayName("출석 대상 일자인지 확인")
    void checkAttendDayIsNormalDay(Attend attend, boolean actual) {
        //when
        var expected = attend.isDayOff();

        //then
        assertThat(expected).isEqualTo(actual);
    }

    @ParameterizedTest
    @CsvSource(value = {"09:59,10:00,true", "10:00,10:00,false", "10:01,10:00,false"})
    @DisplayName("Attend 시간이 대상 시간 이전인지 확인")
    void checkAttendTimeIsBefore(String time, String compareTime, boolean actual) {
        //given
        Attend attend = Attend.fromTime(LocalTime.parse(time));
        LocalTime compare = LocalTime.parse(compareTime);

        //when
        var result = attend.isBefore(compare);

        //then
        assertThat(result).isEqualTo(actual);
    }

    @ParameterizedTest
    @CsvSource(value = {"09:59,10:00,false", "10:00,10:00,true", "10:01,10:00,false"})
    @DisplayName("Attend 시간이 대상 시간과 동일한지 확인")
    void checkAttendTimeIsEqual(String time, String compareTime, boolean actual) {
        //given
        Attend attend = Attend.fromTime(LocalTime.parse(time));
        LocalTime compare = LocalTime.parse(compareTime);

        //when
        var result = attend.isEqual(compare);

        //then
        assertThat(result).isEqualTo(actual);
    }

    @ParameterizedTest
    @CsvSource(value = {"09:59,10:00,false", "10:00,10:00,false", "10:01,10:00,true"})
    @DisplayName("Attend 시간이 대상 시간 이후인지 확인")
    void checkAttendTimeIsAfter(String time, String compareTime, boolean actual) {
        //given
        Attend attend = Attend.fromTime(LocalTime.parse(time));
        LocalTime compare = LocalTime.parse(compareTime);

        //when
        var result = attend.isAfter(compare);

        //then
        assertThat(result).isEqualTo(actual);
    }
}
