package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AttendanceCountTest {

    @Test
    void 출결을_계산할_수_있다() {
        //given
        List<Attendance> attendances = List.of(
                new Attendance("pobi", LocalDateTime.of(2024, 12, 5, 10, 1)),
                new Attendance("pobi", LocalDateTime.of(2024, 12, 6, 10, 6)),
                new Attendance("pobi", LocalDateTime.of(2024, 12, 9, 14, 7))
        );
        LocalDate endDate = LocalDate.of(2024, 12, 9);

        //when
        AttendanceCount attendanceCount = AttendanceCount.create("pobi", attendances, new AttendanceDate(endDate));

        //then
        assertThat(attendanceCount).isEqualTo(new AttendanceCount(
                Map.of(
                        AttendanceStatus.ATTENDANCE, 1,
                        AttendanceStatus.LATE, 1,
                        AttendanceStatus.ABSENT, 4
                )
        ));
    }

    @Test
    void 경고_레벨을_알_수_있다() {
        //given
        AttendanceCount attendanceCount = new AttendanceCount(Map.of(
                AttendanceStatus.LATE, 6,
                AttendanceStatus.ABSENT, 4
        ));

        //when
        WarningLevel warningLevel = attendanceCount.getWarningLevel();

        //then
        Assertions.assertThat(warningLevel).isEqualTo(WarningLevel.WEEDING);
    }

    @Test
    void 출결_상태에_따라_비교를_할_수_있다() {
        //given
        AttendanceCount attendanceCount1 = new AttendanceCount(Map.of(
                AttendanceStatus.ABSENT, 4,
                AttendanceStatus.LATE, 6
        ));
        AttendanceCount attendanceCount2 = new AttendanceCount(Map.of(
                AttendanceStatus.ABSENT, 3,
                AttendanceStatus.LATE, 6
        ));

        //when
        int result = attendanceCount1.compareTo(attendanceCount2);

        //then
        assertThat(result).isEqualTo(-1);
    }
}
