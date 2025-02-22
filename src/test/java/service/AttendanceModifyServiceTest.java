package service;

import domain.AttendanceCustomDate;
import domain.AttendanceStatus;
import domain.Crew;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.*;
import service.dto.AttendanceModifyResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

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

    @DisplayName("기존 출석 기록을 수정한다.")
    @Test
    void test() {
        //given
        LocalDate date = LocalDate.of(AttendanceCustomDate.YEAR, AttendanceCustomDate.MONTH.getValue(), 19);
        LocalTime beforeTime = LocalTime.of(10, 30);
        LocalTime afterTime = LocalTime.of(10, 0);
        attendanceRepository.createNewAttendance(name, date, beforeTime);

        //when
        AttendanceModifyResponse response = attendanceModifyService.modify(name, date, afterTime);

        //then
        assertThat(response.beforeTime()).isEqualTo(Optional.of(beforeTime));
        assertThat(response.beforeStatus()).isEqualTo(AttendanceStatus.LATE.getExpression());
        assertThat(response.afterTime()).isEqualTo(Optional.of(afterTime));
        assertThat(response.afterStatus()).isEqualTo(AttendanceStatus.ATTENDANCE.getExpression());
    }
}
