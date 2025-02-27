package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CrewTest {

    private static final LocalDate DATE = LocalDate.of(2025, 02, 25);
    private static final LocalTime TIME = LocalTime.of(10, 00);

    @Test
    @DisplayName("닉네임과 등교 시간을 입력하면 출석할 수 있다")
    void attendanceTest() {
        // given
        Crew crew = new Crew("pobi");
        Crews crews = Crews.generate();
        crews.add(crew);

        // when then
        assertThatCode(() -> {
            Crew found = crews.get("pobi");
            found.attendance(DATE, TIME);
        }).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("이미 출석한 경우 다시 출석을 시도하면 예외를 반환한다")
    void attendanceExceptionTest() {
        // given
        Crew crew = new Crew("pobi");
        crew.attendance(DATE, TIME);

        // when then
        assertThatThrownBy(() -> {
            crew.attendance(DATE, TIME.plusMinutes(30));
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource({
        "07,59",
        "23,01",
    })
    @DisplayName("운영시간이 아닌 시간에 등교를 시도할 경우 예외를 반환한다")
    void attendanceExceptionTest2(int hour, int minute) {
        // given
        Crew crew = new Crew("pobi");

        // when then
        assertThatThrownBy(() -> {
            crew.attendance(DATE, LocalTime.of(hour, minute));
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource({
        "2025,02,22",
        "2025,02,23",
    })
    @DisplayName("운영시간이 아닌 시간에 등교를 시도할 경우 예외를 반환한다")
    void attendanceExceptionTest3(int year, int hour, int minute) {
        // given
        Crew crew = new Crew("pobi");

        // when then
        assertThatThrownBy(() -> {
            crew.attendance(LocalDate.of(year, hour, minute), TIME);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("닉네임, 수정하려는 날짜, 등교 시간으로 출석 기록을 수정할 수 있다")
    void modifyAttendanceTest() {
        // given
        Crew crew = new Crew("pobi");
        crew.attendance(DATE, TIME);
        Crews crews = Crews.generate();
        crews.add(crew);

        // when
        Crew found = crews.get("pobi");
        LocalTime time = TIME.plusMinutes(30);
        found.modifyAttendance(DATE, time);

        // then
        assertThat(found.getAttendanceTimeOf(DATE)).isEqualTo(time);
    }

    @Test
    @DisplayName("출석하지 않은 날짜를 수정 시도할 경우 예외를 반환한다")
    void modifyAttendanceExceptionTest() {
        // given
        Crew crew = new Crew("pobi");

        // when then
        assertThatThrownBy(() -> {
            crew.modifyAttendance(DATE, TIME.plusMinutes(30));
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("등교 기록이 없으면 결석을 반환한다")
    void fromAbsenceTest() {
        // given
        Crew crew = new Crew("pobi");

        // when then
        assertSoftly(softly -> {
            softly.assertThat(crew.getAttendanceStatusOf(DATE.plusDays(0))).isEqualTo(AttendanceStatus.ABSENCE);
            softly.assertThat(crew.getAttendanceStatusOf(DATE.plusDays(1))).isEqualTo(AttendanceStatus.ABSENCE);
            softly.assertThat(crew.getAttendanceStatusOf(DATE.plusDays(2))).isEqualTo(AttendanceStatus.ABSENCE);
        });
    }

    @Test
    @DisplayName("등교 기록이 없어도 주말 및 공휴일이면 결석을 반환하지 않는다")
    void fromAbsenceTest2() {
        // given
        Crew crew = new Crew("pobi");

        // when then
        assertSoftly(softly -> {
            softly.assertThat(crew.getAttendanceStatusOf(LocalDate.of(2025, 02, 22)))
                .isEqualTo(AttendanceStatus.DAY_OFF);
            softly.assertThat(crew.getAttendanceStatusOf(LocalDate.of(2025, 02, 23)))
                .isEqualTo(AttendanceStatus.DAY_OFF);
        });
    }

    @Test
    @DisplayName("전날까지의 크루 출석 기록을 바탕으로 제적 위험자를 파악한다")
    void getRiskTest() {
        // given
        Crew crew1 = new Crew("crew1");
        Crew crew2 = new Crew("crew2");
        Crew crew3 = new Crew("crew3");

        // when
        // crew1: 결석 2회 -> 경고
        crew1.attendance(LocalDate.of(2025, 02, 03), LocalTime.of(13, 00));
        crew1.attendance(LocalDate.of(2025, 02, 04), LocalTime.of(10, 00));
        crew1.attendance(LocalDate.of(2025, 02, 05), LocalTime.of(10, 00));
        crew1.attendance(LocalDate.of(2025, 02, 06), LocalTime.of(10, 00));

        // crew2: 결석 3회 -> 면담
        crew2.attendance(LocalDate.of(2025, 02, 03), LocalTime.of(13, 00));
        crew2.attendance(LocalDate.of(2025, 02, 04), LocalTime.of(10, 00));
        crew2.attendance(LocalDate.of(2025, 02, 05), LocalTime.of(10, 00));

        // crew3: 결석 6회 -> 제적

        // then
        assertSoftly(softly -> {
            softly.assertThat(crew1.getRisk(LocalDate.of(2025, 02, 11))).isEqualTo(Risk.WARNING);
            softly.assertThat(crew2.getRisk(LocalDate.of(2025, 02, 11))).isEqualTo(Risk.INTERVIEW);
            softly.assertThat(crew3.getRisk(LocalDate.of(2025, 02, 11))).isEqualTo(Risk.EXPULSION);
        });
    }
}
