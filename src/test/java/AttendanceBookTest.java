import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import domain.Attend;
import domain.AttendanceBook;
import domain.Current;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
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

    private static Attend createAttendDateAndTime(int day, int hour, int minute) {
        return new Attend(LocalDate.of(Current.TODAY.getYear(), Current.TODAY.getMonth(), day),
                LocalTime.of(hour, minute));
    }

    private static Attend createAttendDate(int day) {
        return new Attend(LocalDate.of(Current.TODAY.getYear(), Current.TODAY.getMonth(), day));
    }

    private static List<Attend> createAttendUntilToday() {
        return List.of(
                createAttendDateAndTime(2, 10, 5),
                createAttendDateAndTime(3, 10, 5),
                createAttendDateAndTime(4, 10, 5),
                createAttendDateAndTime(5, 10, 30),
                createAttendDateAndTime(6, 10, 30),
                createAttendDateAndTime(9, 10, 30),
                createAttendDateAndTime(10, 10, 31),
                createAttendDateAndTime(11, 10, 31),
                createAttendDateAndTime(12, 10, 31)
        );
    }

    @Test
    @DisplayName("대상 닉네임의 오늘 직전까지의 출석 현황을 조회한다")
    void searchAttendResultUsingName() {
        //given
        AttendanceBook attendanceBook = new AttendanceBook();
        String name = "플린트";
        attendanceBook.register(name);
        List<Attend> attends = createAttendUntilToday();
        for (Attend attend : attends) {
            attendanceBook.addAttend(name, attend);
        }

        //when
        List<Attend> actual = attendanceBook.searchAttend(name, Current.TODAY.getDay());

        //then
        assertThat(actual).isEqualTo(attends);
    }
}
