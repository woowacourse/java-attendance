package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class AttendanceResultTest {

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
        AttendanceResult attendanceResult = AttendanceResult.create("pobi", attendances, new AttendanceDate(endDate));

        //then
        assertThat(attendanceResult).isEqualTo(new AttendanceResult(
                "pobi",
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
        AttendanceResult attendanceResult = new AttendanceResult(
                "pobi",
                Map.of(
                        AttendanceStatus.LATE, 6,
                        AttendanceStatus.ABSENT, 4
                ));

        //when
        WarningLevel warningLevel = attendanceResult.getWarningLevel();

        //then
        assertThat(warningLevel).isEqualTo(WarningLevel.WEEDING);
    }

    @Test
    void 전체_결석_횟수가_더_많다면_더_크다() {
        //given
        AttendanceResult attendanceResult1 = new AttendanceResult(
                "pobi",
                Map.of(
                        AttendanceStatus.ABSENT, 4,
                        AttendanceStatus.LATE, 6
                ));
        AttendanceResult attendanceResult2 = new AttendanceResult(
                "neo",
                Map.of(
                        AttendanceStatus.ABSENT, 4,
                        AttendanceStatus.LATE, 3
                ));

        //when
        int result = attendanceResult1.compareTo(attendanceResult2);

        //then
        assertThat(result).isEqualTo(-1);
    }

    @Test
    void 전체_결석_횟수가_같다면_지각_횟수로_비교한다() {
        //given
        AttendanceResult attendanceResult1 = new AttendanceResult(
                "pobi",
                Map.of(
                        AttendanceStatus.ABSENT, 4,
                        AttendanceStatus.LATE, 2
                ));
        AttendanceResult attendanceResult2 = new AttendanceResult(
                "neo",
                Map.of(
                        AttendanceStatus.ABSENT, 4,
                        AttendanceStatus.LATE, 1
                ));

        //when
        int result = attendanceResult1.compareTo(attendanceResult2);

        //then
        assertThat(result).isEqualTo(-1);
    }
}
