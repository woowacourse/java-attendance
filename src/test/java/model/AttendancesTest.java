package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AttendancesTest {

    @Test
    void 닉네임과_등교_시간을_입력하면_출석할_수_있다() {
        //given
        Crew crew = Crew.of("쿠키");
        LocalDateTime checkInTime = LocalDateTime.of(2024, 12, 3, 9, 30);
        Attendance attendance = Attendance.of(crew, checkInTime);
        Attendances attendances = Attendances.of(new ArrayList<>());

        //when
        attendances.checkIn(attendance);

        //then
        Assertions.assertThat(attendances.contains(attendance)).isTrue();
    }

    @Test
    void 같은_날에_이미_출석한_경우에는_다시_출석할_수_없다() {
        //given
        Crew crew = Crew.of("쿠키");
        LocalDateTime checkInTime1 = LocalDateTime.of(2024, 12, 3, 9, 30);
        Attendance attendance1 = Attendance.of(crew, checkInTime1);
        Attendances attendances = Attendances.of(List.of(attendance1));

        LocalDateTime checkInTime2 = LocalDateTime.of(2024, 12, 3, 8, 30);
        Attendance attendance2 = Attendance.of(crew, checkInTime2);

        //when & then
        Assertions.assertThatThrownBy(() -> attendances.checkIn(attendance2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 출석한 경우에는 다시 출석할 수 없습니다.");
    }

    @Test
    void 출석_시간을_수정할_수_있다() {
        //given
        Crew crew = Crew.of("쿠키");
        LocalDateTime checkInTime = LocalDateTime.of(2024, 12, 3, 9, 30);
        Attendance attendance = Attendance.of(crew, checkInTime);
        Attendances attendances = Attendances.of(new ArrayList<>());

        attendances.checkIn(attendance);
        LocalDateTime modifiedCheckInTime = LocalDateTime.of(2024, 12, 3, 9, 40);

        //when
        Attendance modifiedAttendance = attendances.modify(crew, modifiedCheckInTime);

        //then
        Assertions.assertThat(modifiedAttendance.getAttendanceType()).isEqualTo(AttendanceType.SUCCESS);
        Assertions.assertThat(modifiedAttendance.getCheckInTime()).isEqualTo(modifiedCheckInTime);
    }

    @Test
    void 출석_시간을_수정할_때_출석이_없으면_새로_생성한다() {
        //given
        Crew crew = Crew.of("쿠키");
        LocalDateTime modifiedCheckInTime = LocalDateTime.of(2024, 12, 3, 10, 0);
        Attendances attendances = Attendances.of(new ArrayList<>());

        //when
        attendances.modify(crew, modifiedCheckInTime);

        //then
        Assertions.assertThat(attendances.getAttendances()).contains(Attendance.of(crew, modifiedCheckInTime));
    }

    @Test
    void 크루_한_명의_이번_달_출석을_조회할_수_있다() {
        //given
        Crew crew = Crew.of("쿠키");
        Attendance attendance1 = Attendance.of(crew, LocalDateTime.of(2024, 12, 3, 9, 30));
        Attendance attendance2 = Attendance.of(crew, LocalDateTime.of(2024, 10, 3, 9, 30));
        Attendances attendances1 = Attendances.of(List.of(attendance1, attendance2));

        Attendance attendance3 = Attendance.createTimeNullAbsence(crew, LocalDate.of(2024, 12, 2));
        Attendances attendances2 = Attendances.of(List.of(attendance3, attendance1));

        //when
        Attendances filteredAttendances = attendances1.findByCrewThisMonth(crew, LocalDate.of(2024, 12, 4));

        //then
        Assertions.assertThat(filteredAttendances).isEqualTo(attendances2);
    }

    @Test
    void 전체_출석_통계를_생성할_수_있다() {
        //given
        Crew crew = Crew.of("쿠키");
        Attendance attendance1 = Attendance.createTimeNullAbsence(crew, LocalDate.of(2024, 10, 2));
        Attendance attendance2 = Attendance.of(crew, LocalDateTime.of(2024, 12, 2, 9, 30));
        Attendance attendance3 = Attendance.of(crew, LocalDateTime.of(2024, 12, 3, 10, 30));
        Attendances attendances = Attendances.of(List.of(attendance1, attendance2, attendance3));
        AttendanceStatistics expected = AttendanceStatistics.of(crew,
                Map.of(AttendanceType.SUCCESS, 1, AttendanceType.BE_LATE, 1, AttendanceType.ABSENCE, 0));

        //when
        AttendanceStatistics actual = attendances.createStatistics(crew, LocalDate.of(2024, 12, 4));

        //then
        Assertions.assertThat(expected).isEqualTo(actual);
    }
}
