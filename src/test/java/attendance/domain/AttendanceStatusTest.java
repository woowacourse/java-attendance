package attendance.domain;

import static attendance.domain.AttendanceStatus.calculateTotalAbsentCount;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceStatusTest {

    @CsvSource(value = {
            "17,12,59,OK", "17,13,05,OK", "17,13,06,LATE", "17,13,30,LATE", "17,13,31,ABSENT",
            "18,9,59,OK", "18,10,05,OK", "18,10,06,LATE", "18,10,30,LATE", "18,10,31,ABSENT"
    })
    @ParameterizedTest
    void 출석_날짜와_시간을_알려주면_출석_상태를_알려준다(int day, int hour, int minute, AttendanceStatus expected) {
        AttendanceStatus status = AttendanceStatus.findByAttendanceDateTime(
                new AttendanceDate(LocalDate.of(2025, 2, day)),
                new AttendanceTime(LocalTime.of(hour, minute)));

        assertThat(status).isEqualTo(expected);
    }

    @Test
    void 출석_기록을_알려주면_출석_상태_횟수를_알려준다() {
        Attendances attendances = new Attendances(List.of(
                LocalDateTime.of(2025, 2, 3, 10, 0),
                LocalDateTime.of(2025, 2, 4, 10, 6),
                LocalDateTime.of(2025, 2, 5, 10, 31)
        ), LocalDateTime.of(2025, 2, 6, 10, 0));

        Map<String, Integer> statusCount = attendances.calculateStatusCount();

        assertThat(statusCount).containsKeys("출석", "지각", "결석")
                .containsValues(1, 1, 1);
    }

    @Test
    void 출석_상태를_알려주면_총_결석_횟수를_알려준다() {
        List<AttendanceStatus> statuses = List.of(AttendanceStatus.OK
                , AttendanceStatus.LATE, AttendanceStatus.LATE, AttendanceStatus.LATE,
                AttendanceStatus.ABSENT);

        assertThat(calculateTotalAbsentCount(statuses)).isEqualTo(2);
    }

}
