package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

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
        Attendance actual = attendances.findByCrewAndDate(crew, time.toLocalDate());
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

        Attendances attendances = new Attendances(List.of(attendance1));
        Attendances expected = new Attendances(List.of(attendance1));

        //when
        Attendances filteredAttendances = attendances.createMonthlyAttendances(crew, day);

        //then
        assertThat(filteredAttendances).isEqualTo(expected);
    }

    @Test
    void 출석유형별_횟수를_조회한다() {
        //given
        Crew crew = new Crew("쿠키");
        AttendanceTime attendanceTime1 = new AttendanceTime(LocalDateTime.of(2024, 12, 3, 9, 30));
        AttendanceTime attendanceTime2 = new AttendanceTime(LocalDateTime.of(2024, 12, 4, 10, 6));
        AttendanceTime attendanceTime3 = new AttendanceTime(LocalDateTime.of(2024, 12, 5, 10, 31));
        Attendance attendance1 = Attendance.of(crew, attendanceTime1);
        Attendance attendance2 = Attendance.of(crew, attendanceTime2);
        Attendance attendance3 = Attendance.of(crew, attendanceTime3);

        Attendances attendances = new Attendances(List.of(attendance1, attendance2, attendance3));
        Map<AttendanceType, Integer> expected = Map.of(AttendanceType.SUCCESS, 1, AttendanceType.LATE, 1,
                AttendanceType.ABSENCE, 1);

        //when
        Map<AttendanceType, Integer> actual = attendances.countAttendanceType();

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 기존_출석기록을_삭제하고_새로운_출석기록을_등록한다() {
        //given
        Crew crew = new Crew("쿠키");
        LocalDateTime time = LocalDateTime.of(2024, 12, 2, 9, 30);
        AttendanceTime attendanceTime = new AttendanceTime(time);
        Attendance attendance = Attendance.of(crew, attendanceTime);
        Attendances attendances = new Attendances(new ArrayList<>(List.of(attendance)));

        LocalDateTime newTime = LocalDateTime.of(2024, 12, 2, 10, 0);
        Attendance newAttendance = Attendance.of(crew, new AttendanceTime(newTime));
        Attendances newAttendances = new Attendances(List.of(newAttendance));
        //when
        attendances.modifyAttendanceTime(crew, newTime);
        //then
        assertThat(attendances).isEqualTo(newAttendances);
    }

    @ParameterizedTest
    @CsvSource({
            "10, 31, 9, 30, -1",
            "10, 0, 10, 0, 0",
            "10, 0, 10, 31, 1"
    })
    void 결석횟수로_출석목록을_비교한다(int hour1, int minute1, int hour2, int minute2, int expected) {
        //given
        Crew crew = new Crew("쿠키");
        LocalDateTime time = LocalDateTime.of(2024, 12, 3, hour1, minute1);
        AttendanceTime attendanceTime = new AttendanceTime(time);
        Attendance attendance = Attendance.of(crew, attendanceTime);
        Attendances attendances = new Attendances(new ArrayList<>(List.of(attendance)));

        LocalDateTime time2 = LocalDateTime.of(2024, 12, 3, hour2, minute2);
        AttendanceTime attendanceTime2 = new AttendanceTime(time2);
        Attendance attendance2 = Attendance.of(crew, attendanceTime2);
        Attendances attendances2 = new Attendances(new ArrayList<>(List.of(attendance2)));
        //when
        int difference = attendances.compareWithConvertedAbsenceCount(attendances2);
        //then
        assertThat(difference).isEqualTo(expected);
    }

    @ParameterizedTest
    @MethodSource("createData")
    void 제적위험도가_없는_출석목록과_경고대상_출석목록의_차이는_1이다(Attendances attendances1, Attendances attendances2) {
        //when
        int difference = attendances1.compareWithPenalty(attendances2);
        //then
        assertThat(difference).isEqualTo(1);
    }

    private static Stream<Arguments> createData() {
        Crew crew = new Crew("쿠키");
        LocalDateTime time = LocalDateTime.of(2024, 12, 3, 9, 30);
        AttendanceTime attendanceTime = new AttendanceTime(time);
        Attendance attendance = Attendance.of(crew, attendanceTime);
        Attendances attendances = new Attendances(new ArrayList<>(List.of(attendance)));

        LocalDateTime time2 = LocalDateTime.of(2024, 12, 3, 10, 31);
        AttendanceTime attendanceTime2 = new AttendanceTime(time2);
        LocalDateTime time3 = LocalDateTime.of(2024, 12, 3, 10, 31);
        AttendanceTime attendanceTime3 = new AttendanceTime(time3);
        Attendance attendance2 = Attendance.of(crew, attendanceTime2);
        Attendance attendance3 = Attendance.of(crew, attendanceTime3);
        Attendances attendances2 = new Attendances(new ArrayList<>(List.of(attendance2, attendance3)));
        return Stream.of(
                Arguments.of(attendances, attendances2)
        );
    }
}
