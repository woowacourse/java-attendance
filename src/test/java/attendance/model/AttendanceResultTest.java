package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AttendanceResultTest {

    @DisplayName("출석 결과를 생성할 수 있다.")
    @Test
    void createAttendanceResult() {
        //given
        Crew crew = new Crew("포비");
        List<Attendance> attendances = List.of(
                new Attendance(crew, LocalDateTime.of(2024, 12, 2, 10, 1)),
                new Attendance(crew, LocalDateTime.of(2024, 12, 3, 13, 1)),
                new Attendance(crew, LocalDateTime.of(2024, 12, 4, 10, 6))
        );

        //when
        AttendanceResult attendanceResult = AttendanceResult.create(crew, attendances);

        //then
        assertThat(attendanceResult)
                .isEqualTo(new AttendanceResult(
                        crew,
                        Map.of(
                                AttendanceType.OK, 1,
                                AttendanceType.LATE, 1,
                                AttendanceType.ABSENCE, 1
                        ),
                        List.of(
                                new Attendance(crew, LocalDateTime.of(2024, 12, 2, 10, 1)),
                                new Attendance(crew, LocalDateTime.of(2024, 12, 3, 13, 1)),
                                new Attendance(crew, LocalDateTime.of(2024, 12, 4, 10, 6))
                        )
                ));
    }

    @DisplayName("출석 결과를 비교할 수 있다.")
    @MethodSource("generate")
    @ParameterizedTest
    void compareTo(AttendanceResult attendanceResult1, AttendanceResult attendanceResult2,
                   Predicate<Integer> condition) {
        //given //when
        int actual = attendanceResult1.compareTo(attendanceResult2);

        //then
        Assertions.assertThat(condition.test(actual)).isTrue();
    }

    @DisplayName("출석 경고 레벨을 알 수 있다.")
    @Test
    void getAttendanceWarningLevel() {
        //given
        AttendanceResult attendanceResult = new AttendanceResult(
                new Crew("pobi"),
                Map.of(
                        AttendanceType.ABSENCE, 3
                ),
                null
        );

        //when
        AttendanceWarningLevel result = attendanceResult.getAttendanceWarningLevel();

        //then
        assertThat(result).isEqualTo(AttendanceWarningLevel.MEETING);
    }

    private static Stream<Arguments> generate() {
        return Stream.of(
                Arguments.of(
                        new AttendanceResult(
                                new Crew("pobi"),
                                Map.of(
                                        AttendanceType.ABSENCE, 3
                                ),
                                null
                        ),
                        new AttendanceResult(
                                new Crew("neo"),
                                Map.of(
                                        AttendanceType.ABSENCE, 2
                                ),
                                null
                        ),
                        (Predicate<Integer>) result -> result < 0
                ),
                Arguments.of(
                        new AttendanceResult(
                                new Crew("pobi"),
                                Map.of(
                                        AttendanceType.LATE, 3,
                                        AttendanceType.ABSENCE, 3
                                ),
                                null
                        ),
                        new AttendanceResult(
                                new Crew("neo"),
                                Map.of(
                                        AttendanceType.LATE, 2,
                                        AttendanceType.ABSENCE, 3
                                ),
                                null
                        ),
                        (Predicate<Integer>) result -> result < 0
                ),
                Arguments.of(
                        new AttendanceResult(
                                new Crew("네오"),
                                Map.of(
                                        AttendanceType.LATE, 3,
                                        AttendanceType.ABSENCE, 3
                                ),
                                null
                        ),
                        new AttendanceResult(
                                new Crew("데오"),
                                Map.of(
                                        AttendanceType.LATE, 3,
                                        AttendanceType.ABSENCE, 3
                                ),
                                null
                        ),
                        (Predicate<Integer>) result -> result < 0
                ),
                Arguments.of(
                        new AttendanceResult(
                                new Crew("네오"),
                                Map.of(
                                        AttendanceType.LATE, 3,
                                        AttendanceType.ABSENCE, 3
                                ),
                                null
                        ),
                        new AttendanceResult(
                                new Crew("네오"),
                                Map.of(
                                        AttendanceType.LATE, 3,
                                        AttendanceType.ABSENCE, 3
                                ),
                                null
                        ),
                        (Predicate<Integer>) result -> result == 0
                )
        );
    }
}
