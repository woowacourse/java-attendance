package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import attendance.domain.AttendanceManager;
import attendance.repository.AttendanceFileRepository;
import attendance.service.AttendanceManagerService;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceModifyTest {

    @AfterEach
    void afterTest() {
        AttendanceManager.initiateInstance();
    }

    private static Stream<Arguments> attendanceTest() {
        return Stream.of(
                Arguments.arguments(
                        "/testModify.csv",
                        "투다",
                        LocalDate.of(2024, 12, 13),
                        LocalTime.of(10, 31),
                        "-> 10:31 (결석) 수정 완료!"
                ),
                Arguments.arguments(
                        "/testModify.csv",
                        "체체",
                        LocalDate.of(2024, 12, 13),
                        LocalTime.of(10, 15),
                        "-> 10:15 (지각) 수정 완료!"
                ),
                Arguments.arguments(
                        "/testModify.csv",
                        "꾹이",
                        LocalDate.of(2024, 12, 13),
                        LocalTime.of(10, 15),
                        "-> 10:15 (지각) 수정 완료!"
                ),
                Arguments.arguments(
                        "/testModify.csv",
                        "비타",
                        LocalDate.of(2024, 12, 13),
                        LocalTime.of(10, 4),
                        "-> 10:04 (출석) 수정 완료!"
                ),
                Arguments.arguments(
                        "/testModify.csv",
                        "듀이",
                        LocalDate.of(2024, 12, 13),
                        LocalTime.of(10, 41),
                        "-> 10:41 (결석) 수정 완료!"

                )
        );
    }

    @ParameterizedTest
    @MethodSource("attendanceTest")
    @DisplayName("출석 데이터 수정 테스트")
    void testAttendances(String src, String name, LocalDate modifyDate, LocalTime afterModifyTime,
                         String formattedResult) {
        AttendanceManagerService attendanceManagerService = new AttendanceManagerService(
                AttendanceManager.getInstance(), new AttendanceFileRepository(src));
        assertThat(
                attendanceManagerService.attendanceModify(name, modifyDate, afterModifyTime)
        ).contains(formattedResult);
    }
}
