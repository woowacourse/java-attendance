package attendance.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;

import static attendance.domain.AttendanceStateType.ATTENDANCE;
import static attendance.domain.AttendanceStateType.EXPULSION;
import static attendance.domain.AttendanceStateType.LATE;

class AttendanceStatusTest {

    @Test
    void 전날까지의_출석_기록으로_객체를_생성한다() {
        // given
        Attendances attendances = createAttendances(List.of(
                LocalDateTime.of(2024, 12, 3, 10, 0),
                LocalDateTime.of(2024, 12, 3, 10, 6),
                LocalDateTime.of(2024, 12, 3, 10, 31)
        ));

        // when
        AttendanceStatus attendanceStatus = new AttendanceStatus(attendances);
        EnumMap<AttendanceStateType, Integer> result = attendanceStatus.getStatus();

        // then
        Assertions.assertThat(result.get(EXPULSION)).isEqualTo(1);
        Assertions.assertThat(result.get(LATE)).isEqualTo(1);
        Assertions.assertThat(result.get(ATTENDANCE)).isEqualTo(1);
    }

    private Attendances createAttendances(List<LocalDateTime> dateTimes) {
        Attendances attendances = new Attendances();

        for (LocalDateTime dateTime : dateTimes) {
            attendances.addAttendance(dateTime);
        }
        return attendances;
    }
}
