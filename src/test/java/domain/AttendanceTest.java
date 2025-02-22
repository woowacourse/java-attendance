package domain;

import dto.result.AttendResult;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import util.exception.IllegalAttendTimeException;
import util.exception.WeekendAttendException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AttendanceTest {
    
    @Nested
    class 출석_테스트 {
        
        @Test
        void 닉네임과_등교_시간을_입력하면_출석할_수_있다() {
            // given
            LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 3, 10, 5);
            
            // when
            Attendance attendance = new Attendance(localDateTime);
            
            // then
            assertThat(attendance).extracting(
                    "attendanceDateTime", "attendanceStatus"
            ).containsExactly(
                    LocalDateTime.of(2024, 12, 3, 10, 5), AttendanceStatus.출석
            );
        }
        
        @Test
        void 월요일은_13시_5분까지_출석이다() {
            // given
            LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 2, 13, 5);
            
            // when
            Attendance attendance = new Attendance(localDateTime);
            
            // then
            assertThat(attendance).extracting(
                    "attendanceDateTime", "attendanceStatus"
            ).containsExactly(
                    LocalDateTime.of(2024, 12, 2, 13, 5), AttendanceStatus.출석
            );
        }
        
        @Test
        void 출석_시간보다_5분_초과_늦으면_지각이다() {
            // given
            LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 3, 10, 6);
            
            // when
            Attendance attendance = new Attendance(localDateTime);
            
            // then
            assertThat(attendance).extracting(
                    "attendanceDateTime", "attendanceStatus"
            ).containsExactly(
                    LocalDateTime.of(2024, 12, 3, 10, 6), AttendanceStatus.지각
            );
        }
        
        @Test
        void 월요일은_13시_6분부터_지각이다() {
            // given
            LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 2, 13, 6);
            
            // when
            Attendance attendance = new Attendance(localDateTime);
            
            // then
            assertThat(attendance).extracting(
                    "attendanceDateTime", "attendanceStatus"
            ).containsExactly(
                    LocalDateTime.of(2024, 12, 2, 13, 6), AttendanceStatus.지각
            );
        }
        
        @Test
        void 출석_시간보다_5분_초과_30분_이하_늦으면_지각이다() {
            // given
            LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 3, 10, 30);
            
            // when
            Attendance attendance = new Attendance(localDateTime);
            
            // then
            assertThat(attendance).extracting(
                    "attendanceDateTime", "attendanceStatus"
            ).containsExactly(
                    LocalDateTime.of(2024, 12, 3, 10, 30), AttendanceStatus.지각
            );
        }
        
        @Test
        void 월요일은_13시_30분까지_지각이다() {
            // given
            LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 2, 13, 30);
            
            // when
            Attendance attendance = new Attendance(localDateTime);
            
            // then
            assertThat(attendance).extracting(
                    "attendanceDateTime", "attendanceStatus"
            ).containsExactly(
                    LocalDateTime.of(2024, 12, 2, 13, 30), AttendanceStatus.지각
            );
        }
        
        @Test
        void 출석_시간보다_30분_초과_늦으면_지각이다() {
            // given
            LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 3, 10, 31);
            
            // when
            Attendance attendance = new Attendance(localDateTime);
            
            // then
            assertThat(attendance).extracting(
                    "attendanceDateTime", "attendanceStatus"
            ).containsExactly(
                    LocalDateTime.of(2024, 12, 3, 10, 31), AttendanceStatus.결석
            );
        }
        
        @Test
        void 월요일은_13시_31분부터_결석이다() {
            // given
            LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 2, 13, 31);
            
            // when
            Attendance attendance = new Attendance(localDateTime);
            
            // then
            assertThat(attendance).extracting(
                    "attendanceDateTime", "attendanceStatus"
            ).containsExactly(
                    LocalDateTime.of(2024, 12, 2, 13, 31), AttendanceStatus.결석
            );
        }
        
        @Test
        void 주말에_출석하면_예외가_발생한다() {
            // given
            LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 8, 10, 30);
            
            // expected
            assertThatThrownBy(() -> new Attendance(localDateTime))
                    .isExactlyInstanceOf(WeekendAttendException.class)
                    .hasMessage("주말에는 출석할 수 없습니다.");
        }
        
        @Test
        void _8시와_23시_사이가_아니면_예외가_발생한다() {
            // given
            LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 3, 7, 59);
            
            // expected
            assertThatThrownBy(() -> new Attendance(localDateTime))
                    .isExactlyInstanceOf(IllegalAttendTimeException.class)
                    .hasMessage("출석 가능한 시간이 아닙니다. (출석 가능 시간 : 08:00 ~ 23:00)");
        }
    }
    
    @Nested
    class 출석_기록_확인_테스트 {
        
        @Test
        void 출석을_완료하면_출석기록이_출력된다() {
            // given
            LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 3, 9, 45);
            Attendance attendance = new Attendance(localDateTime);
            
            // when
            AttendResult result = attendance.createAttendanceResult();
            
            // then
            assertAll(
                    () -> assertThat(result.attendanceDateTime()).isEqualTo(LocalDateTime.of(2024, 12, 3, 9, 45)),
                    () -> assertThat(result.attendanceStatus()).isEqualTo(AttendanceStatus.출석)
            );
        }
    }
    
    @Nested
    class 출석_시간_수정_테스트 {
        
        @Test
        void 출석_시간을_수정한다() {
            // given
            LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 3, 9, 45);
            Attendance attendance = new Attendance(localDateTime);
            LocalTime newAttendanceTime = LocalTime.of(10, 6);
            
            // when
            Attendance result = attendance.withNewAttendanceTime(newAttendanceTime);
            
            // then
            assertThat(result).extracting(
                    "attendanceDateTime", "attendanceStatus"
            ).containsExactly(
                    LocalDateTime.of(2024, 12, 3, 10, 6), AttendanceStatus.지각
            );
        }
    }
    
    @Nested
    class 출석_날짜_동일_여부_판단_테스트 {
        
        @ParameterizedTest
        @ValueSource(strings = {"2024-12-03", "2025-01-23", "2025-05-19"})
        void 출석_날짜가_동일한지_판단한다(String date) {
            // given
            Attendance attendance = new Attendance(LocalDateTime.of(LocalDate.parse(date), LocalTime.of(9, 45)));
            LocalDate sameDay = LocalDate.parse(date);
            
            // when
            boolean result = attendance.isSameDay(sameDay);
            
            // then
            assertThat(result).isTrue();
        }
    }
}
