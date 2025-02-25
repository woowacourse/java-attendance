package attendance.model.attendance;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.campus.CampusOperationPolicy;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AttendanceLogsTest {

    private static final CampusOperationPolicy campusOperationPolicy = new CampusOperationPolicy();
    private static AttendanceLogs attendanceLogs;

    @BeforeAll
    static void setUp() {
        List<AttendanceLog> values = new ArrayList<>();
        values.add(
                AttendanceLog.fromAttendanceDateTime(
                        LocalDateTime.of(2024, 12, 2, 13, 0),
                        campusOperationPolicy
                )
        );
        values.add(
                AttendanceLog.fromAttendanceDateTime(
                        LocalDateTime.of(2024, 12, 4, 10, 5),
                        campusOperationPolicy
                )
        );
        values.add(
                AttendanceLog.fromAttendanceDateTime(
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
                AttendanceLog.fromAttendanceDateTime(
                        LocalDateTime.of(2024, 12, 2, 13, 0),
                        campusOperationPolicy
                )
        );
        expected.add(
                AttendanceLog.fromAbsenceDate(
                        LocalDate.of(2024, 12, 3)
                )
        );
        expected.add(
                AttendanceLog.fromAttendanceDateTime(
                        LocalDateTime.of(2024, 12, 4, 10, 5),
                        campusOperationPolicy
                )
        );
        expected.add(
                AttendanceLog.fromAttendanceDateTime(
                        LocalDateTime.of(2024, 12, 5, 11, 0),
                        campusOperationPolicy
                )
        );

        // When
        final List<AttendanceLog> actual = attendanceLogs.getAllAttendanceLogs(from, to, campusOperationPolicy);

        // Then
        assertThat(actual).containsExactlyElementsOf(expected);
    }
}
