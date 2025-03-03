package domain;

import fixture.LocalDateFixture;
import fixture.LocalTimeFixture;
import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AttendanceRecordTest {

    @Nested
    @DisplayName("예외가 발생하지 않는 테스트")
    class Success {
        @Test
        @DisplayName("정상적으로 출석 기록이 생성된다")
        void new_test() {
            // given
            String nickname = "우가";
            Crew crew = new Crew(nickname);
            LocalDate lectureDate = LocalDateFixture.LECTURE_DATE1;
            LocalTime time = LocalTimeFixture.MONDAY_LATE;

            // when && then
            Assertions.assertThatCode(() -> {
                AttendanceRecord.of(crew, lectureDate, time);
            }).doesNotThrowAnyException();
        }
    }

    @Nested
    @DisplayName("예외 테스트")
    class Fail {

        @Test
        @DisplayName("출석 기록 날짜가 교육이 있는 날이 아니면 예외가 발생한다")
        void validateDate_test_exception() {
            // given
            String nickname = "우가";
            Crew crew = new Crew(nickname);
            LocalDate saturday = LocalDateFixture.NOT_LECTURE_DATE;
            LocalTime time = LocalTimeFixture.CAMPUS_TIME;

            // when && then
            Assertions.assertThatThrownBy(() -> {
                AttendanceRecord.of(crew, saturday, time);
            }).isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("출석 기록 시간이 캠퍼스 운영 시간이 아니면 예외가 발생한다")
        void validateCampusTime_test_exception() {
            // given
            String nickname = "우가";
            Crew crew = new Crew(nickname);
            LocalDate date = LocalDateFixture.LECTURE_DATE1;
            LocalTime notCampusTime = LocalTimeFixture.NOT_CAMPUS_TIME;

            // when && then
            Assertions.assertThatThrownBy(() -> {
                AttendanceRecord.of(crew, date, notCampusTime);
            }).isInstanceOf(IllegalArgumentException.class);
        }
    }
}