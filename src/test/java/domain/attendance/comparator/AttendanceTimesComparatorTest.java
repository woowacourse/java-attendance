package domain.attendance.comparator;

import static domain.testdata.AttendanceTestData.AttendanceTimesData.createNormalAttendanceTimes;
import static domain.testdata.AttendanceTestData.AttendanceTimesData.createWarnedAttendanceTimes;
import static org.assertj.core.api.Assertions.assertThat;

import domain.attendance.AttendanceTimes;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTimesComparatorTest {

    @Test
    @DisplayName("Comparator를 이용한 AttendanceTimes 비교")
    void compareAttendanceTimesTest() {
        // given
        AttendanceTimes normalAttendanceTimes = createNormalAttendanceTimes();
        AttendanceTimes warnedAttendanceTimes = createWarnedAttendanceTimes();
        AttendanceTimes sameWarnedAttendanceTimes = createWarnedAttendanceTimes();
        AttendanceTimesComparator attendanceTimesComparator = new AttendanceTimesComparator(
                LocalDate.of(2024, 12, 31)
        );

        // when
        int negative = attendanceTimesComparator.compare(normalAttendanceTimes, warnedAttendanceTimes);
        int positive = attendanceTimesComparator.compare(warnedAttendanceTimes, normalAttendanceTimes);
        int zero = attendanceTimesComparator.compare(warnedAttendanceTimes, sameWarnedAttendanceTimes);

        // then
        assertThat(negative).isLessThan(0);
        assertThat(positive).isGreaterThan(0);
        assertThat(zero).isEqualTo(0);
    }
}
