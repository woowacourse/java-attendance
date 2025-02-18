import domain.Attendance;
import dto.AttendanceModifyDTO;
import dto.AttendanceResultDTO;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AttendanceTest {
    
    @Nested
    class 등교_테스트 {
        
        @Test
        void 닉네임과_등교_시간을_입력하면_출석() {
            // given
            String inputName = "Lemon";
            LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 3, 10, 5);
            
            // when
            Attendance attendance = new Attendance(inputName, localDateTime);
            
            // then
            assertThat(attendance).extracting("attendanceTime").isEqualTo(LocalDateTime.of(2024, 12, 3, 10, 5));
            assertThat(attendance).extracting("attendanceStatus").isEqualTo("출석");
        }
        
        @Test
        void 출석_시간보다_5분_초과_늦으면_지각() {
            // given
            String inputName = "Lemon";
            LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 3, 10, 6);
            
            // when
            Attendance attendance = new Attendance(inputName, localDateTime);
            
            // then
            assertThat(attendance).extracting("attendanceStatus").isEqualTo("지각");
        }
        
        @Test
        void 출석_시간보다_5분_초과_30분_이하_늦으면_지각() {
            // given
            String inputName = "Lemon";
            LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 3, 10, 30);
            
            // when
            Attendance attendance = new Attendance(inputName, localDateTime);
            
            // then
            assertThat(attendance).extracting("attendanceStatus").isEqualTo("지각");
        }
        
        @Test
        void 출석_시간보다_30분_초과_늦으면_지각() {
            // given
            String inputName = "Lemon";
            LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 3, 10, 31);
            
            // when
            Attendance attendance = new Attendance(inputName, localDateTime);
            
            // then
            assertThat(attendance).extracting("attendanceStatus").isEqualTo("결석");
        }
        
        @Test
        void 주말에_출석하면_예외가_발생한다() {
            // given
            String inputName = "Lemon";
            LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 8, 10, 30);
            
            // expected
            assertThatThrownBy(() -> new Attendance(inputName, localDateTime))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("출석 가능한 날짜가 아닙니다.");
        }
        
        @Test
        void _8시와_23시_사이가_아니면_예외가_발생한다() {
            // given
            String inputName = "Lemon";
            LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 3, 7, 59);
            
            // expected
            assertThatThrownBy(() -> new Attendance(inputName, localDateTime))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("출석 가능한 시간이 아닙니다.");
        }
        
        @Test
        void 출석을_완료하면_출석기록이_출력된다() {
            // given
            String inputName = "Lemon";
            LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 3, 9, 45);
            Attendance attendance = new Attendance(inputName, localDateTime);
            
            // when
            AttendanceResultDTO result = attendance.createAttendanceResult();
            
            //then
            assertThat(result.getAttendanceTime()).isEqualTo(LocalDateTime.of(2024, 12, 3, 9, 45));
            assertThat(result.getAttendanceStatus()).isEqualTo("출석");
        }
    }
    
    @Nested
    class 수정_테스트 {
        
        @Test
        void 등교_시간을_수정한다() {
            //given
            String inputName = "Lemon";
            LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 3, 9, 45);
            Attendance attendance = new Attendance(inputName, localDateTime);
            LocalTime newAttendanceTime = LocalTime.of(10, 6);
            
            //when
            AttendanceModifyDTO result = attendance.modifyAttendanceTime(newAttendanceTime);
            
            //then
            assertThat(result.getAttendanceDate()).isEqualTo(LocalDate.of(2024, 12, 3));
            assertThat(result.getOldAttendanceTime()).isEqualTo(LocalTime.of(9, 45));
            assertThat(result.getOldAttendanceStatus()).isEqualTo("출석");
            assertThat(result.getNewAttendanceTime()).isEqualTo(LocalTime.of(10, 6));
            assertThat(result.getNewAttendanceStatus()).isEqualTo("지각");
        }
    }
}
