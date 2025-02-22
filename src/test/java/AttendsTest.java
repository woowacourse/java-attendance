import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.Attend;
import domain.Attends;
import domain.Current;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendsTest {

    static Stream<Arguments> provideAttendForIsContain() {
        return Stream.of(
                Arguments.of(Attend.of(LocalDate.of(2024, 12, 3), LocalTime.of(10, 0)), Attend.fromDay(3), true),
                Arguments.of(Attend.of(LocalDate.of(2024, 12, 3), LocalTime.of(10, 0)), Attend.fromDay(2), false)
        );
    }

    @Test
    @DisplayName("대상 날짜 Attend 리턴 테스트")
    void returnAttendUsingDay() {
        //given
        int targetDay = 2;
        Attend attend = Attend.fromDay(2);
        Attends attends = new Attends(new ArrayList<>());

        //when
        attends.addAttend(attend);
        Attend result = attends.findByDay(targetDay);

        //then
        assertThat(result).isEqualTo(attend);
    }

    @Test
    @DisplayName("중복된 날짜의 Attend가 존재하면 예외 발생")
    void throwExceptionWhenContainDuplicateAttend() {
        //given
        Attend attend = Attend.fromDay(2);
        Attends attends = new Attends(new ArrayList<>());

        //when & then
        attends.addAttend(attend);
        assertThatThrownBy(() -> attends.addAttend(attend))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("provideAttendForIsContain")
    @DisplayName("Attend에 들어있는 Attend 중에서 같은 Attend가 존재하는지 확인")
    void findAttendWhenSame(Attend containedAttend, Attend attendToAdd, boolean actual) {
        // given
        Attends attends = new Attends(new ArrayList<>());
        attends.addAttend(containedAttend);

        // when
        boolean hasDayEqualsAttend = attends.hasDayEqualsAttend(attendToAdd);

        // than
        assertThat(hasDayEqualsAttend).isEqualTo(actual);
    }

    @Test
    @DisplayName("같은 날짜에 출석이 존재하지 않으면 수정할 값으로 출석을 추가하는 기능")
    void should_edit_attend_not_exist_case() {
        // given
        Attends attends = new Attends(new ArrayList<>());
        Attend attend = Attend.of(LocalDate.of(2024, 12, 13), LocalTime.of(10, 0));

        // when
        attends.edit(attend);

        // then
        Attend expected = attends.findByDay(13);
        assertThat(expected).isEqualTo(attend);
    }

    @Test
    @DisplayName("같은 날짜에 출석이 존재하면 수정할 값으로 출석을 변경하는 기능")
    void should_edit_attend_exist_case() {
        // given
        Attends attends = new Attends(new ArrayList<>());
        Attend beforeAttend = Attend.of(LocalDate.of(2024, 12, 13), LocalTime.of(10, 1));
        Attend afterAttend = Attend.of(LocalDate.of(2024, 12, 13), LocalTime.of(10, 0));
        attends.addAttend(beforeAttend);

        // when
        attends.edit(afterAttend);

        // then
        Attend expected = attends.findByDay(13);
        assertAll(
                () -> assertThat(expected).isNotEqualTo(beforeAttend),
                () -> assertThat(expected).isEqualTo(afterAttend)
        );
    }

    @Test
    @DisplayName("출석 대상 날짜들이 들어있는 리스트를 토대로 출석 객체들을 반환한다")
    void shouldReturnAttendsByDayOfWeek() {
        // given
        List<Integer> dayOfWeek = Current.TODAY.getAttendUntilDay();
        List<Attend> attendsInitValue = List.of(
                Attend.of(LocalDate.of(2024, 12, 2), LocalTime.of(13, 0)),
                Attend.of(LocalDate.of(2024, 12, 3), LocalTime.of(10, 7)),
                Attend.of(LocalDate.of(2024, 12, 4), LocalTime.of(10, 7)),
                Attend.of(LocalDate.of(2024, 12, 5), LocalTime.of(10, 7)),
                Attend.of(LocalDate.of(2024, 12, 6), LocalTime.of(10, 0)),
                Attend.of(LocalDate.of(2024, 12, 7), LocalTime.of(10, 0)),
                Attend.of(LocalDate.of(2024, 12, 8), LocalTime.of(10, 0)),
                Attend.of(LocalDate.of(2024, 12, 9), LocalTime.of(13, 0)),
                Attend.of(LocalDate.of(2024, 12, 10), LocalTime.of(13, 0)),
                Attend.of(LocalDate.of(2024, 12, 11), LocalTime.of(13, 0)),
                Attend.of(LocalDate.of(2024, 12, 12), LocalTime.of(13, 0)),
                Attend.of(LocalDate.of(2024, 12, 13), LocalTime.of(13, 0))
        );
        Attend weekEndAttend = Attend.of(LocalDate.of(2024, 12, 7), LocalTime.of(13, 0));
        Attend futureAttend = Attend.of(LocalDate.of(2024, 12, 13), LocalTime.of(13, 0));
        Attends attends = new Attends(new ArrayList<>(attendsInitValue));

        // when
        List<Attend> result = attends.getAttends(dayOfWeek);

        // than
        List<Attend> actual = List.of(
                Attend.of(LocalDate.of(2024, 12, 2), LocalTime.of(13, 0)),
                Attend.of(LocalDate.of(2024, 12, 3), LocalTime.of(10, 7)),
                Attend.of(LocalDate.of(2024, 12, 4), LocalTime.of(10, 7)),
                Attend.of(LocalDate.of(2024, 12, 5), LocalTime.of(10, 7)),
                Attend.of(LocalDate.of(2024, 12, 6), LocalTime.of(10, 0)),
                Attend.of(LocalDate.of(2024, 12, 9), LocalTime.of(13, 0)),
                Attend.of(LocalDate.of(2024, 12, 10), LocalTime.of(13, 0)),
                Attend.of(LocalDate.of(2024, 12, 11), LocalTime.of(13, 0)),
                Attend.of(LocalDate.of(2024, 12, 12), LocalTime.of(13, 0))
        );

        assertAll(
                () -> assertThat(result).doesNotContain(weekEndAttend),
                () -> assertThat(result).containsOnlyOnceElementsOf(actual),
                () -> assertThat(result).doesNotContain(futureAttend)
        );
    }
}
