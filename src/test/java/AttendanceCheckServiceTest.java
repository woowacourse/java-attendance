import domain.Attendance;
import domain.Crew;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.AttendanceRepository;
import repository.AttendanceRepositoryImpl;
import repository.CrewRepository;
import repository.CrewRepositoryImpl;
import service.AttendanceCheckService;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

public class AttendanceCheckServiceTest {
    CrewRepository crewRepository;
    AttendanceRepository attendanceRepository;
    AttendanceCheckService attendanceCheckService;

    @BeforeEach
    void setUp() {
        crewRepository = new CrewRepositoryImpl();
        crewRepository.save(new Crew("이든"));
        attendanceRepository = new AttendanceRepositoryImpl();
        attendanceCheckService = new AttendanceCheckService(crewRepository, attendanceRepository);
    }

    @DisplayName("닉네임을 입력하면 올바른 크루 객체를 반환할 수 있다.")
    @Test
    void test() {
        // given
        String name = "이든";
        Crew crew = new Crew(name);
        crewRepository.save(crew);

        // when
        Crew found = attendanceCheckService.findCrew(name);

        // then
        assertThat(found).isEqualTo(crew);
    }

    @DisplayName("등교시간을 입력하면 Attendance 객체를 추가할 수 있다.")
    @Test
    void test2() {
        // given
        String name = "이든";
        LocalDateTime time = LocalDateTime.of(2025, 2, 18, 15, 52);
        Attendance original = new Attendance(new Crew(name), time);

        // when
        Attendance attendance = attendanceCheckService.register(name,time);

        // then
        assertThat(attendance).isEqualTo(original);
    }

    @DisplayName("이미 출석이 존재하는 경우에는 예외를 발생시킨다.")
    @Test
    void test3() {
        // given
        String name = "이든";
        LocalDateTime time = LocalDateTime.of(2025, 2, 18, 15, 52);
        Attendance original = new Attendance(new Crew(name), time);
        attendanceRepository.save(original);
        LocalDateTime inputTime = LocalDateTime.of(2025, 2, 18, 16, 55);

        // when & then
        assertThatThrownBy(() -> {
            attendanceCheckService.register(name, inputTime);
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("이미 출석한 날짜입니다.");
    }
}
