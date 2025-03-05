package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class AttendanceManagerTest extends BaseAttendanceTest {

    @Test
    void 등록되지_않은_닉네임으로_크루를_조회하면_예외가_발생한다() {
        AttendanceManager attendanceManager = new AttendanceManager(dateProviderDec13);
        assertThatThrownBy(() -> attendanceManager.findCrewExactlyByNickname("폰트"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    @Test
    void 닉네임으로_크루를_조회한다() {
        AttendanceManager attendanceManager = new AttendanceManager(dateProviderDec13);
        attendanceManager.addCrew("폰트", LocalDateTime.of(2024, 12, 13, 9, 59));

        assertThat(attendanceManager.findCrewExactlyByNickname("폰트").getNickname()).isEqualTo("폰트");
    }

    @Test
    void 크루_출석_기록을_추가할_때_크루가_있으면_시간만_추가한다() {
        AttendanceManager attendanceManager = new AttendanceManager(dateProviderDec13);

        attendanceManager.addCrew("이든", LocalDateTime.of(2024, 12, 13, 9, 59));
        attendanceManager.addCrew("이든", LocalDateTime.of(2024, 12, 14, 9, 59));
        assertThat(attendanceManager.getCrewSize()).isEqualTo(1);
    }

    @Test
    void 크루_출석_기록을_추가할_때_크루가_없으면_크루를_추가한다() {
        AttendanceManager attendanceManager = new AttendanceManager(dateProviderDec13);

        attendanceManager.addCrew("이든", LocalDateTime.of(2024, 12, 13, 9, 59));
        attendanceManager.addCrew("쿠키", LocalDateTime.of(2024, 12, 13, 9, 59));
        assertThat(attendanceManager.getCrewSize()).isEqualTo(2);
    }

    @Test
    void 크루_닉네임으로_출석_기록을_가져온다() {
        AttendanceManager attendanceManager = new AttendanceManager(dateProviderDec13);
        attendanceManager.addCrew("이든", LocalDateTime.of(2024, 12, 13, 9, 59));

        assertThat(attendanceManager.findAttendanceRecordByNickname("이든").findAttendanceTimeByDay(13))
                .isEqualTo(LocalDateTime.of(2024, 12, 13, 9, 59));
    }


}
