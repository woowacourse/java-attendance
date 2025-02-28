package attendance.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

class AttendanceRecordTest {

    @Test
    @DisplayName("전날 출석 날짜, 시간과 출결 상황을 반환한다")
    void 전날_출석_날짜_시간과_출결_상황을_반환한다() {
        // given
        LocalDate nowDate = LocalDate.now();

        AttendanceRecord baseRecord = new AttendanceRecord(List.of(
                new Attendance(LocalDateTime.of(nowDate, LocalTime.of(13, 0))),
                new Attendance(LocalDateTime.of(nowDate.minusDays(1), LocalTime.of(13, 0))),
                new Attendance(LocalDateTime.of(nowDate.minusDays(2), LocalTime.of(13, 0)))
        ));

        List<Attendance> exceptedRecord = List.of(
                new Attendance(LocalDateTime.of(nowDate.minusDays(1), LocalTime.of(13, 0))),
                new Attendance(LocalDateTime.of(nowDate.minusDays(2), LocalTime.of(13, 0)))
        );

        // when
        List<Attendance> result = baseRecord.getRecordExcludingToday();

        // then
        Assertions.assertThat(result)
                .containsExactlyElementsOf(exceptedRecord);
    }
}
