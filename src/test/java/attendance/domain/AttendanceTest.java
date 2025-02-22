package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceTest {
    @DisplayName("이름이 동일한 Crew 객체로 해당 Attendance을 확인한다.")
    @MethodSource("provideAttendanceInstances")
    @ParameterizedTest
    void findCrewInfoFromAttendance(List<Attendance> attendances) {
        for (Attendance attendance : attendances) {
            assertThatCode(() -> attendance.isSameCrew(new Crew("리원"))).doesNotThrowAnyException();
        }
    }

    private static Stream<Arguments> provideAttendanceInstances() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                new Attendance(
                                        new Crew("리원"),
                                        LocalDateTime.of(2025, 2, 19, 9, 50, 0),
                                        AttendanceType.SAFE
                                ),
                                new Attendance(
                                        new Crew("리원"),
                                        LocalDateTime.of(2025, 2, 20, 9, 50, 0),
                                        AttendanceType.SAFE
                                )
                        )
                )
        );
    }

    @DisplayName("출석 시간이 바뀌면 그에 맞게 AttendanceType을 갱신한다.")
    @Test
    void changeAttendanceTypeForModifiedTime() {
        Attendance attendance = new Attendance(
                new Crew("엠제이"),
                LocalDateTime.of(2025, 2, 20, 9, 50, 0),
                AttendanceType.SAFE
        );

        attendance.modifyLocalDateTime(LocalDateTime.of(
                2025, 2, 20, 10, 6, 0)
        );

        assertThat(attendance.getType()).isEqualTo(AttendanceType.LATE);
    }
}
