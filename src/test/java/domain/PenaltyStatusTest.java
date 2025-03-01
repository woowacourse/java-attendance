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
                        new Attendance(LocalDateTime.of(2025, 2, 5, 10, 0)),
                        new Attendance(LocalDateTime.of(2025, 2, 6, 10, 31)),
                        new Attendance(LocalDateTime.of(2025, 2, 7, 10, 50))),
                "이든", List.of(
                        new Attendance(LocalDateTime.of(2025, 2, 3, 13, 31)),
                        new Attendance(LocalDateTime.of(2025, 2, 4, 10, 31)),
                        new Attendance(LocalDateTime.of(2025, 2, 5, 10, 31)),
                        new Attendance(LocalDateTime.of(2025, 2, 6, 9, 58)),
                        new Attendance(LocalDateTime.of(2025, 2, 7, 10, 7))),
                "빙티", List.of(
                        new Attendance(LocalDateTime.of(2025, 2, 3, 13, 31)),
                        new Attendance(LocalDateTime.of(2025, 2, 4, 12, 0)),
                        new Attendance(LocalDateTime.of(2025, 2, 5, 13, 0)),
                        new Attendance(LocalDateTime.of(2025, 2, 6, 15, 0)),
                        new Attendance(LocalDateTime.of(2025, 2, 7, 14, 0)),
                        new Attendance(LocalDateTime.of(2025, 2, 10, 14, 0))),
                "쿠키", List.of(
                        new Attendance(LocalDateTime.of(2025, 2, 3, 13, 31)),
                        new Attendance(LocalDateTime.of(2025, 2, 4, 10, 0)),
                        new Attendance(LocalDateTime.of(2025, 2, 5, 9, 30)),
                        new Attendance(LocalDateTime.of(2025, 2, 6, 10, 5)),
                        new Attendance(LocalDateTime.of(2025, 2, 7, 10, 1)))
        ));
    }

    @Test
    void 해당_크루가_경고_대상자인지_확인한다() {
        String nickname = "짱수";

        PenaltyStatus penaltyStatus = PenaltyStatus.findStatusByNickname(nickname, attendances);

        assertThat(penaltyStatus.getName()).isEqualTo("경고");
    }

    @Test
    void 해당_크루가_면담_대상자인지_확인한다() {
        String nickname = "이든";

        PenaltyStatus penaltyStatus = PenaltyStatus.findStatusByNickname(nickname, attendances);

        assertThat(penaltyStatus.getName()).isEqualTo("면담");
    }

    @Test
    void 해당_크루가_제적_대상자인지_확인한다() {
        String nickname = "빙티";

        PenaltyStatus penaltyStatus = PenaltyStatus.findStatusByNickname(nickname, attendances);

        assertThat(penaltyStatus.getName()).isEqualTo("제적");
    }

    @Test
    void 해당_크루가_패널티_대상자가_아님을_확인한다() {
        String nickname = "쿠키";

        PenaltyStatus penaltyStatus = PenaltyStatus.findStatusByNickname(nickname, attendances);

        assertThat(penaltyStatus.getName()).isEqualTo("비대상자");
    }

}
