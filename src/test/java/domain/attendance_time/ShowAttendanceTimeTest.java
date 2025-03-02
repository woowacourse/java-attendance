package domain.attendance_time;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Attendance;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ShowAttendanceTimeTest {
    
    @Nested
    class 생성_테스트 {
        
        @Test
        void 시간으로_생성한다() {
            //given
            var time = LocalTime.of(10, 5);
            
            //when
            var result = new ShowAttendanceTime(time);
            
            //then
            assertThat(result.getAttendTime()).isEqualTo(Optional.of(LocalTime.of(10, 5)));
        }
        
        @ParameterizedTest
        @ValueSource(strings = {"07:59", "07:58"})
        void _8시_이전으로_생성하면_예외가_발생한다(String timeStringValue) {
            //given
            var date = LocalDate.of(2024, 12, 3);
            var time = LocalTime.parse(timeStringValue);
            
            //expected
            assertThatThrownBy(() -> Attendance.show(date, time))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("캠퍼스 운영시간이 아닙니다. (08:00~23:00)");
        }
        
        @ParameterizedTest
        @ValueSource(strings = {"23:01", "23:02"})
        void _23시_이후로_생성하면_예외가_발생한다(String timeStringValue) {
            //given
            var date = LocalDate.of(2024, 12, 3);
            var time = LocalTime.parse(timeStringValue);
            
            //expected
            assertThatThrownBy(() -> Attendance.show(date, time))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("캠퍼스 운영시간이 아닙니다. (08:00~23:00)");
        }
    }
    
}
