package service;

import constants.DateConstants;
import domain.Crew;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.*;
import service.dto.AttendanceModifyResponse;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class AttendanceModifyServiceTest {
    String name = "빙티";
    Crew crew = new Crew(name);
    AttendanceRepository attendanceRepository;
    AttendanceModifyService attendanceModifyService;

    @BeforeEach
    void setUp() {
        attendanceRepository = new AttendanceRepositoryImpl();
        attendanceModifyService = new AttendanceModifyService(attendanceRepository);

        attendanceRepository.save(crew);
    }

    @DisplayName("수정한다.")
    @Test
    void test() {
        //given
        LocalDateTime before = LocalDateTime.of(
                DateConstants.YEAR,
                DateConstants.MONTH.getValue(),
                19,
                10,
                30
        );
        attendanceRepository.createNewAttendance(name, before.getDayOfMonth(), before.getHour(), before.getMinute());

        //when
        int date = 19;
        int hour = 10;
        int minutes = 0;
        LocalDateTime after = LocalDateTime.of(DateConstants.YEAR, DateConstants.MONTH.getValue(), date, hour, minutes);

        AttendanceModifyResponse response = attendanceModifyService.modify(name, date, hour, minutes);

        //then
        assertThat(response.beforeTime()).isEqualTo(before);
        assertThat(response.beforeStatus()).isEqualTo("지각");
        assertThat(response.afterTime()).isEqualTo(after);
        assertThat(response.afterStatus()).isEqualTo("출석");
    }
}
