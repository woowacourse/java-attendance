package domain.attendance;

import domain.crew.Crew;
import domain.date.AttendanceDate;
import domain.date.AttendanceDateTime;
import java.time.DayOfWeek;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTypeCountTest {
    AttendanceHistory makePresentHistoryFrom(Crew crew, int day) {
        if (DayOfWeek.of(AttendanceDate.getDayOfWeek(day)) == DayOfWeek.MONDAY) {
            AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(day, 13, 0);
            return AttendanceHistory.of(crew, attendanceDateTime);
        }

        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(day, 10, 0);
        return AttendanceHistory.of(crew, attendanceDateTime);
    }

    AttendanceHistory makeLateHistoryFrom(Crew crew, int day) {
        if (DayOfWeek.of(AttendanceDate.getDayOfWeek(day)) == DayOfWeek.MONDAY) {
            AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(day, 13, 6);
            return AttendanceHistory.of(crew, attendanceDateTime);
        }

        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(day, 10, 6);
        return AttendanceHistory.of(crew, attendanceDateTime);
    }

    @DisplayName("AttendanceTypeCount 가 올바르게 생성되는지 확인한다.")
    @Test
    void attendanceTypeCountTest1() {
        // given
        Crew crew = Crew.from("히스타");
        AttendanceHistory attendanceHistory = makePresentHistoryFrom(crew, 2);
        List<AttendanceHistory> attendanceHistories = List.of(attendanceHistory);

        // when
        AttendanceTypeCount attendanceTypeCount = AttendanceTypeCount.from(3, attendanceHistories);

        // then
        Assertions.assertThat(attendanceTypeCount.getAttendanceCount()).isEqualTo(1);
        Assertions.assertThat(attendanceTypeCount.getLateCount()).isEqualTo(0);
        Assertions.assertThat(attendanceTypeCount.getAbsenceCount()).isEqualTo(0);
    }

    @DisplayName("AttendanceTypeCount 가 올바르게 생성되는지 확인한다.")
    @Test
    void attendanceTypeCountTest2() {
        // given
        Crew crew = Crew.from("히스타");
        AttendanceHistory attendanceHistory = makePresentHistoryFrom(crew, 2);
        AttendanceHistory attendanceHistory2 = makePresentHistoryFrom(crew, 3);
        AttendanceHistory attendanceHistory3 = makeLateHistoryFrom(crew, 4);
        List<AttendanceHistory> attendanceHistories = List.of(
                attendanceHistory,
                attendanceHistory2,
                attendanceHistory3
        );

        // when
        AttendanceTypeCount attendanceTypeCount = AttendanceTypeCount.from(7, attendanceHistories);

        // then
        Assertions.assertThat(attendanceTypeCount.getAttendanceCount()).isEqualTo(2);
        Assertions.assertThat(attendanceTypeCount.getLateCount()).isEqualTo(1);
        Assertions.assertThat(attendanceTypeCount.getAbsenceCount()).isEqualTo(2);
    }

    @DisplayName("getTotalAbsenceCount() 가 올바르게 동작하는지 확인한다.")
    @Test
    void getConsideredAbsenceTest1() {
        // given
        Crew crew = Crew.from("히스타");
        List<AttendanceHistory> attendanceHistories = List.of(
                makeLateHistoryFrom(crew, 2),
                makeLateHistoryFrom(crew, 3),
                makeLateHistoryFrom(crew, 4),
                makePresentHistoryFrom(crew, 5),
                makePresentHistoryFrom(crew, 6)
        );

        // when
        AttendanceTypeCount attendanceTypeCount = AttendanceTypeCount.from(7, attendanceHistories);

        // then
        // 지각 3회는 결석 1회이므로 expected = 1
        Assertions.assertThat(attendanceTypeCount.getTotalAbsenceCount()).isEqualTo(1);
    }
}
