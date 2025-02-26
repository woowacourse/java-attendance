package domain;

import exception.DuplicateAttendanceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CrewAttendancesTest {
    private CrewAttendances crewAttendances = new CrewAttendances();
    private String name = "이든";

    @BeforeEach
    void setUp() {
        Crew crew = new Crew(name);
        crewAttendances = new CrewAttendances();
        crewAttendances.save(crew);
    }

    @DisplayName("등교시간을 입력하면 Attendance 객체를 추가할 수 있다.")
    @Test
    void test2() {
        // given
        String name = "이든";
        LocalDate date = LocalDate.of(AttendanceCustomDate.YEAR, AttendanceCustomDate.MONTH.getValue(), 18);
        LocalTime time = LocalTime.of(15, 52);

        // when
        Attendance attendance = crewAttendances.createNewAttendance(name, date, time);

        // then
        assertThat(attendance.getDate()).isEqualTo(date);
        assertThat(attendance.getTime()).isEqualTo(Optional.of(time));
    }

    @DisplayName("이미 출석이 존재하는 경우에는 예외를 발생시킨다.")
    @Test
    void test3() {
        // given
        String name = "이든";
        LocalDate date = LocalDate.of(AttendanceCustomDate.YEAR, AttendanceCustomDate.MONTH.getValue(), 18);
        LocalTime time = LocalTime.of(16, 55);
        crewAttendances.createNewAttendance(name, date, time);

        // when & then
        assertThatThrownBy(() -> {
            crewAttendances.createNewAttendance(name, date, LocalTime.of(10, 0));
        }).isInstanceOf(DuplicateAttendanceException.class);
    }

    @DisplayName("이미 출석부를 생성한 크루는 재생성할 수 없다.")
    @Test
    void test1() {
        // given

        // when & then
        assertThatThrownBy(() -> crewAttendances.save(new Crew(name)))
                .isInstanceOf(RuntimeException.class);
    }
}
