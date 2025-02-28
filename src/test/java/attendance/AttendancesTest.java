package attendance;

import attendance.domain.Attendance;
import attendance.domain.Attendances;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AttendancesTest {

    @Test
    @DisplayName("이미 존재하는 날짜의 출석이 있으면 예외를 던진다")
    void addTest1() {
        Attendances attendances = new Attendances();
        attendances.add(new Attendance(LocalDateTime.of(2024,12,11,10,8)));

        assertThatThrownBy(() -> attendances.add(new Attendance(LocalDateTime.of(2024,12,11,10,8))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("출석을 추가할 수 있다.")
    void addTest2() {
        Attendances attendances = new Attendances();
        attendances.add(new Attendance(LocalDateTime.of(2024,12,11,10,8)));
        attendances.add(new Attendance(LocalDateTime.of(2024,12,12,10,8)));

        assertThat(attendances.getAttendances().size()).isEqualTo(2);
    }

    @Test
    void removeTest1() {
        Attendances attendances = new Attendances();
        attendances.add(new Attendance(LocalDateTime.of(2024,12,11,10,8)));
        attendances.add(new Attendance(LocalDateTime.of(2024,12,12,10,8)));

        assertThat(attendances.getAttendances().size()).isEqualTo(2);
    }
}