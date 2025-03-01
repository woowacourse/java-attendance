import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import domain.Attend;
import domain.AttendCount;
import domain.AttendanceBook;
import domain.Current;
import domain.WarningCrew;
import domain.WarningStatus;
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
    @DisplayName("출석부에 닉네임을 중복해서 등록하면 이전 값을 가지고 있다")
    void registerNameDuplicate() {
        //given

        AttendanceBook attendanceBook = new AttendanceBook();
        String name = "플린트";
        attendanceBook.register(name);
        Attend attend = new Attend(LocalDate.of(2024, 12, 2), LocalTime.of(10, 0));
        attendanceBook.addAttend(name, attend);
        List<Attend> before = attendanceBook.searchAttend(name, 3);

        //when
        attendanceBook.register(name);
        List<Attend> after = attendanceBook.searchAttend(name, 3);

        //then
        LocalTime beforeTime = before.getFirst().getTime();
        LocalTime afterTime = after.getFirst().getTime();
        assertThat(beforeTime).isEqualTo(afterTime);
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

    private static List<Attend> createAttendUntilToday() {
        return List.of(
                createAttendDateAndTime(2, 13, 5),
                createAttendDateAndTime(3, 10, 5),
                createAttendDateAndTime(4, 10, 5),
                createAttendDateAndTime(5, 10, 30),
                createAttendDateAndTime(6, 10, 30),
                createAttendDateAndTime(9, 13, 30),
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

    private static List<Attend> createExpelAttend() {
        return List.of(
                createAttendDateAndTime(2, 13, 5),
                createAttendDateAndTime(3, 10, 5),
                createAttendDateAndTime(4, 10, 5),
                createAttendDateAndTime(5, 10, 31),
                createAttendDateAndTime(6, 10, 31),
                createAttendDateAndTime(9, 13, 31),
                createAttendDateAndTime(10, 10, 31),
                createAttendDateAndTime(11, 10, 31),
                createAttendDateAndTime(12, 10, 31)
        );
    }

    @Test
    @DisplayName("대상 닉네임의 출석 위험을 판정한다 - 제적 대상")
    void judgeAttendStatusEXPELL() {
        //given
        AttendanceBook attendanceBook = new AttendanceBook();
        String name = "플린트";
        attendanceBook.register(name);
        List<Attend> attends = createExpelAttend();
        for (Attend attend : attends) {
            attendanceBook.addAttend(name, attend);
        }

        //when
        WarningStatus actual = attendanceBook.judgeAttendStatus(name);

        //then
        assertThat(actual).isEqualTo(WarningStatus.EXPEL);
    }

    @Test
    @DisplayName("대상 닉네임의 출석 위험을 판정한다 - 면담 대상")
    void judgeAttendStatus() {
        //given
        AttendanceBook attendanceBook = new AttendanceBook();
        String name = "플린트";
        attendanceBook.register(name);
        List<Attend> attends = createAttendUntilToday();
        for (Attend attend : attends) {
            attendanceBook.addAttend(name, attend);
        }

        //when
        WarningStatus actual = attendanceBook.judgeAttendStatus(name);

        //then
        assertThat(actual).isEqualTo(WarningStatus.INTERVIEW);
    }

    private static List<Attend> createPassAttend() {
        return List.of(
                createAttendDateAndTime(2, 10, 0),
                createAttendDateAndTime(3, 10, 0),
                createAttendDateAndTime(4, 10, 0),
                createAttendDateAndTime(5, 10, 0),
                createAttendDateAndTime(6, 10, 0),
                createAttendDateAndTime(9, 10, 0),
                createAttendDateAndTime(10, 10, 0),
                createAttendDateAndTime(11, 10, 0),
                createAttendDateAndTime(12, 10, 0)
        );
    }

    @Test
    @DisplayName("대상 닉네임의 출석 위험을 판정한다 - 경고 대상")
    void judgeAttendStatusWarning() {
        //given
        AttendanceBook attendanceBook = new AttendanceBook();
        String name = "플린트";
        attendanceBook.register(name);
        List<Attend> attends = createAttendUntilToday();
        for (Attend attend : attends) {
            attendanceBook.addAttend(name, attend);
        }
        attendanceBook.edit(name, createAttendDateAndTime(12, 10, 0));
        attendanceBook.edit(name, createAttendDateAndTime(11, 10, 0));

        //when
        WarningStatus actual = attendanceBook.judgeAttendStatus(name);

        //then
        assertThat(actual).isEqualTo(WarningStatus.WARNING);
    }

    @Test
    @DisplayName("대상 닉네임의 출석 위험을 판정한다 - 위험 없음")
    void judgeAttendStatusPASS() {
        //given
        AttendanceBook attendanceBook = new AttendanceBook();
        String name = "플린트";
        attendanceBook.register(name);
        List<Attend> attends = createPassAttend();
        for (Attend attend : attends) {
            attendanceBook.addAttend(name, attend);
        }

        //when
        WarningStatus actual = attendanceBook.judgeAttendStatus(name);

        //then
        assertThat(actual).isEqualTo(WarningStatus.PASS);
    }

    @Test
    @DisplayName("대상 닉네임의 출석 상태 횟수를 계산한다")
    void calculateAttendCount() {
        //given
        AttendanceBook attendanceBook = new AttendanceBook();
        String name = "플린트";
        attendanceBook.register(name);
        List<Attend> attends = createAttendUntilToday();
        for (Attend attend : attends) {
            attendanceBook.addAttend(name, attend);
        }

        //when
        AttendCount actual = attendanceBook.countAttend(name);

        //then
        AttendCount expected = new AttendCount(3, 3, 3);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("제적 위험 대상자를 찾는다")
    void searchWarningCrew() {
        //given
        AttendanceBook attendanceBook = new AttendanceBook();
        String name = "플린트";
        attendanceBook.register(name);
        List<Attend> attends = createPassAttend();
        for (Attend attend : attends) {
            attendanceBook.addAttend(name, attend);
        }

        String warningName = "가나다";
        attendanceBook.register(warningName);
        List<Attend> warningAttend = createAttendUntilToday();
        for (Attend attend : warningAttend) {
            attendanceBook.addAttend(warningName, attend);
        }

        //when
        List<WarningCrew> actual = attendanceBook.searchWarningCrew();

        //then
        List<WarningCrew> expected = List.of(
                new WarningCrew(warningName, new AttendCount(3, 3, 3), WarningStatus.INTERVIEW)
        );
        assertThat(actual).isEqualTo(expected);
    }
}
