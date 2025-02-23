package attendance.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.EnumMap;

class AttendanceStatusTest {

    @Test
    void 전날까지의_출석_기록으로_객체를_생성한다() {
        // given
        Attendances attendances = new Attendances();

        LocalDateTime firstDateTime = LocalDateTime.of(2024, 12, 3, 10, 0);
        LocalDateTime secondDateTime = LocalDateTime.of(2024, 12, 3, 10, 6);
        LocalDateTime thirdDateTime = LocalDateTime.of(2024, 12, 3, 10, 31);

        attendances.addAttendance(firstDateTime);
        attendances.addAttendance(secondDateTime);
        attendances.addAttendance(thirdDateTime);

        // when
        AttendanceStatus attendanceStatus = new AttendanceStatus(attendances);
        EnumMap<AttendanceStatusType, Integer> result = attendanceStatus.getStatus();

        // then
        Assertions.assertThat(result.get(AttendanceStatusType.EXPULSION)).isEqualTo(1);
        Assertions.assertThat(result.get(AttendanceStatusType.LATE)).isEqualTo(1);
        Assertions.assertThat(result.get(AttendanceStatusType.ATTENDANCE)).isEqualTo(1);
        ;
    }
}
