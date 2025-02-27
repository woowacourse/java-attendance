package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AttendanceTest {
    private AttendanceRepository repository;

    @BeforeEach
    void setUp() {
        repository = new AttendanceRepository();
    }

    @Test
    void 닉네임과_등교_시간을_입력시_출석된다() {
        // given
        String name = "fora";
        LocalDate localDate = LocalDate.of(2024, 12, 3);
        LocalTime localTime = LocalTime.of(9, 55);

        // when
        Attendance attendance = new Attendance(name, localDate, localTime);

        // then
        Assertions.assertThat(attendance.getName()).isEqualTo(name);
        Assertions.assertThat(attendance.getLocalTime()).isEqualTo(localTime);
    }

    @Test
    void 출석을_저장한다() {
        // given
        String name = "fora";
        LocalDate localDate = LocalDate.of(2024, 12, 3);
        LocalTime localTime = LocalTime.of(9, 55);

        // when
        repository.checkIn(name, localDate, localTime);
        Attendance attendance = repository.getAttendance(name, localDate);

        // then
        Assertions.assertThat(attendance.getName().equals(name));
        Assertions.assertThat(attendance.getLocalTime().equals(localTime));
    }

    @Test
    void 이미_출석한_경우_다시_출석할_수_없다() {
        // given
        String name = "fora";
        LocalDate localDate = LocalDate.of(2024, 12, 3);
        LocalTime firstTime = LocalTime.of(9, 55);
        LocalTime secondTime = LocalTime.of(10, 3);

        // when
//        repository.checkIn(name, localDate, firstTime);
//        repository.checkIn(name, localDate, secondTime);

        // then
        Assertions.assertThatThrownBy(() -> {
            repository.checkIn(name, localDate, firstTime);
            repository.checkIn(name, localDate, secondTime);
        }).isInstanceOf(IllegalArgumentException.class);

    }
}
