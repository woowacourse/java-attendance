package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.Test;

public class AttendanceResultTest {
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

    @Test
    void 전체_결석_횟수와_지각_횟수가_같다면_이름을_기준으로_비교한다() {
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
                        AttendanceStatus.LATE, 2
                ));

        //when
        int result = attendanceResult1.compareTo(attendanceResult2);

        //then
        assertThat(result > 0).isTrue();
    }
}
