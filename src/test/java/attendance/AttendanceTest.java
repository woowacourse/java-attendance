package attendance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTest {

    private static final LocalDate DATE = LocalDate.of(2025, 02, 25);
    private static final LocalTime TIME = LocalTime.of(10, 00);

    @Test
    @DisplayName("닉네임과 등교 시간을 입력하면 출석할 수 있다")
    void attendanceTest() {
        // given
        Crew crew = new Crew("pobi");
        Crews crews = new Crews();
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

    @Test
    @DisplayName("운영시간이 아닌 시간에 등교를 시도할 경우 예외를 반환한다")
    void attendanceExceptionTest2() {
        // given
        Crew crew = new Crew("pobi");

        // when then
        assertThatThrownBy(() -> {
            crew.attendance(DATE, LocalTime.of(07, 59));
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("닉네임, 수정하려는 날짜, 등교 시간으로 출석 기록을 수정할 수 있다")
    void modifyAttendanceTest() {
        // given
        Crew crew = new Crew("pobi");
        crew.attendance(DATE, TIME);
        Crews crews = new Crews();
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
}
