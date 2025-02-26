package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendancesTest {

    @CsvSource(value = {
            "26,true", "27,false"
    })
    @ParameterizedTest
    void 날짜를_알려주면_출석_기록이_존재하는지_알려준다(int day, boolean expected) {
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, 26, 10, 0));
        Attendances attendances = new Attendances(List.of(attendance));

        assertThat(attendances.hasAttendanceByLocalDate(LocalDate.of(2025, 2, day))).isEqualTo(expected);
    }

    @Test
    void 날짜를_알려주면_출석_기록을_알려준다() {
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, 26, 10, 0));
        Attendances attendances = new Attendances(List.of(attendance));

        assertThat(attendances.findSameDateAttendance(LocalDate.of(2025, 2, 26)))
                .isEqualTo(new Attendance(LocalDateTime.of(2025, 2, 26, 10, 0)));
    }

    @Test
    void 기록이_존재하지_않는_날짜를_알려주면_출석_기록을_조회할_수_없다() {
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, 26, 10, 0));
        Attendances attendances = new Attendances(List.of(attendance));

        assertThatThrownBy(() -> attendances.findSameDateAttendance(LocalDate.of(2025, 2, 25)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 수정_일자를_알려주면_해당_출석_기록을_수정한다() {
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, 26, 10, 0));
        Attendances attendances = new Attendances(new ArrayList<>(List.of(attendance)));
        LocalDateTime modificationDateTime = LocalDateTime.of(2025, 2, 26, 9, 50);

        attendances.modifyByModificationDateTime(modificationDateTime);

        assertThat(attendances.findSameDateAttendance(modificationDateTime.toLocalDate()))
                .isEqualTo(new Attendance(modificationDateTime));
    }

    @Test
    void 존재하지_않는_일자를_알려주면_출석_기록을_수정할_수_없다() {
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, 26, 10, 0));
        Attendances attendances = new Attendances(new ArrayList<>(List.of(attendance)));
        LocalDateTime modificationDateTime = LocalDateTime.of(2025, 2, 27, 9, 50);

        assertThatThrownBy(() -> attendances.modifyByModificationDateTime(modificationDateTime))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 출석을_저장한다() {
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, 26, 10, 0));
        Attendances attendances = new Attendances(new ArrayList<>(List.of(attendance)));
        Attendance addedAttendance = new Attendance(LocalDateTime.of(2025, 2, 27, 9, 50));

        attendances.add(addedAttendance);

        assertThat(attendances.findSameDateAttendance(LocalDate.of(2025, 2, 27)))
                .isEqualTo(new Attendance(LocalDateTime.of(2025, 2, 27, 9, 50)));
    }

    @Test
    void 해당_날짜에_이미_존재하는_출석_기록은_저장할_수_없다() {
        Attendance attendance = new Attendance(LocalDateTime.of(2025, 2, 26, 10, 0));
        Attendances attendances = new Attendances(new ArrayList<>(List.of(attendance)));
        Attendance addedAttendance = new Attendance(LocalDateTime.of(2025, 2, 26, 9, 50));

        assertThatThrownBy(() -> attendances.add(addedAttendance))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
