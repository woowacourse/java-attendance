package model;

import converter.StringConverter;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.DataReader;

class AttendancesTest {

    private final StringConverter stringConverter = new StringConverter();
    private Attendances attendances;

    @BeforeEach
    void beforeEach() {
        List<String> rawAttendances = new DataReader().readAttendances("src/test/resources/attendances.csv");
        Crews crews = stringConverter.convertToCrews(rawAttendances);
        attendances = stringConverter.convertToAttendances(rawAttendances, crews);
    }

    @Test
    @DisplayName("닉네임과 등교 시간을 입력하면 출석할 수 있다.")
    void test1() {
        //given
        Crew crew = Crew.of("쿠키");
        LocalDateTime checkInTime = LocalDateTime.of(2024, 12, 3, 9, 35);
        Attendance attendance = Attendance.of(crew, checkInTime);

        //when
        attendances.checkIn(attendance);

        //then
        Assertions.assertThat(attendances.contains(attendance)).isTrue();
    }

    @Test
    @DisplayName("이미 출석한 경우에는 다시 출석할 수 없다.")
    void test2() {
        //given
        Crew crew = Crew.of("쿠키");
        LocalDateTime checkInTime = LocalDateTime.of(2024, 12, 3, 9, 35);
        Attendance attendance = Attendance.of(crew, checkInTime);
        attendances.checkIn(attendance);

        //when & then
        Assertions.assertThatThrownBy(() -> attendances.checkIn(attendance))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 출석한 경우에는 다시 출석할 수 없습니다.");
    }

    @Test
    @DisplayName("출석 시간을 수정한다.")
    void test3() {
        //given
        Crew crew = Crew.of("쿠키");
        LocalDateTime checkInTime = LocalDateTime.of(2025, 2, 27, 10, 31);
        Attendance attendance = Attendance.of(crew, checkInTime);

        attendances.checkIn(attendance);

        LocalDateTime modifiedCheckInTime = LocalDateTime.of(2025, 2, 27, 10, 0);

        //when
        Attendance modifiedAttendance = attendances.modify(crew, modifiedCheckInTime);

        //then
        Assertions.assertThat(modifiedAttendance.getAttendanceType()).isEqualTo(AttendanceType.SUCCESS);
        Assertions.assertThat(modifiedAttendance.getCheckInTime()).isEqualTo(modifiedCheckInTime);
    }

    @Test
    @DisplayName("출석 시간을 수정할 때 출석이 없으면 새로 생성한다.")
    void test4() {
        //given
        Crew crew = Crew.of("쿠키");
        LocalDateTime modifiedCheckInTime = LocalDateTime.of(2024, 12, 3, 10, 0);

        //when
        attendances.modify(crew, modifiedCheckInTime);

        //then
        Assertions.assertThat(attendances.getAttendances()).contains(Attendance.of(crew, modifiedCheckInTime));
    }

    @Test
    @DisplayName("크루의 출석 기록을 조회한다.")
    void test8() {
        //given
        Crew crew = Crew.of("쿠키");

        Attendance attendance1 = Attendance.of(crew, LocalDateTime.of(2025, 2, 17, 10, 0, 0));
        Attendance attendance2 = Attendance.of(crew, LocalDateTime.of(2025, 2, 18, 10, 31, 0));
        Attendance attendance3 = Attendance.of(crew, LocalDateTime.of(2025, 2, 19, 10, 6, 0));

        //when
        Attendances filteredAttendances = attendances.findByCrewAndMonth(crew, 2);

        //then
        Assertions.assertThat(filteredAttendances.getAttendances())
                .contains(attendance1, attendance2, attendance3);
    }
}
