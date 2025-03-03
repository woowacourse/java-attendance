package attendance.domain;

import java.time.LocalDate;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceHistoryTest {

    @Test
    void 입력_받은_시간으로_출석_기록을_추가한다() {

        // given®
        final AttendanceHistory attendanceHistory = new AttendanceHistory();

        // when
        attendanceHistory.add(new AttendanceTime(LocalDate.of(2025, 2, 27), 10, 10));

        // then
        Assertions.assertThat(attendanceHistory.getHistory().size()).isEqualTo(1);
    }

    @Test
    void 입력_받은_날짜에_대한_출석_기록을_가져온다() {

        // given
        final AttendanceHistory attendanceHistory = new AttendanceHistory();
        attendanceHistory.add(new AttendanceTime(LocalDate.of(2025, 2, 27), 10, 20));

        // when
        final AttendanceTime attendanceTime = attendanceHistory.getAttendanceTime(LocalDate.of(2025, 2, 27));

        // then
        org.junit.jupiter.api.Assertions.assertAll(() -> {
            org.junit.jupiter.api.Assertions.assertEquals(attendanceTime.getDate(), LocalDate.of(2025, 2, 27));
            org.junit.jupiter.api.Assertions.assertEquals(attendanceTime.getHour(), 10);
            org.junit.jupiter.api.Assertions.assertEquals(attendanceTime.getMinute(), 20);
        });
    }

    @ParameterizedTest
    @MethodSource("dateAndResult")
    void 입력_받은_날짜에_대한_출석_기록이_이미_존재하는지_판단한다(final LocalDate date, final boolean expectedResult) {

        // given
        final AttendanceHistory attendanceHistory = new AttendanceHistory();
        attendanceHistory.add(new AttendanceTime(LocalDate.of(2025, 3, 3), 10, 20));

        // when
        final boolean result = attendanceHistory.isAlreadyExists(date);

        // then
        Assertions.assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("attendanceStatusAndResult")
    void 입력_받은_출석_상태에_대한_기록_횟수를_반환한다(final AttendanceStatus attendanceStatus, final int expectedResult) {

        // given
        final AttendanceHistory attendanceHistory = new AttendanceHistory();
        attendanceHistory.add(new AttendanceTime(LocalDate.of(2025, 2, 25), 10, 5));
        attendanceHistory.add(new AttendanceTime(LocalDate.of(2025, 2, 26), 10, 6));
        attendanceHistory.add(new AttendanceTime(LocalDate.of(2025, 2, 27), 10, 30));
        attendanceHistory.add(new AttendanceTime(LocalDate.of(2025, 2, 28), 10, 31));
        attendanceHistory.add(new AttendanceTime(LocalDate.of(2025, 3, 3), 13, 31));
        attendanceHistory.add(new AttendanceTime(LocalDate.of(2025, 3, 4), 10, 31));

        // when
        final int result = attendanceHistory.getAttendanceStatusCount(attendanceStatus);

        // then
        Assertions.assertThat(result).isEqualTo(expectedResult);
    }

    public static Stream<Arguments> dateAndResult() {

        return Stream.of(
                Arguments.of(LocalDate.of(2025, 3, 3), true),
                Arguments.of(LocalDate.of(2025, 3, 4), false)
        );
    }

    public static Stream<Arguments> attendanceStatusAndResult() {

        return Stream.of(
                Arguments.of(AttendanceStatus.ATTEND, 1),
                Arguments.of(AttendanceStatus.LATE, 2),
                Arguments.of(AttendanceStatus.ABSENT, 3)
        );
    }
}
