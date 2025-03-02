package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import fixture.AttendanceDateTimeFixture;
import fixture.AttendanceHistoriesFixture;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AttendanceHistoriesTest {
    private static final LocalDate MONDAY_DATE = LocalDate.of(2025, 2, 24);
    private static final LocalDate TUESDAY_DATE = LocalDate.of(2025, 2, 25);
    private static final LocalDate DEFAULT_DATE = LocalDate.of(2025, 2, 21);
    private static final LocalTime DEFAULT_TIME = LocalTime.of(10, 0);
    private static final Crew DEFAULT_CREW = new Crew("노랑");
    private static final Crew INVALID_CREW = new Crew("포비");
    private static final LocalDateTime DEFAULT_DATE_TIME = LocalDateTime.of(DEFAULT_DATE, DEFAULT_TIME);

    private final AttendanceHistories defaultAttendanceHistory = AttendanceHistoriesFixture.createWithSingleAttendance(
            DEFAULT_CREW, DEFAULT_DATE_TIME);

    @Nested
    @DisplayName("1.1 닉네임과 등교 시간을 받으면 오늘 날짜로 출석 기록을 생성할 수 있다.")
    class AttendanceCheckTest {
        @Test
        @DisplayName("화요일은 10시 5분에 출석할 경우 출석으로 처리한다.")
        void testPresentAttendance() {
            // given
            LocalTime time = LocalTime.of(10, 5);
            // when
            AttendanceStatus attendanceStatus = defaultAttendanceHistory.addAttendanceHistory(DEFAULT_CREW,
                    LocalDateTime.of(TUESDAY_DATE, time));
            // then
            assertThat(attendanceStatus).isEqualTo(AttendanceStatus.PRESENT);
        }

        @Test
        @DisplayName("화요일은 10시 30분에 출석할 경우 지각으로 처리한다.")
        void testTardyAttendance() {
            // given
            LocalTime time = LocalTime.of(10, 30);
            // when
            AttendanceStatus attendanceStatus = defaultAttendanceHistory.addAttendanceHistory(DEFAULT_CREW,
                    LocalDateTime.of(TUESDAY_DATE, time));
            // then
            assertThat(attendanceStatus).isEqualTo(AttendanceStatus.TARDY);
        }

        @Test
        @DisplayName("화요일은 10시 30분 1초에 출석할 경우 결석으로 처리한다.")
        void testAbsentAttendance() {
            // given
            LocalTime time = LocalTime.of(10, 30, 1);
            // when
            AttendanceStatus attendanceStatus = defaultAttendanceHistory.addAttendanceHistory(DEFAULT_CREW,
                    LocalDateTime.of(TUESDAY_DATE, time));
            // then
            assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ABSENT);
        }

        @Test
        @DisplayName("월요일은 13시 5분에 출석할 경우 출석으로 처리한다.")
        void testPresentAttendanceOnMonday() {
            // given
            LocalTime time = LocalTime.of(13, 5);
            // when
            AttendanceStatus attendanceStatus = defaultAttendanceHistory.addAttendanceHistory(DEFAULT_CREW,
                    LocalDateTime.of(MONDAY_DATE, time));
            // then
            assertThat(attendanceStatus).isEqualTo(AttendanceStatus.PRESENT);
        }

        @Test
        @DisplayName("월요일은 13시 30분에 출석할 경우 지각으로 처리한다.")
        void testTardyAttendanceOnMonday() {
            // given
            LocalTime time = LocalTime.of(13, 30);
            // when
            AttendanceStatus attendanceStatus = defaultAttendanceHistory.addAttendanceHistory(DEFAULT_CREW,
                    LocalDateTime.of(MONDAY_DATE, time));
            // then
            assertThat(attendanceStatus).isEqualTo(AttendanceStatus.TARDY);
        }

        @Test
        @DisplayName("월요일은 13시 30분 1초에 출석할 경우 결석으로 처리한다.")
        void testAbsentAttendanceOnMonday() {
            // given
            LocalTime time = LocalTime.of(13, 30, 1);
            // when
            AttendanceStatus attendanceStatus = defaultAttendanceHistory.addAttendanceHistory(DEFAULT_CREW,
                    LocalDateTime.of(MONDAY_DATE, time));
            // then
            assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ABSENT);
        }
    }

    @Test
    @DisplayName("1.2 이미 출석한 경우 예외를 발생시킬 수 있다.")
    void testValidateDuplicateAttendance() {
        assertThatThrownBy(() -> defaultAttendanceHistory.addAttendanceHistory(DEFAULT_CREW, DEFAULT_DATE_TIME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요.");
    }

    @Test
    @DisplayName("1.3 기록이 없는 닉네임을 입력하면 예외를 발생시킬 수 있다.")
    void testValidateCrewPresenceWhenCheck() {
        assertThatThrownBy(() -> defaultAttendanceHistory.addAttendanceHistory(INVALID_CREW, DEFAULT_DATE_TIME))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    @Nested
    @DisplayName("1.4 등교일이 아닐 경우(주말, 공휴일) 예외를 발생시킬 수 있다.")
    public class ValidateDayOffTest {
        @Test
        @DisplayName("토요일에 등교할 경우 예외를 발생시킬 수 있다.")
        void testSaturdayException() {
            // given
            LocalDate saturday = LocalDate.of(2025, 2, 22);
            LocalDateTime dateTime = saturday.atTime(DEFAULT_TIME);
            // when & then
            assertThatThrownBy(() -> defaultAttendanceHistory.addAttendanceHistory(DEFAULT_CREW, dateTime))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("[ERROR] 2월 22일 토요일은 등교일이 아닙니다.");
        }

        @Test
        @DisplayName("일요일에 등교할 경우 예외를 발생시킬 수 있다.")
        void testSundayException() {
            // given
            LocalDate sunday = LocalDate.of(2025, 3, 2);
            LocalDateTime dateTime = sunday.atTime(DEFAULT_TIME);
            // when & then
            assertThatThrownBy(() -> defaultAttendanceHistory.addAttendanceHistory(DEFAULT_CREW, dateTime))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("[ERROR] 3월 2일 일요일은 등교일이 아닙니다.");
        }

        @Test
        @DisplayName("법정공휴일에 등교할 경우 예외를 발생시킬 수 있다.")
        void validateHolidayException() {
            // given
            LocalDate holiday = LocalDate.of(2025, 3, 3);
            LocalDateTime dateTime = holiday.atTime(DEFAULT_TIME);
            // when & then
            assertThatThrownBy(() -> defaultAttendanceHistory.addAttendanceHistory(DEFAULT_CREW, dateTime))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("[ERROR] 3월 3일 월요일은 등교일이 아닙니다.");
        }

        @ParameterizedTest
        @DisplayName("방학에 등교할 경우 예외를 발생시킬 수 있다.")
        @CsvSource({"2025-04-07", "2025-04-14", "2025-08-25"})
        void validateVacationException(LocalDate vacationDate) {
            // given
            LocalDateTime dateTime = vacationDate.atTime(10, 0);
            // when & then
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M월 d일 E요일");
            assertThatThrownBy(() -> defaultAttendanceHistory.addAttendanceHistory(DEFAULT_CREW, dateTime))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(String.format("[ERROR] %s은 등교일이 아닙니다.", formatter.format(vacationDate)));
        }
    }

    @ParameterizedTest
    @DisplayName("1.5 캠퍼스 운영 시간이 아닐 경우 예외를 발생시킬 수 있다.")
    @CsvSource({"07:59", "23:01"})
    void testValidateOperatingTime(LocalTime invalidTime) {
        // given
        LocalDateTime dateTime = MONDAY_DATE.atTime(invalidTime);
        // when & then
        assertThatThrownBy(() -> defaultAttendanceHistory.addAttendanceHistory(DEFAULT_CREW, dateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 캠퍼스 운영 시간에만 출석이 가능합니다.");
    }

    @Test
    @DisplayName("2.1 닉네임, 수정하려는 날짜, 등교 시간을 입력하여 기록을 수정할 수 있다.")
    void testReplaceAttendanceHistory() {
        // given
        LocalDateTime newAttendanceDateTime = DEFAULT_DATE_TIME.plusMinutes(5);
        // when
        AttendanceDateTime oldAttendanceDateTime = defaultAttendanceHistory.replaceAttendanceHistory(DEFAULT_CREW,
                newAttendanceDateTime);
        // then
        AttendanceDateTime expectedAttendanceDateTime = new AttendanceDateTime(DEFAULT_DATE_TIME);
        assertThat(oldAttendanceDateTime).isEqualTo(expectedAttendanceDateTime);
    }

    @Test
    @DisplayName("2.2 기록이 없는 닉네임을 입력하면 예외를 발생시킬 수 있다.")
    void testValidateCrewPresenceWhenReplace() {
        assertThatThrownBy(
                () -> defaultAttendanceHistory.replaceAttendanceHistory(INVALID_CREW, MONDAY_DATE.atTime(DEFAULT_TIME)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    @Test
    @DisplayName("2.3 출석 기록이 없는 날짜를 입력한 경우 예외를 발생시킬 수 있다.")
    void testValidateDateWhenReplace() {
        // given & when
        LocalDateTime dateTime = DEFAULT_DATE_TIME.plusDays(1);
        // then
        assertThatThrownBy(() -> defaultAttendanceHistory.replaceAttendanceHistory(DEFAULT_CREW, dateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 출석 기록이 없는 날짜는 수정할 수 없습니다.");
    }

    @Test
    @DisplayName("3.1 닉네임을 입력하면 전날까지의 출결 기록을 확인할 수 있다.")
    void testGetAttendanceDateTimes() {
        // given & when
        AttendanceDateTimes actualAttendanceDateTimes = defaultAttendanceHistory.getAttendanceDateTimes(DEFAULT_CREW);
        // then
        AttendanceDateTimes expectedAttendanceDateTimes = new AttendanceDateTimes(
                List.of(new AttendanceDateTime(DEFAULT_DATE_TIME)));
        assertThat(actualAttendanceDateTimes).isEqualTo(expectedAttendanceDateTimes);
    }

    @Nested
    @DisplayName("3.2 닉네임을 입력하면 전날까지의 크루 출결 횟수를 확인할 수 있다.")
    public class GetAttendanceCountTest {
        private static final LocalDate START_DATE = LocalDate.of(2025, 2, 11);
        private static final LocalDate LAST_DATE = AttendanceDateTimeFixture.getNthValidDate(START_DATE, 12);

        AttendanceHistories attendanceHistoriesForCount = AttendanceHistoriesFixture.createWithMultipleAttendance(
                DEFAULT_CREW, START_DATE, 3, 4, 5);

        @Test
        @DisplayName("출석 횟수를 확인할 수 있다.")
        void testGetPresentCount() {
            // given & when
            int presentCount = attendanceHistoriesForCount.getPresentCount(DEFAULT_CREW, LAST_DATE);
            // then
            assertThat(presentCount).isEqualTo(3);
        }

        @Test
        @DisplayName("지각 횟수를 확인할 수 있다.")
        void testGetTardyCount() {
            // given & when
            int tardyCount = attendanceHistoriesForCount.getTardyCount(DEFAULT_CREW, LAST_DATE);
            // then
            assertThat(tardyCount).isEqualTo(4);
        }

        @Test
        @DisplayName("결석 횟수를 확인할 수 있다.")
        void testGetAbsentCount() {
            // given & when
            int absentCount = attendanceHistoriesForCount.getAbsentCount(DEFAULT_CREW, LAST_DATE);
            // then
            assertThat(absentCount).isEqualTo(5);
        }

        @Test
        @DisplayName("기록이 없는 날짜는 결석 횟수로 기록된다.")
        void testGetAbsentCountWithEmptyHistory() {
            // given
            LocalDate lastDate = LAST_DATE.plusDays(5);
            // when
            int absentCount = attendanceHistoriesForCount.getAbsentCount(DEFAULT_CREW, lastDate);
            // then
            assertThat(absentCount).isEqualTo(10);
        }
    }


    @Test
    @DisplayName("3.4 기록이 없는 닉네임을 입력하면 예외를 발생시킬 수 있다.")
        // TODO 케이스별로 작성
    void validateCrewPresenceWhenGetHistory() {
        assertThatThrownBy(() -> defaultAttendanceHistory.getAttendanceDateTimes(INVALID_CREW))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 등록되지 않은 닉네임입니다.");
    }
}
