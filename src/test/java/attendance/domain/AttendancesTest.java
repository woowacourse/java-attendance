package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

class AttendancesTest {

    @Test
    void 찾으려는_날짜를_입력하면_출석_기록을_찾아준다() {
        // given
        List<LocalDateTime> attendanceDateTimes = List.of(LocalDateTime.of(2025, 2, 3, 10, 0));
        LocalDateTime today = LocalDateTime.of(2025, 2, 4, 10, 0);
        Attendances attendances = new Attendances(attendanceDateTimes, today);

        // when & then
        assertThat(attendances.findAttendanceByLocalDate(LocalDate.of(2025, 2, 3))).isNotNull();
    }

    @Test
    void 오늘_날짜를_알려주면_전날까지의_해당_크루의_출석_기록을_알려준다() {
        // Given
        List<LocalDateTime> attendanceDateTimes = List.of(LocalDateTime.of(2025, 2, 3, 10, 0));
        LocalDateTime today = LocalDateTime.of(2025, 2, 6, 10, 0);
        Attendances attendances = new Attendances(attendanceDateTimes, today);

        // When & Then
        assertThat(attendances.findAllBeforeToday(today)).hasSize(3);
    }

}
