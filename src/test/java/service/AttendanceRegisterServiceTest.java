package service;

import domain.attendance.Attendance;
import domain.crew.Crew;
import domain.date.CustomDate;
import exception.DuplicateAttendanceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.AttendanceRepository;
import repository.AttendanceRepositoryImpl;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

public class AttendanceRegisterServiceTest {
    AttendanceRepository attendanceRepository;
    AttendanceRegisterService attendanceCheckService;

    @BeforeEach
    void setUp() {
        Crew crew = new Crew("이든");
        attendanceRepository = new AttendanceRepositoryImpl();
        attendanceCheckService = new AttendanceRegisterService(attendanceRepository);

        attendanceRepository.save(crew);
    }

//    @DisplayName("닉네임을 입력하면 올바른 크루 객체를 반환할 수 있다.")
//    @Test
//    void test() {
//        // given
//        String name = "이든";
//        Crew crew = new Crew(name);
//
//        // when
//        Crew found = attendanceCheckService.findCrew(name);
//
//        // then
//        assertThat(found).isEqualTo(crew);
//    }

    @DisplayName("등교시간을 입력하면 Attendance 객체를 추가할 수 있다.")
    @Test
    void test2() {
        // given
        String name = "이든";
        LocalDateTime time = LocalDateTime.of(
                CustomDate.YEAR,
                CustomDate.MONTH.getValue(),
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
                CustomDate.YEAR,
                CustomDate.MONTH.getValue(),
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
