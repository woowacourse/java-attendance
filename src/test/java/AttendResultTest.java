import domain.Attend;
import domain.AttendCount;
import domain.AttendResult;
import domain.Current;
import domain.WarningStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
        LocalDate today = Current.TODAY.getDate();
        AttendResult attendResult = new AttendResult(attend, today);
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
        LocalDate today = Current.TODAY.getDate();
        AttendResult attendResult = new AttendResult(attend, today);

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
        LocalDate today = Current.TODAY.getDate();
        AttendResult attendResult = new AttendResult(attend, today);

        //when & then
        assertDoesNotThrow(() -> attendResult.addAttend(targetAttend));
    }

    @Test
    @DisplayName("대상 일자의 출석을 변경하는 기능")
    void changeAttend() {
        //given
        List<Attend> attend = createAttends();
        LocalDate today = Current.TODAY.getDate();
        AttendResult attendResult = new AttendResult(attend, today);
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
        LocalDate today = Current.TODAY.getDate();
        AttendResult attendResult = new AttendResult(attend, today);
        Attend beforeAttend = new Attend(targetDate);
        Attend targetAttend = new Attend(targetDate, LocalTime.of(10, 0));

        //when
        Attend actual = attendResult.edit(targetAttend);
        List<Attend> actualAttend = new ArrayList<>(attend);
        actualAttend.add(targetAttend);
        AttendResult expectedResult = new AttendResult(actualAttend, today);

        //then
        assertAll(
                () -> assertThat(actual).isEqualTo(beforeAttend),
                () -> assertThat(attendResult).isEqualTo(expectedResult)
        );
    }

    @Test
    @DisplayName("대상 날짜 직전까지의 출석 결과를 반환한다")
    void getAttendsUntilDay() {
        //given
        List<Attend> attend = createAttends();
        LocalDate today = Current.TODAY.getDate();
        AttendResult attendResult = new AttendResult(attend, today);
        int targetDay = 5;

        //when
        List<Attend> actual = attendResult.getAttendResult(targetDay);

        //then
        List<Attend> expected = createAttends();
        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("대상 날짜 직전까지의 출석 결과를 누락된 날짜의 출석도 포함하여 반환한다")
    void getAttendsUntilDayContainAbsence() {
        //given
        List<Attend> attend = createAttends();
        LocalDate today = Current.TODAY.getDate();
        AttendResult attendResult = new AttendResult(attend, today);
        int targetDay = 6;

        //when
        List<Attend> actual = attendResult.getAttendResult(targetDay);

        //then
        List<Attend> expected = createAttends();
        expected.add(new Attend(LocalDate.of(2024, 12, 5)));
        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    @DisplayName("특정 날짜까지의 출석, 지각, 결석 횟수를 계산한다")
    void countAttendStatus() {
        //given
        List<Attend> attend = List.of(
                new Attend(LocalDate.of(2024, 12, 2), LocalTime.of(13, 5)),
                new Attend(LocalDate.of(2024, 12, 3), LocalTime.of(10, 6)),
                new Attend(LocalDate.of(2024, 12, 4), LocalTime.of(10, 31)),
                new Attend(LocalDate.of(2024, 12, 5))
        );
        LocalDate today = Current.TODAY.getDate();
        AttendResult attendResult = new AttendResult(attend, today);
        int targetDay = 6;

        //when
        AttendCount actual = attendResult.countAttendStatus(targetDay);

        //then
        AttendCount expected = new AttendCount(1, 1, 2);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("현재 출석 정보들을 기반으로 제적 위험 여부를 판정한다")
    void judgeWarningStatus() {
        //given
        List<Attend> attend = List.of(
                new Attend(LocalDate.of(2024, 12, 2), LocalTime.of(13, 0)),
                new Attend(LocalDate.of(2024, 12, 3), LocalTime.of(10, 0)),
                new Attend(LocalDate.of(2024, 12, 4), LocalTime.of(10, 31)),
                new Attend(LocalDate.of(2024, 12, 5))
        );
        LocalDate today = Current.TODAY.getDate();
        AttendResult attendResult = new AttendResult(attend, today);
        int targetDay = 6;

        //when
        WarningStatus actual = attendResult.judgeWarningStatus(targetDay);

        //then
        assertThat(actual).isEqualTo(WarningStatus.WARNING);
    }
}
