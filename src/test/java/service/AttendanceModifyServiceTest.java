package service;

import domain.Attendance;
import domain.Crew;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.AttendanceRepository;
import repository.CrewRepository;
import repository.CrewRepositoryImpl;
import service.dto.AttendanceModifyResponse;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AttendanceModifyServiceTest {
    CrewRepository crewRepository;
    AttendanceRepository attendanceRepository;
    AttendanceModifyService attendanceModifyService;

    @BeforeEach
    void setUp() {
        crewRepository = new CrewRepositoryImpl();
        crewRepository.save(new Crew("이든"));
        attendanceRepository = new AttendanceRepository();
        attendanceModifyService = new AttendanceModifyService(crewRepository, attendanceRepository);
    }

    @DisplayName("수정한다.")
    @Test
    void test() {
        //given
        String name = "빙티";
        Crew crew = new Crew(name);
        LocalDateTime before = LocalDateTime.of(2025, 2, 19, 10, 30);
        crewRepository.save(crew);
        attendanceRepository.save(new Attendance(crew, before));

        //when
        int date = 19;
        int hour = 10;
        int minutes = 0;
        LocalDateTime after = LocalDateTime.of(2025, 2, 19, 10, 0);

        AttendanceModifyResponse response = attendanceModifyService.modify(name, date, hour, minutes);

        //then
        assertThat(response.beforeTime()).isEqualTo(before);
        assertThat(response.beforeStatus()).isEqualTo("지각");
        assertThat(response.afterTime()).isEqualTo(after);
        assertThat(response.beforeStatus()).isEqualTo("출석");
    }

}
