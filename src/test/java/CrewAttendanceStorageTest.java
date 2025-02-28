import exception.CrewNotExistException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class CrewAttendanceStorageTest {
    @DisplayName("새로운 크루의 출석 저장소를 생성할 수 있다.")
    @Test
    void test1() {
        // given
        String crew = "밍곰";
        CrewAttendanceStorage crewAttendanceStorage = CrewAttendanceStorage.init();

        // when & then
        assertDoesNotThrow(() -> {
            crewAttendanceStorage.create(crew);
        });
    }

    @DisplayName("이미 출석 저장소가 존재하는 크루는 저장소를 재생성 할 수 없다.")
    @Test
    void test2() {
        // given
        String crew = "밍곰";
        CrewAttendanceStorage crewAttendanceStorage = CrewAttendanceStorage.init();
        crewAttendanceStorage.create(crew);

        // when & then
        assertThatThrownBy(() -> {
            crewAttendanceStorage.create(crew);
        }).isInstanceOf(RuntimeException.class);
    }

    @DisplayName("등록되지 않은 크루의 출석 저장소를 요청하는 경우 예외가 발생한다.")
    @Test
    void test3() {
        // given
        CrewAttendanceStorage crewAttendanceStorage = CrewAttendanceStorage.init();

        // when & then
        assertThatThrownBy(() -> {
            crewAttendanceStorage.findAttendance("누구", LocalDate.of(2025, 2, 28));
        }).isInstanceOf(CrewNotExistException.class);
    }


    @DisplayName("새로운 출석 기록을 등록할 수 있다.")
    @Test
    void test4() {
        // given
        String crew = "밍곰";
        CrewAttendanceStorage crewAttendanceStorage = CrewAttendanceStorage.of(
                Map.of(crew, new AttendanceStorage())
        );

        // when
        final boolean result = crewAttendanceStorage.register(
                crew, LocalDate.of(2025, 2, 28), LocalTime.of(10, 0)
        );

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("새롭게 등록된 출석 기록을 조회할 수 있다.")
    @Test
    void test5() {
        // given
        String crew = "밍곰";
        CrewAttendanceStorage crewAttendanceStorage = CrewAttendanceStorage.of(
                Map.of(crew, new AttendanceStorage())
        );
        LocalDate date = LocalDate.of(2025, 2, 28);
        LocalTime time = LocalTime.of(10, 0);

        // when
        crewAttendanceStorage.register(crew, date, time);
        Attendance attendance = crewAttendanceStorage.findAttendance(crew, date).get();

        // then
        assertAll(
                () -> assertThat(attendance.isAttendedOn(date)).isTrue(),
                () -> assertThat(attendance.getDateTime()).isEqualTo(LocalDateTime.of(date, time))
        );
    }

    @DisplayName("출석 기록을 수정한 후 이를 조회할 수 있다.")
    @Test
    void test6() {
        // given
        String crew = "밍곰";
        CrewAttendanceStorage crewAttendanceStorage = CrewAttendanceStorage.of(
                Map.of(crew, new AttendanceStorage())
        );
        LocalDate date = LocalDate.of(2025, 2, 28);
        LocalTime time = LocalTime.of(10, 0);
        LocalTime modifiedTime = LocalTime.of(10, 30);

        crewAttendanceStorage.register(crew, date, time);

        // when
        crewAttendanceStorage.modify(crew, date, modifiedTime);
        Optional<Attendance> attendance = crewAttendanceStorage.findAttendance(crew, date);

        // then
        assertAll(
                () -> assertThat(attendance.isPresent()).isTrue(),
                () -> assertThat(attendance.get().getDateTime()).isEqualTo(LocalDateTime.of(date, modifiedTime)),
                () -> assertThat(attendance.get().getStatus()).isSameAs(AttendanceStatus.LATE)
        );
    }
}
