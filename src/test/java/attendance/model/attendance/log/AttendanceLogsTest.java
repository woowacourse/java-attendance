package attendance.model.attendance.log;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import attendance.model.attendance.AttendanceStatus;
import attendance.model.campus.CampusOperationPolicy;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AttendanceLogsTest {

    private static final CampusOperationPolicy campusOperationPolicy = new CampusOperationPolicy();
    private static AttendanceLogs attendanceLogs;

    @BeforeAll
    static void setUp() {
        List<AttendanceLog> values = new ArrayList<>();
        values.add(
                AttendanceLog.fromDateTime(
                        LocalDateTime.of(2024, 12, 2, 13, 0),
                        campusOperationPolicy
                )
        );
        values.add(
                AttendanceLog.fromDateTime(
                        LocalDateTime.of(2024, 12, 4, 10, 6),
                        campusOperationPolicy
                )
        );
        values.add(
                AttendanceLog.fromDateTime(
                        LocalDateTime.of(2024, 12, 5, 11, 0),
                        campusOperationPolicy
                )
        );
        attendanceLogs = new AttendanceLogs(values);
    }

    @Test
    void getAllAttendanceLogs() {

        // Given
        final LocalDate from = LocalDate.of(2024, 12, 2);
        final LocalDate to = LocalDate.of(2024, 12, 5);
        final List<AttendanceLog> expected = new ArrayList<>();
        expected.add(
                AttendanceLog.fromDateTime(
                        LocalDateTime.of(2024, 12, 2, 13, 0),
                        campusOperationPolicy
                )
        );
        expected.add(
                AttendanceLog.fromAbsenceDate(
                        LocalDate.of(2024, 12, 3),
                        campusOperationPolicy
                )
        );
        expected.add(
                AttendanceLog.fromDateTime(
                        LocalDateTime.of(2024, 12, 4, 10, 6),
                        campusOperationPolicy
                )
        );
        expected.add(
                AttendanceLog.fromDateTime(
                        LocalDateTime.of(2024, 12, 5, 11, 0),
                        campusOperationPolicy
                )
        );

        // When
        final List<AttendanceLog> actual = attendanceLogs.getAllAttendanceLogs(from, to, campusOperationPolicy);

        // Then
        assertThat(actual).containsExactlyElementsOf(expected);
    }

    @Test
    void getAttendanceStatusStatistics() {

        // Given
        final LocalDate from = LocalDate.of(2024, 12, 2);
        final LocalDate to = LocalDate.of(2024, 12, 5);
        final Map<AttendanceStatus, Integer> expected = new LinkedHashMap<>();
        expected.put(AttendanceStatus.ATTENDANCE, 1);
        expected.put(AttendanceStatus.ABSENCE, 2);
        expected.put(AttendanceStatus.LATE, 1);

        // When
        final Map<AttendanceStatus, Integer> actual = attendanceLogs.getAttendanceStatusStatistics(
                from,
                to,
                campusOperationPolicy
        );

        // Then
        assertAll(
                () -> assertThat(actual).containsExactlyEntriesOf(expected), // 순서와 값 모두 검증
                () -> assertThat(actual.get(AttendanceStatus.ATTENDANCE)).isEqualTo(1),
                () -> assertThat(actual.get(AttendanceStatus.ABSENCE)).isEqualTo(2),
                () -> assertThat(actual.get(AttendanceStatus.LATE)).isEqualTo(1)
        );
    }
}
