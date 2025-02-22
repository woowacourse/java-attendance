package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AttendanceSheetTest {

    @Nested
    @DisplayName("날짜 일치 테스트")
    class DayEqualsTest {
        @Test
        @DisplayName("날짜 일치 여부를 테스트할 수 있다.")
        void isDaySameTest() {
            //given
            AttendanceSheet attendanceSheet = new AttendanceSheet("링크",
                    AttendanceDateTime.from(LocalDateTime.of(2024, 12, 13, 10, 10)));
            AttendanceSheet attendanceSheetToCompare = new AttendanceSheet("링크",
                    AttendanceDateTime.from(LocalDateTime.of(2024, 12, 13, 11, 20)));

            //when
            assertThat(attendanceSheet.isSame(attendanceSheetToCompare)).isTrue();
        }
    }
}
