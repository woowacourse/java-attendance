package attendance.model.crew;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.attendance.status.AttendanceStatus;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CrewStatusTest {

    private static Stream<Arguments> fromAttendanceStatuses() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                AttendanceStatus.ABSENCE,
                                AttendanceStatus.ABSENCE,
                                AttendanceStatus.ABSENCE,
                                AttendanceStatus.ABSENCE,
                                AttendanceStatus.ABSENCE,
                                AttendanceStatus.ABSENCE
                        ),
                        CrewStatus.EXPULSION
                ),
                Arguments.of(
                        List.of(
                                AttendanceStatus.ABSENCE,
                                AttendanceStatus.ABSENCE,
                                AttendanceStatus.ABSENCE,
                                AttendanceStatus.ABSENCE,
                                AttendanceStatus.ABSENCE,
                                AttendanceStatus.LATE,
                                AttendanceStatus.LATE,
                                AttendanceStatus.LATE
                        ),
                        CrewStatus.EXPULSION
                ),
                Arguments.of(
                        List.of(
                                AttendanceStatus.ABSENCE,
                                AttendanceStatus.ABSENCE,
                                AttendanceStatus.ABSENCE,
                                AttendanceStatus.ABSENCE,
                                AttendanceStatus.ABSENCE,
                                AttendanceStatus.LATE,
                                AttendanceStatus.LATE
                        ),
                        CrewStatus.CONSULTATION
                ),
                Arguments.of(
                        List.of(
                                AttendanceStatus.ABSENCE,
                                AttendanceStatus.ABSENCE,
                                AttendanceStatus.ABSENCE,
                                AttendanceStatus.ABSENCE,
                                AttendanceStatus.ABSENCE,
                                AttendanceStatus.LATE,
                                AttendanceStatus.LATE
                        ),
                        CrewStatus.CONSULTATION
                ),
                Arguments.of(
                        List.of(
                                AttendanceStatus.ABSENCE,
                                AttendanceStatus.ABSENCE,
                                AttendanceStatus.ABSENCE
                        ),
                        CrewStatus.CONSULTATION
                ),
                Arguments.of(
                        List.of(
                                AttendanceStatus.ABSENCE,
                                AttendanceStatus.ABSENCE
                        ),
                        CrewStatus.WARNING
                ),
                Arguments.of(
                        List.of(
                                AttendanceStatus.ABSENCE
                        ),
                        CrewStatus.NORMAL
                )
        );
    }

    @DisplayName("출석 상태 리스트를 통해 크루 상태를 반환한다.")
    @ParameterizedTest
    @MethodSource("fromAttendanceStatuses")
    void fromAttendanceStatuses(final List<AttendanceStatus> attendanceStatuses, final CrewStatus expected) {

        // When
        final CrewStatus actual = CrewStatus.fromAttendanceStatuses(attendanceStatuses);

        // Then
        assertThat(actual).isEqualTo(expected);
    }
}
