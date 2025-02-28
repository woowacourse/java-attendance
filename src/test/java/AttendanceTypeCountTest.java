import java.time.LocalDate;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTypeCountTest {

    @Test
    @DisplayName("날짜에 따른 출석 타입을 이용해 각 출석 타입의 개수를 올바르게 센다")
    void test1() {
        // given
        Map<LocalDate, AttendanceType> attendanceTypeOfDates = Map.of(
                LocalDate.of(2024, 12, 23), AttendanceType.PRESENT,
                LocalDate.of(2024, 12, 24), AttendanceType.ABSENCE,
                LocalDate.of(2024, 12, 26), AttendanceType.LATE,
                LocalDate.of(2024, 12, 27), AttendanceType.LATE
        );

        // when
        AttendanceTypeCount attendanceTypeCount = AttendanceTypeCount.createFrom(attendanceTypeOfDates);

        // then
        Assertions.assertAll(
                () -> org.assertj.core.api.Assertions.assertThat(attendanceTypeCount.getAbsenceCount()).isEqualTo(1),
                () -> org.assertj.core.api.Assertions.assertThat(attendanceTypeCount.getLateCount()).isEqualTo(2)
        );
    }
}
