package service;

import constants.DateConstants;
import domain.AttendanceCustomDate;
import domain.AttendanceStatus;
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
        LocalDateTime now = AttendanceCustomDate.now();

        attendanceRepository = new AttendanceRepositoryImpl();
        attendanceModifyService = new AttendanceModifyService(attendanceRepository);

        attendanceRepository.save(crew, now.getYear(), now.getMonthValue());
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
        assertThat(response.beforeStatus()).isSameAs(AttendanceStatus.LATE);
        assertThat(response.afterTime()).isEqualTo(after);
        assertThat(response.afterStatus()).isSameAs(AttendanceStatus.ATTENDANCE);
    }
}
