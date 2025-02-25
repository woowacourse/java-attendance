package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceTest {
    @DisplayName("출석 시간을 보고 알맞는 타입을 계산한다.")
    @MethodSource("provideAttendances")
    @ParameterizedTest
    void calculateAttendanceType(List<Attendance> attendances) {
        for (Attendance attendance : attendances) {
            attendance.calculateAttendanceType();
        }

        assertAll(
                () -> assertThat(attendances.get(0).getType()).isEqualTo(AttendanceType.ABSENT),
                () -> assertThat(attendances.get(1).getType()).isEqualTo(AttendanceType.LATE),
                () -> assertThat(attendances.get(2).getType()).isEqualTo(AttendanceType.PRESENT)
        );

    }

    private static Stream<Arguments> provideAttendances() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:m");
        return Stream.of(
                Arguments.of(
                        List.of(
                                new Attendance(
                                        LocalDateTime.parse("2025-02-14 13:03", dateTimeFormatter)
                                ),
                                new Attendance(
                                        LocalDateTime.parse("2025-02-13 10:08", dateTimeFormatter)
                                ),
                                new Attendance(
                                        LocalDateTime.parse("2025-02-12 9:49", dateTimeFormatter)
                                )
                        )
                )
        );
    }
}
