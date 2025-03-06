import static org.assertj.core.api.Assertions.assertThat;

import domain.Attendance;
import domain.AttendanceRecords;
import domain.AttendanceStatus;
import domain.AttendanceStatusCalculator;
import domain.AttendanceStatusCounter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceStatusCalculatorTest {

    @DisplayName("크루의 누적 출석, 지각 그리고 결석 횟수를 계산한다")
    @Test
    void calculateAttendanceStatusCount() {
        // given
        String crewName = "웨이드";
        LocalDate localDate1 = LocalDate.of(2024, 12, 10);
        LocalDate localDate2 = LocalDate.of(2024, 12, 11);
        LocalDate localDate3 = LocalDate.of(2024, 12, 12);
        LocalDate localDate4 = LocalDate.of(2024, 12, 13);
        Attendance attendance = Attendance.createAbsenceAttendance(localDate4);
        List<Attendance> attendances = new ArrayList<>(List.of(
                AttendanceFixture.createAttendance(localDate1, 10, 0),
                AttendanceFixture.createAttendance(localDate2, 10, 7),
                AttendanceFixture.createAttendance(localDate3, 10, 31),
                attendance
        ));

        AttendanceStatusCalculator calculator = new AttendanceStatusCalculator();
        // when
        AttendanceStatusCounter result = calculator.calculateAllCount(new AttendanceRecords(attendances));

        // then
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(result.retrieveAttendanceStatusCount(AttendanceStatus.ATTENDANCE)).isEqualTo(1);
            softly.assertThat(result.retrieveAttendanceStatusCount(AttendanceStatus.LATE)).isEqualTo(1);
            softly.assertThat(result.retrieveAttendanceStatusCount(AttendanceStatus.ABSENCE)).isEqualTo(2);
        });
    }
}
