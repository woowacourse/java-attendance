import domain.AttendanceStatus;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SomeTest {
    // 월요일의 경우, 출석, 지각, 결석 상태 반환
    @DisplayName("월요일 정상 출석")
    @Test
    void test1() {
        // given
        LocalDateTime attendedTime =  LocalDateTime.of(2024, 12, 2, 13, 0);

        // when & then
        Assertions.assertThat(AttendanceStatus.getStatusByAttendedTime(attendedTime))
                .isEqualTo(AttendanceStatus.ATTEND);
    }

}
