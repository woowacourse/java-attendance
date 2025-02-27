package domain;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class AttendanceTest {
    
    @Nested
    class 생성_테스트 {
        
        @Test
        void 시간과_날짜로_생성한다() {
            //given
            var date = LocalDate.of(2024, 12, 20);
            var time = LocalTime.of(10, 5);
            
            //when
            var result = Attendance.of(date, time);
            
            //then
            assertAll(
                    () -> assertThat(result.getAttendDate()).isEqualTo(LocalDate.of(2024, 12, 20)),
                    () -> assertThat(result.getAttendTime().getAttendTime()).isEqualTo(LocalTime.of(10, 5)),
                    () -> assertThat(result.getStatus()).isEqualTo(AttendanceStatus.출석)
            );
        }
        
        @Test
        void 노쇼한_경우_시간은_null이다() {
            //given
            var date = LocalDate.of(2024, 12, 20);
            
            //when
            var result = Attendance.noShow(date);
            
            //then
            assertThat(result.getAttendTime().getAttendTime()).isNull();
        }
        
        @Test
        void 노쇼한_경우_결석이다() {
            //given
            var date = LocalDate.of(2024, 12, 20);
            
            //when
            var result = Attendance.noShow(date);
            
            //then
            assertThat(result.getStatus()).isEqualTo(AttendanceStatus.결석);
        }
        
        @ParameterizedTest
        @ValueSource(strings = {"13:04", "13:05"})
        void 월요일은_13시_5분까지_출석이다(String timeStringValue) {
            //given
            var date = LocalDate.of(2024, 12, 2);
            var time = LocalTime.parse(timeStringValue);
            
            //when
            var result = Attendance.of(date, time);
            
            //then
            assertThat(result.getStatus()).isEqualTo(AttendanceStatus.출석);
        }
        
        @ParameterizedTest
        @ValueSource(strings = {"13:06", "13:06", "13:29", "13:30"})
        void 월요일은_13시_6분부터_30분까지_지각이다(String timeStringValue) {
            //given
            var date = LocalDate.of(2024, 12, 2);
            var time = LocalTime.parse(timeStringValue);
            
            //when
            var result = Attendance.of(date, time);
            
            //then
            assertThat(result.getStatus()).isEqualTo(AttendanceStatus.지각);
        }
        
        @ParameterizedTest
        @ValueSource(strings = {"13:31", "13:32"})
        void 월요일은_13시_31분부터_결석이다(String timeStringValue) {
            //given
            var date = LocalDate.of(2024, 12, 2);
            var time = LocalTime.parse(timeStringValue);
            
            //when
            var result = Attendance.of(date, time);
            
            //then
            assertThat(result.getStatus()).isEqualTo(AttendanceStatus.결석);
        }
        
        @ParameterizedTest
        @ValueSource(strings = {"10:04", "10:05"})
        void 월요일을_제외한_출석날짜는_10시_5분까지_출석이다(String timeStringValue) {
            //given
            var date = LocalDate.of(2024, 12, 3);
            var time = LocalTime.parse(timeStringValue);
            
            //when
            var result = Attendance.of(date, time);
            
            //then
            assertThat(result.getStatus()).isEqualTo(AttendanceStatus.출석);
        }
        
        @ParameterizedTest
        @ValueSource(strings = {"10:06", "10:07", "10:29", "10:30"})
        void 월요일을_제외한_출석날짜는_10시_6분부터_30분까지_지각이다(String timeStringValue) {
            //given
            var date = LocalDate.of(2024, 12, 3);
            var time = LocalTime.parse(timeStringValue);
            
            //when
            var result = Attendance.of(date, time);
            
            //then
            assertThat(result.getStatus()).isEqualTo(AttendanceStatus.지각);
        }
        
        @ParameterizedTest
        @ValueSource(strings = {"10:31", "10:32"})
        void 월요일을_제외한_출석날짜는_10시_31분부터_결석이다(String timeStringValue) {
            //given
            var date = LocalDate.of(2024, 12, 3);
            var time = LocalTime.parse(timeStringValue);
            
            //when
            var result = Attendance.of(date, time);
            
            //then
            assertThat(result.getStatus()).isEqualTo(AttendanceStatus.결석);
        }
        
        @ParameterizedTest
        @ValueSource(strings = {"07:59", "07:58"})
        void _8시_이전으로_생성하면_예외가_발생한다(String timeStringValue) {
            //given
            var date = LocalDate.of(2024, 12, 3);
            var time = LocalTime.parse(timeStringValue);
            
            //expected
            assertThatThrownBy(() -> Attendance.of(date, time))
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
            assertThatThrownBy(() -> Attendance.of(date, time))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("캠퍼스 운영시간이 아닙니다. (08:00~23:00)");
        }
        
        @Test
        void 주말로_생성하면_예외가_발생한다() {
            //given
            var date = LocalDate.of(2024, 12, 1);
            var time = LocalTime.of(10, 5);
            
            //expected
            assertThatThrownBy(() -> Attendance.of(date, time))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("주말에는 출석할 수 없습니다.");
        }
        
        @Test
        void 공휴일로_생성하면_예외가_발생한다() {
            //given
            var date = LocalDate.of(2024, 12, 25);
            var time = LocalTime.of(10, 5);
            
            //expected
            assertThatThrownBy(() -> Attendance.of(date, time))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("공휴일에는 출석할 수 없습니다.");
        }
    }
    
}
