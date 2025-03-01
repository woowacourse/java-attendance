package attendance.domain;

import attendance.controller.util.AttendanceBookFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class AttendanceBookFactoryTest {

    @Test
    void 입력된_데이터를_통해_AttendanceBook객체를_만든다() {
        List<LocalDateTime> attendances = List.of(LocalDateTime.of(2024, 12, 12, 9, 59));
        Map<String, List<LocalDateTime>> input = Map.of("훌라", attendances);
        LocalDate today = LocalDate.of(2024, 12, 13);
        AttendanceBook attendanceBook = AttendanceBookFactory.create(input, today);

        assertThat(attendanceBook).isNotNull();
    }
}