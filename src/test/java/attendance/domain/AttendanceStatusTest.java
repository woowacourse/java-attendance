package attendance.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AttendanceStatusTest {

    @ParameterizedTest
    @MethodSource("provideMondayPresenceTimes")
    void 월요일_출석_상태를_찾는다(LocalTime attendanceTime) {
        // given
        LocalDate monday = LocalDate.of(2024, 12, 2);

        // when
        AttendanceStatus attendanceStatus = AttendanceStatus.findAttendanceStatus(monday, attendanceTime);

        // then
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.PRESENCE);
    }

    private Stream<LocalTime> provideMondayPresenceTimes() {
        return Stream.of(
            LocalTime.of(8,0),
            LocalTime.of(13,5)
        );
    }

    @ParameterizedTest
    @MethodSource("provideMondayLateTimes")
    void 월요일_지각상태를_찾는다(LocalTime attendanceTime) {
        // given
        LocalDate monday = LocalDate.of(2024, 12, 2);

        // when
        AttendanceStatus attendanceStatus = AttendanceStatus.findAttendanceStatus(monday, attendanceTime);

        // then
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.LATE);
    }

    private Stream<LocalTime> provideMondayLateTimes() {
        return Stream.of(
            LocalTime.of(13,5).plusSeconds(1),
            LocalTime.of(13,30)
        );
    }

    @ParameterizedTest
    @MethodSource("provideMondayAbsenceTimes")
    void 월요일_결석상태를_찾는다(LocalTime attendanceTime) {
        // given
        LocalDate monday = LocalDate.of(2024, 12, 2);

        // when
        AttendanceStatus attendanceStatus = AttendanceStatus.findAttendanceStatus(monday, attendanceTime);

        // then
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ABSENCE);
    }

    private Stream<LocalTime> provideMondayAbsenceTimes() {
        return Stream.of(
            LocalTime.of(13,30).plusSeconds(1),
            LocalTime.of(22,59)
        );
    }

    @ParameterizedTest
    @MethodSource("provideWeekdayPresenceTime")
    void 월요일_아닌_날_출석상태를_찾는다(LocalTime attendanceTime) {
        // given
        LocalDate tuesday = LocalDate.of(2024, 12, 3);

        // when
        AttendanceStatus attendanceStatus = AttendanceStatus.findAttendanceStatus(tuesday, attendanceTime);

        // then
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.PRESENCE);
    }

    private Stream<LocalTime> provideWeekdayPresenceTime() {
        return Stream.of(
            LocalTime.of(8,0),
            LocalTime.of(10,5)
        );
    }

    @ParameterizedTest
    @MethodSource("provideWeekdayLateTime")
    void 월요일_아닌_날_지각상태를_찾는다(LocalTime attendanceTime) {
        // given
        LocalDate friday = LocalDate.of(2024, 12, 6);

        // when
        AttendanceStatus attendanceStatus = AttendanceStatus.findAttendanceStatus(friday, attendanceTime);

        // then
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.LATE);
    }

    private Stream<LocalTime> provideWeekdayLateTime() {
        return Stream.of(
            LocalTime.of(10,5).plusSeconds(1),
            LocalTime.of(10,30)
        );
    }

    @ParameterizedTest
    @MethodSource("provideWeekdayAbsenceTime")
    void 월요일_아닌_날_결석상태를_찾는다(LocalTime attendanceTime) {
        // given
        LocalDate friday = LocalDate.of(2024, 12, 6);

        // when
        AttendanceStatus attendanceStatus = AttendanceStatus.findAttendanceStatus(friday, attendanceTime);

        // then
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ABSENCE);
    }

    private Stream<LocalTime> provideWeekdayAbsenceTime() {
        return Stream.of(
            LocalTime.of(10,30).plusSeconds(1),
            LocalTime.of(22, 59)
        );
    }

    @Test
    void 초기맵을_생성한다() {
        // when
        Map<AttendanceStatus, Integer> attendanceStatusCounts = AttendanceStatus.initMap();

        // then
        assertThat(attendanceStatusCounts).isEqualTo(Map.of(
            AttendanceStatus.LATE, 0,
            AttendanceStatus.ABSENCE, 0,
            AttendanceStatus.PRESENCE, 0));
    }
}
