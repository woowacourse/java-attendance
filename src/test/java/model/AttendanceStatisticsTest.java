package model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AttendanceStatisticsTest {

    @Test
    void 출석_통계를_생성할_수_있다() {
        //given
        Crew crew = Crew.of("쿠키");
        Attendance attendance1 = Attendance.of(crew, LocalDateTime.of(2024, 12, 2, 9, 30));
        Attendance attendance2 = Attendance.of(crew, LocalDateTime.of(2024, 12, 3, 9, 30));
        Attendance attendance3 = Attendance.of(crew, LocalDateTime.of(2024, 12, 4, 10, 30));
        Attendances attendances = Attendances.of(List.of(attendance1, attendance2, attendance3));

        Map<AttendanceType, Integer> expected = Map.of(AttendanceType.SUCCESS, 2, AttendanceType.BE_LATE, 1,
                AttendanceType.ABSENCE, 0);
        //when
        AttendanceStatistics statistics = AttendanceStatistics.of(crew, expected);
        //then
        assertThat(statistics).extracting("statistics").isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource(value = "createAttendanceTypeCounts")
    void 출석_통계를_통해_제적_위험도를_알_수_있다(Map<AttendanceType, Integer> counts, PunishmentType expected) {
        //given
        AttendanceStatistics statistics = AttendanceStatistics.of(Crew.of("쿠키"), counts);
        //when
        PunishmentType actual = statistics.getPunishmentType();
        //then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> createAttendanceTypeCounts() {
        return Stream.of(
                Arguments.of(
                        Map.of(AttendanceType.SUCCESS, 0, AttendanceType.BE_LATE, 0, AttendanceType.ABSENCE, 0),
                        PunishmentType.NONE
                ),
                Arguments.of(
                        Map.of(AttendanceType.SUCCESS, 0, AttendanceType.BE_LATE, 3, AttendanceType.ABSENCE, 1),
                        PunishmentType.WARNING
                ),
                Arguments.of(
                        Map.of(AttendanceType.SUCCESS, 0, AttendanceType.BE_LATE, 0, AttendanceType.ABSENCE, 3),
                        PunishmentType.MEETING
                ),
                Arguments.of(
                        Map.of(AttendanceType.SUCCESS, 0, AttendanceType.BE_LATE, 0, AttendanceType.ABSENCE, 6),
                        PunishmentType.EXPULSION
                )
        );
    }


}