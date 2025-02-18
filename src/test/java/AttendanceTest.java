import domain.Attendance;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AttendanceTest {

    @Test
    void 닉네임과_등교_시간을_입력하면_출석() {
        // given
        String inputName = "Lemon";
        LocalDateTime localDateTime = LocalDateTime.of(2024,12,3,10,5);

        // when
        Attendance attendance = new Attendance(inputName,localDateTime);

        // then
        assertThat(attendance).extracting("name").isEqualTo("Lemon");
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
        LocalDateTime localDateTime = LocalDateTime.of(2024,12,8,10,30);

        // expected
        assertThatThrownBy(() -> new Attendance(inputName,localDateTime))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석 가능한 날짜가 아닙니다.");
    }

    @Test
    void _8시와_23시_사이가_아니면_예외가_발생한다() {
        // given
        String inputName = "Lemon";
        LocalDateTime localDateTime = LocalDateTime.of(2024,12,3,7,59);

        // expected
        assertThatThrownBy(() -> new Attendance(inputName,localDateTime))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석 가능한 시간이 아닙니다.");
    }
}
