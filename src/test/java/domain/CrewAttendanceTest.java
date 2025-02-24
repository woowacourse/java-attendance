package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class CrewAttendanceTest {

    @Test
    void 출석을_추가한다() {
        // given
        Crew crew = new Crew("홍길동");
        WorkDateTime workDateTime = new WorkDateTime(new WorkDate(2024, 12, 2), new WorkTime(9, 0));
        Attendance attendance = new Attendance(Map.of(workDateTime.getDate(), new WorkTime(null, null)));
        CrewAttendance crewAttendance = new CrewAttendance(crew, attendance);

        // when
        crewAttendance.addAttendance(workDateTime);

        // then
        assertThat(crewAttendance.retrieveAttendance(workDateTime.getDate())).isEqualTo(workDateTime);
    }

    @Test
    void 출석을_수정한다() {
        // given
        Crew crew = new Crew("홍길동");
        WorkDateTime originalWorkDateTime = new WorkDateTime(new WorkDate(2024, 12, 2), new WorkTime(9, 0));
        WorkDateTime updatedWorkDateTime = new WorkDateTime(originalWorkDateTime.getDate(), new WorkTime(10, 5));

        Attendance attendance = new Attendance(Map.of(originalWorkDateTime.getDate(), originalWorkDateTime.getTime()));
        CrewAttendance crewAttendance = new CrewAttendance(crew, attendance);

        // when
        crewAttendance.updateAttendance(updatedWorkDateTime);

        // then
        assertThat(crewAttendance.retrieveAttendance(updatedWorkDateTime.getDate()))
                .isEqualTo(updatedWorkDateTime);
    }

    @Test
    void 출석_수정시_출석이_없으면_예외가_발생한다() {
        // given
        Crew crew = new Crew("홍길동");
        WorkDateTime originalWorkDateTime = new WorkDateTime(new WorkDate(2024, 12, 2), new WorkTime(null, null));
        WorkDateTime updateWorkDateTime = new WorkDateTime(originalWorkDateTime.getDate(), new WorkTime(10, 5));
        Attendance attendance = new Attendance(Map.of(originalWorkDateTime.getDate(), originalWorkDateTime.getTime()));
        CrewAttendance crewAttendance = new CrewAttendance(crew, attendance);

        // when & then
        assertThatThrownBy(() -> crewAttendance.updateAttendance(updateWorkDateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 날짜의 출석 정보가 없습니다.");
    }

    @Test
    void 특정_날짜의_출석_정보를_조회한다() {
        // given
        Crew crew = new Crew("홍길동");
        WorkDateTime workDateTime = new WorkDateTime(new WorkDate(2024, 12, 2), new WorkTime(9, 0));
        Attendance attendance = new Attendance(Map.of(workDateTime.getDate(), workDateTime.getTime()));
        CrewAttendance crewAttendance = new CrewAttendance(crew, attendance);

        // when
        WorkDateTime retrievedWorkDateTime = crewAttendance.retrieveAttendance(workDateTime.getDate());

        // then
        assertThat(retrievedWorkDateTime).isEqualTo(workDateTime);
    }

    @Test
    void 출석_정보들을_날짜순으로_조회한다() {
        // given
        Crew crew = new Crew("홍길동");
        WorkDateTime workDateTime1 = new WorkDateTime(new WorkDate(2024, 12, 2), new WorkTime(9, 0));
        WorkDateTime workDateTime2 = new WorkDateTime(new WorkDate(2024, 12, 3), new WorkTime(10, 0));

        Attendance attendance = new Attendance(Map.of(
                workDateTime1.getDate(), workDateTime1.getTime(),
                workDateTime2.getDate(), workDateTime2.getTime()
        ));
        CrewAttendance crewAttendance = new CrewAttendance(crew, attendance);

        // when
        List<WorkDateTime> workDateTimes = crewAttendance.retrieveAttendanceOrderByDate();

        // then
        assertThat(workDateTimes).containsExactly(workDateTime1, workDateTime2);
    }

    @Test
    void 특정_날짜의_출석_상태를_계산한다() {
        // given
        Crew crew = new Crew("홍길동");
        WorkDateTime workDateTime = new WorkDateTime(new WorkDate(2024, 12, 2), new WorkTime(9, 0));
        Attendance attendance = new Attendance(Map.of(workDateTime.getDate(), workDateTime.getTime()));
        CrewAttendance crewAttendance = new CrewAttendance(crew, attendance);

        // when
        AttendanceStatus status = crewAttendance.calculateAttendanceStatus(workDateTime.getDate());

        // then
        assertThat(status).isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @Test
    void 출석_상태_개수를_계산한다() {
        // given
        Crew crew = new Crew("홍길동");
        WorkDateTime workDateTime1 = new WorkDateTime(new WorkDate(2024, 12, 2), new WorkTime(13, 5));
        WorkDateTime workDateTime2 = new WorkDateTime(new WorkDate(2024, 12, 3), new WorkTime(10, 6));
        WorkDateTime workDateTime3 = new WorkDateTime(new WorkDate(2024, 12, 4), new WorkTime(10, 31));
        WorkDateTime workDateTime4 = new WorkDateTime(new WorkDate(2024, 12, 5), new WorkTime(null, null));

        Attendance attendance = new Attendance(Map.of(
                workDateTime1.getDate(), workDateTime1.getTime(),
                workDateTime2.getDate(), workDateTime2.getTime(),
                workDateTime3.getDate(), workDateTime3.getTime(),
                workDateTime4.getDate(), workDateTime4.getTime()
        ));
        CrewAttendance crewAttendance = new CrewAttendance(crew, attendance);

        // when
        Map<AttendanceStatus, Integer> statusCount = crewAttendance.calculateAttendanceStatusCount();

        // then
        assertSoftly(softly -> {
            softly.assertThat(statusCount.get(AttendanceStatus.ATTENDANCE)).isEqualTo(1);
            softly.assertThat(statusCount.get(AttendanceStatus.PERCEPTION)).isEqualTo(1);
            softly.assertThat(statusCount.get(AttendanceStatus.ABSENCE)).isEqualTo(2);
        });
    }

    @Test
    void 패널티가_있는지_확인한다() {
        // given
        Crew crew = new Crew("홍길동");
        WorkDateTime workDateTime1 = new WorkDateTime(new WorkDate(2024, 12, 2), new WorkTime(15, 0));
        WorkDateTime workDateTime2 = new WorkDateTime(new WorkDate(2024, 12, 3), new WorkTime(10, 0));
        WorkDateTime workDateTime3 = new WorkDateTime(new WorkDate(2024, 12, 4), new WorkTime(null, null));

        Attendance attendance = new Attendance(Map.of(
                workDateTime1.getDate(), workDateTime1.getTime(),
                workDateTime2.getDate(), workDateTime2.getTime(),
                workDateTime3.getDate(), workDateTime3.getTime()
        ));
        CrewAttendance crewAttendance = new CrewAttendance(crew, attendance);

        // when
        boolean hasPenalty = crewAttendance.isPenalty();

        // then
        assertThat(hasPenalty).isTrue();
    }

    @Test
    void 패널티를_계산한다() {
        // given
        Crew crew = new Crew("홍길동");
        WorkDateTime workDateTime1 = new WorkDateTime(new WorkDate(2024, 12, 2), new WorkTime(15, 0));
        WorkDateTime workDateTime2 = new WorkDateTime(new WorkDate(2024, 12, 3), new WorkTime(10, 0));
        WorkDateTime workDateTime3 = new WorkDateTime(new WorkDate(2024, 12, 4), new WorkTime(null, null));

        Attendance attendance = new Attendance(Map.of(
                workDateTime1.getDate(), workDateTime1.getTime(),
                workDateTime2.getDate(), workDateTime2.getTime(),
                workDateTime3.getDate(), workDateTime3.getTime()
        ));
        CrewAttendance crewAttendance = new CrewAttendance(crew, attendance);
        
        // when
        Penalty penalty = crewAttendance.calculatePenalty();

        // then
        assertThat(penalty).isEqualTo(Penalty.WARNING);
    }
}
