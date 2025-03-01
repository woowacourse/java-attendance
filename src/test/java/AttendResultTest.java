import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import domain.Attend;
import domain.AttendResult;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendResultTest {

    @Test
    @DisplayName("이미 존재하는 attend 를 다시 추가할 때 예외 처리")
    void throwExceptionWhenExistedAttend() {
        //given
        List<Attend> attend = createAttends();
        AttendResult attendResult = new AttendResult(attend);
        Attend targetAttend = new Attend(LocalDate.of(2024, 12, 2));

        //when & then
        Assertions.assertThatThrownBy(() -> attendResult.addAttend(targetAttend))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static List<Attend> createAttends() {
        List<Attend> attends = List.of(
                new Attend(LocalDate.of(2024, 12, 2), LocalTime.of(10, 0)),
                new Attend(LocalDate.of(2024, 12, 3), LocalTime.of(10, 0)),
                new Attend(LocalDate.of(2024, 12, 4), LocalTime.of(10, 0))
        );
        return new ArrayList<>(attends);
    }

    private static Stream<Arguments> provideNotOperationTimeAttend() {
        return Stream.of(
                Arguments.of(new Attend(LocalDate.of(2024, 12, 5), LocalTime.of(7, 59))),
                Arguments.of(new Attend(LocalDate.of(2024, 12, 5), LocalTime.of(23, 1)))
        );
    }

    @ParameterizedTest
    @MethodSource("provideNotOperationTimeAttend")
    @DisplayName("운영 시간 외의 attend 를 추가할 때 예외 처리")
    void throwExceptionWhenOutOfOperationTime(Attend targetAttend) {
        //given
        List<Attend> attend = createAttends();
        AttendResult attendResult = new AttendResult(attend);

        //when & then
        Assertions.assertThatThrownBy(() -> attendResult.addAttend(targetAttend))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> provideAttend() {
        return Stream.of(
                Arguments.of(new Attend(LocalDate.of(2024, 12, 5), LocalTime.of(10, 0))),
                Arguments.of(new Attend(LocalDate.of(2024, 12, 5)))
        );
    }

    @ParameterizedTest
    @MethodSource("provideAttend")
    @DisplayName("출석 데이터를 추가하는 기능")
    void addAttend(Attend targetAttend) {
        //given
        List<Attend> attend = createAttends();
        AttendResult attendResult = new AttendResult(attend);

        //when & then
        assertDoesNotThrow(() -> attendResult.addAttend(targetAttend));
    }

    @Test
    @DisplayName("대상 일자의 출석을 변경하는 기능")
    void changeAttend() {
        //given
        List<Attend> attend = createAttends();
        AttendResult attendResult = new AttendResult(attend);
        Attend beforeAttend = attend.getFirst();
        Attend targetAttend = new Attend(LocalDate.of(2024, 12, 2), LocalTime.of(13, 0));

        //when
        Attend actual = attendResult.edit(targetAttend);

        //then
        assertThat(actual).isEqualTo(beforeAttend);
    }

    @Test
    @DisplayName("존재하지 않는 대상 일자의 출석은 추가 후, LocalDate 만 가지는 Attend 를 리턴한다.")
    void addAttendWhenIsNotExist() {
        //given
        List<Attend> attend = createAttends();
        attend.removeFirst();
        LocalDate targetDate = LocalDate.of(2024, 12, 2);
        AttendResult attendResult = new AttendResult(attend);
        Attend beforeAttend = new Attend(targetDate);
        Attend targetAttend = new Attend(targetDate, LocalTime.of(10, 0));

        //when
        Attend actual = attendResult.edit(targetAttend);
        List<Attend> actualAttend = new ArrayList<>(attend);
        actualAttend.add(targetAttend);
        AttendResult expectedResult = new AttendResult(actualAttend);

        //then
        assertAll(
                () -> assertThat(actual).isEqualTo(beforeAttend),
                () -> assertThat(attendResult).isEqualTo(expectedResult)
        );
    }

}
