package service;

import domain.Attendance;
import domain.Crew;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.*;
import service.dto.AttendanceModifyResponse;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AttendanceModifyServiceTest {
    int nowMonth = LocalDateTime.now().getMonthValue();

    class FakeAttendanceModifyRepository extends FakeAttendanceRepository {
        @Override
        public Optional<Attendance> findByCrewAndDate(Crew crew, int date) {
            int month = nowMonth;
            return findByCrew(crew).stream()
                    .filter(attendance ->
                            attendance.getTime().getMonthValue() == month && attendance.getTime().getDayOfMonth() == date
                    )
                    .findFirst();
        }
    }
    String name = "빙티";
    Crew crew = new Crew(name);
    CrewRepository crewRepository;
    AttendanceRepository attendanceRepository;
    AttendanceModifyService attendanceModifyService;

    @BeforeEach
    void setUp() {
        crewRepository = new CrewRepositoryImpl();
        crewRepository.save(crew);
        attendanceRepository = new FakeAttendanceModifyRepository();
        attendanceModifyService = new AttendanceModifyService(crewRepository, attendanceRepository);
    }

    @DisplayName("수정한다.")
    @Test
    void test() {
        //given
        LocalDateTime before = LocalDateTime.of(2025, nowMonth, 19, 10, 30);
        attendanceRepository.save(new Attendance(crew, before));

        //when
        int date = 19;
        int hour = 10;
        int minutes = 0;
        LocalDateTime after = LocalDateTime.of(2025, nowMonth, date, hour, minutes);

        AttendanceModifyResponse response = attendanceModifyService.modify(name, date, hour, minutes);

        //then
        assertThat(response.beforeTime()).isEqualTo(before);
        assertThat(response.beforeStatus()).isEqualTo("지각");
        assertThat(response.afterTime()).isEqualTo(after);
        assertThat(response.afterStatus()).isEqualTo("출석");
    }

}
