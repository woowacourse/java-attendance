package attendance.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

import static attendance.domain.AttendanceState.ABSENCE;
import static attendance.domain.AttendanceState.ATTENDANCE;
import static attendance.domain.AttendanceState.LATE;
import static org.assertj.core.api.Assertions.assertThat;

class AttendanceStatusTest {

    @Test
    void 출석_기록으로_객체를_생성한다() {
        // given
        List<Attendance> attendances = createAttendances(List.of(
                LocalDateTime.of(2024, 12, 3, 10, 0),
                LocalDateTime.of(2024, 12, 4, 10, 6),
                LocalDateTime.of(2024, 12, 5, 10, 31)
        ));

        // when
        AttendanceStatus attendanceStatus = new AttendanceStatus(attendances);
        EnumMap<AttendanceState, Integer> result = attendanceStatus.getStatus();

        // then
        assertThat(result.get(ABSENCE)).isEqualTo(1);
        assertThat(result.get(LATE)).isEqualTo(1);
        assertThat(result.get(ATTENDANCE)).isEqualTo(1);
    }

    @Test
    void 위험자_상태가_높은_경우_양수를_반환한다() {
        // given
        List<Attendance> origin = createAttendances(List.of(
                LocalDateTime.of(2024, 12, 3, 18, 0),
                LocalDateTime.of(2024, 12, 4, 18, 0),
                LocalDateTime.of(2024, 12, 5, 18, 0)
        ));

        List<Attendance> compared = createAttendances(List.of(
                LocalDateTime.of(2024, 12, 3, 10, 0),
                LocalDateTime.of(2024, 12, 4, 10, 0),
                LocalDateTime.of(2024, 12, 5, 10, 0)
        ));

        // when
        AttendanceStatus originStatus = new AttendanceStatus(origin);
        AttendanceStatus comparedStatus = new AttendanceStatus(compared);

        // then
        assertThat(originStatus.compareTo(comparedStatus))
                .isPositive();
    }

    @Test
    void 위험자_상태가_낮은_경우_음수를_반환한다() {
        // given
        List<Attendance> origin = createAttendances(List.of(
                LocalDateTime.of(2024, 12, 3, 10, 0),
                LocalDateTime.of(2024, 12, 4, 10, 0),
                LocalDateTime.of(2024, 12, 5, 10, 0)
        ));

        List<Attendance> compared = createAttendances(List.of(
                LocalDateTime.of(2024, 12, 3, 18, 0),
                LocalDateTime.of(2024, 12, 4, 18, 0),
                LocalDateTime.of(2024, 12, 5, 18, 0)
        ));

        // when
        AttendanceStatus originStatus = new AttendanceStatus(origin);
        AttendanceStatus comparedStatus = new AttendanceStatus(compared);

        // then
        assertThat(originStatus.compareTo(comparedStatus))
                .isNegative();
    }

    @Test
    void 출결에_결석_및_지각이_더_적은_경우_양수를_반환한다() {
        // given
        List<Attendance> origin = createAttendances(List.of(
                LocalDateTime.of(2024, 12, 3, 18, 0),
                LocalDateTime.of(2024, 12, 4, 18, 0),
                LocalDateTime.of(2024, 12, 5, 10, 0)
        ));

        List<Attendance> compared = createAttendances(List.of(
                LocalDateTime.of(2024, 12, 3, 18, 0),
                LocalDateTime.of(2024, 12, 4, 18, 0),
                LocalDateTime.of(2024, 12, 5, 10, 6)
        ));

        // when
        AttendanceStatus originStatus = new AttendanceStatus(origin);
        AttendanceStatus comparedStatus = new AttendanceStatus(compared);

        // then
        assertThat(originStatus.compareTo(comparedStatus))
                .isPositive();
    }

    @Test
    void 출결에_결석_및_지각이_더_많은_경우_음수를_반환한다() {
        // given
        List<Attendance> origin = createAttendances(List.of(
                LocalDateTime.of(2024, 12, 3, 18, 0),
                LocalDateTime.of(2024, 12, 4, 18, 0),
                LocalDateTime.of(2024, 12, 5, 10, 6)
        ));

        List<Attendance> compared = createAttendances(List.of(
                LocalDateTime.of(2024, 12, 3, 18, 0),
                LocalDateTime.of(2024, 12, 4, 18, 0),
                LocalDateTime.of(2024, 12, 5, 10, 0)
        ));

        // when
        AttendanceStatus originStatus = new AttendanceStatus(origin);
        AttendanceStatus comparedStatus = new AttendanceStatus(compared);

        // then
        assertThat(originStatus.compareTo(comparedStatus))
                .isNegative();
    }

    @Test
    void 출결_상황이_동일한_경우_0을_반환한다() {
        // given
        List<Attendance> origin = createAttendances(List.of(
                LocalDateTime.of(2024, 12, 3, 10, 0),
                LocalDateTime.of(2024, 12, 4, 10, 0),
                LocalDateTime.of(2024, 12, 5, 10, 0)
        ));

        List<Attendance> compared = createAttendances(List.of(
                LocalDateTime.of(2024, 12, 3, 10, 0),
                LocalDateTime.of(2024, 12, 4, 10, 0),
                LocalDateTime.of(2024, 12, 5, 10, 0)
        ));

        // when
        AttendanceStatus originStatus = new AttendanceStatus(origin);
        AttendanceStatus comparedStatus = new AttendanceStatus(compared);

        // then
        assertThat(originStatus.compareTo(comparedStatus))
                .isZero();
    }

    private List<Attendance> createAttendances(List<LocalDateTime> dateTimes) {
        List<Attendance> attendances = new ArrayList<>();

        for (LocalDateTime dateTime : dateTimes) {
            attendances.add(new Attendance(dateTime));
        }
        return attendances;
    }
}
