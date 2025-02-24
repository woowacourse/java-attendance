package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class AttendanceTest {
    @Test
    void 출석을_여러개_생성한다() {
        // given
        Map<WorkDate, WorkTime> dateTimes = Map.of(
                new WorkDate(2024, 12, 2), new WorkTime(13, 4),
                new WorkDate(2024, 12, 3), new WorkTime(13, 5)
        );

        // when
        Attendance crewAttendance = new Attendance(dateTimes);

        // then
        assertSoftly(softly -> {
            softly.assertThat(crewAttendance.retrieveDateTimes().size())
                    .isEqualTo(dateTimes.size());
        });
    }

    @Test
    void 출석을_다시_할_수_없다() {
        // given
        Map<WorkDate, WorkTime> dateTimes = Map.of(
                new WorkDate(2024, 12, 2), new WorkTime(13, 4),
                new WorkDate(2024, 12, 3), new WorkTime(13, 5)
        );
        Attendance crewAttendance = new Attendance(dateTimes);

        // when & then
        assertThatThrownBy(() -> crewAttendance.addDateTime(new WorkDateTime(
                new WorkDate(2024, 12, 2),
                new WorkTime(13, 4)
        )))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 날짜의 출석 정보가 이미 존재합니다.");
    }

    @Test
    void 출석을_한다() {
        // given
        Map<WorkDate, WorkTime> dateTimes = Map.of(new WorkDate(2024, 12, 2), new WorkTime(null, null));
        Attendance attendance = new Attendance(dateTimes);

        // when
        attendance.addDateTime(new WorkDateTime(new WorkDate(2024, 12, 2), new WorkTime(13, 4)));

        // then
        assertThat(attendance.retrieveDateTimes().get(0))
                .isEqualTo(new WorkDateTime(new WorkDate(2024, 12, 2), new WorkTime(13, 4)));
    }

    @Test
    void 출석을_수정한다() {
        // given
        Map<WorkDate, WorkTime> dateTimes = Map.of(
                new WorkDate(2024, 12, 2), new WorkTime(13, 4)
        );
        Attendance attendance = new Attendance(dateTimes);

        //given
        attendance.updateDateTime(
                new WorkDateTime(new WorkDate(2024, 12, 2), new WorkTime(10, 5))
        );

        //when
        assertThat(attendance.retrieveDateTimes().getFirst())
                .isEqualTo(new WorkDateTime(new WorkDate(2024, 12, 2), new WorkTime(10, 5)));
    }

    @Test
    void 출석이_없으면_수정하지_못한다() {
        // given
        Map<WorkDate, WorkTime> dateTimes = Map.of(
                new WorkDate(2024, 12, 2), new WorkTime(null, null)
        );
        Attendance attendance = new Attendance(dateTimes);

        // when & then
        assertThatThrownBy(() -> attendance.updateDateTime(
                new WorkDateTime(new WorkDate(2024, 12, 2), new WorkTime(10, 5))))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 날짜의 출석 정보가 없습니다.");
    }

    @Test
    void 출석_정보를_조회한다() {
        // given
        WorkDate workDate = new WorkDate(2024, 12, 2);
        WorkTime workTime = new WorkTime(9, 0);
        Map<WorkDate, WorkTime> dateTimes = Map.of(
                workDate, workTime
        );
        Attendance attendance = new Attendance(dateTimes);

        // when
        WorkDateTime workDateTime = attendance.retrieveDateTime(workDate);

        // then
        assertThat(workDateTime).isEqualTo(new WorkDateTime(workDate, workTime));
    }

    @Test
    void 출석_정보들을_조회한다() {
        // given
        WorkDateTime workDateTime1 = new WorkDateTime(new WorkDate(2024, 12, 2), new WorkTime(9, 0));
        WorkDateTime workDateTime2 = new WorkDateTime(new WorkDate(2024, 12, 3), new WorkTime(10, 0));
        Map<WorkDate, WorkTime> dateTimes = Map.of(
                workDateTime1.getDate(), workDateTime1.getTime(),
                workDateTime2.getDate(), workDateTime2.getTime()
        );
        Attendance attendance = new Attendance(dateTimes);

        // when
        List<WorkDateTime> workDateTimes = attendance.retrieveDateTimes();

        // then
        assertThat(workDateTimes)
                .containsExactlyInAnyOrder(workDateTime1, workDateTime2);
    }

    @Test
    void 출석_상태를_계산한다() {
        // given
        Map<WorkDate, WorkTime> dateTimes = Map.of(
                new WorkDate(2024, 12, 2), new WorkTime(9, 0)
        );
        Attendance attendance = new Attendance(dateTimes);

        // when
        AttendanceStatus status = attendance.calculateAttendanceStatus(new WorkDate(2024, 12, 2));

        // then
        assertThat(status).isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @Test
    void 출석_상태_개수를_계산한다() {
        // given
        Map<WorkDate, WorkTime> dateTimes = Map.of(
                new WorkDate(2024, 12, 2), new WorkTime(13, 5),
                new WorkDate(2024, 12, 3), new WorkTime(10, 6),
                new WorkDate(2024, 12, 4), new WorkTime(10, 31),
                new WorkDate(2024, 12, 5), new WorkTime(null, null)
        );
        Attendance attendance = new Attendance(dateTimes);

        // when
        Map<AttendanceStatus, Integer> statusCount = attendance.calculateAttendanceStatusCount();

        // then
        assertSoftly(softly -> {
            softly.assertThat(statusCount.get(AttendanceStatus.ATTENDANCE)).isEqualTo(1);
            softly.assertThat(statusCount.get(AttendanceStatus.PERCEPTION)).isEqualTo(1);
            softly.assertThat(statusCount.get(AttendanceStatus.ABSENCE)).isEqualTo(2);
        });
    }

    @Test
    void 패널티를_계산한다() {
        // given
        Map<WorkDate, WorkTime> dateTimes = Map.of(
                new WorkDate(2024, 12, 2), new WorkTime(15, 0),
                new WorkDate(2024, 12, 3), new WorkTime(10, 0),
                new WorkDate(2024, 12, 4), new WorkTime(null, null)
        );
        Attendance attendance = new Attendance(dateTimes);

        // when
        Penalty penalty = attendance.calculatePenalty();

        // then
        assertThat(penalty).isEqualTo(Penalty.WARNING);
    }

}
