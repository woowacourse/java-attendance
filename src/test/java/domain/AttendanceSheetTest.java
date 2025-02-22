package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("출석부 확인 테스트")
public class AttendanceSheetTest {

    @Nested
    @DisplayName("날짜 일치 테스트")
    class DayEqualsTest {
        @Test
        @DisplayName("출석부를 비교하여 날짜 일치 여부를 테스트할 수 있다.")
        void isDayCorrectTestFromSheet() {
            //given
            AttendanceSheet attendanceSheet = new AttendanceSheet("링크",
                    AttendanceDateTime.from(LocalDateTime.of(2024, 12, 13, 10, 10)));
            AttendanceSheet attendanceSheetToCompare = new AttendanceSheet("링크",
                    AttendanceDateTime.from(LocalDateTime.of(2024, 12, 13, 11, 20)));

            //when
            assertThat(attendanceSheet.isCorrectDay(attendanceSheetToCompare)).isTrue();
        }

        @Test
        @DisplayName("날짜를 입력받아 날짜 일치 여부를 테스트할 수 있다.")
        void isDayCorrectTestFromDay() {
            //given
            AttendanceSheet attendanceSheet = new AttendanceSheet("링크",
                    AttendanceDateTime.from(LocalDateTime.of(2024, 12, 13, 10, 10)));

            //when
            assertThat(attendanceSheet.isCorrectDay(13)).isTrue();
        }
    }
}
