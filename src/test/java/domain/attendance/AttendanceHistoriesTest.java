package domain.attendance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.crew.Crew;
import domain.date.AttendanceDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceHistoriesTest {

    private final AttendanceHistories attendanceHistories = new AttendanceHistories();

    @Test
    @DisplayName("같은 정보를 가진 출석 기록을 정상적으로 조회해온다")
    void testFindByCrewAndDay() {
        // given
        Crew crew = Crew.of("히로");
        int day = 2;

        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(day, 10, 0);
        attendanceHistories.add(crew, attendanceDateTime);

        // when
        AttendanceHistory foundHistory = attendanceHistories.findByCrewAndDay(crew, day);

        // then
        assertAll(
                () -> assertThat(foundHistory).extracting("attendanceDateTime")
                        .extracting("day")
                        .isEqualTo(day),
                () -> assertThat(foundHistory).extracting("attendanceDateTime")
                        .extracting("hour")
                        .isEqualTo(10),
                () -> assertThat(foundHistory).extracting("attendanceDateTime")
                        .extracting("minute")
                        .isEqualTo(0)
        );

    }

    @Test
    @DisplayName("출석 기록이 수정된다")
    void testUpdateAttendanceHistory() {
        // given
        Crew crew = Crew.of("히로");
        int day = 2;

        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(day, 10, 0);
        attendanceHistories.add(crew, attendanceDateTime);

        AttendanceDateTime updatedAttendanceDateTime = AttendanceDateTime.of(day, 10, 6);

        AttendanceHistory beforeHistory = attendanceHistories.findByCrewAndDay(crew, day);

        // when
        attendanceHistories.update(beforeHistory, AttendanceHistory.of(crew, updatedAttendanceDateTime));

        // then
        AttendanceHistory result = attendanceHistories.findByCrewAndDay(crew, day);
        assertThat(result).extracting("attendanceDateTime")
                .extracting("minute")
                .isEqualTo(6);
    }

    @Test
    @DisplayName("이전 날짜의 출석 기록만 가져온다")
    void testFindHistoriesBefore() {
        // given
        Crew crew = Crew.of("히로");

        List<AttendanceDateTime> attendanceDateTimes = List.of(
                AttendanceDateTime.of(1, 10, 0),
                AttendanceDateTime.of(2, 10, 0),
                AttendanceDateTime.of(3, 10, 0),
                AttendanceDateTime.of(7, 10, 0)
        );
        attendanceDateTimes.forEach(attendanceDateTime -> attendanceHistories.add(crew, attendanceDateTime));

        // when
        List<AttendanceHistory> beforeHistories = attendanceHistories.findHistoriesBefore(crew, 4);

        // then
        assertThat(beforeHistories).extracting("day").containsExactly(1, 2, 3);
    }

}
