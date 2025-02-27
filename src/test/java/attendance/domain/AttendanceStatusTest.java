package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("출석 상태 테스트")
public class AttendanceStatusTest {

    @ParameterizedTest(name = "{index} : {2}")
    @MethodSource("getAttendDateAndTime")
    void 날짜와_시간에따라_출석인_상태를_반환한다(LocalDate inputDate, LocalTime inputTime, String message) {
        assertThat(AttendanceStatus.determine(inputDate, inputTime)).isEqualTo(AttendanceStatus.ATTEND);
    }

    static Stream<Arguments> getAttendDateAndTime() {
        LocalDate monday = LocalDate.of(2024, 12, 9);
        LocalDate tuesday = LocalDate.of(2024, 12, 10);

        return Stream.of(
                Arguments.of(monday, EducationTime.MONDAY_ATTEND.getTime().minusNanos(1), "월요일 출석 시작 시간 1 나노초 전"),
                Arguments.of(monday, EducationTime.MONDAY_ATTEND.getTime(), "월요일 출석 시작 시간"),
                Arguments.of(monday, EducationTime.MONDAY_LATE.getTime(), "월요일 출석 종료 시간"),
                Arguments.of(tuesday, EducationTime.GENERAL_ATTEND.getTime().minusNanos(1), "화요일 출석 시작 시간 1 나노초 전"),
                Arguments.of(tuesday, EducationTime.GENERAL_ATTEND.getTime(), "화요일 출석 종료 시간"),
                Arguments.of(tuesday, EducationTime.GENERAL_LATE.getTime(), "화요일 출석 종료 시간")
        );
    }

    @ParameterizedTest(name = "{index} : {2}")
    @MethodSource("getLateDateAndTime")
    void 날짜와_시간에따라_지각인_상태를_반환한다(LocalDate inputDate, LocalTime inputTime, String message) {
        assertThat(AttendanceStatus.determine(inputDate, inputTime)).isEqualTo(AttendanceStatus.LATE);
    }

    static Stream<Arguments> getLateDateAndTime() {
        LocalDate monday = LocalDate.of(2024, 12, 9);
        LocalDate wednesday = LocalDate.of(2024, 12, 11);

        return Stream.of(
                Arguments.of(monday, EducationTime.MONDAY_LATE.getTime().plusNanos(1), "월요일 지각 시작 시간"),
                Arguments.of(monday, EducationTime.MONDAY_ABSENT.getTime(), "월요일 지각 종료 시간"),
                Arguments.of(wednesday, EducationTime.GENERAL_LATE.getTime().plusNanos(1), "수요일 지각 시 시간"),
                Arguments.of(wednesday, EducationTime.GENERAL_ABSENT.getTime(), "수요일 지각 종료 시간")
        );
    }

    @ParameterizedTest(name = "{index} : {2}")
    @MethodSource("getAbsentDateAndTime")
    void 날짜와_시간에따라_결석인_상태를_반환한다(LocalDate inputDate, LocalTime inputTime, String message) {
        assertThat(AttendanceStatus.determine(inputDate, inputTime)).isEqualTo(AttendanceStatus.ABSENT);
    }

    static Stream<Arguments> getAbsentDateAndTime() {
        LocalDate monday = LocalDate.of(2024, 12, 9);
        LocalDate friday = LocalDate.of(2024, 12, 13);

        return Stream.of(
                Arguments.of(monday, EducationTime.MONDAY_ABSENT.getTime().plusNanos(1), "월요일 결석 시작 시간"),
                Arguments.of(friday, EducationTime.GENERAL_ABSENT.getTime().plusNanos(1), "금요일 지각 시 시간")
        );
    }
}
