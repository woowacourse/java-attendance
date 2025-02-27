package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CampusManagerTest {
    @DisplayName("캠퍼스의_등교일_여부_반환")
    @Nested
    class isOperationDate {
        @DisplayName("주어진_날짜가_공휴일이_아닌_평일이면_true_를_반환한다")
        @Test
        void should_ReturnTrue_WhenDateIsNotWeekendAndHoliday() {
            //given
            CampusManager campusManager = new CampusManager();
            LocalDate date = LocalDate.of(2024, 12, 26);

            //when
            boolean result = campusManager.isOperationDate(date);

            //then
            assertThat(result).isTrue();
        }

        @DisplayName("주어진_날짜가_공휴일이면_false_를_반환한다")
        @Test
        void should_ReturnFalse_WhenDateIsHoliday() {
            //given
            CampusManager campusManager = new CampusManager();
            LocalDate date = LocalDate.of(2024, 12, 25);

            //when
            boolean result = campusManager.isOperationDate(date);

            //then
            assertThat(result).isFalse();
        }

        @DisplayName("주어진_날짜가_주말이면_false_를_반환한다")
        @Test
        void should_ReturnFalse_WhenDateIsWeekend() {
            //given
            CampusManager campusManager = new CampusManager();
            LocalDate date = LocalDate.of(2024, 12, 22);

            //when
            boolean result = campusManager.isOperationDate(date);

            //then
            assertThat(result).isFalse();
        }
    }

    @DisplayName("캠퍼스의_운영_시간_여부_반환")
    @Nested
    class isOperationTime {

        @DisplayName("주어진_시간이_캠퍼스_운영_시간이면_true_를_반환한다")
        @Test
        void should_ReturnTrue_WhenTimeIsOperationTime() {
            //given
            CampusManager campusManager = new CampusManager();
            LocalTime time = LocalTime.of(23, 00);

            //when
            boolean result = campusManager.isOperationTime(time);

            //then
            assertThat(result).isTrue();
        }

        @DisplayName("주어진_시간이_캠퍼스_운영_시간이_아니면_false_를_반환한다")
        @Test
        void should_ReturnFalse_WhenTimeIsOperationTime() {
            //given
            CampusManager campusManager = new CampusManager();
            LocalTime time = LocalTime.of(23, 01);

            //when
            boolean result = campusManager.isOperationTime(time);

            //then
            assertThat(result).isFalse();
        }
    }
}
