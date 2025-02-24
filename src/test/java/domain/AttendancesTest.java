package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AttendancesTest {
    @Test
    @DisplayName("해당 이름이 출석부에 존재하면 출석부 반환")
    void findAttendanceByNameTest() {
        //given
        String name = "조로";
        Attendance attendance = Attendance.of(Crew.of(name), CheckInTimes.of(List.of()));
        Attendances attendances = Attendances.of(Map.of(Crew.of(name), attendance));

        //when
        Attendance found = attendances.findAttendanceByName("조로");

        //then
        assertThat(found).isEqualTo(attendance);
    }

    @Test
    @DisplayName("해당 이름이 출석부에 존재하지 않으면 예외 발생")
    void findAttendanceByNonExistingNameTest() {
        //given
        String name = "조로";
        Attendance attendance = Attendance.of(Crew.of(name), CheckInTimes.of(List.of()));
        Attendances attendances = Attendances.of(Map.of(Crew.of(name), attendance));

        //when , then
        assertThatThrownBy(() -> attendances.findAttendanceByName("차니"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("제적 위험자 리스트 반환")
    void findDangerCrewsTest() {
        //given
        Crew crew1 = Crew.of("경고");
        Crew crew2 = Crew.of("면담");
        Crew crew3 = Crew.of("정상");

        CheckInTimes checkInTimes1 = CheckInTimes.of(List.of());
        CheckInTimes checkInTimes2 = CheckInTimes.of(List.of());
        CheckInTimes checkInTimes3 = CheckInTimes.of(List.of());

        // 2 결석
        Attendance attendance1 = Attendance.of(crew1, checkInTimes1);
        attendance1.checkIn(LocalDateTime.of(2024, 12, 3, 15, 30));
        attendance1.checkIn(LocalDateTime.of(2024, 12, 4, 16, 30));
        // 3 결석
        Attendance attendance2 = Attendance.of(crew2, checkInTimes2);
        attendance2.checkIn(LocalDateTime.of(2024, 12, 3, 15, 30));
        attendance2.checkIn(LocalDateTime.of(2024, 12, 4, 16, 30));
        attendance2.checkIn(LocalDateTime.of(2024, 12, 5, 16, 30));
        // 0 지각, 0 결석
        Attendance attendance3 = Attendance.of(crew3, checkInTimes3);
        attendance3.checkIn(LocalDateTime.of(2024, 12, 3, 9, 30));

        Attendances attendances = Attendances.of(Map.of(
                crew1, attendance1,
                crew2, attendance2,
                crew3, attendance3
        ));

        //when
        List<Attendance> dangerCrews = attendances.findDangerCrews();

        //then
        // assertThat(dangerCrews).hasSize(3);
        assertTrue(dangerCrews.contains(attendance1));
        assertTrue(dangerCrews.contains(attendance2));
        assertTrue(dangerCrews.contains(attendance3));
        //assertFalse(dangerCrews.contains(attendance2));
    }
}