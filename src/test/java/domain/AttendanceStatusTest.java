package domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class AttendanceStatusTest {
    
    @Nested
    class 출석_상태_생성_테스트 {
        
        @ParameterizedTest
        @CsvSource({"13:02", "13:03", "13:04", "13:05"})
        void 월요일은_13시_5분까지_출석이다(String timeValue) {
            //given
            var monday = DayOfWeek.MONDAY;
            var time = LocalTime.parse(timeValue);
            
            //when
            var result = AttendanceStatus.of(monday, time);
            
            //then
            assertThat(result).isEqualTo(AttendanceStatus.출석);
        }
        
        @ParameterizedTest
        @CsvSource({"13:06", "13:07", "13:29", "13:30"})
        void 월요일은_13시_6분부터_30분까지_지각이다(String timeValue) {
            //given
            var monday = DayOfWeek.MONDAY;
            var time = LocalTime.parse(timeValue);
            
            //when
            var result = AttendanceStatus.of(monday, time);
            
            //then
            assertThat(result).isEqualTo(AttendanceStatus.지각);
        }
        
        @ParameterizedTest
        @CsvSource({"13:31", "13:32", "13:33", "13:34"})
        void 월요일은_13시_31분부터_결석이다(String timeValue) {
            //given
            var monday = DayOfWeek.MONDAY;
            var time = LocalTime.parse(timeValue);
            
            //when
            var result = AttendanceStatus.of(monday, time);
            
            //then
            assertThat(result).isEqualTo(AttendanceStatus.결석);
        }
        
        @ParameterizedTest
        @CsvSource({"10:02", "10:03", "10:04", "10:05"})
        void 월요일이_아닌_평일에는_10시_5분까지_출석이다(String timeValue) {
            //given
            var notMonday = DayOfWeek.TUESDAY;
            var time = LocalTime.parse(timeValue);
            
            //when
            var result = AttendanceStatus.of(notMonday, time);
            
            //then
            assertThat(result).isEqualTo(AttendanceStatus.출석);
        }
        
        @ParameterizedTest
        @CsvSource({"10:06", "10:07", "10:29", "10:30"})
        void 월요일이_아닌_평일에는_10시_6분부터_10시_30분가지_지각이다(String timeValue) {
            //given
            var notMonday = DayOfWeek.TUESDAY;
            var time = LocalTime.parse(timeValue);
            
            //when
            var result = AttendanceStatus.of(notMonday, time);
            
            //then
            assertThat(result).isEqualTo(AttendanceStatus.지각);
        }
        
        @ParameterizedTest
        @CsvSource({"10:31", "10:32", "10:33", "10:34"})
        void 월요일이_아닌_평일에는_10시_31분부터_결석이다(String timeValue) {
            //given
            var notMonday = DayOfWeek.TUESDAY;
            var time = LocalTime.parse(timeValue);
            
            //when
            var result = AttendanceStatus.of(notMonday, time);
            
            //then
            assertThat(result).isEqualTo(AttendanceStatus.결석);
        }
    }
    
}
