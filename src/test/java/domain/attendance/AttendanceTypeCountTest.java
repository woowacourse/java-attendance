package domain.attendance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.crew.Crew;
import domain.date.AttendanceDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTypeCountTest {

    private final Crew crew = Crew.of("히로");

    @Test
    @DisplayName("기록이 존재하지 않는 날은 결석으로 간주한다")
    void countAsAbsenceWhenHistoryIsNotExisted() {
        // given
        int targetDay = 7;
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(2, 13, 0);
        AttendanceHistory attendanceHistory = AttendanceHistory.of(crew, attendanceDateTime);

        // when
        AttendanceTypeCount attendanceTypeCount = AttendanceTypeCount.from(targetDay, List.of(attendanceHistory));

        // then
        assertAll(
                () -> assertThat(attendanceTypeCount.getAbsenceCount()).isEqualTo(4),
                () -> assertThat(attendanceTypeCount.getLateCount()).isEqualTo(0)
        );
    }

    @Test
    @DisplayName("기록이 존재하지 않더라도 휴일인 날은 결석으로 간주하지 않는다")
    void countNotAsAbsenceWhenDayIsRestDay() {
        // given
        int targetDay = 9;
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(2, 13, 0);
        AttendanceHistory attendanceHistory = AttendanceHistory.of(crew, attendanceDateTime);

        // when
        AttendanceTypeCount attendanceTypeCount = AttendanceTypeCount.from(targetDay, List.of(attendanceHistory));

        // then
        assertAll(
                () -> assertThat(attendanceTypeCount.getAbsenceCount()).isEqualTo(4),
                () -> assertThat(attendanceTypeCount.getLateCount()).isEqualTo(0)
        );
    }

    @Test
    @DisplayName("올바르게 지각과 결석 횟수를 센다")
    void countLateCountAndAbsenceCount() {
        // given
        int targetDay = 7;
        List<AttendanceHistory> attendanceHistories = List.of(
                AttendanceHistory.of(crew, AttendanceDateTime.of(2, 13, 6)),
                AttendanceHistory.of(crew, AttendanceDateTime.of(3, 10, 6))
        );

        // when
        AttendanceTypeCount attendanceTypeCount = AttendanceTypeCount.from(targetDay, attendanceHistories);

        // then
        assertAll(
                () -> assertThat(attendanceTypeCount.getAbsenceCount()).isEqualTo(3),
                () -> assertThat(attendanceTypeCount.getLateCount()).isEqualTo(2)
        );
    }

    @Test
    @DisplayName("지각 3회를 결석 1회로 간주해 결석 횟수를 반환한다")
    void returnAbsenceCountConsideredLateCount() {
        // given
        int targetDay = 7;
        List<AttendanceHistory> attendanceHistories = List.of(
                AttendanceHistory.of(crew, AttendanceDateTime.of(2, 13, 6)),
                AttendanceHistory.of(crew, AttendanceDateTime.of(3, 10, 6)),
                AttendanceHistory.of(crew, AttendanceDateTime.of(4, 10, 6))
        );

        // when
        AttendanceTypeCount attendanceTypeCount = AttendanceTypeCount.from(targetDay, attendanceHistories);

        // then
        assertAll(
                () -> assertThat(attendanceTypeCount.getTotalAbsenceCount()).isEqualTo(3),
                () -> assertThat(attendanceTypeCount.getLateCount()).isEqualTo(3)
        );
    }

}
