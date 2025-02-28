import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import attendance.model.AttendanceBook;
import attendance.model.AttendanceStatus;
import attendance.model.AttendanceTime;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceBookTest {

    @Test
    void 입력_받은_이름과_날짜로_출석_기록을_추가한다() {

        // given
        final AttendanceTime attendanceTime = new AttendanceTime(LocalDate.of(2025, 2, 27), 10, 5);

        // when
        final AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.add("이름", attendanceTime);

        // then
        final AttendanceTime result = attendanceBook.getAttendance("이름", LocalDate.of(2025, 2, 27));
        assertThat(result).isEqualTo(attendanceTime);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "이름1, true",
            "이름2, false"
    })
    void 입력_받은_이름이_크루_목록에_존재하는지_판단한다(final String name, final boolean expectedResult) {

        // given
        final AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.add("이름1", new AttendanceTime(LocalDate.of(2025, 2, 27), 10, 10));

        // when
        final boolean result = attendanceBook.isCrewExists(name);

        // then
        Assertions.assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "27,true",
            "28,false"
    })
    void 입력_받은_이름과_날짜에_해당하는_출석_기록이_존재하는지_판단한다(final int date, final boolean expectedResult) {

        // given
        final AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.add("이름", new AttendanceTime(LocalDate.of(2025, 2, 27), 10, 10));

        // when
        final boolean result = attendanceBook.isAlreadyExists("이름", LocalDate.of(2025, 2, date));

        // then
        Assertions.assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void 입력_받은_이름에_대한_출석_기록을_모두_가져온다() {

        // given
        final AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.add("이름1", new AttendanceTime(LocalDate.of(2025, 2, 24), 10, 10));
        attendanceBook.add("이름2", new AttendanceTime(LocalDate.of(2025, 2, 25), 10, 20));
        attendanceBook.add("이름1", new AttendanceTime(LocalDate.of(2025, 2, 26), 10, 30));

        // when
        final List<AttendanceTime> attendances = attendanceBook.getAttendancesByName("이름1");

        // then
        Assertions.assertThat(attendances.size()).isEqualTo(2);
    }

    @Test
    void 출결_기록이_없는_날짜에_대해_결석_기록을_추가한다() {

        // given
        final AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.add("이름1", new AttendanceTime(LocalDate.of(2025, 2, 24), 10, 10));

        // when
        attendanceBook.initCrewsAbsence();

        // then
        Assertions.assertThat(attendanceBook.getAttendancesByName("이름1").size())
                .isEqualTo(LocalDate.now().getDayOfMonth() - getWeekendCount());
    }

    @ParameterizedTest
    @MethodSource("nameAndAttendanceStatus")
    void 입력_받은_크루의_출석_상태에_대한_횟수를_계산한다(final String name, final AttendanceStatus attendanceStatus,
                                      final int expectedResult) {

        // given
        final AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.add("이름1", new AttendanceTime(LocalDate.of(2025, 2, 25), 10, 5));
        attendanceBook.add("이름2", new AttendanceTime(LocalDate.of(2025, 2, 25), 10, 6));
        attendanceBook.add("이름2", new AttendanceTime(LocalDate.of(2025, 2, 26), 10, 30));
        attendanceBook.add("이름3", new AttendanceTime(LocalDate.of(2025, 2, 25), 10, 31));
        attendanceBook.add("이름3", new AttendanceTime(LocalDate.of(2025, 2, 26), 10, 31));
        attendanceBook.add("이름3", new AttendanceTime(LocalDate.of(2025, 2, 27), 10, 31));
        attendanceBook.add("이름3", new AttendanceTime(LocalDate.of(2025, 2, 28)));

        // when
        final int result = attendanceBook.getAttendanceStatusCount(name, attendanceStatus);

        // then
        Assertions.assertThat(result).isEqualTo(expectedResult);
    }

    public static Stream<Arguments> nameAndAttendanceStatus() {

        return Stream.of(
                Arguments.of("이름1", AttendanceStatus.ATTEND, 1),
                Arguments.of("이름2", AttendanceStatus.LATE, 2),
                Arguments.of("이름3", AttendanceStatus.ABSENT, 4)
        );
    }

    private int getWeekendCount() {

        int count = 0;
        LocalDate now = LocalDate.now();
        for (int day = 1; day <= now.getDayOfMonth(); day++) {
            LocalDate localDate = LocalDate.of(now.getYear(), now.getMonthValue(), day);
            if (AttendanceTime.isWeekend(localDate.getDayOfWeek())) {
                count++;
            }
        }
        return count;
    }
}
