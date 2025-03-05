package domain;

import controller.AttendanceController;
import domain.attendance.Attendances;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {
    @DisplayName("해당 닉네임의 크루가 존재하는지 확인한다")
    @Test
    void test() {
        // given
        String nickname = "수양";
        AttendanceBook attendanceBook = new AttendanceBook(Map.of("수양", List.of(), "빙봉", List.of()),
                AttendanceController.START_DATE, AttendanceController.END_DATE);

        // when
        boolean isContain = attendanceBook.has(nickname);

        // then
        Assertions.assertThat(isContain).isTrue();
    }

    @DisplayName("전체 출석부에 경고 이상을 받은 사람을 반환한다")
    @Test
    void test2() {
        // given
        LocalDateTime dateTime = LocalDateTime.of(2025, 2, 25, 10, 0);
        Map<String, List<LocalDateTime>> crewsInfo = Map.of("수양", List.of(dateTime, dateTime.plusDays(1)));
        AttendanceBook attendanceBook = new AttendanceBook(crewsInfo, dateTime.toLocalDate(),
                dateTime.toLocalDate().plusDays(7));

        // when
        Map<String, Attendances> crews = attendanceBook.findWarningCrews();

        // then
        Assertions.assertThat(crews.keySet()).contains("수양");
    }
}
