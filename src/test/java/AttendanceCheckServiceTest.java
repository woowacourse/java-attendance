import domain.Attendance;
import domain.Crew;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.AttendanceRepository;
import repository.CrewRepository;
import repository.CrewRepositoryImpl;
import service.AttendanceCheckService;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AttendanceCheckServiceTest {
    CrewRepository crewRepository = new CrewRepositoryImpl();
    AttendanceRepository attendanceRepository = new AttendanceRepository();
    AttendanceCheckService attendanceCheckService = new AttendanceCheckService(crewRepository, attendanceRepository);

    @DisplayName("닉네임을 입력하면 올바른 크루 객체를 반환할 수 있다.")
    @Test
    void test() {
        // given
        String name = "이든";
        crewRepository.save(new Crew(name));

        // when
        Crew crew = attendanceCheckService.findCrew(name);

        // then
        assertThat(crew.getName()).isEqualTo(name);
    }

    @DisplayName("등교시간을 입력하면 Attendance 객체를 추가할 수 있다.")
    @Test
    void test2() {
        // given
        Crew crew = new Crew("이든");
        LocalDateTime time = LocalDateTime.of(2025, 2, 18, 15, 52);
        Attendance original = new Attendance(crew, time);

        // when
        Attendance attendance = attendanceCheckService.register(crew, time);

        // then
        assertThat(attendance).isEqualTo(original);
    }

    @DisplayName("이미 출석이 존재하는 경우에는 예외를 발생시킨다.")
    @Test
    void test3() {
        // given
        Crew crew = new Crew("이든");
        LocalDateTime time = LocalDateTime.of(2025, 2, 18, 15, 52);
        Attendance original = new Attendance(crew, time);
        attendanceRepository.save(original);

        // when & then
        assertThatThrownBy(() -> {
            attendanceCheckService.register(crew, time);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
