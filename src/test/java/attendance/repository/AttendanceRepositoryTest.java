package attendance.repository;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.domain.Attendance;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceRepositoryTest {

    @DisplayName("출결 관리에서 출석부를 생성한다.")
    @Test
    void 출결_관리에서_출석부를_생성한다() {

        // given
        List<Attendance> attendances = List.of(
                new Attendance("a", LocalDateTime.now()),
                new Attendance("b", LocalDateTime.now()),
                new Attendance("a", LocalDateTime.now())
        );
        AttendanceRepository attendanceRepository = new AttendanceRepository(attendances);
        // when
        Set<String> uniqueNames = attendanceRepository.getUniqueNames();
        // then
        assertThat(uniqueNames.size()).isEqualTo(2);
    }
}