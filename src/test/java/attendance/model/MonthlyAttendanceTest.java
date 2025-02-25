package attendance.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MonthlyAttendanceTest {

    @DisplayName("특정 날짜까지 출석 결과를 계산할 수 있다.")
    @Test
    void getAttendancesUntilDate() {
        //given
        Crew crew = new Crew("포비");
        MonthlyAttendance monthlyAttendance = new MonthlyAttendance(
                Month.DECEMBER,
                crew,
                List.of(
                        new Attendance(crew, LocalDateTime.of(2024, 12, 2, 10, 1)),
                        new Attendance(crew, LocalDateTime.of(2024, 12, 3, 10, 1))
                )
        );

        //when
        AttendanceResult attendanceResult = monthlyAttendance.calculateAttendanceResultUntilDate(
                LocalDate.of(2024, 12, 5));

        //then
        Assertions.assertThat(attendanceResult)
                .isEqualTo(new AttendanceResult(
                        crew,
                        Map.of(
                                AttendanceType.OK, 2,
                                AttendanceType.ABSENCE, 2
                        ),
                        List.of(
                                new Attendance(crew, LocalDateTime.of(2024, 12, 2, 10, 1)),
                                new Attendance(crew, LocalDateTime.of(2024, 12, 3, 10, 1)),
                                new Attendance(crew, LocalDate.of(2024, 12, 4), null),
                                new Attendance(crew, LocalDate.of(2024, 12, 5), null)
                        )
                ));
    }
}
