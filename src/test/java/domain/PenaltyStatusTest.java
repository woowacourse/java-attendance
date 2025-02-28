package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class PenaltyStatusTest {

    private static Attendances attendances;

    @BeforeEach
    public void setAttendances() {
        attendances = new Attendances(Map.of(
                "짱수", List.of(
                        new Attendance(LocalDateTime.of(2025, 2, 3, 13, 5)),
                        new Attendance(LocalDateTime.of(2025, 2, 4, 10, 10)),
                        new Attendance(LocalDateTime.of(2025, 2, 5, 13, 31)),
                        new Attendance(LocalDateTime.of(2025, 2, 6, 10, 31)),
                        new Attendance(LocalDateTime.of(2025, 2, 7, 10, 50)))
        )
        );
    }

    @Test
    void 해당_크루의_제적_상태를_확인한다() {
        String nickname = "짱수";

        PenaltyStatus penaltyStatus = PenaltyStatus.findStatusByNickname(nickname, attendances);

        assertThat(penaltyStatus.getName()).isEqualTo("경고");
    }

}
