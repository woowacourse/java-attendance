import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import domain.Attend;
import domain.AttendanceBook;
import domain.Current;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceBookTest {

    @Test
    @DisplayName("대상 닉네임이 존재하지 않으면 예외를 던진다")
    void throwExceptionNotExistName() {
        //given
        AttendanceBook attendanceBook = new AttendanceBook();
        String name = "플린트";
        Attend attend = new Attend(Current.TODAY.getDate());

        //when & then
        Assertions.assertThatThrownBy(() -> attendanceBook.addAttend(name, attend));
    }

    @Test
    @DisplayName("출석부에 닉네임을 등록한다")
    void registerName() {
        //given
        AttendanceBook attendanceBook = new AttendanceBook();
        String name = "플린트";

        //when & then
        assertDoesNotThrow(() -> attendanceBook.register(name));
    }

    @Test
    @DisplayName("대상 닉네임의 출석을 추가한다")
    void addAttendUsingName() {
        //given
        AttendanceBook attendanceBook = new AttendanceBook();
        String name = "플린트";
        Attend attend = new Attend(Current.TODAY.getDate());
        attendanceBook.register(name);

        //when & then
        assertDoesNotThrow(() -> attendanceBook.addAttend(name, attend));
    }

    @Test
    @DisplayName("대상 출석 데이터가 이미 존재하면 예외를 던진다")
    void throwExceptionExistAttend() {
        //given
        AttendanceBook attendanceBook = new AttendanceBook();
        String name = "플린트";
        Attend attend = new Attend(Current.TODAY.getDate());
        attendanceBook.register(name);
        attendanceBook.addAttend(name, attend);

        //when & then
        Attend anotherAttend = new Attend(Current.TODAY.getDate(), LocalTime.of(11, 0));
        Assertions.assertThatThrownBy(() -> attendanceBook.addAttend(name, anotherAttend));
    }

    private static Stream<Arguments> provideBeforeAndAfterAttend() {
        return Stream.of(
                Arguments.of(new Attend(Current.TODAY.getDate()),
                        new Attend(Current.TODAY.getDate(), LocalTime.of(10, 0)),
                        new Attend(Current.TODAY.getDate())),
                Arguments.of(new Attend(Current.TODAY.getDate()),
                        new Attend(LocalDate.of(2024, 12, 2), LocalTime.of(10, 0)),
                        new Attend(LocalDate.of(2024, 12, 2)))
        );
    }

    @ParameterizedTest
    @MethodSource("provideBeforeAndAfterAttend")
    @DisplayName("대상 닉네임의 출석을 수정한다")
    void editAttendUsingName(Attend before, Attend after, Attend expected) {
        //given
        AttendanceBook attendanceBook = new AttendanceBook();
        String name = "플린트";
        attendanceBook.register(name);
        attendanceBook.addAttend(name, before);

        //when
        Attend actual = attendanceBook.edit(name, after);

        //then
        assertThat(actual).isEqualTo(expected);
    }
}
