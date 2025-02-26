package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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

    @Test
    void 오늘_날짜까지_모두_채워넣는다() {
        List<LocalDateTime> inputDateTimes = List.of(LocalDateTime.of(2024, 12, 12, 9, 59));
        LocalDate today = LocalDate.of(2024, 12, 13);
        List<Attendance> attendances = AttendanceBookFactory.initAttendances(inputDateTimes, today);

        assertThat(attendances).hasSize(9);
    }
}