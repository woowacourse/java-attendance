package attendance.domain;

import attendance.util.FormattedErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("출석 테스트")
public class AttendanceTest {

    @ParameterizedTest(name = "{index} : {1}")
    @MethodSource("getWeekend")
    void 주말에_출석을_하면_예외가_발생한다(LocalDate weekend, String message) {
        LocalTime attendTime = CampusOperatingTime.OPEN_AT.getTime();

        assertThatThrownBy(() -> new Attendance(weekend, attendTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(FormattedErrorMessage.INVALID_ATTEND_DATE_ERROR.getDateFormatMessage(weekend));

    }

    static Stream<Arguments> getWeekend() {
        return Stream.of(
                Arguments.of(LocalDate.of(2024, 12, 14), "2024년 12월 토요일"),
                Arguments.of(LocalDate.of(2024, 12, 14), "2024년 12월 일요일"),
                Arguments.of(LocalDate.of(2025, 1, 25), "2025년 1월 토요일"),
                Arguments.of(LocalDate.of(2025, 1, 26), "2025년 1월 일요일"),
                Arguments.of(LocalDate.of(2025, 2, 22), "2025년 2월 토요일"),
                Arguments.of(LocalDate.of(2025, 2, 23), "2025년 2월 일요일"),
                Arguments.of(LocalDate.of(2025, 8, 30), "2025년 8월 토요일"),
                Arguments.of(LocalDate.of(2025, 8, 31), "2025년 8월 일요일")
        );
    }

    @ParameterizedTest(name = "{index} : {1}")
    @MethodSource("getHoliday")
    void 공휴일에_출석을_하면_예외가_발생한다(LocalDate holiday, String message) {
        LocalTime attendTime = CampusOperatingTime.CLOSE_AT.getTime().minusMinutes(1);

        assertThatThrownBy(() -> new Attendance(holiday, attendTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(FormattedErrorMessage.INVALID_ATTEND_DATE_ERROR.getDateFormatMessage(holiday));
    }

    static Stream<Arguments> getHoliday() {
        return Stream.of(
                Arguments.of(LocalDate.of(2024, 12, 25), "2024년 크리스마스"),
                Arguments.of(LocalDate.of(2025, 1, 29), "2025년 설날"),
                Arguments.of(LocalDate.of(2025, 3, 3), "2025년 삼일절 대체공휴일"),
                Arguments.of(LocalDate.of(2025, 5, 5), "2025년 어린이날"),
                Arguments.of(LocalDate.of(2025, 6, 6), "2025년 현충일"),
                Arguments.of(LocalDate.of(2025, 10, 9), "2025년 한글날"),
                Arguments.of(LocalDate.of(2025, 12, 25), "2025년 크리스마스")
        );
    }

    @ParameterizedTest(name = "{index} : {1}")
    @MethodSource("getNotInOperatingTime")
    void 캠퍼스_운영시간_이외의_시간에_출석을_하면_예외가_발생한다(LocalTime notInOperatingTime, String message) {
        LocalDate attendDate = LocalDate.of(2024, 12, 13);

        assertThatThrownBy(() -> new Attendance(attendDate, notInOperatingTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(FormattedErrorMessage.INVALID_ATTEND_TIME_ERROR.getTimeFormatMessage(notInOperatingTime));
    }

    static Stream<Arguments> getNotInOperatingTime() {
        return Stream.of(
                Arguments.of(CampusOperatingTime.OPEN_AT.getTime().minusNanos(1), "운영 시작 시간 1 나노초 전"),
                Arguments.of(CampusOperatingTime.OPEN_AT.getTime().minusMinutes(1), "운영 시작 시간 1분 전"),
                Arguments.of(CampusOperatingTime.OPEN_AT.getTime().minusHours(1), "운영 시작 시간 1시간 전"),
                Arguments.of(CampusOperatingTime.CLOSE_AT.getTime(), "운영 종료 시간"),
                Arguments.of(CampusOperatingTime.CLOSE_AT.getTime().plusNanos(1), "운영 종료 시간 1 나노초 후"),
                Arguments.of(CampusOperatingTime.CLOSE_AT.getTime().plusMinutes(1), "운영 종료 시간 1분 후"),
                Arguments.of(CampusOperatingTime.CLOSE_AT.getTime().plusHours(1), "운영 종료 시간 1시간 후")
        );
    }

    @Test
    void 월요일_1시부터_1시5분까지는_출석이다() {
        LocalDate monday = LocalDate.of(2024, 12, 9);
        LocalTime mondayAttendStartTime = LocalTime.of(13, 0);
        LocalTime mondayAttendEndTime = LocalTime.of(13, 5);

        assertAll(
                () -> assertThat(new Attendance(monday, mondayAttendStartTime).determineStatus()).isEqualTo("출석"),
                () -> assertThat(new Attendance(monday, mondayAttendEndTime).determineStatus()).isEqualTo("출석")
        );
    }
}
