import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Attendance;
import domain.AttendanceTime;
import domain.Attendances;
import domain.Crew;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class AttendancesTest {

    @Test
    void 출석목록에_출석기록을_추가한다() {
        //given
        Crew crew = new Crew("쿠키");
        AttendanceTime time = new AttendanceTime(LocalDateTime.of(2024, 12, 2, 9, 30));
        Attendance attendance = Attendance.of(crew, time);
        Attendances attendances = new Attendances(new ArrayList<>());
        Attendances expected = new Attendances(List.of(attendance));
        //when
        attendances.add(attendance);
        //then
        assertThat(expected).isEqualTo(attendances);
    }

    @Test
    void 출석기록을_추가할_때_오늘의_출석이_존재하면_예외를_발생시킨다() {
        //given
        Crew crew = new Crew("쿠키");
        AttendanceTime time = new AttendanceTime(LocalDateTime.of(2024, 12, 2, 9, 30));
        Attendance attendance = Attendance.of(crew, time);
        Attendances expected = new Attendances(List.of(attendance));
        //when & then
        assertThatThrownBy(() -> expected.add(attendance))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석이 이미 존재합니다.");
    }

    @Test
    void 크루와_날짜로_출석기록을_조회한다() {
        //given
        Crew crew = new Crew("쿠키");
        LocalDateTime time = LocalDateTime.of(2024, 12, 2, 9, 30);
        AttendanceTime attendanceTime = new AttendanceTime(time);
        Attendance attendance = Attendance.of(crew, attendanceTime);
        Attendances attendances = new Attendances(List.of(attendance));

        Attendance expected = Attendance.of(crew, attendanceTime);
        //when
        Attendance actual = attendances.findByCrewAndDate(crew, time.toLocalDate()).get();
        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 특정_크루의_이번달_출석목록을_조회한다() {
        //given
        Crew crew = new Crew("쿠키");
        LocalDateTime time = LocalDateTime.of(2024, 12, 2, 9, 30);
        AttendanceTime attendanceTime = new AttendanceTime(time);
        Attendance attendance1 = Attendance.of(crew, attendanceTime);
        LocalDate day = LocalDate.of(2024, 12, 3);
        Attendance attendance2 = Attendance.createAbsence(crew, day);

        Attendances attendances = new Attendances(List.of(attendance1));
        Attendances expected = new Attendances(List.of(attendance1, attendance2));

        //when
        Attendances filteredAttendances = attendances.createMonthlyAttendances(crew, day);

        //then
        assertThat(filteredAttendances).isEqualTo(expected);
    }
}
