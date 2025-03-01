package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTimeTest {

    @Test
    @DisplayName("일과 시간으로 AttendanceTime을 생성한다.")
    void createAttendanceTimeTest() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 10);
        LocalTime time = LocalTime.of(9, 30);

        // when, then
        assertThatCode(() -> AttendanceTime.of(date, time))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("주말 출석 시간 생성 시 예외 발생")
    void givenWeekendThrowException() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 8);
        LocalTime time = LocalTime.of(9, 30);

        // when, then
        assertThatThrownBy(() -> AttendanceTime.of(date, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주말에는 출석할 수 없습니다.");
    }

    @Test
    @DisplayName("공휴일 출석 시간 생성 시 예외 발생")
    void givenHolidayThrowException() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 25);
        LocalTime time = LocalTime.of(9, 30);

        // when, then
        assertThatThrownBy(() -> AttendanceTime.of(date, time))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("공휴일에는 출석할 수 없습니다.");
    }

    @Test
    @DisplayName("isSameDate(AttendanceTime time)에서 날짜가 같으면 true반환")
    void givenAttendanceTimeIsSameDateTest() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 10);
        AttendanceTime attendanceTime = AttendanceTime.of(
                date, LocalTime.of(10, 5)
        );

        // when
        boolean b1 = attendanceTime.isSameDate(AttendanceTime.of(
                LocalDate.of(2024, 12, 10),
                null)
        );
        boolean b2 = attendanceTime.isSameDate(AttendanceTime.of(
                LocalDate.of(2024, 12, 11),
                null)
        );

        // then
        assertThat(b1).isTrue();
        assertThat(b2).isFalse();
    }

    @Test
    @DisplayName("isSameDate(LocalDate date)에서 날짜가 같으면 true반환")
    void givenLocalDateIsSameDateTest() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 10);
        AttendanceTime attendanceTime = AttendanceTime.of(
                date, LocalTime.of(10, 5)
        );

        // when
        boolean b1 = attendanceTime.isSameDate(
                LocalDate.of(2024, 12, 10)
        );
        boolean b2 = attendanceTime.isSameDate(
                LocalDate.of(2024, 12, 11)
        );

        // then
        assertThat(b1).isTrue();
        assertThat(b2).isFalse();
    }

    @Test
    @DisplayName("날짜와 시간이 같으면 같은 객체로 판단")
    void equalsAttendanceTimeTest() {
        // given
        AttendanceTime time = AttendanceTime.of(
                LocalDate.of(2024, 12, 10),
                LocalTime.of(9, 30));

        AttendanceTime compared = AttendanceTime.of(
                LocalDate.of(2024, 12, 10),
                LocalTime.of(9, 30));

        // when
        boolean isEqual = time.equals(compared);

        // then
        assertThat(isEqual).isTrue();
    }

    @Test
    @DisplayName("날짜와 시간이 같다면 HashSet에서 중복 제거 확인")
    void hashSetDistinctAttendanceTimeTest() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 10);
        AttendanceTime time = AttendanceTime.of(date, LocalTime.of(10, 30));
        AttendanceTime other = AttendanceTime.of(date, LocalTime.of(10, 30));

        // when
        Set<AttendanceTime> times = new HashSet<>();
        times.add(time);
        times.add(other);

        // then
        assertThat(times).hasSize(1);
    }

    @Test
    @DisplayName("날짜 수정 테스트")
    void modifyAttendanceTimeTest() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 10);
        LocalTime time = LocalTime.of(9, 30);
        AttendanceTime attendanceTime = AttendanceTime.of(date, time);

        // when
        AttendanceTime previous = attendanceTime.modify(
                AttendanceTime.of(date, LocalTime.of(10, 20))
        );

        // then
        AttendanceTime expected = AttendanceTime.of(
                date, LocalTime.of(10, 20)
        );
        assertThat(attendanceTime).isEqualTo(expected);
        assertThat(previous).isEqualTo(AttendanceTime.of(date, time));
    }

    @Test
    @DisplayName("AttendanceTime에서 LocalDate 반환")
    void AttendanceTimeToLocalDateTest() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 10);
        LocalTime time = LocalTime.of(9, 30);
        AttendanceTime attendanceTime = AttendanceTime.of(date, time);

        // when
        LocalDate converted = attendanceTime.toLocalDate();

        // then
        LocalDate expected = LocalDate.of(2024, 12, 10);
        assertThat(converted).isEqualTo(expected);
    }

    @Test
    @DisplayName("AttendanceTime에서 LocalTime 반환")
    void AttendanceTimeToLocalTimeTest() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 10);
        LocalTime time = LocalTime.of(9, 30);
        AttendanceTime attendanceTime = AttendanceTime.of(date, time);

        // when
        LocalTime converted = attendanceTime.toLocalTime();

        // then
        LocalTime expected = LocalTime.of(9, 30);
        assertThat(converted).isEqualTo(expected);
    }

    @Test
    @DisplayName("해당 시간이 지각인지 판단")
    void isAttendanceTimeLate() {
        // given
        AttendanceTime notLateTime = AttendanceTime.of(
                LocalDate.of(2024, 12, 3),
                LocalTime.of(10, 5)
        );
        AttendanceTime lateTime = AttendanceTime.of(
                LocalDate.of(2024, 12, 3),
                LocalTime.of(10, 6)
        );

        // when
        boolean b1 = notLateTime.isLate();
        boolean b2 = lateTime.isLate();

        // then
        assertThat(b1).isFalse();
        assertThat(b2).isTrue();
    }

    @Test
    @DisplayName("해당 시간이 결석인지 판단")
    void isAttendanceTimeAbsence() {
        // given
        AttendanceTime notAbsenceTime = AttendanceTime.of(
                LocalDate.of(2024, 12, 3),
                LocalTime.of(10, 30)
        );
        AttendanceTime absenceTime = AttendanceTime.of(
                LocalDate.of(2024, 12, 3),
                LocalTime.of(10, 31)
        );

        // when
        boolean b1 = notAbsenceTime.isAbsence();
        boolean b2 = absenceTime.isAbsence();

        // then
        assertThat(b1).isFalse();
        assertThat(b2).isTrue();
    }

    @Test
    @DisplayName("날짜를 입력받아 필드의 날짜가 이전인지 판단")
    void isBeforeTest() {
        // given
        AttendanceTime time1 = AttendanceTime.of(
                LocalDate.of(2024, 12, 3),
                LocalTime.of(10, 30)
        );
        AttendanceTime time2 = AttendanceTime.of(
                LocalDate.of(2024, 12, 2),
                LocalTime.of(10, 30)
        );
        AttendanceTime time3 = AttendanceTime.of(
                LocalDate.of(2024, 12, 4),
                LocalTime.of(10, 30)
        );
        LocalDate today = LocalDate.of(2024, 12, 3);

        // when
        boolean b1 = time1.isBefore(today);
        boolean b2 = time2.isBefore(today);
        boolean b3 = time3.isBefore(today);

        // then
        assertThat(b1).isFalse();
        assertThat(b2).isTrue();
        assertThat(b3).isFalse();
    }

    @Test
    @DisplayName("AttendanceTime 객체에 대한 대소 비교")
    void compareAttendanceTimeTest() {
        // given
        AttendanceTime big = AttendanceTime.of(
                LocalDate.of(2024, 12, 3),
                LocalTime.of(10, 0)
        );
        AttendanceTime small = AttendanceTime.of(
                LocalDate.of(2024, 12, 2),
                LocalTime.of(9, 0)
        );
        AttendanceTime small2 = AttendanceTime.of(
                LocalDate.of(2024, 12, 2),
                LocalTime.of(9, 0)
        );

        // when
        int positive = big.compareTo(small);
        int negative = small.compareTo(big);
        int zero = small.compareTo(small2);

        // then
        assertThat(positive).isGreaterThan(0);
        assertThat(negative).isLessThan(0);
        assertThat(zero).isEqualTo(0);
    }
}
