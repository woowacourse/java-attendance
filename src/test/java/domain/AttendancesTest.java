package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AttendancesTest {
    @Test
    @DisplayName("해당 이름이 출석부에 존재하면 출석부 반환")
    void findAttendanceByNameTest() {
        //given
        String name = "조로";
        Attendance attendance = Attendance.of(Crew.of(name), CheckInTimes.of(List.of()));
        Attendances attendances = Attendances.of(List.of(attendance));

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
        Attendances attendances = Attendances.of(List.of(attendance));

        //when , then
        assertThatThrownBy(() -> attendances.findAttendanceByName("차니"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }
}