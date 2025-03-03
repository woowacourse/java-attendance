package domain.attendance;

import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceWarningTest {
    @DisplayName("지각 누적이 2회 이상이 되면 경고 상태이다.")
    @Test
    void test() {
        // given
        LocalDateTime attendanceDate = LocalDateTime.of(2025, 3, 3, 13, 0);
        Attendances attendances = new Attendances(List.of(attendanceDate),
                attendanceDate.toLocalDate(),
                attendanceDate.toLocalDate().plusDays(3));
        int countAbsence = attendances.countAbsencePerTardy() + attendances.countAbsence();

        // when
        AttendanceWarning attendanceWarning = AttendanceWarning.calculateWarning(countAbsence);

        // then
        Assertions.assertThat(attendanceWarning).isEqualTo(AttendanceWarning.WARNING);
    }
}