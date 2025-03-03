package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("교육 시간 테스트")
class EducationTimeTest {

    @ParameterizedTest(name = "{index} : {2}")
    @MethodSource("getAttendTimeByDayOfWeek")
    void 요일별_시간이_출석시간_사이라면_true를_반환한다(DayOfWeek inputDayOfWeek, LocalTime inputTime, String message) {
        assertThat(EducationTime.isBetweenAttendTime(inputDayOfWeek, inputTime)).isTrue();
    }

    static Stream<Arguments> getAttendTimeByDayOfWeek() {
        return Stream.of(
                Arguments.of(DayOfWeek.MONDAY, CampusOperatingTime.OPEN.getTime(), "월요일 캠퍼스 운영 시작 시간"),
                Arguments.of(DayOfWeek.MONDAY, EducationTime.MONDAY_ATTEND.getTime().minusNanos(1), "월요일 출석 시작 시간 1 나노초 전"),
                Arguments.of(DayOfWeek.MONDAY, EducationTime.MONDAY_ATTEND.getTime(), "월요일 출석 시작 시간"),
                Arguments.of(DayOfWeek.MONDAY, EducationTime.MONDAY_LATE.getTime(), "월요일 출석 종료 시간"),
                Arguments.of(DayOfWeek.WEDNESDAY, CampusOperatingTime.OPEN.getTime(), "수요일 캠퍼스 운영 시작 시간"),
                Arguments.of(DayOfWeek.WEDNESDAY, EducationTime.GENERAL_ATTEND.getTime().minusNanos(1), "수요일 출석 시작 시간 1 나노초 전"),
                Arguments.of(DayOfWeek.WEDNESDAY, EducationTime.GENERAL_ATTEND.getTime(), "수요일 출석 시작 시간"),
                Arguments.of(DayOfWeek.WEDNESDAY, EducationTime.GENERAL_LATE.getTime(), "수요일 출석 종료 시간")
        );
    }

    @ParameterizedTest(name = "{index} : {2}")
    @MethodSource("getNotAttendTimeByDayOfWeek")
    void 요일별_시간이_출석시간_사이가_아니라면_false를_반환한다(DayOfWeek inputDayOfWeek, LocalTime inputTime, String message) {
        assertThat(EducationTime.isBetweenAttendTime(inputDayOfWeek, inputTime)).isFalse();
    }

    static Stream<Arguments> getNotAttendTimeByDayOfWeek() {
        return Stream.of(
                Arguments.of(DayOfWeek.MONDAY, CampusOperatingTime.OPEN.getTime().minusNanos(1), "월요일 캠퍼스 운영 시작 시간 1 나노초 전"),
                Arguments.of(DayOfWeek.MONDAY, EducationTime.MONDAY_LATE.getTime().plusNanos(1), "월요일 출석 종료 시간 1 나노초 후"),
                Arguments.of(DayOfWeek.WEDNESDAY, CampusOperatingTime.OPEN.getTime().minusNanos(1), "수요일 캠퍼스 운영 시작 시간 1 나노초 전"),
                Arguments.of(DayOfWeek.WEDNESDAY, EducationTime.GENERAL_LATE.getTime().plusNanos(1), "수요일 출석 종료 시간 1 나노초 후")
        );
    }

    @ParameterizedTest(name = "{index} : {2}")
    @MethodSource("getLateTimeByDayOfWeek")
    void 요일별_시간이_지각시간_사이라면_true를_반환한다(DayOfWeek inputDayOfWeek, LocalTime inputTime, String message) {
        assertThat(EducationTime.isBetweenLateTime(inputDayOfWeek, inputTime)).isTrue();
    }

    static Stream<Arguments> getLateTimeByDayOfWeek() {
        return Stream.of(
                Arguments.of(DayOfWeek.MONDAY, EducationTime.MONDAY_LATE.getTime().plusNanos(1), "월요일 지각 시작 시간"),
                Arguments.of(DayOfWeek.MONDAY, EducationTime.MONDAY_ABSENT.getTime(), "월요일 지각 종료 시간"),
                Arguments.of(DayOfWeek.TUESDAY, EducationTime.GENERAL_LATE.getTime().plusNanos(1), "화요일 지각 시작 시간"),
                Arguments.of(DayOfWeek.TUESDAY, EducationTime.GENERAL_ABSENT.getTime(), "화요일 지각 종료 시간")
        );
    }

    @ParameterizedTest(name = "{index} : {2}")
    @MethodSource("getNotLateTimeByDayOfWeek")
    void 요일별_시간이_지각시간_사이가_아니라면_false를_반환한다(DayOfWeek inputDayOfWeek, LocalTime inputTime, String message) {
        assertThat(EducationTime.isBetweenLateTime(inputDayOfWeek, inputTime)).isFalse();
    }

    static Stream<Arguments> getNotLateTimeByDayOfWeek() {
        return Stream.of(
                Arguments.of(DayOfWeek.MONDAY, EducationTime.MONDAY_LATE.getTime().minusNanos(1), "월요일 지각 시작 시간 1 나노초 전"),
                Arguments.of(DayOfWeek.MONDAY, EducationTime.MONDAY_ABSENT.getTime().plusNanos(1), "월요일 지각 종료 시간 1 나노초 후"),
                Arguments.of(DayOfWeek.TUESDAY, EducationTime.GENERAL_LATE.getTime().minusNanos(1), "화요일 지각 시작 시간 1 나노초 전"),
                Arguments.of(DayOfWeek.TUESDAY, EducationTime.GENERAL_ABSENT.getTime().plusNanos(1), "화요일 지각 종료 시간 1 나노초 후")
        );
    }
}
