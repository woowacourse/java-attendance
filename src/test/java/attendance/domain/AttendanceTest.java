package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTest {
    private Attendance attendance;

    @BeforeEach
    void setUp() {
        attendance = new Attendance(
                new Crew("리원"),
                LocalDateTime.of(2025, 2, 19, 9, 50, 0),
                AttendanceType.SAFE
        );
    }

    @DisplayName("기능: 동일한 출석 날짜 및 시간에 대한 출석 객체 비교 확인")
    @Test
    void compareSameTimeFromAttendance() {
        assertThat(attendance.isSameTime(LocalDateTime.of(2025, 2, 19, 9, 50, 0))).isTrue();
    }

    @DisplayName("기능: 동일한 크루 이름에 대한 출석 객체 비교 확인")
    @Test
    void compareSameCrewFromAttendance() {
        assertThat(attendance.isSameCrew(new Crew("리원"))).isTrue();
    }

    @DisplayName("기능: 동일한 크루 이름 및 날짜 객체에 대한 출석 객체 비교 확인")
    @Test
    void compareSameCrewDateFromAttendance() {
        assertThat(attendance.isSameCrewDate(new Crew("리원"), LocalDate.of(2025, 2, 19))).isTrue();
    }

    @DisplayName("기능: 출석 시간 수정에 따른 출석 유형 갱신 확인")
    @Test
    void changeAttendanceTypeForModifiedTime() {
        attendance.modifyLocalDateTime(LocalDateTime.of(
                2025, 2, 19, 10, 6, 0)
        );

        assertThat(attendance.getType()).isEqualTo(AttendanceType.LATE);
    }
}
