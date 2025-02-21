import constants.DateConstants;
import domain.Attendance;
import domain.Crew;
import exception.DuplicateAttendanceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.AttendanceRepository;
import repository.AttendanceRepositoryImpl;
import service.AttendanceCheckService;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

public class AttendanceCheckServiceTest {
    AttendanceRepository attendanceRepository;
    AttendanceCheckService attendanceCheckService;

    @BeforeEach
    void setUp() {
        Crew crew = new Crew("이든");
        attendanceRepository = new AttendanceRepositoryImpl();
        attendanceCheckService = new AttendanceCheckService(attendanceRepository);

        attendanceRepository.save(crew);
    }

    @DisplayName("등교시간을 입력하면 Attendance 객체를 추가할 수 있다.")
    @Test
    void test2() {
        // given
        String name = "이든";
        LocalDateTime time = LocalDateTime.of(
                DateConstants.YEAR,
                DateConstants.MONTH.getValue(),
                18,
                15,
                52
        );
        Attendance original = new Attendance(time);

        // when
        Attendance attendance = attendanceCheckService.register(name, time);

        // then
        assertThat(attendance).isEqualTo(original);
    }

    @DisplayName("이미 출석이 존재하는 경우에는 예외를 발생시킨다.")
    @Test
    void test3() {
        // given
        String name = "이든";
        attendanceRepository.createNewAttendance(name, 18, 15, 52);
        LocalDateTime inputTime = LocalDateTime.of(
                DateConstants.YEAR,
                DateConstants.MONTH.getValue(),
                18,
                16,
                55
        );

        // when & then
        assertThatThrownBy(() -> {
            attendanceCheckService.register(name, inputTime);
        }).isInstanceOf(DuplicateAttendanceException.class);
    }
}
