package model;

import static constant.ErrorMessage.CANNOT_CHECK_IN_ON_WEEKEND;
import static constant.PathConstant.ATTENDANCE_FILE_PATH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.DateTimeGenerator;
import util.FileParser;
import util.FixedDateTimeStrategy;

class AttendanceTypeTest {

    LocalDate fixedDate;
    DateTimeGenerator dateTimeGenerator;
    Attendances attendances;

    @BeforeEach
    void beforeEach() {
        fixedDate = LocalDate.of(2024, 12, 13);
        FixedDateTimeStrategy fixedDateTimeStrategy = new FixedDateTimeStrategy(fixedDate);
        dateTimeGenerator = new DateTimeGenerator(fixedDateTimeStrategy);

        List<String> lines = FileParser.readLines(ATTENDANCE_FILE_PATH.getPath());
        attendances = Attendances.from(lines, dateTimeGenerator);
    }

    @Test
    @DisplayName("금요일 출석 타입을 반환한다.")
    void test1() {
        // given
        LocalDate localDate = LocalDate.of(2025, 2, 28);
        LocalTime localTime = LocalTime.of(9, 30);

        // when
        AttendanceType result = AttendanceType.find(localDate, localTime);

        // then
        assertThat(result).isEqualTo(AttendanceType.SUCCESS);
    }

    @Test
    @DisplayName("금요일 지각 타입을 반환한다.")
    void test2() {
        // given
        LocalDate localDate = LocalDate.of(2025, 2, 28);
        LocalTime localTime = LocalTime.of(10, 6);

        // when
        AttendanceType result = AttendanceType.find(localDate, localTime);

        // then
        assertThat(result).isEqualTo(AttendanceType.BE_LATE);
    }

    @Test
    @DisplayName("금요일 결석 타입을 반환한다.")
    void test3() {
        // given
        LocalDate localDate = LocalDate.of(2025, 2, 28);
        LocalTime localTime = LocalTime.of(10, 31);

        // when
        AttendanceType result = AttendanceType.find(localDate, localTime);

        // then
        assertThat(result).isEqualTo(AttendanceType.ABSENCE);
    }

    @Test
    @DisplayName("월요일 출석 타입을 반환한다.")
    void test4() {
        // given
        LocalDate localDate = LocalDate.of(2025, 2, 24);
        LocalTime localTime = LocalTime.of(12, 30);

        // when
        AttendanceType result = AttendanceType.find(localDate, localTime);

        // then
        assertThat(result).isEqualTo(AttendanceType.SUCCESS);
    }

    @Test
    @DisplayName("월요일 지각 타입을 반환한다.")
    void test5() {
        // given
        LocalDate localDate = LocalDate.of(2025, 2, 24);
        LocalTime localTime = LocalTime.of(13, 6);

        // when
        AttendanceType result = AttendanceType.find(localDate, localTime);

        // then
        assertThat(result).isEqualTo(AttendanceType.BE_LATE);
    }

    @Test
    @DisplayName("월요일 결석 타입을 반환한다.")
    void test6() {
        // given
        LocalDate localDate = LocalDate.of(2025, 2, 24);
        LocalTime localTime = LocalTime.of(13, 31);

        // when
        AttendanceType result = AttendanceType.find(localDate, localTime);

        // then
        assertThat(result).isEqualTo(AttendanceType.ABSENCE);
    }

    @Test
    @DisplayName("주말은 출석을 할 수 없다.")
    void test7() {
        // given
        LocalDate localDate = LocalDate.of(2025, 3, 1);
        LocalTime localTime = LocalTime.of(10, 0);

        // when & then
        assertThatThrownBy(() -> AttendanceType.find(localDate, localTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(CANNOT_CHECK_IN_ON_WEEKEND.getMessage());
    }

    @Test
    @DisplayName("징계 총합을 반환한다.")
    void test8() {
        // given
        Crew crew = Crew.of("미소");
        List<Attendance> attendances = this.attendances.getAttendancesByCrew(crew);

        // when
        EnumMap<AttendanceType, Integer> attendanceTotal = AttendanceType.calculateTotal(attendances);

        // then
        Assertions.assertAll(
                () -> assertThat(attendanceTotal.get(AttendanceType.SUCCESS)).isEqualTo(3),
                () -> assertThat(attendanceTotal.get(AttendanceType.BE_LATE)).isEqualTo(2),
                () -> assertThat(attendanceTotal.get(AttendanceType.ABSENCE)).isEqualTo(4)
        );
    }
}
