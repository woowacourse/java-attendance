import exception.CrewNotExistException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

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
                Map.of(crew, AttendanceStorage.init())
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
                Map.of(crew, AttendanceStorage.init())
        );
        LocalDate date = LocalDate.of(2025, 2, 28);
        LocalTime time = LocalTime.of(10, 0);

        // when
        crewAttendanceStorage.register(crew, date, time);
        Attendance attendance = crewAttendanceStorage.findAttendance(crew, date);

        // then
        assertAll(
                () -> assertThat(attendance.isAttendedOn(date)).isTrue(),
                () -> assertThat(attendance.getTime()).isEqualTo(time)
        );
    }

    @DisplayName("출석 기록을 수정한 후 이를 조회할 수 있다.")
    @Test
    void test6() {
        // given
        String crew = "밍곰";
        CrewAttendanceStorage crewAttendanceStorage = CrewAttendanceStorage.of(
                Map.of(crew, AttendanceStorage.init())
        );
        LocalDate date = LocalDate.of(2025, 2, 28);
        LocalTime time = LocalTime.of(10, 0);
        LocalTime modifiedTime = LocalTime.of(10, 30);

        crewAttendanceStorage.register(crew, date, time);

        // when
        crewAttendanceStorage.modify(crew, date, modifiedTime);
        Attendance attendance = crewAttendanceStorage.findAttendance(crew, date);

        // then
        assertAll(
                () -> assertThat(attendance.isTimeRecorded()).isTrue(),
                () -> assertThat(attendance.getTime()).isEqualTo(modifiedTime),
                () -> assertThat(attendance.getStatus()).isSameAs(AttendanceStatus.LATE)
        );
    }

    @DisplayName("출석 기록이 존재하지 않는 경우 결석 상태의 출석을 반환할 수 있다.")
    @Test
    void test7() {
        // given
        String crew = "밍곰";
        CrewAttendanceStorage crewAttendanceStorage = CrewAttendanceStorage.of(
                Map.of(crew, AttendanceStorage.init())
        );
        LocalDate date = LocalDate.of(2025, 2, 28);

        // when
        Attendance attendance = crewAttendanceStorage.findAttendance(crew, date);

        // then
        assertAll(
                () -> assertThat(attendance.isTimeRecorded()).isFalse(),
                () -> assertThat(attendance.getStatus()).isSameAs(AttendanceStatus.ABSENCE)
        );
    }

    @DisplayName("크루 이름과 날짜를 입력하면 전날까지의 출석 기록 리스트를 반환할 수 있다.")
    @Test
    void test8() {
        // given
        String crew = "밍곰";
        AttendanceStorage attendanceStorage = AttendanceStorage.of(List.of(
                new ExistAttendance(LocalDate.of(2025, 2, 24), LocalTime.of(13, 0)),
                new ExistAttendance(LocalDate.of(2025, 2, 25), LocalTime.of(10, 0)),
                new ExistAttendance(LocalDate.of(2025, 2, 27), LocalTime.of(10, 0)),
                new ExistAttendance(LocalDate.of(2025, 2, 28), LocalTime.of(10, 0))
        ));
        CrewAttendanceStorage crewAttendanceStorage = CrewAttendanceStorage.of(
                Map.of(crew, attendanceStorage)
        );
        LocalDate startDate = LocalDate.of(2025, 2, 24);
        LocalDate endDate = LocalDate.of(2025, 3, 1);

        // when
        List<Attendance> attendance = crewAttendanceStorage.findAttendanceByDateRange(crew, startDate, endDate);

        // then
        assertThat(attendance).isEqualTo(List.of(
                new ExistAttendance(LocalDate.of(2025, 2, 24), LocalTime.of(13, 0)),
                new ExistAttendance(LocalDate.of(2025, 2, 25), LocalTime.of(10, 0)),
                EmptyAttendance.of(LocalDate.of(2025, 2, 26)),
                new ExistAttendance(LocalDate.of(2025, 2, 27), LocalTime.of(10, 0)),
                new ExistAttendance(LocalDate.of(2025, 2, 28), LocalTime.of(10, 0))
        ));
    }

    @DisplayName("크루 이름과 날짜를 입력하면 전날까지의 출석 통계 결과를 반환할 수 있다.")
    @Test
    void test9() {
        // given
        String crew = "밍곰";
        AttendanceStorage attendanceStorage = AttendanceStorage.of(List.of(
                new ExistAttendance(LocalDate.of(2025, 2, 24), LocalTime.of(13, 0)),
                new ExistAttendance(LocalDate.of(2025, 2, 25), LocalTime.of(10, 30)),
                new ExistAttendance(LocalDate.of(2025, 2, 27), LocalTime.of(10, 0)),
                new ExistAttendance(LocalDate.of(2025, 2, 28), LocalTime.of(10, 0))
        )); // 출석 3 지각 1 결석 1
        CrewAttendanceStorage crewAttendanceStorage = CrewAttendanceStorage.of(
                Map.of(crew, attendanceStorage)
        );
        LocalDate startDate = LocalDate.of(2025, 2, 24);
        LocalDate endDate = LocalDate.of(2025, 3, 1);

        // when
        Map<AttendanceStatus, Integer> statistic =
                crewAttendanceStorage.findStatisticByDateRange(crew, startDate, endDate);

        // then
        assertAll(
                () -> assertThat(statistic.get(AttendanceStatus.ATTENDANCE)).isEqualTo(3),
                () -> assertThat(statistic.get(AttendanceStatus.LATE)).isEqualTo(1),
                () -> assertThat(statistic.get(AttendanceStatus.ABSENCE)).isEqualTo(1)
        );
    }
}
