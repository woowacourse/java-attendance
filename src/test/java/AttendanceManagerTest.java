import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.AttendanceManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AttendanceManagerTest {

    private LocalDate weekday;
    private LocalDate weekend;

    @BeforeEach
    void setUp() {
        weekday = LocalDate.of(2024, 12, 13);
        weekend = LocalDate.of(2024, 12, 14);
    }

    @Test
    void 등록되지_않은_닉네임의_출석을_등록하면_예외가_발생한다() {
        assertThatThrownBy(() -> new AttendanceManager(() -> weekday).attend("이든", LocalTime.of(9, 59)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    @Test
    void 등록되지_않은_닉네임으로_크루를_조회하면_예외가_발생한다() {
        AttendanceManager attendanceManager = new AttendanceManager(() -> weekday);
        assertThatThrownBy(() -> attendanceManager.findCrewExactlyByNickname("폰트"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 등록되지 않은 닉네임입니다.");
    }

    @Test
    void 닉네임으로_크루를_조회한다() {
        AttendanceManager attendanceManager = new AttendanceManager(() -> weekday);
        attendanceManager.addCrew("폰트", LocalDateTime.of(2024, 12, 13, 9, 59));

        assertThat(attendanceManager.findCrewExactlyByNickname("폰트").getNickname()).isEqualTo("폰트");
    }

    @Test
    void 크루의_출석을_등록한다() {
        AttendanceManager attendanceManager = new AttendanceManager(() -> weekday);
        attendanceManager.addCrew("이든", LocalDateTime.of(2024, 12, 12, 10, 0));

        LocalDateTime attendanceTime = attendanceManager.attend("이든", LocalTime.of(9, 59));

        assertThat(attendanceTime).isEqualTo(LocalDateTime.of(2024, 12, 13, 9, 59));
    }

    @Test
    void 크루_출석_기록을_추가할_때_크루가_있으면_시간만_추가한다() {
        AttendanceManager attendanceManager = new AttendanceManager(() -> weekday);

        attendanceManager.addCrew("이든", LocalDateTime.of(2024, 12, 13, 9, 59));
        attendanceManager.addCrew("이든", LocalDateTime.of(2024, 12, 14, 9, 59));
        assertThat(attendanceManager.getCrewSize()).isEqualTo(1);
    }

    @Test
    void 크루_출석_기록을_추가할_때_크루가_없으면_크루를_추가한다() {
        AttendanceManager attendanceManager = new AttendanceManager(() -> weekday);

        attendanceManager.addCrew("이든", LocalDateTime.of(2024, 12, 13, 9, 59));
        attendanceManager.addCrew("쿠키", LocalDateTime.of(2024, 12, 13, 9, 59));
        assertThat(attendanceManager.getCrewSize()).isEqualTo(2);
    }

    @Test
    void 크루_닉네임으로_출석_기록을_가져온다() {
        AttendanceManager attendanceManager = new AttendanceManager(() -> weekday);
        attendanceManager.addCrew("이든", LocalDateTime.of(2024, 12, 13, 9, 59));

        assertThat(attendanceManager.findAttendanceRecordByNickname("이든").findAttendanceTimeByDay(13))
                .isEqualTo(LocalDateTime.of(2024, 12, 13, 9, 59));
    }


}
