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
        LocalTime attendTime = CampusOperatingTime.OPEN.getTime();

        assertThatThrownBy(() -> Attendance.of(weekend, attendTime))
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
        LocalTime attendTime = CampusOperatingTime.CLOSE.getTime().minusMinutes(1);

        assertThatThrownBy(() -> Attendance.of(holiday, attendTime))
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

        assertThatThrownBy(() -> Attendance.of(attendDate, notInOperatingTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(FormattedErrorMessage.INVALID_ATTEND_TIME_ERROR.getTimeFormatMessage(notInOperatingTime));
    }

    static Stream<Arguments> getNotInOperatingTime() {
        return Stream.of(
                Arguments.of(CampusOperatingTime.OPEN.getTime().minusNanos(1), "운영 시작 시간 1 나노초 전"),
                Arguments.of(CampusOperatingTime.OPEN.getTime().minusMinutes(1), "운영 시작 시간 1분 전"),
                Arguments.of(CampusOperatingTime.OPEN.getTime().minusHours(1), "운영 시작 시간 1시간 전"),
                Arguments.of(CampusOperatingTime.CLOSE.getTime().plusNanos(1), "운영 종료 시간 1 나노초 후"),
                Arguments.of(CampusOperatingTime.CLOSE.getTime().plusMinutes(1), "운영 종료 시간 1분 후"),
                Arguments.of(CampusOperatingTime.CLOSE.getTime().plusHours(1), "운영 종료 시간 1시간 후")
        );
    }

    @ParameterizedTest(name = "{index} : {2}")
    @MethodSource("getMondayAttendTime")
    void 월요일_출석시간에_따라_출석상태를_반환한다(LocalTime attendTime, AttendanceStatus expectedStatus, String message) {
        LocalDate monday = LocalDate.of(2024, 12, 9);

        assertThat(Attendance.of(monday, attendTime).determineStatus()).isEqualTo(expectedStatus);
    }

    static Stream<Arguments> getMondayAttendTime() {
        return Stream.of(
                Arguments.of(EducationTime.MONDAY_ATTEND.getTime(), AttendanceStatus.ATTEND, "월요일 출석 시작 시간"),
                Arguments.of(EducationTime.MONDAY_LATE.getTime(), AttendanceStatus.ATTEND, "월요일 출석 종료 시간"),
                Arguments.of(EducationTime.MONDAY_LATE.getTime().plusNanos(1), AttendanceStatus.LATE, "월요일 지각 시작 시간"),
                Arguments.of(EducationTime.MONDAY_ABSENT.getTime(), AttendanceStatus.LATE, "월요일 지각 종료 시간"),
                Arguments.of(EducationTime.MONDAY_ABSENT.getTime().plusNanos(1), AttendanceStatus.ABSENT, "월요일 결석 시작 시간")
        );
    }

    @ParameterizedTest(name = "{index} : {2}")
    @MethodSource("getGeneralAttendTime")
    void 월요일이_아닌_요일의_출석시간에_따라_출석상태를_반환한다(LocalTime attendTime, AttendanceStatus expectedStatus, String message) {
        LocalDate thursday = LocalDate.of(2024, 12, 12);

        assertThat(Attendance.of(thursday, attendTime).determineStatus()).isEqualTo(expectedStatus);
    }

    static Stream<Arguments> getGeneralAttendTime() {
        return Stream.of(
                Arguments.of(EducationTime.GENERAL_ATTEND.getTime(), AttendanceStatus.ATTEND, "목요일 출석 시작 시간"),
                Arguments.of(EducationTime.GENERAL_LATE.getTime(), AttendanceStatus.ATTEND, "목요일 출석 종료 시간"),
                Arguments.of(EducationTime.GENERAL_LATE.getTime().plusNanos(1), AttendanceStatus.LATE, "목요일 지각 시작 시간"),
                Arguments.of(EducationTime.GENERAL_ABSENT.getTime(), AttendanceStatus.LATE, "목요일 지각 종료 시간"),
                Arguments.of(EducationTime.GENERAL_ABSENT.getTime().plusNanos(1), AttendanceStatus.ABSENT, "목요일 결석 시작 시간")
        );
    }

    @Test
    void 입력된_출석의_일자와_현재_출석의_일자가_동일하면_true_아니면_false를_반환한다() {
        Attendance attendance = Attendance.of(LocalDate.of(2024, 12, 12), LocalTime.of(13, 0));
        Attendance sameDateAttendance = Attendance.of(LocalDate.of(2024, 12, 12), LocalTime.of(13, 0));
        Attendance differentDateAttendance = Attendance.of(LocalDate.of(2024, 12, 13), LocalTime.of(13, 0));

        assertAll(
                () -> assertThat(attendance.isSameDate(sameDateAttendance)).isTrue(),
                () -> assertThat(attendance.isSameDate(differentDateAttendance)).isFalse()
        );
    }

    @Test
    void 입력된_일자와_출석의_일자가_동일하면_true_아니면_false를_반환한다() {
        Attendance attendance = Attendance.of(LocalDate.of(2024, 12, 12), LocalTime.of(13, 0));
        LocalDate sameDate = LocalDate.of(2024, 12, 12);
        LocalDate differentDate = LocalDate.of(2024, 12, 13);

        assertAll(
                () -> assertThat(attendance.isSameDate(sameDate)).isTrue(),
                () -> assertThat(attendance.isSameDate(differentDate)).isFalse()
        );
    }

    @Test
    void 입력된_날짜와_출석의_연월이_동일하면_true_아니면_false를_반환한다() {
        Attendance attendance = Attendance.of(LocalDate.of(2024, 12, 12), LocalTime.of(13, 0));
        LocalDate sameYearAndMonth = LocalDate.of(2024, 12, 12);
        LocalDate differentYearAndMonth = LocalDate.of(2024, 11, 12);

        assertAll(
                () -> assertThat(attendance.isSameYearAndMonth(sameYearAndMonth)).isTrue(),
                () -> assertThat(attendance.isSameYearAndMonth(differentYearAndMonth)).isFalse()
        );
    }
}
