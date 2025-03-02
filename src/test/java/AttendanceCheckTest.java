import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Attendance;
import domain.AttendanceBook;
import domain.Attendances;
import domain.Crew;
import domain.Day;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceCheckTest {
    final Day today = new Day(LocalDate.of(2025, 2, 26));

    private static Stream<Arguments> provideEachDayOfWeekAttendance() {
        return Stream.of(
                // 월요일
                Arguments.of(new Day(LocalDate.of(2025, 2, 24)), "에드", LocalTime.of(13, 0), false, false),
                Arguments.of(new Day(LocalDate.of(2025, 2, 24)), "제프", LocalTime.of(13, 6), true, false),
                //수요일
                Arguments.of(new Day(LocalDate.of(2025, 2, 25)), "율무", LocalTime.of(13, 0), false, true));
    }

    private static Stream<Arguments> provideDifferentHoursOfAttendance() {
        return Stream.of(
                // 출석
                Arguments.of("에드", LocalTime.of(10, 4), false, false),
                // 지각
                Arguments.of("제프", LocalTime.of(10, 6), true, false),
                Arguments.of("율무", LocalTime.of(10, 30), true, false),
                // 결석
                Arguments.of("링크", LocalTime.of(13, 31), false, true));
    }

    @Test
    void 닉네임과_등교_시간을_입력하면_출석할_수_있다() {
        Crew crew = new Crew("에드");
        LocalTime attendanceTime = LocalTime.of(9, 59);

        Attendance attendance = new Attendance(today, attendanceTime);
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.recordAttendance(crew, attendance);
        Attendances attendances = attendanceBook.getAttendances(crew);

        assertThatCode(() -> attendances.findByDay(today))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("provideDifferentHoursOfAttendance")
    void 출석_시간에_따라_출석_상태를_다르게_처리한다(String nickname, LocalTime attendanceTime, Boolean isLateExpected,
                                   Boolean isAbsentExpected) {
        Attendance attendance = new Attendance(today, attendanceTime);
        AttendanceBook attendanceBook = new AttendanceBook();
        Crew crew = new Crew(nickname);
        attendanceBook.recordAttendance(crew, attendance);

        Attendances attendances = attendanceBook.getAttendances(crew);
        final var attendanceRecord = attendances.findByDay(today);
        final var attendanceTimeRecord = attendanceRecord.getTime();
        final var isLate = attendanceRecord.isLate();
        final var isAbsent = attendanceRecord.isAbsent();

        assertThat(attendanceTimeRecord).isEqualTo(attendanceTime);
        assertThat(isLate).isEqualTo(isLateExpected);
        assertThat(isAbsent).isEqualTo(isAbsentExpected);

    }

    @Test
    void 이미_출석한_상태에서_출석을_시도하면_예외처리() {
        final var crew = new Crew("에드");
        final var attendanceTime = LocalTime.of(9, 59);
        Attendance attendance = new Attendance(today, attendanceTime);
        AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.recordAttendance(crew, attendance);

        assertThatThrownBy(() -> attendanceBook.recordAttendance(crew, new Attendance(today, attendanceTime)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("[ERROR] 이미 출석이 완료되었습니다. 수정 기능을 이용하세요.");
    }


    @ParameterizedTest
    @MethodSource("provideEachDayOfWeekAttendance")
    void 요일에_따라_다른_시작_시간을_적용한다(Day today, String nickname, LocalTime attendanceTime, Boolean isLateExpected,
                               Boolean isAbsentExpected) {
        Attendance attendance = new Attendance(today, attendanceTime);
        AttendanceBook attendanceBook = new AttendanceBook();
        Crew crew = new Crew(nickname);
        attendanceBook.recordAttendance(crew, attendance);

        final var attendances = attendanceBook.getAttendances(crew);
        final var attendanceRecord = attendances.findByDay(today);

        final var isLate = attendanceRecord.isLate();
        final var isAbsent = attendanceRecord.isAbsent();

        assertThat(isLate).isEqualTo(isLateExpected);
        assertThat(isAbsent).isEqualTo(isAbsentExpected);
    }

    @ParameterizedTest
    @CsvSource({
            "2025-01-01", // 설날
            "2025-02-22", // 토요일
            "2025-02-23" // 일요일
    })
    void 주말_및_공휴일에는_출석을_받지_않는다(String dateString) {
        // 일요일
        assertThatThrownBy(() -> new Day(LocalDate.parse(dateString)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @ParameterizedTest
    @CsvSource({
            "07:59",
            "23:01"
    })
    void 운영_시간을_벗어난_출석은_받지_않는다(String timeString) {
        final var attendanceTime = LocalTime.parse(timeString);

        assertThatThrownBy(() -> new Attendance(today, attendanceTime))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageStartingWith("[ERROR]");
    }

}
