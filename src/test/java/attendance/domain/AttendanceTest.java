package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTest {
    @DisplayName("기능: 출석 시간이 동일한 날짜 객체로 해당 Attendance 비교")
    @Test
    void compareSameTimeFromAttendance() {
        Attendance attendance = new Attendance(
                new Crew("리원"),
                LocalDateTime.of(2025, 2, 19, 9, 50, 0),
                AttendanceType.SAFE
        );
        assertThat(attendance.isSameTime(LocalDateTime.of(2025, 2, 19, 9, 50, 0))).isTrue();
    }

    @DisplayName("기능: 이름이 동일한 Crew 객체로 해당 Attendance 비교")
    @Test
    void compareSameCrewFromAttendance() {
        List.of(
                new Attendance(
                        new Crew("리원"),
                        LocalDateTime.of(2025, 2, 19, 9, 50, 0),
                        AttendanceType.SAFE
                ),
                new Attendance(
                        new Crew("리원"),
                        LocalDateTime.of(2025, 2, 20, 10, 7, 0),
                        AttendanceType.LATE
                )
        ).forEach(attendance -> {
            assertThat(attendance.isSameCrew(new Crew("리원"))).isTrue();
        });
    }

    @DisplayName("기능: 이름이 동일한 Crew 및 날짜 객체로 해당 Attendance 비교")
    @Test
    void compareSameCrewDateFromAttendance() {
        Attendance attendance = new Attendance(
                new Crew("리원"),
                LocalDateTime.of(2025, 2, 19, 9, 50, 0),
                AttendanceType.SAFE
        );
        assertThat(attendance.isSameCrewDate(new Crew("리원"), LocalDate.of(2025, 2, 19))).isTrue();
    }

    @DisplayName("기능: 변경된 출석 시간으로 헤당 AttendanceType 수정")
    @Test
    void changeAttendanceTypeForModifiedTime() {
        Attendance attendance = new Attendance(
                new Crew("엠제이"),
                LocalDateTime.of(2025, 2, 20, 9, 50, 0),
                AttendanceType.SAFE
        );

        attendance.modifyLocalDateTime(LocalDateTime.of(
                2025, 2, 20, 10, 6, 0)
        );

        assertThat(attendance.getType()).isEqualTo(AttendanceType.LATE);
    }

    @DisplayName("기능: 해당 Attendance 날짜와 시간 및 출석 유형 정보 문자열로 반환")
    @Test
    void returnDateTimeAttendanceTypeFromAttendance() {
        Attendance attendance = new Attendance(
                new Crew("엠제이"),
                LocalDateTime.of(2025, 2, 20, 9, 50, 0),
                AttendanceType.SAFE
        );

        List.of("2", "20", "목", "09:50", "출석").forEach(
                info -> assertThat(attendance.getInfo()).contains(info)
        );
    }
}
