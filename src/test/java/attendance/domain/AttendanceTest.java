package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceTest {
    @DisplayName("기능: 출석 시간이 동일한 날짜 객체로 해당 Attendance 비교")
    @MethodSource("provideAttendanceInstances")
    @ParameterizedTest
    void compareSameTimeFromAttendance(List<Attendance> attendances) {
        Attendance attendance = attendances.getFirst();

        assertThat(attendance.isSameTime(LocalDateTime.of(2025, 2, 19, 9, 50, 0))).isTrue();
        assertThat(attendance.isSameTime(LocalDateTime.of(2025, 2, 19, 9, 51, 0))).isFalse();
    }

    @DisplayName("기능: 이름이 동일한 Crew 객체로 해당 Attendance 비교")
    @MethodSource("provideAttendanceInstances")
    @ParameterizedTest
    void compareSameCrewFromAttendance(List<Attendance> attendances) {
        for (Attendance attendance : attendances) {
            assertThatCode(() -> attendance.isSameCrew(new Crew("리원"))).doesNotThrowAnyException();
            assertThat(attendance.isSameCrew(new Crew("리원"))).isTrue();
        }
    }

    @DisplayName("기능: 이름이 동일한 Crew 및 날짜 객체로 해당 Attendance 비교")
    @MethodSource("provideAttendanceInstances")
    @ParameterizedTest
    void compareSameCrewDateFromAttendance(List<Attendance> attendances) {
        Attendance attendance = attendances.getFirst();

        assertThat(attendance.isSameCrewDate(new Crew("리원"), LocalDate.of(2025, 2, 19))).isTrue();
        assertThat(attendance.isSameCrewDate(new Crew("리원"), LocalDate.of(2025, 2, 20))).isFalse();
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

    @DisplayName("기능: 변경된 출석 시간으로 헤당 Attendance의 AttendanceType 수정")
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

    @DisplayName("기능: 해당 Attendance의 날짜와 시간 및 출석 유형 정보 문자열로 반환")
    @Test
    void returnDateTimeAttendanceTypeFromAttendance() {
        Attendance attendance = new Attendance(
                new Crew("엠제이"),
                LocalDateTime.of(2025, 2, 20, 9, 50, 0),
                AttendanceType.SAFE
        );

        List.of("2", "20", "목", "09:50", "출석").forEach(
                info -> assertThat(attendance.getInfo()).contains(info)
        );
    }
}
