import domain.AttendanceCustomDate;
import domain.Crew;
import exception.DuplicateAttendanceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.AttendanceRepository;
import repository.AttendanceRepositoryImpl;
import service.AttendanceCheckService;
import service.dto.AttendanceRegisterResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class AttendanceCheckServiceTest {
    AttendanceRepository attendanceRepository;
    AttendanceCheckService attendanceCheckService;

    @BeforeEach
    void setUp() {
        LocalDateTime now = AttendanceCustomDate.now();

        Crew crew = new Crew("이든");
        attendanceRepository = new AttendanceRepositoryImpl();
        attendanceCheckService = new AttendanceCheckService(attendanceRepository);

        attendanceRepository.save(crew, now.getYear(), now.getMonthValue());
    }

    @DisplayName("등교시간을 입력하면 Attendance 객체를 추가할 수 있다.")
    @Test
    void test2() {
        // given
        String name = "이든";
        LocalDate date = LocalDate.of(AttendanceCustomDate.YEAR, AttendanceCustomDate.MONTH.getValue(), 18);
        LocalTime time = LocalTime.of(15, 52);

        // when
        AttendanceRegisterResponse response = attendanceCheckService.register(name, date, time);

        // then
        assertThat(response.date()).isEqualTo(date);
        assertThat(response.time()).isEqualTo(Optional.of(time));
    }

    @DisplayName("이미 출석이 존재하는 경우에는 예외를 발생시킨다.")
    @Test
    void test3() {
        // given
        String name = "이든";
        LocalDate date = LocalDate.of(AttendanceCustomDate.YEAR, AttendanceCustomDate.MONTH.getValue(), 18);
        LocalTime time = LocalTime.of(16, 55);
        attendanceRepository.createNewAttendance(name, date, time);

        // when & then
        assertThatThrownBy(() -> {
            attendanceCheckService.register(name, date, LocalTime.of(10, 0));
        }).isInstanceOf(DuplicateAttendanceException.class);
    }
}
