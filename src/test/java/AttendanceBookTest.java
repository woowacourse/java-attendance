import domain.Attendance;
import domain.AttendanceBook;
import domain.MemberAttendances;
import dto.AttendanceResultDTO;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class AttendanceBookTest {
    
    private final Map<String, MemberAttendances> attendancesMap = Map.of(
            "Lemon", new MemberAttendances("Lemon", List.of(
                    new Attendance(LocalDateTime.of(2024, 12, 2, 10, 0)),
                    new Attendance(LocalDateTime.of(2024, 12, 3, 10, 1)),
                    new Attendance(LocalDateTime.of(2024, 12, 4, 10, 5)),
                    new Attendance(LocalDateTime.of(2024, 12, 5, 10, 6)))),
            "Dompoo", new MemberAttendances("Dompoo", List.of(
                    new Attendance(LocalDateTime.of(2024, 12, 6, 10, 15)),
                    new Attendance(LocalDateTime.of(2024, 12, 10, 10, 30)),
                    new Attendance(LocalDateTime.of(2024, 12, 11, 10, 31)),
                    new Attendance(LocalDateTime.of(2024, 12, 12, 10, 32))))
    );
    
    @Nested
    class 출석_테스트 {
        
        @Test
        void 닉네임과_날짜를_입력하면_출석이_기록된다() {
            // given
            String inputName = "Lemon";
            LocalDateTime attendDateTime = LocalDateTime.of(2024, 12, 13, 10, 2);
            AttendanceBook attendanceBook = new AttendanceBook(attendancesMap);
            
            // expected
            assertThatCode(() -> attendanceBook.addAttendance(inputName, attendDateTime)).doesNotThrowAnyException();
        }
        
        @Test
        void 출석이_기록되면_정보를_확인할수_있다() {
            // given
            String inputName = "Lemon";
            LocalDateTime attendDateTime = LocalDateTime.of(2024, 12, 13, 10, 2);
            AttendanceBook attendanceBook = new AttendanceBook(attendancesMap);
            
            // when
            AttendanceResultDTO attendanceResultDTO = attendanceBook.addAttendance(inputName, attendDateTime);
            
            // then
            assertThat(attendanceResultDTO.getAttendanceStatus()).isEqualTo("출석");
            assertThat(attendanceResultDTO.getAttendanceTime()).isEqualTo(LocalDateTime.of(2024, 12, 13, 10, 2));
        }
        
        @Test
        void 존재하지_않는_멤버를_입력시_예외() {
            // given
            String inputName = "WANNI";
            LocalDateTime attendDateTime = LocalDateTime.of(2024, 12, 13, 10, 2);
            AttendanceBook attendanceBook = new AttendanceBook(attendancesMap);
            
            // expected
            assertThatThrownBy(() -> attendanceBook.addAttendance(inputName, attendDateTime))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("해당 멤버는 존재하지 않습니다.");
        }
        
        @Test
        void 공휴일에_출석시_예외() {
            // given
            String inputName = "Lemon";
            LocalDateTime attendDateTime = LocalDateTime.of(2024, 12, 1, 10, 2);
            AttendanceBook attendanceBook = new AttendanceBook(attendancesMap);
            
            // expected
            assertThatThrownBy(() -> attendanceBook.addAttendance(inputName, attendDateTime))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("출석 가능한 날짜가 아닙니다.");
        }
        
        @Test
        void 지정된_시간이_아닐_때_출석시_예외() {
            // given
            String inputName = "Lemon";
            LocalDateTime attendDateTime = LocalDateTime.of(2024, 12, 3, 7, 30);
            AttendanceBook attendanceBook = new AttendanceBook(attendancesMap);
            
            // expected
            assertThatThrownBy(() -> attendanceBook.addAttendance(inputName, attendDateTime))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage("출석 가능한 시간이 아닙니다.");
        }
    }
    
    
}
